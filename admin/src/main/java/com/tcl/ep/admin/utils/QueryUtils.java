package com.tcl.ep.admin.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 查询时的一些处理 Created by machangsheng on 15/10/13.
 */
public class QueryUtils {

    public static final long INVALID_DATE = 0L;

    /**
     * 关键词过滤处理
     * 
     * @param keyword 关键词
     * @return 处理后的结果
     */
    public static String formatKeyword(String keyword) {
        String formatedWord = "";

        // 不处理空查询
        if (keyword == null || keyword.trim().equals("")) {
            return formatedWord;
        }
        formatedWord = keyword.trim();

        // 1. 第一步,处理超长问题
        if (formatedWord.length() > 38) {
            formatedWord = formatedWord.substring(0, 38);// 限制为38个字符
        }

        // 日期判断,并不是严格的日期判断
        // 只要类似日期,就不做后面的处理了
        if (formatedWord.matches("[0-9]{1,4}(-)?[0-9]{0,2}(-)?[0-9]{0,2}")) {
            return formatedWord;
        }

        // 2. 第二步,处理特殊字符
        formatedWord = transformMetachar(formatedWord);

        // 3. 第三步及第N步, 可继续处理
        // 去中文标点符号
        formatedWord = formatedWord.replaceAll("(\\pP|\\pS)", "");
        // 返回结果
        return formatedWord;
    }

    /**
     * Solr字符转义处理，否则查询下列字符会报查询错误。 特殊字符串：+ – && || ! ( ) { } [ ] ^ ” ~ * ? : \ / 空格
     * 
     * @param input 输入
     * @return 输出
     */
    public static String transformMetachar(String input) {
        StringBuffer sb = new StringBuffer();
        try {
            String regex = "[+/\\-&|!(){}\\[\\]^\"~*?:(\\)\\s\\\\]";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(input);
            while (matcher.find()) {
                matcher.appendReplacement(sb, "\\\\" + matcher.group());
            }
            matcher.appendTail(sb);
        } catch (Exception e) {
            // TODO
        }
        return sb.toString();
    }

    /**
     * 格式化时间
     * 
     * @param date 日期
     * @return 格式化后的日期
     */
    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 格式化时间
     * 
     * @param date 日期
     * @param format 格式
     * @return 格式化后的时间
     */
    public static String formatDate(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        if (date == null) {
            date = new Date();
        }

        return sdf.format(date);
    }

    /**
     * 检查一个日期字符串是否有效,若无效,则返回NULL
     * 
     * @param dateStr 日期字符串
     * @return 相应的结果
     */
    public static long checkDate(String dateStr) {

        if (StringUtils.isBlank(dateStr)) {
            return INVALID_DATE;
        }

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return INVALID_DATE;
    }
}
