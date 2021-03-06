package com.phone.book.common.utils;


import static com.phone.book.common.ConstantsKt.Fixture_OTA_MAX_CLICK_TIME;
import static com.phone.book.common.ConstantsKt.SINGLE_CLICK_EVENT_TIME;

/**
 * description ：Double-click
 * author : Andy
 * email : 495311081@qq.com
 * date : 2020/4/20
 */
public class OnClickUtils {

    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= SINGLE_CLICK_EVENT_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
    public static boolean isLow3sClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= Fixture_OTA_MAX_CLICK_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
