package com.sweetk.iitp.api.constant;

public final class BasicConstants {
    private BasicConstants() {
        throw new IllegalStateException("BasicConstants class");
    }

    public static final class Api {
        private Api() {
            throw new IllegalStateException("BasicConstants - Api class");
        }

        public static final String FORMAT_STAT_COLLECT_DATE = "%s~%s";
        public static final String FORMAT_STAT_LATEST_CHN_DATE = "yyyy-MM-dd";
    }

}
