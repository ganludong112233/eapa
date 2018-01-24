package com.ep.inst;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import com.ep.util.StringUtil;

/**
 * 
 * @author zdwang
 * 
 */
public class MonitorValidator {
    private Set<String> excludedPatterns = new HashSet<String>();
    private Set<String> excludedAnnotions = new HashSet<String>();

    private Set<String> matchPatterns = new HashSet<String>();
    private Set<String> matchAnnotions = new HashSet<String>();

    public MonitorValidator() {

    }

    public MonitorValidator(String matchPatternStr, String excludedPatternStr,
            String matchAnnotionStr, String excludedAnnotionStr, String separator) {
        if (!StringUtil.isBlank(matchPatternStr)) {
            String[] mps = matchPatternStr.split(separator);
            for (String matchPattern : mps) {
                matchPatterns.add(matchPattern);
            }
        }

        if (!StringUtil.isBlank(matchAnnotionStr)) {
            String[] ma = matchAnnotionStr.split(separator);
            for (String matchAnnotion : ma) {
                matchAnnotions.add(matchAnnotion);
            }
        }

        if (!StringUtil.isBlank(excludedPatternStr)) {
            String[] ep = excludedPatternStr.split(separator);
            for (String excludedPattern : ep) {
                excludedPatterns.add(excludedPattern);
            }
        }

        if (!StringUtil.isBlank(excludedAnnotionStr)) {
            String[] ea = excludedAnnotionStr.split(separator);
            for (String excludedAnnotion : ea) {
                excludedAnnotions.add(excludedAnnotion);
            }
        }
    }

    public void addPattern(String pattern, boolean isExcluded) {
        if (pattern == null || pattern.isEmpty()) {
            return;
        }
        // if (!checkPattern(pattern)) {
        // return;
        // }
        if (isExcluded) {
            excludedPatterns.add(pattern);
        } else {
            matchPatterns.add(pattern);
        }
    }

    public void addPatterns(Set<String> patterns, boolean isExcluded) {
        if (patterns == null || patterns.isEmpty()) {
            return;
        }
        // if (!checkPattern(pattern)) {
        // return;
        // }
        if (isExcluded) {
            excludedPatterns.addAll(patterns);
        } else {
            matchPatterns.addAll(patterns);
        }
    }

    public void addAnnotion(String className, boolean isExcluded) {
        if (className == null || className.isEmpty()) {
            return;
        }
        if (isExcluded) {
            excludedAnnotions.add(className);
        } else {
            matchAnnotions.add(className);
        }
    }

    public void addAnnotion(Set<String> classNames, boolean isExcluded) {
        if (classNames == null || classNames.isEmpty()) {
            return;
        }
        if (isExcluded) {
            excludedAnnotions.addAll(classNames);
        } else {
            matchAnnotions.addAll(classNames);
        }
    }

    /**
     * 类级别校验
     * 
     * @param className
     * @param classAnnotions
     * @param methodAnnotions
     * @return
     */
    public boolean verifyClass(String className, Set<String> classAnnotions,
            Set<String> methodAnnotions) {
        if (className == null || className.isEmpty()) {
            return false;
        }
        // excluded匹配则返回false
        if (checkClassPattern(className, excludedPatterns)
                || checkClassAnnotion(classAnnotions, excludedAnnotions)
                || checkClassAnnotion(methodAnnotions, excludedAnnotions)) {
            return false;
        }
        // match匹配则返回true
        if (checkClassPattern(className, matchPatterns)
                || checkClassAnnotion(classAnnotions, matchAnnotions)
                || checkClassAnnotion(methodAnnotions, matchAnnotions)) {
            return true;
        }
        return false;
    }

    public boolean verifyClassPatternList(String className) {
        if (className == null || className.isEmpty()) {
            return false;
        }
        // excluded匹配则返回false
        if (checkClassPattern(className, excludedPatterns)) {
            return false;
        }
        // match匹配则返回true
        if (checkClassPattern(className, matchPatterns)) {
            return true;
        }
        return false;
    }

    public boolean verifyClassAnnotionList(Set<String> classAnnotions, Set<String> methodAnnotions) {
        // excluded匹配则返回false
        if (checkClassAnnotion(classAnnotions, excludedAnnotions)
                || checkClassAnnotion(methodAnnotions, excludedAnnotions)) {
            return false;
        }
        // match匹配则返回true
        if (checkClassAnnotion(classAnnotions, matchAnnotions)
                || checkClassAnnotion(methodAnnotions, matchAnnotions)) {
            return true;
        }
        return false;
    }

