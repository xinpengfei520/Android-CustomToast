package com.xpf.customtoast;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

/**
 * Created by xpf on 2018/3/20 :)
 * Function:自定义toast样式、显示时长
 */
public class CustomToastUI {

    private Toast mToast;
    private String message;
    private TextView mTextView;
    private TimeCount timeCount;
    private boolean canceled = true;
    private Handler mHandler = new Handler();

    public CustomToastUI(Context context, int layoutId, String msg) {
        message = msg;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 自定义布局
        View view = inflater.inflate(layoutId, null);
        /*
         * 如果是非全屏应用使用下面Flag，让Toast显示到状态栏底部
         * 设置Toast布局可以到系统状态栏 SYSTEM_UI_FLAG_FULLSCREEN
         */
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        // 自定义toast文本
        mTextView = (TextView) view.findViewById(R.id.tvResult);
        mTextView.setText(msg);
        if (mToast == null) {
            mToast = new Toast(context);
        }
        // 设置toast顶部且以手机屏幕宽度显示
        mToast.setGravity(Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK | Gravity.TOP, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setView(view);
        setToastAnimation(mToast);
    }

    /**
     * 设置Toast全屏显示和进入、退出动画设置
     * Android7.0以后设置动画不起作用！
     *
     * @param toast
     */
    private void setToastAnimation(Toast toast) {
        try {
            Field mTN = toast.getClass().getDeclaredField("mTN");
            mTN.setAccessible(true);
            Object mTNObj = mTN.get(toast);
            Field mParams = mTNObj.getClass().getDeclaredField("mParams");
            mParams.setAccessible(true);
            WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams.get(mTNObj);
            params.width = -1;//-1表示全屏, 你也可以设置任意宽度.
            params.height = -2;//
            // 设置动画, 需要是style类型
            params.windowAnimations = R.style.BaseToastAnimation;
            //默认系统的Toast动画
            //params.windowAnimations = com.android.internal.R.style.Animation_Toast;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 自定义居中显示toast
     */
    public void show() {
        mToast.show();
    }

    /**
     * 自定义时长、居中显示toast
     *
     * @param duration
     */
    public void show(int duration) {
        timeCount = new TimeCount(duration, 1000);
        if (canceled) {
            timeCount.start();
            canceled = false;
            showUntilCancel();
        }
    }

    /**
     * 隐藏toast
     */
    private void hide() {
        if (mToast != null) {
            mToast.cancel();
        }
        canceled = true;
    }

    private void showUntilCancel() {
        if (canceled) { //如果已经取消显示，就直接return
            return;
        }
        mToast.show();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showUntilCancel();
            }
        }, Toast.LENGTH_LONG);
    }

    /**
     * 自定义计时器
     */
    private class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval); //millisInFuture总计时长，countDownInterval时间间隔(一般为1000ms)
        }

        @Override
        public void onTick(long millisUntilFinished) {
            mTextView.setText(message + ": " + millisUntilFinished / 1000 + "s后消失");
        }

        @Override
        public void onFinish() {
            hide();
        }
    }
}
