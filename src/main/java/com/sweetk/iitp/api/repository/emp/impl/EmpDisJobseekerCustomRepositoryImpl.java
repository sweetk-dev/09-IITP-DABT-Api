package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobSeeker;
import com.sweetk.iitp.api.entity.emp.EmpDisJobseekerEntity;
import com.sweetk.iitp.api.repository.emp.support.EmpEditionFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class EmpDisJobseekerCustomRepositoryImpl implements EmpDisJobseekerCustomRepository {
    @PersistenceContext
    private EntityManager em;

    // 검색 키 + 판(edition) 조건에 따라 구직자 현황 조회 (최신순, 페이징)
    // - searchReq 가 null 이면 검색 조건 없이 전체를 대상으로 한다.
    // - 판 조건은 본 조회와 건수(count) 조회에 똑같이 붙인다. 그래야 페이징 총 건수가 실제 목록과 맞는다.
    @Override
    public Page<EmpDisJobseekerEntity> findJobSeekersBySearchKey(EmpSearchReqJobSeeker searchReq, LocalDate baseDt, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT e FROM EmpDisJobseekerEntity e WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(e) FROM EmpDisJobseekerEntity e WHERE 1=1");
        List<Object> params = new ArrayList<>();
        List<String> whereList = new ArrayList<>();

        if (searchReq != null) {
            if (StringUtils.hasText(searchReq.getJobType())) {
                whereList.add("e.jobType LIKE ?" + (params.size() + 1));
                params.add("%" + searchReq.getJobType() + "%");
            }
            if (StringUtils.hasText(searchReq.getRegion())) {
                whereList.add("e.region LIKE ?" + (params.size() + 1));
                params.add("%" + searchReq.getRegion() + "%");
            }
            if (searchReq.getSalaryType() != null) {
                whereList.add("e.salaryType = ?" + (params.size() + 1));
                params.add(searchReq.getSalaryType().getName());
            }
            // 추가 조건 필요시 여기에
        }

        // 판(edition) 조건: 기준일 지정 시 그 판, 아니면 최신 판 (모두 NULL 인 동안은 전체)
        // 위치 바인딩(?n)을 쓰는 쿼리라 자리 표시도 다음 번호로 맞춘다 (이름 바인딩과 섞어 쓸 수 없음).
        EmpEditionFilter edition = EmpEditionFilter.resolve(
                em, EmpDisJobseekerEntity.class, "e", baseDt, "?" + (params.size() + 1));
        whereList.add(edition.jpql());
        if (edition.hasValue()) {
            params.add(edition.value());
        }

        for (String where : whereList) {
            jpql.append(" AND ").append(where);
            countJpql.append(" AND ").append(where);
        }
        jpql.append(" ORDER BY e.regDate DESC, e.seqNo DESC");

        TypedQuery<EmpDisJobseekerEntity> query = em.createQuery(jpql.toString(), EmpDisJobseekerEntity.class);
        TypedQuery<Long> countQuery = em.createQuery(countJpql.toString(), Long.class);
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
            countQuery.setParameter(i + 1, params.get(i));
        }
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<EmpDisJobseekerEntity> resultList = query.getResultList();
        long total = countQuery.getSingleResult();
        return new PageImpl<>(resultList, pageable, total);
    }
} 