package com.ep.jdbc;

import java.util.HashMap;
import java.util.Map;

public class SQLFormater {
    static Map<String, String> cachedRSql = new HashMap<String, String>();

    public static String format(String sql, String originalSql) {
        String rSql = sql != null ? sql : originalSql;
        String cachedSql = cachedRSql.get(rSql);
        if (cachedSql != null) {
            return cachedSql;
        }
        cachedSql = rSql.replaceAll("\\s+", " ");
        cachedRSql.put(rSql, cachedSql);
        return cachedSql;
    }
}
