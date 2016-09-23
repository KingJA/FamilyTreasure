package com.tdr.familytreasure.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.widget.BaseAdapter;

import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.tdr.familytreasure.R;

/**
 * Description：TODO
 * Create Time：2016/8/19 16:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DialogUtil {
    public static NormalDialog getDoubleDialog(Context context, String title, String leftText, String rightText) {
        final NormalDialog dialog = new NormalDialog(context);
        dialog.isTitleShow(false)
                .content(title)
                .contentTextSize(15f)
                .bgColor(ContextCompat.getColor(context, R.color.bg_white))
                .cornerRadius(5)
                .contentGravity(Gravity.CENTER)
                .widthScale(0.85f)
                .contentTextColor(ContextCompat.getColor(context, R.color.font_3))
                .dividerColor(ContextCompat.getColor(context, R.color.bg_divider))
                .btnTextSize(15f, 15f)
                .btnText(leftText, rightText)
                .btnTextColor(ContextCompat.getColor(context, R.color.bg_blue), ContextCompat.getColor(context, R.color.bg_blue))
                .btnPressColor(ContextCompat.getColor(context, R.color.bg_press));
        return dialog;
    }

    public static NormalListDialog getListDialog(Context context, String title, BaseAdapter adapter) {
        final NormalListDialog dialog = new NormalListDialog(context, adapter);
        dialog.title(title).layoutAnimation(null).heightScale(0.5f)
                .titleTextColor(ContextCompat.getColor(context, R.color.font_3))
                .titleBgColor(ContextCompat.getColor(context, R.color.bg_light))
                .dividerHeight(0.3f)
                .dividerColor(ContextCompat.getColor(context, R.color.bg_divider))
                .show();
        return dialog;
    }

    public static void showQuitDialog(final Activity activity) {
        final NormalDialog dialog = new NormalDialog(activity);
        dialog.isTitleShow(false)
                .content("是否退出当前页面")
                .contentTextSize(15f)
                .bgColor(ContextCompat.getColor(activity, R.color.bg_white))
                .cornerRadius(5)
                .contentGravity(Gravity.CENTER)
                .widthScale(0.85f)
                .contentTextColor(ContextCompat.getColor(activity, R.color.font_3))
                .dividerColor(ContextCompat.getColor(activity, R.color.bg_divider))
                .btnTextSize(15f, 15f)
                .btnText("取消", "确定")
                .btnTextColor(ContextCompat.getColor(activity, R.color.bg_blue), ContextCompat.getColor(activity, R.color.bg_blue))
                .btnPressColor(ContextCompat.getColor(activity, R.color.bg_press))
                .show();
        dialog.setOnBtnClickL(new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                dialog.dismiss();
                activity.finish();
            }
        });
    }
}
