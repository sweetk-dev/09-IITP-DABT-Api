package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.entity.poi.PoiEntity;
import com.sweetk.iitp.api.repository.poi.PoiRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PoiRepositoryImpl implements PoiRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<PoiEntity> findAllByActiveTrue(Pageable pageable) {
        String jpql = "SELECT p FROM PoiEntity p WHERE p.active = true";
        TypedQuery<PoiEntity> query = entityManager.createQuery(jpql, PoiEntity.class);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        String countJpql = "SELECT COUNT(p) FROM PoiEntity p WHERE p.active = true";
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, total);
    }

    @Override
    public Page<PoiEntity> findByType(String type, Pageable pageable) {
        String jpql = "SELECT p FROM PoiEntity p WHERE p.type = :type AND p.active = true";
        TypedQuery<PoiEntity> query = entityManager.createQuery(jpql, PoiEntity.class);
        query.setParameter("type", type);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        String countJpql = "SELECT COUNT(p) FROM PoiEntity p WHERE p.type = :type AND p.active = true";
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        countQuery.setParameter("type", type);
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, total);
    }

    @Override
    public Page<PoiEntity> findByNameContaining(String name, Pageable pageable) {
        String jpql = "SELECT p FROM PoiEntity p WHERE p.name LIKE :name AND p.active = true";
        TypedQuery<PoiEntity> query = entityManager.createQuery(jpql, PoiEntity.class);
        query.setParameter("name", "%" + name + "%");
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        String countJpql = "SELECT COUNT(p) FROM PoiEntity p WHERE p.name LIKE :name AND p.active = true";
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        countQuery.setParameter("name", "%" + name + "%");
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, total);
    }

    @Override
    public Page<PoiEntity> findByLocationWithinRadius(Double latitude, Double longitude, Double radius, Pageable pageable) {
        String jpql = """
            SELECT p FROM PoiEntity p 
            WHERE p.active = true 
            AND ST_Distance(
                ST_SetSRID(ST_MakePoint(p.longitude, p.latitude), 4326),
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)
            ) <= :radius
            """;
        TypedQuery<PoiEntity> query = entityManager.createQuery(jpql, PoiEntity.class);
        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        query.setParameter("radius", radius);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        String countJpql = """
            SELECT COUNT(p) FROM PoiEntity p 
            WHERE p.active = true 
            AND ST_Distance(
                ST_SetSRID(ST_MakePoint(p.longitude, p.latitude), 4326),
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)
            ) <= :radius
            """;
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        countQuery.setParameter("latitude", latitude);
        countQuery.setParameter("longitude", longitude);
        countQuery.setParameter("radius", radius);
        Long total = countQuery.getSingleResult();

        return new PageImpl<>(query.getResultList(), pageable, total);
    }
} 
