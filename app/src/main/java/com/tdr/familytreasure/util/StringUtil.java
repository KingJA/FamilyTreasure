package com.tdr.familytreasure.util;

import android.text.TextUtils;

import java.util.UUID;

/**
 * Description：TODO
 * Create Time：2016/8/22 14:33
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class StringUtil {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 身份证号码，隐藏中间的出身年月日
     */
    public static final String hideID(String id) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < id.length() - 4; i++) {
            sb.append("*");
        }
        String newId = id.substring(0, 2) + sb.toString()
                + id.substring(id.length() - 2);
        return newId;
    }

    public static final String hidePhone(String phone, int hideCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hideCount; i++) {
            sb.append("*");
        }
        String newId = phone.substring(0, 3) + sb.toString()
                + phone.substring(phone.length() - 4);
        return newId;
    }


    public static String getWholeUrl(String url) {
        String result = url;
        if (!TextUtils.isEmpty(url)&&!url.startsWith("http")) {
            result = "http://" + url;
        }
        return result;
    }
}
