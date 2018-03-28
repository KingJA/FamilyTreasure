package com.tdr.familytreasure.fragment;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.activity.ForgetActivityPhone;
import com.tdr.familytreasure.activity.MainCareActivity;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.Login;
import com.tdr.familytreasure.entiy.PhoneInfo;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.GoUtil;
import com.tdr.familytreasure.util.MD5;
import com.tdr.familytreasure.util.PermissionsDialogUtil;
import com.tdr.familytreasure.util.PhoneUtil;
import com.tdr.familytreasure.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
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
                    LoginFragmentPermissionsDispatcher.doLoginWithCheck(this,userName,userPwd);
                }

                break;

            case R.id.text_forgetPwd:
                Intent intent = new Intent(getActivity(), ForgetActivityPhone.class);
                startActivity(intent);
                break;
        }


    }
    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    public void doLogin(String name, String password) {
        setProgressDialog(true);
        PhoneInfo phoneInfo = new PhoneUtil(getActivity()).getInfo();
        imei = phoneInfo.getIMEI();
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
                        sendCurrentCityCode(Constants.CURRENT_CITY);
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

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void showRationaleForCamera(final PermissionRequest request) {
        PermissionsDialogUtil.showRationaleDialog(getActivity(),"需要打开相机权限", request);
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void showDeniedForCamera() {
        ToastUtil.showToast("未获取响应权限，无法继续操作");
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void showNeverAskForCamera() {
        PermissionsDialogUtil.showSettingdDialog(getContext(),"获取手机硬件信息");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
