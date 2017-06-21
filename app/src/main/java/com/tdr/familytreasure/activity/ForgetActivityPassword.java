package com.tdr.familytreasure.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.base.BackTitleActivity;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.util.ActivityManager;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.GoUtil;
import com.tdr.familytreasure.util.MD5;
import com.tdr.familytreasure.util.StringUtil;
import com.tdr.familytreasure.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：忘记密码-设置新密码
 * Create Time：2017/1/17 14:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ForgetActivityPassword extends BackTitleActivity {

    private MaterialEditText mMetPassword;
    private MaterialEditText mMetRepeatPassword;
    private TextView mTvConfirm;
    private TextView mTvPhone;
    private String verificationCode;
    private String verificationCodeID;
    private String phone;


    @Override
    protected void initVariables() {
        phone = getIntent().getStringExtra("phone");
        verificationCode = getIntent().getStringExtra("verificationCode");
        verificationCodeID = getIntent().getStringExtra("verificationCodeID");

    }

    @Override
    protected void initContentView() {
        mMetPassword = (MaterialEditText) findViewById(R.id.met_password);
        mMetRepeatPassword = (MaterialEditText) findViewById(R.id.met_repeat_password);
        mTvConfirm = (TextView) findViewById(R.id.tv_confirm);
        mTvPhone = (TextView) findViewById(R.id.et_phone);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    @Override
    protected void setData() {
        mTvPhone.setText(StringUtil.hidePhone(phone, 4));
        setTitle("忘记密码");
    }

    public static void goActivity(Activity activity, String phone, String verificationCode, String verificationCodeID) {
        Intent intent = new Intent(activity, ForgetActivityPassword.class);
        intent.putExtra("phone", phone);
        intent.putExtra("verificationCode", verificationCode);
        intent.putExtra("verificationCodeID", verificationCodeID);
        activity.startActivity(intent);
    }

    private void resetPassword() {
        String password = mMetPassword.getText().toString().trim();
        String repeatPassword = mMetRepeatPassword.getText().toString().trim();
        if (!(CheckUtil.checkPasswordFormat(password, 6, 12, "") &&
                CheckUtil.checkEmpty(repeatPassword, "请输入重复密码") &&
                CheckUtil.checkEquals(password, repeatPassword, "两次输入密码不一样，请重新输入"))) {
            return;
        }
        password = MD5.getMD5(password);

        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("Phone", phone);
        param.put("UserName", "");
        param.put("UserPassword", password);
        param.put("VerificationCode", verificationCode);
        param.put("VerificationCodeID", verificationCodeID);
        param.put("IsValid", "1");
        new ThreadPoolTask.Builder()
                .setGeneralParam("", "", "ChangePassword", param)
                .setBeanType(Object.class)
                .setCallBack(new WebServiceCallBack<Object>() {
                    @Override
                    public void onSuccess(Object bean) {
                        ToastUtil.showToast("密码修改成功");
                        setProgressDialog(false);
                        ActivityManager.getAppManager().finishAllActivity();
                        GoUtil.goActivity(ForgetActivityPassword.this, LoginActivity.class);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }
}
