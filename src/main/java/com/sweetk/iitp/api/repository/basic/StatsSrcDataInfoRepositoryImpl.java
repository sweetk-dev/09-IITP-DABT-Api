package com.sweetk.iitp.api.repository.basic;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.entity.basic.QStatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.sys.QSysCommonCodeEntity;
import com.sweetk.iitp.api.entity.sys.SysCommonCodeEntity;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StatsSrcDataInfoRepositoryImpl implements StatsSrcDataInfoRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    private final QStatsSrcDataInfoEntity qSrc = QStatsSrcDataInfoEntity.statsSrcDataInfoEntity;
    private final QSysCommonCodeEntity qCode = QSysCommonCodeEntity.sysCommonCodeEntity;

    @Override
    public StatsSrcDataInfoEntity findWithStatOrgByIntgTblId(String intgTblId) {

        Tuple tuple = queryFactory
            .select(qSrc, qCode)
            .from(qSrc)
            .leftJoin(qCode)
                .on(qSrc.statOrgId.eq(qCode.codeId)
                    .and(qCode.grpId.eq("stats_src_orgId")))
            .where(qSrc.intgTblId.eq(intgTblId))
            .fetchFirst();

        if (tuple == null) {
            return null;
        }

        StatsSrcDataInfoEntity entity = tuple.get(qSrc);
        SysCommonCodeEntity statOrg = tuple.get(qCode);
        entity.setStatOrg(statOrg);
        if (statOrg != null) {
            entity.setStatOrgName(statOrg.getCodeNm());
        }
        return entity;
    }


    @Override
    public StatsSrcDataInfoEntity findWithStatOrgNameByIntgTblId(String intgTblId) {


        // 조인하여 기관명(codeNm)까지 한 번에 조회
        Tuple tuple = queryFactory
                .select(qSrc, qCode.codeNm)
                .from(qSrc)
                .leftJoin(qCode)
                .on(qSrc.statOrgId.eq(qCode.codeId)
                        .and(qCode.grpId.eq("stats_src_orgId")))
                .where(qSrc.intgTblId.eq(intgTblId))
                .fetchFirst();

        if (tuple == null) {
            return null;
        }

        StatsSrcDataInfoEntity entity = tuple.get(qSrc);
        String statOrgName = tuple.get(qCode.codeNm);
        entity.setStatOrgName(statOrgName);

        return entity;
    }
} 