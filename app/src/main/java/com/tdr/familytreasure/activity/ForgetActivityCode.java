package com.tdr.familytreasure.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.base.BackTitleActivity;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.SendCodeSms;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：忘记密码-输入验证码
 * Create Time：2017/1/17 14:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ForgetActivityCode extends BackTitleActivity implements TextWatcher {
    private MaterialEditText mMetCode;
    private TextView mTvGetCode;
    private TextView mTvForgetStepCode;
    private String phone;
    private String verificationCode;
    private String verificationCodeID;
    private CountDownTimer timer;
    private TextView mTvPhone;



    @Override
    protected void initVariables() {
        phone = getIntent().getStringExtra("phone");

    }

    @Override
    protected void initContentView() {
        mMetCode = (MaterialEditText) findViewById(R.id.met_code);
        mTvGetCode = (TextView) findViewById(R.id.tv_getCode);
        mTvPhone = (TextView) findViewById(R.id.et_phone);
        mTvForgetStepCode = (TextView) findViewById(R.id.tv_forget_step_code);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_forget_code;
    }

    @Override
    protected void initNet() {
        startCountDownTime(60);
        getCheckCode();
    }

    @Override
    protected void initData() {
        mTvGetCode.setOnClickListener(this);
        mTvForgetStepCode.setOnClickListener(this);
        mMetCode.addTextChangedListener(this);
    }

    @Override
    protected void setData() {
        mTvPhone.setText(StringUtil.hidePhone(phone, 4));
        setTitle("忘记密码");
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_getCode:
                initNet();
                break;
            case R.id.tv_forget_step_code:
                goSetPassword();
                break;
            default:
                break;

        }
    }

    private void goSetPassword() {
        String checkCode = mMetCode.getText().toString().trim();
        if (CheckUtil.checkEmpty(checkCode, "请输入验证码") && CheckUtil.checkEquals(checkCode, verificationCode, "验证码有误,请重新输入")) {
            timer.cancel();
            ForgetActivityPassword.goActivity(this, phone, verificationCode, verificationCodeID);
        }
    }

    public static void goActivity(Activity activity, String phone) {
        Intent intent = new Intent(activity, ForgetActivityCode.class);
        intent.putExtra("phone", phone);
        activity.startActivity(intent);
    }

    private void getCheckCode() {
        if (!CheckUtil.checkPhoneFormat(phone)) {
            return;
        }
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);
        param.put("CodeType", "1");
        new ThreadPoolTask.Builder()
                .setGeneralParam("", "","SendCodeSms", param)
                .setBeanType(SendCodeSms.class)
                .setCallBack(new WebServiceCallBack<SendCodeSms>() {
                    @Override
                    public void onSuccess(SendCodeSms bean) {
                        setProgressDialog(false);
                        verificationCode = bean.getContent().getVerificationCode();
                        verificationCodeID = bean.getContent().getVerificationCodeID();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void startCountDownTime(long time) {
        mTvGetCode.setEnabled(false);
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTvGetCode.setText(millisUntilFinished / 1000 + "秒重新发送");
                Log.d(TAG, "onTick  " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mTvGetCode.setText("重新获取验证码");
                mTvGetCode.setEnabled(true);
            }
        };
        timer.start();// 开始计时
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() >0) {
            mTvForgetStepCode.setBackgroundResource(R.drawable.btn_blue_match);
        }else{
            mTvForgetStepCode.setBackgroundResource(R.drawable.btn_gray_match);
        }
    }
}
