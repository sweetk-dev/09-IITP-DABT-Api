package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.dto.emp.EmpSearchReqJobPost;
import com.sweetk.iitp.api.entity.emp.EmpDisJobPostingEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


public class EmpDisJobPostingCustomRepositoryImpl implements EmpDisJobPostingCustomRepository {
    @PersistenceContext
    private EntityManager em;

    // 검색 키에 따라 구직자 현황 조회 (최신순, 페이징)
    @Override
    public Page<EmpDisJobPostingEntity> findJobPostsBySearchKey(EmpSearchReqJobPost searchReq, Pageable pageable) {
        StringBuilder jpql = new StringBuilder("SELECT e FROM EmpDisJobPostingEntity e WHERE 1=1");
        StringBuilder countJpql = new StringBuilder("SELECT COUNT(e) FROM EmpDisJobPostingEntity e WHERE 1=1");
        List<Object> params = new ArrayList<>();
        List<String> whereList = new ArrayList<>();

        if (StringUtils.hasText(searchReq.getJobType())) {
            whereList.add("e.jobType LIKE ?" + (params.size() + 1));
            params.add("%" + searchReq.getJobType() + "%");
        }

        if (searchReq.getEmpType() != null) {
            whereList.add("e.empType = ?" + (params.size() + 1));
            params.add(searchReq.getEmpType().getName());
        }

        if (StringUtils.hasText(searchReq.getAddress())) {
            whereList.add("e.address LIKE ?" + (params.size() + 1));
            params.add("%" + searchReq.getAddress() + "%");
        }

        if (StringUtils.hasText(searchReq.getName())) {
            whereList.add("e.companyName LIKE ?" + (params.size() + 1));
            params.add("%" + searchReq.getName() + "%");
        }

        for (String where : whereList) {
            jpql.append(" AND ").append(where);
            countJpql.append(" AND ").append(where);
        }
        jpql.append(" ORDER BY e.applyDate DESC, e.seqNo DESC");

        TypedQuery<EmpDisJobPostingEntity> query = em.createQuery(jpql.toString(), EmpDisJobPostingEntity.class);
        TypedQuery<Long> countQuery = em.createQuery(countJpql.toString(), Long.class);
        for (int i = 0; i < params.size(); i++) {
            query.setParameter(i + 1, params.get(i));
            countQuery.setParameter(i + 1, params.get(i));
        }
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<EmpDisJobPostingEntity> resultList = query.getResultList();
        long total = countQuery.getSingleResult();
        return new PageImpl<>(resultList, pageable, total);
    }
} 