package com.gxb.gxbcompanyintegrity.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.gxb.gxbcompanyintegrity.R;

/**
 * 倒计时工具类
 * Created by baoyb on 2017/5/31.
 */

public class CountDownUtils extends CountDownTimer {
    private Context context;
    private TextView tvCode;
    private long nSecond = 0;
    public CountDownUtils(Context context, long millisInFuture, long countDownInterval, TextView tvCode) {
        super(millisInFuture, countDownInterval);
        this.context = context;
        this.tvCode = tvCode;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tvCode.setEnabled(false);
        nSecond = millisUntilFinished / 1000;
        tvCode.setTextColor(ContextCompat.getColor(context, R.color.text_yellow));
        tvCode.setText(nSecond + "s重新发送");
    }

    @Override
    public void onFinish() {
        tvCode.setText("获取验证码");
        tvCode.setTextColor(ContextCompat.getColor(context, R.color.text_black));
        tvCode.setEnabled(true);
        nSecond = 0;
    }
}
