package kr.co.cleanbasket.cleanbasketdelivererandroid.utils;

/**
 *  LogUtils.java
 *  CleanBasket Deliverer Android
 *
 *  Created by Yongbin Cha
 *  Copyright (c) 2016 WashAppKorea. All rights reserved.
 *
 *  How To Use : private static final String TAG = LogUtils.makeTag({Class Name}.class);
 *
 */

public class LogUtils {
    public static final String PRE_FIX = "CleanBasket_";

    public static String makeTag(Class clazz) {
        return PRE_FIX + clazz.getSimpleName();
    }
}