    private boolean checkClassAnnotion(Set<String> classAnnotions, Set<String> annotions) {
        if (classAnnotions == null || classAnnotions.isEmpty()) {
            return false;
        }
        if (annotions.isEmpty()) {
            return false;
        }
        return hasInterIection(classAnnotions, annotions);
    }

    private boolean checkClassPattern(String className, Set<String> patternList) {
        className = className.replace("$", "#"); // replace the inner class $ character
        for (String pattern : patternList) {
            pattern=pattern.replace("$", "#");
            if (pattern.endsWith("()")) {
                String patternPath = pattern.substring(0, pattern.lastIndexOf("."));
                String regex =
                        patternPath.replace(".", "\\.").replace("**", ".+")
                                .replace("*", "[a-zA-Z#]*");
                if (Pattern.compile(regex).matcher(className).matches()) {
                    return true;
                }
            } else {
                String regex = pattern.replace(".", "\\.").replace("**", ".+") + "\\..+";
                if (Pattern.compile(regex).matcher(className).matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 方法级别校验
     * 
     * @param className
     * @param methodName
     * @param classAnnotions
     * @param methodAnnotions
     * @return
     */
    public boolean verifyMethod(String className, String methodName, Set<String> classAnnotions,
            Set<String> methodAnnotions) {

        if (checkMethodAnnotion(classAnnotions, methodAnnotions, excludedAnnotions)
                || checkMethodPattern(className, methodName, excludedPatterns)) {
            return false;
        }

        if (checkMethodAnnotion(classAnnotions, methodAnnotions, matchAnnotions)
                || checkMethodPattern(className, methodName, matchPatterns)) {
            return true;
        }
        return false;
    }

    public boolean verifyMethodPatternList(String className, String methodName) {

        if (checkMethodPattern(className, methodName, excludedPatterns)) {
            return false;
        }

        if (checkMethodPattern(className, methodName, matchPatterns)) {
            return true;
        }
        return false;
    }

    public boolean verifyMethodAnnotionList(Set<String> classAnnotions, Set<String> methodAnnotions) {

        if (checkMethodAnnotion(classAnnotions, methodAnnotions, excludedAnnotions)) {
            return false;
        }

        if (checkMethodAnnotion(classAnnotions, methodAnnotions, matchAnnotions)) {
            return true;
        }
        return false;
    }

    public boolean verifyMethodPatterns(String className, String methodName,
            Set<String> classAnnotions, Set<String> methodAnnotions) {

        if (checkMethodAnnotion(classAnnotions, methodAnnotions, excludedAnnotions)
                || checkMethodPattern(className, methodName, excludedPatterns)) {
            return false;
        }

        if (checkMethodAnnotion(classAnnotions, methodAnnotions, matchAnnotions)
                || checkMethodPattern(className, methodName, matchPatterns)) {
            return true;
        }
        return false;
    }

    private boolean checkMethodAnnotion(Set<String> classAnnotions, Set<String> methodAnnotions,
            Set<String> annotions) {
        if (classAnnotions == null && methodAnnotions == null) {
            return false;
        }
        if (hasInterIection(classAnnotions, annotions)) {
            return true;
        }
        if (hasInterIection(methodAnnotions, annotions)) {
            return true;
        }
        return false;
    }

    private boolean checkMethodPattern(String className, String methodName, Set<String> patternList) {
        className = className.replace("$", "#"); // replace the inner class $ character

        for (String pattern : patternList) {
            pattern=pattern.replace("$", "#");
            if (pattern.endsWith("()")) {
                String patternPath = pattern.substring(0, pattern.length() - 2);
                String regex =
                        patternPath.replace(".", "\\.").replace("**", ".+")
                                .replace("*", "[a-zA-Z#]*");
                if (Pattern.compile(regex).matcher(className + "." + methodName).matches()) {
                    return true;
                }
            } else {
                String regex = pattern.replace(".", "\\.").replace("**", ".+") + "\\..+";
                if (Pattern.compile(regex).matcher(className).matches()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasInterIection(Set<String> A, Set<String> B) {
        if (A == null || A.size() < 1) {
            return false;
        }
        if (B == null || B.size() < 1) {
            return false;
        }
        for (String object : A) {
            if (B.contains(object)) {
                return true;
            }
        }
        return false;
    }

    public void copyTo(MonitorValidator descMoValidator) {
        if (descMoValidator == null) {
            descMoValidator = new MonitorValidator();
        }
        descMoValidator.addAnnotion(this.excludedAnnotions, true);
        descMoValidator.addAnnotion(this.matchAnnotions, false);
        descMoValidator.addPatterns(this.excludedPatterns, true);
        descMoValidator.addPatterns(this.matchPatterns, false);
    }
}
