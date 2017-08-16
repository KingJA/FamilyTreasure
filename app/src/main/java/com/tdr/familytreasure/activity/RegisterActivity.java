package com.tdr.familytreasure.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.base.BackTitleActivity;
import com.tdr.familytreasure.entiy.AddUsers;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.Login;
import com.tdr.familytreasure.entiy.PhoneInfo;
import com.tdr.familytreasure.entiy.SendCodeSms;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.util.ActivityManager;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.GoUtil;
import com.tdr.familytreasure.util.MD5;
import com.tdr.familytreasure.util.MyUtils;
import com.tdr.familytreasure.util.PhoneUtil;
import com.tdr.familytreasure.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends BackTitleActivity implements View.OnClickListener {
    private MaterialEditText mMetCode;
    private TextView mBtnGetCode;
    private MaterialEditText mMetPassword;
    private Button mBtnRegister;
    private String phone;
    private String verificationCode;
    private String verificationCodeID;
    private CountDownTimer timer;
    private boolean mIsSending;
    private String imei;
    private MaterialEditText mMetPasswordRepeat;


    @Override
    protected void initVariables() {
        PhoneInfo phoneInfo = new PhoneUtil(this).getInfo();
        imei = phoneInfo.getIMEI();
        phone = getIntent().getStringExtra("phone");

    }

    @Override
    protected void initContentView() {
        mMetCode = (MaterialEditText) findViewById(R.id.met_code);
        mBtnGetCode = (TextView) findViewById(R.id.btn_getCode);
        mMetPassword = (MaterialEditText) findViewById(R.id.met_password);
        mMetPasswordRepeat = (MaterialEditText) findViewById(R.id.met_passwordRepeat);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initNet() {
        getCode();
    }

    private void getCode() {
        startCountDownTime(60);
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);
        param.put("CodeType", "2");
        new ThreadPoolTask.Builder()
                .setGeneralParam("", "", "SendCodeSms", param)
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

    @Override
    protected void initData() {
        mBtnGetCode.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        setTitle("注册");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_getCode:
                getCode();
                break;
            case R.id.btn_register:
                doRegister();
                break;

        }
    }

    public static void goAcivity(Context context, String phone) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra("phone", phone);
        context.startActivity(intent);
    }

    private void startCountDownTime(long time) {
        mIsSending = true;
        mBtnGetCode.setEnabled(false);
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mBtnGetCode.setText(millisUntilFinished / 1000 + "秒重新发送");
            }

            @Override
            public void onFinish() {
                mBtnGetCode.setText("重新获取验证码");
                mBtnGetCode.setEnabled(true);
                mIsSending = false;
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

    private void doRegister() {
        String checkCode = mMetCode.getText().toString().trim();
        final String password = mMetPassword.getText().toString().trim();
        final String passwordRepeat = mMetPasswordRepeat.getText().toString().trim();
        if (!(CheckUtil.checkEmpty(checkCode, "请输入验证码") &&
                CheckUtil.checkEquals(checkCode, verificationCode, "验证码不正确") &&
                CheckUtil.checkEmpty(password, "请输入密码") &&
                CheckUtil.checkPasswordFormat(password, 6, 12, "") &&
                CheckUtil.checkEmpty(passwordRepeat, "请输入重复密码") &&
                CheckUtil.checkEquals(password, passwordRepeat, "两次密码不一样"))) {
            return;
        }

        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("Phone", phone);
        param.put("UserName", "");
        param.put("UserPassword", MD5.getMD5(password));
        param.put("VerificationCode", verificationCode);
        param.put("VerificationCodeID", verificationCodeID);
        param.put("IsValid", "1");
        new ThreadPoolTask.Builder()
                .setGeneralParam("", "", "AddUsers", param)
                .setBeanType(AddUsers.class)
                .setCallBack(new WebServiceCallBack<AddUsers>() {
                    @Override
                    public void onSuccess(AddUsers bean) {
                        setProgressDialog(false);
                        ToastUtil.showToast("注册成功");
                        doLogin(phone, MD5.getMD5(password));
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void doLogin(String name, String password) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("Phone", name);
        param.put("UserPassword", password);
        param.put("ChannelID", "0123456789");
        param.put("ChannelType", "1");
        param.put("LoginIP", "");
        param.put("IMEI", imei);
        new ThreadPoolTask.Builder()
                .setGeneralParam("", "", "Login", param)
                .setBeanType(Login.class)
                .setCallBack(new WebServiceCallBack<Login>() {
                    @Override
                    public void onSuccess(Login bean) {

                        setProgressDialog(false);
                        save2Local(bean.getContent());
                        sendCurrentCityCode(Constants.CURRENT_CITY);
                        ActivityManager.getAppManager().finishAllActivity();
                        GoUtil.goActivityAndFinish(RegisterActivity.this, MainCareActivity.class);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void sendCurrentCityCode(final String cityCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("CityCode", cityCode);
        new ThreadPoolTask.Builder()
                .setGeneralParam(Constants.getToken(), "", "EditCurrentCity", param)
                .setBeanType(Object.class)
                .setCallBack(new WebServiceCallBack<Object>() {
                    @Override
                    public void onSuccess(Object bean) {
                        Log.e(TAG, "onSuccess: " + "城市设置成功");
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void save2Local(Login.ContentBean content) {
        Constants.setUserId(MyUtils.initNullStr(content.getUserID()));
        Constants.setUserPhone(MyUtils.initNullStr(content.getPhone()));
        Constants.setUserName(MyUtils.initNullStr(content.getUserName()));
        Constants.setUserIdentitycard(MyUtils.initNullStr(content.getIDCard()));
        Constants.setFaceId(MyUtils.initNullStr(content.getFaceID()));
        Constants.setFaceBase(MyUtils.initNullStr(content.getFaceBase()));
        Constants.setToken(MyUtils.initNullStr(content.getToken()));
        Constants.setCertification(MyUtils.initNullStr(content.getCertification() + ""));
        Constants.setRealName(MyUtils.initNullStr(content.getRealname()));
        Constants.setPermanentAddr(MyUtils.initNullStr(content.getAddress()));
        if (content.getCity() != null) {
            Constants.setCityName(MyUtils.initNullStr(content.getCity().getCityName()));
            Constants.setCityCode(MyUtils.initNullStr(content.getCity().getCityCode()));
        }
    }
}
