package com.sweetk.iitp.api.repository.emp.impl;

import com.sweetk.iitp.api.entity.emp.EmpDisStartupLectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpDisStartupLectureRepository extends JpaRepository<EmpDisStartupLectureEntity, Integer> {
    /**
     * 창업넷 일반강좌 전체 조회 — 최신 연도 먼저 (연도 DESC, 같은 연도 안에서는 id 순)
     * <p>이 표는 연도마다 한 판만 두므로 판(src_base_dt) 조건 없이 연도로만 정렬한다.
     * 06 플랫폼 미리보기가 앞쪽 행을 보여주므로 최신 연도가 먼저 오게 한다.</p>
     */
    List<EmpDisStartupLectureEntity> findAllByOrderByYearDescIdAsc();

} 