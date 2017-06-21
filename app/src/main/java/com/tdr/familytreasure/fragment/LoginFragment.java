package com.tdr.familytreasure.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tdr.familytreasure.R;
import com.tdr.familytreasure.activity.ForgetActivityPhone;
import com.tdr.familytreasure.activity.ForgetPwdActivity;
import com.tdr.familytreasure.activity.LoginActivity;
import com.tdr.familytreasure.activity.MainCareActivity;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.Login;
import com.tdr.familytreasure.entiy.PhoneInfo;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.ui.ZProgressHUD;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.CloseActivityUtil;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.GoUtil;
import com.tdr.familytreasure.util.MD5;
import com.tdr.familytreasure.util.PhoneUtil;
import com.tdr.familytreasure.util.Utils;
import com.tdr.familytreasure.util.WebServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = "LoginFragment";

    private MaterialEditText material_loginName, material_loginPwd;
    private TextView text_forgetPwd;
    private Button btn_login;
    private String imei = "";

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String userName = material_loginName.getText().toString();
                String userPwd = material_loginPwd.getText().toString();
                if (CheckUtil.checkEmpty(userName, "请输入用户名") && CheckUtil.checkEmpty(userName, "请输入密码")) {
                    doLogin(userName, userPwd);
                }

                break;

            case R.id.text_forgetPwd:
                Intent intent = new Intent(getActivity(), ForgetActivityPhone.class);
                startActivity(intent);
                break;
        }


    }

    private void doLogin(String name, String password) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("Phone", name);
        param.put("UserPassword", MD5.getMD5(password));
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
                        save2Local(bean.getContent());
                        setProgressDialog(false);
                        sendCurrentCityCode("3303");
                        GoUtil.goActivityAndFinish(getActivity(), MainCareActivity.class);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void save2Local(Login.ContentBean content) {
        Constants.setUserId(content.getUserID());
        Constants.setUserPhone(content.getPhone());
        Constants.setUserName(content.getUserName());
        Constants.setUserIdentitycard(content.getIDCard());
        Constants.setFaceId(content.getFaceID());
        Constants.setFaceBase(content.getFaceBase());
        Constants.setToken(content.getToken());
        Constants.setCertification(content.getCertification() + "");
        Constants.setRealName(content.getRealname());
        Constants.setPermanentAddr(content.getAddress());
        Login.ContentBean.CityBean city = content.getCity();
        if (city != null) {
            Constants.setCityName(city.getCityName());
            Constants.setCityCode(city.getCityCode());
        }
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

    @Override
    public int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        material_loginName = (MaterialEditText) view.findViewById(R.id.material_loginName);
        material_loginPwd = (MaterialEditText) view.findViewById(R.id.material_loginPwd);
        text_forgetPwd = (TextView) view.findViewById(R.id.text_forgetPwd);
        btn_login = (Button) view.findViewById(R.id.btn_login);
    }

    @Override
    public void initFragmentVariables() {
        PhoneInfo phoneInfo = new PhoneUtil(getActivity()).getInfo();
        imei = phoneInfo.getIMEI();
    }

    @Override
    public void initFragmentNet() {

    }

    @Override
    public void initFragmentData() {
        text_forgetPwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void setFragmentData() {

    }
}
