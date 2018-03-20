package com.xpf.customtoast;

import android.content.Context;

/**
 * Created by xpf on 2018/3/20 :)
 * Function:我的自定义Toast显示的工具类
 */

public class MyToastUtil {

    /**
     * 显示红色Toast
     *
     * @param context
     * @param text
     */
    public static void showRedToast(Context context, String text) {
        new CustomToastUI(context, R.layout.toast_red_layout, text).show();
    }

    /**
     * 显示绿色Toast
     *
     * @param context
     * @param text
     */
    public static void showGreenToast(Context context, String text) {
        new CustomToastUI(context, R.layout.toast_green_layout, text).show();
    }

    /**
     * 显示黄色Toast
     *
     * @param context
     * @param text
     */
    public static void showYellowToast(Context context, String text) {
        new CustomToastUI(context, R.layout.toast_yellow_layout, text).show();
    }
}
