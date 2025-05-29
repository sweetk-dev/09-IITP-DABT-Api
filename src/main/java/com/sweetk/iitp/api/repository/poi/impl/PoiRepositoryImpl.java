package com.sweetk.iitp.api.repository.poi.impl;

import com.sweetk.iitp.api.repository.poi.PoiRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PoiRepositoryImpl implements PoiRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Poi> findByType(String type) {
        String jpql = "SELECT p FROM Poi p WHERE p.type = :type AND p.active = true";
        TypedQuery<Poi> query = entityManager.createQuery(jpql, Poi.class);
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public List<Poi> findByNameContaining(String name) {
        String jpql = "SELECT p FROM Poi p WHERE p.name LIKE :name AND p.active = true";
        TypedQuery<Poi> query = entityManager.createQuery(jpql, Poi.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    @Override
    public List<Poi> findByLocationWithinRadius(Double latitude, Double longitude, Double radius) {
        String jpql = """
            SELECT p FROM Poi p 
            WHERE p.active = true 
            AND ST_Distance(
                ST_SetSRID(ST_MakePoint(p.longitude, p.latitude), 4326),
                ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)
            ) <= :radius
            """;
        TypedQuery<Poi> query = entityManager.createQuery(jpql, Poi.class);
        query.setParameter("latitude", latitude);
        query.setParameter("longitude", longitude);
        query.setParameter("radius", radius);
        return query.getResultList();
    }
} 