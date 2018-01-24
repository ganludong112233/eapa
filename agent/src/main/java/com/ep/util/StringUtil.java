package com.ep.util;

import java.util.HashSet;
import java.util.Set;

public class StringUtil {
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        if (str.trim().length() == 0) {
            return true;
        }
        return false;
    }

    public static String append(String str, String appendStr) {
        if (str == null) {
            return null;
        }
        if (appendStr == null) {
            return str;
        }
        return str + appendStr;
    }

    public static Set<String> split2Set(String inputString, String separator) {
        Set<String> strSet = new HashSet<String>();
        if (inputString == null) {
            return strSet;
        }
        for (String str : inputString.split(separator, -1)) {
            strSet.add(str);
        }
        return strSet;
    }

}
