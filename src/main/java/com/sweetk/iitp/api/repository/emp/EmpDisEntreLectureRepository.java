package com.sweetk.iitp.api.repository.emp;

import com.sweetk.iitp.api.entity.emp.EmpDisEntreLectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpDisEntreLectureRepository extends JpaRepository<EmpDisEntreLectureEntity, Integer> {
} 