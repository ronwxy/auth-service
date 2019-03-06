package com.springcloud.service.auth.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
    public static final String MOBILE_PHONE_REGEX = "^1[3456789][\\d]{9}$";
    public static final String EMAIL_REGEX = "";
    public static final Pattern MOBILE_PHONE_PATTERN = Pattern.compile(MOBILE_PHONE_REGEX);
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);


    public static boolean isChinaMobilePhone(String mobile) {
        Matcher matcher = MOBILE_PHONE_PATTERN.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}
