package com.tracy.slark.utils;

import com.tracy.slark.model.Constant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shijiecui on 2018/2/25.
 */

public class StringUtils {
    /**
     * 下划线转驼峰
     */
    public static String replaceUnderlineToHump(String str) {
        if (str != null && !str.equals(Constant.UNKNOWN)) {
            Pattern linePattern = Pattern.compile("_(\\w)");
            str = str.toLowerCase();
            Matcher matcher = linePattern.matcher(str);
            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
            }
            matcher.appendTail(sb);
            str = sb.toString();
        }
        return str;
    }
}
