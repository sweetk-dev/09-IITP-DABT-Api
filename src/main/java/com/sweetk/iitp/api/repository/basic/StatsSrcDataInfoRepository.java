package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 커스텀 리포지토리 인터페이스
interface StatsSrcDataInfoRepositoryCustom {
    StatsSrcDataInfoEntity findWithStatOrgByIntgTblId(String intgTblId);
    StatsSrcDataInfoEntity findWithStatOrgNameByIntgTblId(String intgTblId);

}

@Repository
public interface StatsSrcDataInfoRepository extends JpaRepository<StatsSrcDataInfoEntity, Integer>, StatsSrcDataInfoRepositoryCustom {
    StatsSrcDataInfoEntity findBySrcDataId(Integer srcDataId);
    StatsSrcDataInfoEntity findByIntgTblId(String intgTblId);
}