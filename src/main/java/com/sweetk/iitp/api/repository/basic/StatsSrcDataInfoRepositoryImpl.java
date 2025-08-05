package com.sweetk.iitp.api.repository.basic;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sweetk.iitp.api.constant.DataStatusType;
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
    public StatsSrcDataInfoEntity findActiveByIntgTblId(String intgTblId) {
        Tuple tuple = queryFactory
            .select(qSrc, qCode)
            .from(qSrc)
            .leftJoin(qCode)
                .on(qSrc.statOrgId.eq(qCode.id.codeId)
                    .and(qCode.id.grpId.eq("stats_src_orgId")))
            .where(
                qSrc.intgTblId.eq(intgTblId),
                qSrc.status.eq(DataStatusType.ACTIVE)
            )
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
} 