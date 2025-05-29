package com.sweetk.iitp.api.repository.basic;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface BasicRepository<T, ID> extends JpaRepository<T, ID> {
    Optional<T> findByIdAndActiveTrue(ID id);
    List<T> findAllByActiveTrue();
    void softDelete(ID id);
} 