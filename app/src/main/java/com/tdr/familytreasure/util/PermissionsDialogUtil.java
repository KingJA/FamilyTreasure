package com.tdr.familytreasure.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import permissions.dispatcher.PermissionRequest;

import static com.tdr.familytreasure.util.AppInfoUtil.getPackageName;

/**
 * Description:TODO
 * Create Time:2017/8/14 16:29
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class PermissionsDialogUtil {
    public static void showSettingdDialog(final Context context, String message) {
        new AlertDialog.Builder(context)
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings(context);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .setMessage(String.format("当前应用缺少%s权限\n请点击\"设置\"-\"权限\"打开全选\n最后点击两次返回按钮即可返回应用", message))
                .show();
    }

    public static void showRationaleDialog(Context context,String message, final PermissionRequest request) {
        new AlertDialog.Builder(context)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(@NonNull DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage(message)
                .show();
    }

    private  static void startAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getPackageName(), null));
        context.startActivity(intent);
    }
}
