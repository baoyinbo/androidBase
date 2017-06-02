package com.gxb.gxbcompanyintegrity.util;

import android.text.TextUtils;
import android.widget.Toast;

import com.gxb.gxbcompanyintegrity.GSApplication;


/**
 * 吐司方法工具类
 * Created by baoyb on 2017/3/17.
 */

public class ToastShowUtils {


    /**
     * 加载失败带图片的toast
     * @param text
     */
    public static void showFailedToast(String text) {
//        showCustomToast(text, false, Toast.LENGTH_SHORT);
    }

    /**
     * 加载成功带图片的toast
     * @param text
     */
    public static void showSuccessToast(String text) {
//        showCustomToast(text, true, Toast.LENGTH_SHORT);
    }
    /**
     * 自定义带图片的toast，居中显示
     * @param text
     * @param result
     * @param millisecond
     */
//    public static void showCustomToast(String text, boolean result, int millisecond) {
//        View toastView = LayoutInflater.from(GSApplication.getApplication())
//                .inflate(R.layout.byb_dia_toast, null);
//        ImageView ivToast = (ImageView) toastView.findViewById(R.id.ivToast);
//        TextView tvToast = (TextView) toastView.findViewById(R.id.tvToast);
//        if (result) {
//            ivToast.setImageResource(R.mipmap.ic_toast_post_ok);
//        } else {
//            ivToast.setImageResource(R.mipmap.ic_toast_post_fail);
//        }
//        tvToast.setText(text);
//        Toast toast = new Toast(BybApplication.getApplication());
//        toast.setGravity(Gravity.CENTER, 0, 0);
//        toast.setDuration(millisecond);
//        toast.setView(toastView);
//        toast.show();
//    }

    /**
     * 显示文字toast
     * @param text
     */
    public static void showTextToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            Toast.makeText(GSApplication.getApplication(), text, Toast.LENGTH_SHORT).show();
        }
    }
}
