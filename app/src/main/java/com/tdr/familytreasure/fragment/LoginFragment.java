package com.tdr.familytreasure.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;
import com.orhanobut.logger.Logger;
import com.tdr.familytreasure.R;
import com.tdr.familytreasure.activity.ForgetPwdActivity;
import com.tdr.familytreasure.activity.MainCareActivity;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.LoginInfo;
import com.tdr.familytreasure.entiy.PhoneInfo;
import com.tdr.familytreasure.entiy.User_LogInForKaBao;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.ui.ZProgressHUD;
import com.tdr.familytreasure.util.AppInfoUtil;
import com.tdr.familytreasure.util.CloseActivityUtil;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.DataManager;
import com.tdr.familytreasure.util.MD5;
import com.tdr.familytreasure.util.PhoneUtil;
import com.tdr.familytreasure.util.Utils;
import com.tdr.familytreasure.util.WebServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;



/**
 * 登录
 * Created by Linus_Xie on 2016/8/2.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener, Handler.Callback {

    private static final String TAG = "LoginFragment";

    private MaterialEditText material_loginName, material_loginPwd;
    private TextView text_forgetPwd;
    private Button btn_login;

    private ZProgressHUD mProgressHUD;

    private String imei = "";

    private static final int READ_PHONE_PERM = 122;

    @Override
    public View initView() {
        initProgressHUD();
        final View view = View.inflate(getActivity(), R.layout.fragment_login, null);
        material_loginName = (MaterialEditText) view.findViewById(R.id.material_loginName);
        material_loginPwd = (MaterialEditText) view.findViewById(R.id.material_loginPwd);
        text_forgetPwd = (TextView) view.findViewById(R.id.text_forgetPwd);
        text_forgetPwd.setOnClickListener(this);
        btn_login = (Button) view.findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        return view;
    }

    @Override
    public void initData() {
        PhoneInfo phoneInfo = new PhoneUtil(mActivity).getInfo();
        imei = phoneInfo.getIMEI();
        super.initData();

    }

    private void initProgressHUD() {
        mProgressHUD = new ZProgressHUD(mActivity);
        mProgressHUD.setMessage("登陆中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String userName = material_loginName.getText().toString();
                if (userName.equals("")) {
                    Utils.myToast(mActivity, "请输入用户名");
                    break;
                }
                String userPwd = material_loginPwd.getText().toString();
                if (userPwd.equals("")) {
                    Utils.myToast(mActivity, "请输入密码");
                    break;
                }

                mProgressHUD.show();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("Phone", material_loginName.getText().toString());
                    jsonObject.put("UserPassword", MD5.getMD5(material_loginPwd.getText().toString()));
                    jsonObject.put("channelid", "0123456789");
                    jsonObject.put("channeltype", "1");
                    jsonObject.put("LoginIP", "");
                    jsonObject.put("IMEI", imei);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("token", "");
                map.put("cardType", "");
                map.put("taskId", "");
                map.put("DataTypeCode", "Login");
                map.put("content", jsonObject.toString());
                Gson gson = new Gson();
                Logger.json(gson.toJson(map));
                Log.e(TAG, "Param: "+ gson.toJson(map));

                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            Log.e(TAG, result);
                            try {
                                JSONObject object = new JSONObject(result);
                                int resultCode = object.getInt("ResultCode");
                                String resultText = Utils.initNullStr(object.getString("ResultText"));
                                if (resultCode == 0) {
                                    String content = object.getString("Content");
                                    JSONObject obj = new JSONObject(content);
                                    Constants.setUserId(Utils.initNullStr(obj.getString("UserID")));
                                    Constants.setUserPhone(Utils.initNullStr(obj.getString("Phone")));
                                    Constants.setUserName(Utils.initNullStr(obj.getString("UserName")));
                                    Constants.setUserIdentitycard(Utils.initNullStr(obj.getString("IDCard")));
                                    Constants.setFaceId(Utils.initNullStr(obj.getString("FaceID")));
                                    Constants.setFaceBase(Utils.initNullStr(obj.getString("FaceBase")));
                                    Constants.setToken(Utils.initNullStr(obj.getString("token")));
                                    Constants.setCertification(Utils.initNullStr(obj.getString("Certification")));
                                    Constants.setRealName(Utils.initNullStr(obj.getString("Realname")));
                                    Constants.setPermanentAddr(Utils.initNullStr(obj.getString("Address")));
                                    String City = obj.getString("City");
                                    if (City != null) {
                                        JSONObject json = new JSONObject(City);
                                        Constants.setCityName(Utils.initNullStr(json.getString("CityName")));
                                        Constants.setCityCode(Utils.initNullStr(json.getString("CityCode")));
                                    }
                                    mProgressHUD.dismiss();
                                    goMainCareActivity();
//                                    cardLogin();
                                } else {
                                    mProgressHUD.dismiss();
                                    Utils.myToast(mActivity, resultText);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mProgressHUD.dismiss();
                                Utils.myToast(mActivity, "JSON解析出错");
                            }
                        } else {
                            mProgressHUD.dismiss();
                            Utils.myToast(mActivity, "获取数据错误，请稍后重试！");
                        }
                    }
                });
                break;

            case R.id.text_forgetPwd:
                Intent intent = new Intent(mActivity, ForgetPwdActivity.class);
                intent.putExtra("activity","");
                startActivity(intent);
                mActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
        }


    }

    private void cardLogin() {
        mProgressHUD.show();
        LoginInfo mInfo = new LoginInfo();
        PhoneInfo phoneInfo = new PhoneUtil(getActivity()).getInfo();
        mInfo.setTaskID("1");
        mInfo.setREALNAME("admin");
        mInfo.setIDENTITYCARD("33030219890530401x");
        mInfo.setPHONENUM(DataManager.getUserPhone());
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setSOFTTYPE(1);
        mInfo.setCARDTYPE("1005");
        mInfo.setPHONEINFO(phoneInfo);
        mInfo.setSOFTVERSION(AppInfoUtil.getVersionName());
        mInfo.setTaskID("1");
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "1005", "User_LogInForKaBao", mInfo)
                .setBeanType(User_LogInForKaBao.class)
                .setCallBack(new WebServiceCallBack<User_LogInForKaBao>() {
                    @Override
                    public void onSuccess(User_LogInForKaBao bean) {
                        mProgressHUD.dismiss();
                       goMainCareActivity();
                    }



                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mProgressHUD.dismiss();
                    }
                }).build().execute();
    }

    private void goMainCareActivity() {
        Intent intent = new Intent(mActivity, MainCareActivity.class);
        startActivity(intent);
        mActivity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        CloseActivityUtil.activityFinish(mActivity);
    }
    @Override
    public boolean handleMessage(Message message) {
        return false;
    }
}
