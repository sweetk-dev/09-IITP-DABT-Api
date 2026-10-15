package com.sweetk.iitp.api.service.basic;

import com.sweetk.iitp.api.dto.basic.local.GgDisRegEdition;
import com.sweetk.iitp.api.dto.basic.local.GgDisRegEmd;
import com.sweetk.iitp.api.dto.basic.local.GgDisRegEmdSearchReq;
import com.sweetk.iitp.api.dto.basic.local.LocalDisRegSummary;
import com.sweetk.iitp.api.dto.basic.local.LocalDisRegSummary.Part;
import com.sweetk.iitp.api.dto.basic.local.LocalDisRegSummary.Source;
import com.sweetk.iitp.api.dto.common.PageReq;
import com.sweetk.iitp.api.dto.common.PageRes;
import com.sweetk.iitp.api.dto.internal.PoiPageResult;
import com.sweetk.iitp.api.exception.BusinessException;
import com.sweetk.iitp.api.exception.ErrorCode;
import com.sweetk.iitp.api.repository.basic.local.GgDisRegEmdRepository;
import com.sweetk.iitp.api.repository.basic.local.LocalDisRegRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 지역(시군·구·읍면동) 단위 등록장애인 — 경기 읍면동 집계(경기데이터드림)와 KOSIS 시군구별 통계.
 * 두 원천은 기준 시점이 다르다(경기 집계는 시군마다 원천 기준일, KOSIS 는 연말 기준 연도). 응답에 원천과 기준 시점을 함께 싣는다.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LocalDisRegReadService {

    private final GgDisRegEmdRepository emdRepository;
    private final LocalDisRegRepository localRepository;

    public PageRes<GgDisRegEmd> searchEmd(GgDisRegEmdSearchReq q, PageReq page) {
        int offset = page.getPage() * page.getSize();
        PoiPageResult<GgDisRegEmd> r = emdRepository.search(q.getSigunName(), q.getGuName(), q.getEmdName(),
                q.getDisType(), q.getBaseDt(), offset, page.getSize());
        return new PageRes<>(r.getContent(), page.toPageable(), r.getTotalCount());
    }

    public List<GgDisRegEdition> editions(String sigunName) {
        return emdRepository.editions(sigunName);
    }

    public LocalDisRegSummary summary(String sigunName) {
        if (sigunName == null || sigunName.isBlank()) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "sigunName 은 필수입니다.");
        }
        String sigun = sigunName.trim();
        LocalDisRegSummary out = new LocalDisRegSummary();
        out.setSigunName(sigun);
        out.setGgEmd(ggSource(sigun));
        out.setKosis(kosisSource(sigun));
        if (out.getGgEmd() == null && out.getKosis() == null) {
            throw new BusinessException(ErrorCode.ENTITY_NOT_FOUND, "sigunName(%s) 의 등록장애인 자료가 없습니다.".formatted(sigun));
        }
        List<String> notes = out.getNotes();
        if (out.getGgEmd() != null && out.getKosis() != null) {
            notes.add("두 원천은 기준 시점이 다릅니다(경기 읍면동 집계 %s 기준, KOSIS %d년 말 기준). 값이 다르면 원천과 기준 시점을 함께 안내합니다."
                    .formatted(out.getGgEmd().getBaseDt(), out.getKosis().getYear()));
        }
        if ((out.getGgEmd() != null && out.getGgEmd().getByGu().size() > 1)
                || (out.getKosis() != null && out.getKosis().getByGu().size() > 1)) {
            notes.add("구가 있는 시는 구 값을 더한 값입니다(KOSIS 표에는 시 합계 행이 없습니다).");
        }
        notes.add("시군 사이 비교는 같은 기준 시점인 KOSIS 값을 씁니다. 경기 읍면동 집계는 시군마다 기준일이 다릅니다.");
        return out;
    }

    private Source ggSource(String sigun) {
        LocalDate baseDt = localRepository.ggLatestBaseDt(sigun);
        if (baseDt == null) {
            return null;
        }
        Source s = new Source();
        s.setSourceName(GgDisRegEmdRepository.SOURCE_NAME);
        s.setBaseDt(baseDt);
        List<Part> gus = localRepository.ggByGu(sigun);
        long t = 0, sev = 0, nsev = 0, m = 0, f = 0;
        for (Part p : gus) {
            t += nz(p.getTotalCnt());
            sev += nz(p.getSevCnt());
            nsev += nz(p.getNsevCnt());
            m += nz(p.getMaleCnt());
            f += nz(p.getFemaleCnt());
        }
        s.setTotalCnt(t);
        s.setSevCnt(sev);
        s.setNsevCnt(nsev);
        s.setMaleCnt(m);
        s.setFemaleCnt(f);
        if (!(gus.size() == 1 && gus.get(0).getName() == null)) {
            s.setByGu(gus);
        }
        s.setByType(localRepository.ggByType(sigun));
        return s;
    }

    private Source kosisSource(String sigun) {
        Integer year = localRepository.kosisLatestYear(sigun);
        if (year == null) {
            return null;
        }
        List<String> names = new ArrayList<>();
        List<long[]> rows = localRepository.kosisRaw(sigun, year, names);
        if (rows.isEmpty()) {
            return null;
        }
        Source s = new Source();
        s.setSourceName(LocalDisRegRepository.KOSIS_SOURCE_NAME);
        s.setYear(year);
        // 시 합계 행(이름 = 시군명)이 있으면 그 행을 합계로 쓰고, 없으면 구 행을 더한다(이중 합산 방지).
        long[] sum = new long[5];
        long[] cityRow = null;
        List<Part> gus = new ArrayList<>();
        for (int k = 0; k < rows.size(); k++) {
            long[] r = rows.get(k);
            String name = names.get(k);
            if (Objects.equals(name, sigun)) {
                cityRow = r;
                continue;
            }
            for (int j = 0; j < 5; j++) {
                sum[j] += r[j];
            }
            if (name != null) {
                Part p = new Part();
                p.setName(name.startsWith(sigun) ? name.substring(sigun.length()).trim() : name);
                p.setTotalCnt(r[0]);
                p.setSevCnt(r[1]);
                p.setNsevCnt(r[2]);
                p.setMaleCnt(r[3]);
                p.setFemaleCnt(r[4]);
                gus.add(p);
            }
        }
        if (cityRow != null) {
            sum = cityRow;
        }
        s.setTotalCnt(sum[0]);
        s.setSevCnt(sum[1]);
        s.setNsevCnt(sum[2]);
        s.setMaleCnt(sum[3]);
        s.setFemaleCnt(sum[4]);
        s.setByGu(gus);
        return s;
    }

    private static long nz(Long v) {
        return v == null ? 0L : v;
    }
}
