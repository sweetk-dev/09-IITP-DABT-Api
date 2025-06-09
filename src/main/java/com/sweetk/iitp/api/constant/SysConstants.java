package com.sweetk.iitp.api.constant;


public final class SysConstants {

    private SysConstants() {
        throw new IllegalStateException("Sys Constants class");
    }

    public static final class Stats {
        private Stats() {
            throw new IllegalStateException("Stats Constants class");
        }

        public static final String KOSIS_META_ITEM_OBJ_NAME = "항목";


        //01.Housing
        public static final String TBL_REG_NATL_BY_NEW = "stats_dis_reg_natl_by_new";
        public static final String TBL_REG_NATL_BY_AGE_TYPE_SEV_GEN = "stats_dis_reg_natl_by_age_type_sev_gen";
        public static final String TBL_REG_SIDO_BY_AGE_TYPE_SEV_GEN = "stats_dis_reg_sido_by_age_type_sev_gen";
        public static final String TBL_LIFE_SUPP_NEED_LVL = "stats_dis_life_supp_need_lvl";
        public static final String TBL_LIFE_MAINCARER = "stats_dis_life_maincarer";
        public static final String TBL_LIFE_PRIMCARER = "stats_dis_life_primcarer";
        public static final String TBL_LIFE_SUPP_FIELD = "stats_dis_life_supp_field";
        //02.Health
        public static final String TBL_HLTH_MEDICAL_USAGE = "stats_dis_hlth_medical_usage";
        public static final String TBL_HLTH_DISEASE_COST_SUB = "stats_dis_hlth_disease_cost_sub";
        public static final String TBL_HLTH_SPORT_EXEC_TYPE = "stats_dis_hlth_sport_exec_type";
        public static final String TBL_HLTH_EXRC_BEST_AID = "stats_dis_hlth_exrc_best_aid";
        //03.Aid (Assistive devices)
        public static final String TBL_AID_DEVICE_USAGE = "stats_dis_aid_device_usage";
        public static final String TBL_AID_DEVICE_NEED = "stats_dis_aid_device_need";
        //04.Education
        public static final String TBL_EDU_VOCA_EXEC = "stats_dis_edu_voca_exec";
        public static final String TBL_EDU_VOCA_EXEC_WAY = "stats_dis_edu_voca_exec_way";
        //05.Employment
        public static final String TBL_EMP_NATL = "stats_dis_emp_natl";
        public static final String TBL_EMP_NATL_PUBLIC = "stats_dis_emp_natl_public";
        public static final String TBL_EMP_NATL_PRIVATE = "stats_dis_emp_natl_private";
        public static final String TBL_EMP_NATL_GOV_ORG = "stats_dis_emp_natl_gov_org";
        public static final String TBL_EMP_NATL_DIS_TYPE_SEV = "stats_dis_emp_natl_dis_type_sev";
        public static final String TBL_EMP_NATL_DIS_TYPE_INDUST = "stats_dis_emp_natl_dis_type_indust";
        //06.Social
        public static final String TBL_SOC_PARTIC_FREQ = "stats_dis_soc_partic_freq";
        public static final String TBL_SOC_CONTACT_CNTFREQ = "stats_dis_soc_contact_cntfreq";
        //07.Facilities
        public static final String TBL_FCLTY_WELFARE_USAGE = "stats_dis_fclty_welfare_usage";

    }
}
