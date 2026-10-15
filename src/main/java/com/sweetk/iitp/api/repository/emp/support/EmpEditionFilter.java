package com.sweetk.iitp.api.repository.emp.support;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.List;

/**
 * 고용(emp_dis_*) 표의 "판(edition)" 선택 조건을 만드는 도구.
 *
 * <p><b>배경</b><br>
 * 고용 데이터 수집기는 원천 파일의 과거 판을 지우지 않고 남긴다. 판마다 {@code src_base_dt}(원천 판 기준일)를 기록한다.
 * 연도(year) 컬럼이 없는 9개 표(구직자·구인·훈련센터 이용·고용의무 현황·고용컨설팅·전문인력 교육과정·
 * 발달장애인 지원기관·표준사업장·신규고용장려금)는 여러 판이 한 테이블에 함께 쌓이므로,
 * 조회할 때 판을 하나만 골라야 같은 기관·같은 사람이 판 수만큼 중복으로 나오지 않는다.</p>
 *
 * <p><b>선택 규칙</b></p>
 * <ul>
 *   <li>요청에 기준일(baseDt)이 있으면: {@code src_base_dt = baseDt} 인 행만. 그런 판이 없으면 빈 결과.</li>
 *   <li>기준일이 없으면: 그 표에서 가장 최근 판({@code max(src_base_dt)}) 의 행만.</li>
 *   <li>표 전체가 아직 NULL 이면(값을 채우기 전) {@code max} 도 NULL 이므로 {@code src_base_dt IS NULL} 인 행,
 *       즉 지금까지와 똑같이 전체 행을 돌려준다.
 *       SQL 로 쓰면 {@code src_base_dt IS NOT DISTINCT FROM (SELECT max(src_base_dt) FROM 표)} 와 같은 뜻이다.</li>
 * </ul>
 *
 * <p><b>구현 방식</b><br>
 * 최신 판 날짜를 먼저 {@code SELECT MAX(...)} 로 한 번 구한 뒤, 본 조회에는 {@code = 값} 또는 {@code IS NULL} 조건을 붙인다.
 * JPQL 안에 서브쿼리와 NULL 비교를 섞는 것보다 단순하고, {@code src_base_dt} 인덱스도 그대로 쓸 수 있다.
 * 페이징 조회는 본 조회와 건수(count) 조회에 같은 조건을 붙여야 총 건수가 맞으므로,
 * 조건 문자열과 바인딩 값을 따로 꺼내 쓸 수 있게 했다.</p>
 */
public final class EmpEditionFilter {

    /** WHERE 절에 붙일 JPQL 조건 (예: {@code e.srcBaseDt = ?3} 또는 {@code e.srcBaseDt IS NULL}) */
    private final String jpql;

    /** 조건에 바인딩할 판 기준일. 조건이 {@code IS NULL} 이면 바인딩할 값이 없으므로 null */
    private final LocalDate value;

    private EmpEditionFilter(String jpql, LocalDate value) {
        this.jpql = jpql;
        this.value = value;
    }

    /**
     * 판 선택 조건을 만든다.
     *
     * @param em          엔티티 매니저 (최신 판 날짜 조회에 사용)
     * @param entityType  대상 엔티티 클래스 (src_base_dt 를 {@code srcBaseDt} 필드로 가진 엔티티)
     * @param alias       본 조회 JPQL 에서 쓰는 엔티티 별칭 (예: "e")
     * @param baseDt      요청한 판 기준일. null 이면 최신 판
     * @param placeholder 값을 바인딩할 자리 표시 (이름 바인딩이면 ":baseDt", 위치 바인딩이면 "?3" 처럼 호출 측이 정한다)
     */
    public static EmpEditionFilter resolve(EntityManager em, Class<?> entityType, String alias,
                                           LocalDate baseDt, String placeholder) {
        LocalDate target = baseDt;
        if (target == null) {
            // 기준일을 지정하지 않았으면 그 표의 최신 판 날짜를 구한다. 모두 NULL 이면 결과도 NULL.
            target = em.createQuery(
                    "SELECT MAX(x.srcBaseDt) FROM " + entityName(em, entityType) + " x", LocalDate.class)
                    .getSingleResult();
        }
        if (target == null) {
            // 판 기준일이 아직 하나도 기록되지 않은 상태: NULL 판(=지금까지 쌓인 전체)을 하나의 판으로 본다.
            return new EmpEditionFilter(alias + ".srcBaseDt IS NULL", null);
        }
        return new EmpEditionFilter(alias + ".srcBaseDt = " + placeholder, target);
    }

    /**
     * 판 하나만 골라 전체 목록을 조회한다 (페이징 없는 목록 API 용).
     * <p>정렬은 붙이지 않는다. 판 필터를 넣기 전({@code findAll()})과 같은 순서로 내려주기 위함이다.</p>
     *
     * @param em         엔티티 매니저
     * @param entityType 대상 엔티티 클래스
     * @param baseDt     요청한 판 기준일. null 이면 최신 판
     */
    public static <T> List<T> findList(EntityManager em, Class<T> entityType, LocalDate baseDt) {
        EmpEditionFilter edition = resolve(em, entityType, "e", baseDt, ":baseDt");
        TypedQuery<T> query = em.createQuery(
                "SELECT e FROM " + entityName(em, entityType) + " e WHERE " + edition.jpql(), entityType);
        if (edition.hasValue()) {
            query.setParameter("baseDt", edition.value());
        }
        return query.getResultList();
    }

    /** JPQL 에 쓸 엔티티 이름 (메타모델에서 읽어 클래스 이름 변경·@Entity(name) 지정에도 맞춘다) */
    private static String entityName(EntityManager em, Class<?> entityType) {
        return em.getMetamodel().entity(entityType).getName();
    }

    /** WHERE 절에 붙일 JPQL 조건 */
    public String jpql() {
        return jpql;
    }

    /** 바인딩할 값이 있는지 (조건이 {@code = 값} 형태인지) */
    public boolean hasValue() {
        return value != null;
    }

    /** 바인딩할 판 기준일 ({@link #hasValue()} 가 false 이면 null) */
    public LocalDate value() {
        return value;
    }
}