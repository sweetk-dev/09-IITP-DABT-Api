package com.sweetk.iitp.api.repository.basic;

import com.sweetk.iitp.api.entity.basic.StatsSrcDataInfoEntity;
import com.sweetk.iitp.api.entity.sys.SysCommonCodeEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class StatsSrcDataInfoRepositoryImpl implements StatsSrcDataInfoRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public StatsSrcDataInfoEntity findWithStatOrgByIntgTblId(String intgTblId) {
        StatsSrcDataInfoEntity entity = em.find(StatsSrcDataInfoEntity.class, intgTblId);
        if (entity != null) {
            SysCommonCodeEntity statOrg = em.createQuery(
                "SELECT c FROM SysCommonCodeEntity c WHERE c.codeId = :codeId AND c.grpId = :grpId", SysCommonCodeEntity.class)
                .setParameter("codeId", entity.getStatOrgId())
                .setParameter("grpId", "stats_src_orgId")
                .getResultStream()
                .findFirst()
                .orElse(null);
            entity.setStatOrg(statOrg);
        }
        return entity;
    }


    @Override
    public StatsSrcDataInfoEntity findWithStatOrgNameByIntgTblId(String intgTblId) {
        StatsSrcDataInfoEntity entity = em.find(StatsSrcDataInfoEntity.class, intgTblId);
        if (entity != null) {
            String statOrgName = em.createQuery(
                            "SELECT c.codeNm FROM SysCommonCodeEntity c WHERE c.codeId = :codeId AND c.grpId = :grpId", String.class)
                    .setParameter("codeId", entity.getStatOrgId())
                    .setParameter("grpId", "stats_src_orgId")
                    .getResultStream()
                    .findFirst()
                    .orElse("");
            entity.setStatOrgName(statOrgName);
        }
        return entity;
    }
} 