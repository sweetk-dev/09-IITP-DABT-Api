package com.sweetk.iitp.api.util;

public class RepositoryUtils {
    public static String escapeSql(String input) {
        if (input == null) return null;
        return input.replace("'", "''");
    }

    public static StringBuilder addQueryOffset(StringBuilder baseQuery, int offset, int size) {
        if (baseQuery == null) return null;
        return baseQuery.append(" OFFSET ").append(offset).append(" LIMIT ").append(size);
    }

}
