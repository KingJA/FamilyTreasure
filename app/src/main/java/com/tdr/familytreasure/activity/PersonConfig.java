package com.tdr.familytreasure.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.widget.NormalDialog;
import com.kingja.ui.popupwindow.BottomListPop;
import com.tdr.familytreasure.R;
import com.tdr.familytreasure.amap.MapActivity;
import com.tdr.familytreasure.entiy.CheckElder;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.MessageEvent;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.SelectPicPopupWindow;
import com.tdr.familytreasure.ui.ZProgressHUD;
import com.tdr.familytreasure.ui.niftydialog.NiftyDialogBuilder;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.DataManager;
import com.tdr.familytreasure.util.GoUtil;
import com.tdr.familytreasure.util.ImageUtil;
import com.tdr.familytreasure.util.MyUtils;
import com.tdr.familytreasure.util.ToastUtil;
import com.tdr.familytreasure.util.WebServiceUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 人员配置
 * Created by Linus_Xie on 2016/8/19.
 */
public class PersonConfig extends Activity implements View.OnClickListener, BottomListPop.OnPopItemClickListener {
    private static final String TAG = "PersonConfig";
    private static final int LOCATION = 88;
    private static final int REQUEST_CAMARA = 1009;
    private static final int REQUEST_PICTURE = 1008;

    private Context mContext;


    private ZProgressHUD mProgressHUD;

    private String strPhoto = "";

    //分享老人需要的字段
    private String personType = "";//当前用户类型，0是登记人有编辑权，1是关联人无编辑权
    private String smartcareId = "";//对象ID
    private String targetType = "";//对象类型
    private String olderBase = "";//老人头像
    private String operatorName = "";//分享人姓名（=当前用户名）
    private String shareTime = "";//申报时间

    private SelectPicPopupWindow mSelectPicPopupWindow;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private final static int KEY = 0316;

    private boolean isChanged = false;//是否修改

    private String hideIdentity = "";
    private String identity = "";
    private CheckElder.ContentBean content;
    private NormalDialog quitDialog;
    private TextView tv_deviceCode;
    private TextView tv_deviceType;
    private TextView tv_activity_position;
    private TextView tv_alarm_distance;
    private TextView tv_modify_older;
    private double lat;
    private double lng;
    private FrameLayout fl_root;
    private BottomListPop mDistanceSelector;
    private String deviceId;
    private String deviceType;
    private String alarmRadius;
    private RelativeLayout rl_activity_position;
    private RelativeLayout rl_alarm_distance;
    private String name;
    private String photo;
    private String base64Avatar;
    private BottomListPop mBottomListPop;
    private FrameLayout fl_menu;
    private RelativeLayout relative_title;
    private ImageView iv_bigAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldershare);
        Constants.setBodyPhoto("");
        Log.e(TAG, "1111");
        mContext = this;
        smartcareId = getIntent().getStringExtra("smartcareId");
        targetType = getIntent().getStringExtra("targetType");
        operatorName = Constants.getUserName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        shareTime = sdf.format(new java.util.Date());
        initView();
        initNet();
    }

    private void initNet() {
        mProgressHUD.show();
        Map<String, Object> param = new HashMap<>();
        param.put("SMARTCAREID", smartcareId);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "1005", "CheckElder", param)
                .setBeanType(CheckElder.class)
                .setCallBack(new WebServiceCallBack<CheckElder>() {
                    @Override
                    public void onSuccess(CheckElder bean) {
                        content = bean.getContent();

                        mProgressHUD.dismiss();
                        initData(content);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mProgressHUD.dismiss();
                    }
                }).build().execute();
    }


    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private RelativeLayout relative_olderName, relative_bodyPhoto, relative_olderPhone, relative_olderIdentity,
            relative_olderAddress, relative_olderHealth, relative_olderRemarks;
    private TextView text_olderName, text_olderPhone, text_odlerIdentity, text_olderAddress, text_olderHealth,
            text_olderRemarks;
    private ImageView image_bodyPhoto;
    private TextView text_delGuardian1, text_guardianName1, text_guardianPhone1, text_guardianAddress1;
    private TextView text_delGuardian2, text_guardianName2, text_guardianPhone2, text_guardianAddress2;
    private TextView text_delGuardian3, text_guardianName3, text_guardianPhone3, text_guardianAddress3;
    private LinearLayout linear_guardian1, linear_guardian2, linear_guardian3;
    private RelativeLayout relative_addMoreGuardian;
    private TextView text_addMoreGuardian;

    private void initView() {
        iv_bigAvatar = (ImageView) findViewById(R.id.iv_bigAvatar);
        rl_activity_position = (RelativeLayout) findViewById(R.id.rl_activity_position);
        rl_alarm_distance = (RelativeLayout) findViewById(R.id.rl_alarm_distance);
        tv_activity_position = (TextView) findViewById(R.id.tv_activity_position);
        tv_alarm_distance = (TextView) findViewById(R.id.tv_alarm_distance);
        tv_modify_older = (TextView) findViewById(R.id.tv_modify_older);
        iv_bigAvatar.setOnClickListener(this);
        rl_activity_position.setOnClickListener(this);
        rl_alarm_distance.setOnClickListener(this);
        tv_modify_older.setOnClickListener(this);
        tv_deviceCode = (TextView) findViewById(R.id.tv_deviceCode);
        tv_deviceType = (TextView) findViewById(R.id.tv_deviceType);
        image_back = (ImageView) findViewById(R.id.fl_menu);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("人员配置");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setText("分享");
        text_deal.setVisibility(View.VISIBLE);
        text_deal.setOnClickListener(this);
        fl_root = (FrameLayout) findViewById(R.id.fl_root);
        relative_olderName = (RelativeLayout) findViewById(R.id.relative_olderName);
        relative_olderName.setOnClickListener(this);
        text_olderName = (TextView) findViewById(R.id.text_olderName);

        relative_bodyPhoto = (RelativeLayout) findViewById(R.id.relative_bodyPhoto);
        relative_bodyPhoto.setOnClickListener(this);
        image_bodyPhoto = (ImageView) findViewById(R.id.image_bodyPhoto);
        image_bodyPhoto.setOnClickListener(this);
        relative_olderPhone = (RelativeLayout) findViewById(R.id.relative_olderPhone);
        relative_olderPhone.setOnClickListener(this);
        text_olderPhone = (TextView) findViewById(R.id.text_olderPhone);

        relative_olderIdentity = (RelativeLayout) findViewById(R.id.relative_olderIdentity);
        relative_olderIdentity.setOnClickListener(this);
        text_odlerIdentity = (TextView) findViewById(R.id.text_odlerIdentity);

        relative_olderAddress = (RelativeLayout) findViewById(R.id.relative_olderAddress);
        relative_olderAddress.setOnClickListener(this);
        text_olderAddress = (TextView) findViewById(R.id.text_olderAddress);

        relative_olderHealth = (RelativeLayout) findViewById(R.id.relative_olderHealth);
        relative_olderHealth.setOnClickListener(this);
        text_olderHealth = (TextView) findViewById(R.id.text_olderHealth);

        relative_olderRemarks = (RelativeLayout) findViewById(R.id.relative_olderRemarks);
        relative_olderRemarks.setOnClickListener(this);
        text_olderRemarks = (TextView) findViewById(R.id.text_olderRemarks);

        linear_guardian1 = (LinearLayout) findViewById(R.id.linear_guardian1);
        text_delGuardian1 = (TextView) findViewById(R.id.text_delGuardian1);
        text_delGuardian1.setOnClickListener(this);
        text_guardianName1 = (TextView) findViewById(R.id.text_guardianName1);
        text_guardianPhone1 = (TextView) findViewById(R.id.text_guardianPhone1);
        text_guardianAddress1 = (TextView) findViewById(R.id.text_guardianAddress1);
        linear_guardian2 = (LinearLayout) findViewById(R.id.linear_guardian2);
        text_delGuardian2 = (TextView) findViewById(R.id.text_delGuardian2);
        text_delGuardian2.setOnClickListener(this);
        text_guardianName2 = (TextView) findViewById(R.id.text_guardianName2);
        text_guardianPhone2 = (TextView) findViewById(R.id.text_guardianPhone2);
        text_guardianAddress2 = (TextView) findViewById(R.id.text_guardianAddress2);
        linear_guardian3 = (LinearLayout) findViewById(R.id.linear_guardian3);
        text_delGuardian3 = (TextView) findViewById(R.id.text_delGuardian3);
        text_delGuardian3.setOnClickListener(this);
        text_guardianName3 = (TextView) findViewById(R.id.text_guardianName3);
        text_guardianPhone3 = (TextView) findViewById(R.id.text_guardianPhone3);
        text_guardianAddress3 = (TextView) findViewById(R.id.text_guardianAddress3);

        relative_addMoreGuardian = (RelativeLayout) findViewById(R.id.relative_addMoreGuardian);
        relative_addMoreGuardian.setOnClickListener(this);
        //text_addMoreGuardian = (TextView) findViewById(R.id.text_addMoreGuardian);
        //text_addMoreGuardian.setOnClickListener(this);
        relative_title = (RelativeLayout) findViewById(R.id.relative_title);
        mProgressHUD = new ZProgressHUD(PersonConfig.this);
        //mProgressHUD.setMessage("分享中...");
        //mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mBottomListPop = new BottomListPop(relative_title, this, Arrays.asList("拍照", "相册"));
        mBottomListPop.setOnPopItemClickListener(this);
    }

    private String[] diatanceArr = {"1KM", "2KM", "3KM"};

    private void initData(CheckElder.ContentBean bean) {
        deviceId = bean.getBINDINFO().getDEVICEID();
        deviceType = bean.getBINDINFO().getDEVTYPE();
        lng = Double.valueOf(bean.getLRPARAM().getCENTREPOINTLNG());
        lat = Double.valueOf(bean.getLRPARAM().getCENTREPOINTLAT());
        alarmRadius = bean.getLRPARAM().getRADIUS();
        //TODO
        setClickable();
        mDistanceSelector = new BottomListPop(fl_root, this, Arrays.asList(diatanceArr));
        mDistanceSelector.setOnPopItemClickListener(new BottomListPop.OnPopItemClickListener() {
            @Override
            public void onPopItemClick(int position, String tag) {
                tv_alarm_distance.setText(diatanceArr[position]);
                alarmRadius = position + 1 + "";
            }
        });
        tv_activity_position.setText("(" + lng + "," + lat + ")");
        tv_alarm_distance.setText(getAlarmDistance(alarmRadius));
        name = bean.getLRINFO().getCUSTOMERNAME();
        text_olderName.setText(name);
        photo = bean.getPHOTOINFO().getCUSTOMERPHOTO();
        image_bodyPhoto.setImageBitmap(MyUtils.stringtoBitmap(photo));
        text_olderPhone.setText(bean.getLRINFO().getCUSTOMMOBILE());
        identity = bean.getLRINFO().getCUSTOMERIDCARD();
        hideIdentity = MyUtils.hideID(identity);
        Log.e(TAG, "hideIdentity: " + hideIdentity);
        text_odlerIdentity.setText(hideIdentity);
        text_olderAddress.setText(bean.getLRINFO().getCUSTOMERADDRESS());
        text_olderHealth.setText(bean.getCUSTMERHEALTHINFO().getHEALTHCONDITION().replace(",", "  "));
        text_olderRemarks.setText(bean.getCUSTMERHEALTHINFO().getEMTNOTICE());
        tv_deviceCode.setText(deviceId);
        tv_deviceType.setText(bean.getBINDINFO().getDEVTYPENAME());
        int size = bean.getGUARDERLIST().size();
        if (size == 1) {
            text_delGuardian1.setVisibility(View.GONE);

            linear_guardian2.setVisibility(View.GONE);
            linear_guardian2.setVisibility(View.GONE);
            text_guardianName1.setText(bean.getGUARDERLIST().get(0).getGUARDIANNAME());
            text_guardianPhone1.setText(bean.getGUARDERLIST().get(0).getGUARDIANMOBILE());
            text_guardianAddress1.setText(bean.getGUARDERLIST().get(0).getGUARDIANADDRESS());
        } else if (size == 2) {
            text_delGuardian1.setVisibility(View.VISIBLE);
            text_delGuardian2.setVisibility(View.VISIBLE);

            linear_guardian2.setVisibility(View.VISIBLE);
            linear_guardian3.setVisibility(View.GONE);
            text_guardianName1.setText(bean.getGUARDERLIST().get(0).getGUARDIANNAME());
            text_guardianPhone1.setText(bean.getGUARDERLIST().get(0).getGUARDIANMOBILE());
            text_guardianAddress1.setText(bean.getGUARDERLIST().get(0).getGUARDIANADDRESS());
            text_guardianName2.setText(bean.getGUARDERLIST().get(1).getGUARDIANNAME());
            text_guardianPhone2.setText(bean.getGUARDERLIST().get(1).getGUARDIANMOBILE());
            text_guardianAddress2.setText(bean.getGUARDERLIST().get(1).getGUARDIANADDRESS());
        } else if (size == 3) {
            text_delGuardian1.setVisibility(View.VISIBLE);
            text_delGuardian2.setVisibility(View.VISIBLE);
            text_delGuardian3.setVisibility(View.VISIBLE);

            linear_guardian2.setVisibility(View.VISIBLE);
            linear_guardian3.setVisibility(View.VISIBLE);
            relative_addMoreGuardian.setVisibility(View.GONE);
            text_guardianName1.setText(bean.getGUARDERLIST().get(0).getGUARDIANNAME());
            text_guardianPhone1.setText(bean.getGUARDERLIST().get(0).getGUARDIANMOBILE());
            text_guardianAddress1.setText(bean.getGUARDERLIST().get(0).getGUARDIANADDRESS());
            text_guardianName2.setText(bean.getGUARDERLIST().get(1).getGUARDIANNAME());
            text_guardianPhone2.setText(bean.getGUARDERLIST().get(1).getGUARDIANMOBILE());
            text_guardianAddress2.setText(bean.getGUARDERLIST().get(1).getGUARDIANADDRESS());
            text_guardianName3.setText(bean.getGUARDERLIST().get(2).getGUARDIANNAME());
            text_guardianPhone3.setText(bean.getGUARDERLIST().get(2).getGUARDIANMOBILE());
            text_guardianAddress3.setText(bean.getGUARDERLIST().get(2).getGUARDIANADDRESS());
        }


    }

    private String getAlarmDistance(String code) {
        String result = "";
        switch (code) {
            case "1":
                result = "1KM";
                break;
            case "2":
                result = "2KM";
                break;
            case "3":
                result = "3KM";
                break;
            default:
                break;
        }
        return result;
    }

    private void setClickable() {
        personType = content.getPERSONTYPE();
        if (personType.equals("1")) {
            relative_olderName.setClickable(false);
            relative_bodyPhoto.setClickable(false);
            relative_olderPhone.setClickable(false);
            relative_olderIdentity.setClickable(false);
            relative_olderAddress.setClickable(false);
            relative_olderHealth.setClickable(false);
            relative_olderRemarks.setClickable(false);

            text_delGuardian1.setClickable(false);
            text_delGuardian2.setClickable(false);
            text_delGuardian3.setClickable(false);
            relative_addMoreGuardian.setClickable(false);
            rl_activity_position.setClickable(false);
            rl_alarm_distance.setClickable(false);
            image_bodyPhoto.setClickable(false);
            tv_modify_older.setVisibility(View.GONE);
            //text_addMoreGuardian.setClickable(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_bodyPhoto:
                if (!TextUtils.isEmpty(photo)) {
                    iv_bigAvatar.setImageBitmap(MyUtils.stringtoBitmap(photo));
                    iv_bigAvatar.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_bigAvatar:
                iv_bigAvatar.setVisibility(View.GONE);
                break;
            case R.id.fl_menu:
                finish();
                break;
            case R.id.tv_modify_older:
                backToSave();
                break;
            case R.id.text_deal:
                mProgressHUD.setMessage("分享中...");
                mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                mProgressHUD.show();
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("SMARTCAREID", smartcareId);
                    jsonObject.put("TARGETTYPE", "1");
                    jsonObject.put("OPERATORNAME", operatorName);
                    jsonObject.put("SHARETIME", shareTime);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("token", Constants.getToken());
                map.put("cardType", "1005");
                map.put("taskId", "");
                map.put("DataTypeCode", "Share_Target");
                map.put("content", jsonObject.toString());

                WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                        WebServiceUtils.WebServiceCallBack() {
                            @Override
                            public void callBack(String result) {
                                if (result != null) {
                                    Log.e(TAG, result);
                                    try {
                                        JSONObject json = new JSONObject(result);
                                        int resultCode = json.getInt("ResultCode");
                                        String resultText = MyUtils.initNullStr(json.getString("ResultText"));
                                        if (resultCode == 0) {
                                            String content = json.getString("Content");
                                            JSONObject object = new JSONObject(content);
                                            String guid = object.getString("SHAREID");
                                            mProgressHUD.dismiss();
                                            generateQRcode("Q3", targetType, guid);
                                            //dialogShow(0, guid, "");
                                        } else {
                                            mProgressHUD.dismiss();
                                            MyUtils.myToast(mContext, result);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        mProgressHUD.dismiss();
                                        MyUtils.myToast(mContext, "JSON解析出错");
                                    }
                                } else {
                                    mProgressHUD.dismiss();
                                    MyUtils.myToast(mContext, "获取数据错误，请稍后重试！");
                                }
                            }
                        });

                break;
            case R.id.relative_olderName:
                Intent intentOlderName = new Intent(mContext, ModifyActivity.class);
                intentOlderName.putExtra("activityName", "olderName");
                intentOlderName.putExtra("value", (String) content.getLRINFO().getCUSTOMERNAME());
                startActivityForResult(intentOlderName, KEY);
                break;

            case R.id.relative_bodyPhoto:
                //实例化SelectPicPopupWindow
//                mSelectPicPopupWindow = new SelectPicPopupWindow(PersonConfig.this, this);
//                // 显示窗口
//
//                mSelectPicPopupWindow.showAtLocation(fl_root,
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
                mBottomListPop.showPopupWindow();
                break;

            case R.id.btn_takephoto:
                Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 判断存储卡是否可以用，可用进行存储
                if (MyUtils.hasSdcard()) {
                    intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                }

                startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
                mSelectPicPopupWindow.dismiss();
                break;

            case R.id.btn_pickphoto:
                getPhoto();
                mSelectPicPopupWindow.dismiss();
                break;

            case R.id.relative_olderPhone:
                Intent intentOlderPhone = new Intent(mContext, ModifyActivity.class);
                intentOlderPhone.putExtra("activityName", "olderPhone");
                intentOlderPhone.putExtra("value", content.getLRINFO().getCUSTOMMOBILE());
                startActivityForResult(intentOlderPhone, KEY);
                break;

            case R.id.relative_olderIdentity:
                Intent intentOlderIdentity = new Intent(mContext, ModifyActivity.class);
                intentOlderIdentity.putExtra("activityName", "olderIdentity");
                intentOlderIdentity.putExtra("value", content.getLRINFO().getCUSTOMERIDCARD());
                startActivityForResult(intentOlderIdentity, KEY);
                break;

            case R.id.relative_olderAddress:
                Intent intentOlderAddress = new Intent(mContext, ModifyActivity.class);
                intentOlderAddress.putExtra("activityName", "olderAddress");
                intentOlderAddress.putExtra("value", content.getLRINFO().getCUSTOMERADDRESS());
                startActivityForResult(intentOlderAddress, KEY);
                break;

            case R.id.relative_olderHealth:
                Intent intentOlderHealth = new Intent(mContext, HealthActivity.class);
                intentOlderHealth.putExtra("value", content.getCUSTMERHEALTHINFO().getHEALTHCONDITION());
                startActivityForResult(intentOlderHealth, KEY);
                break;
            case R.id.relative_olderRemarks:
                Intent intentOlderRemarks = new Intent(mContext, ModifyActivity.class);
                intentOlderRemarks.putExtra("activityName", "olderRemarks");
                intentOlderRemarks.putExtra("value", content.getCUSTMERHEALTHINFO().getEMTNOTICE());
                startActivityForResult(intentOlderRemarks, KEY);
                break;

            case R.id.text_delGuardian1:
                //linear_guardian1.setVisibility(View.GONE);
                dialogShow(1, content.getGUARDERLIST().get(0).getGUARDIANID(), "a");
                break;

            case R.id.text_delGuardian2:
                // linear_guardian2.setVisibility(View.GONE);
                dialogShow(1, content.getGUARDERLIST().get(1).getGUARDIANID(), "b");
                break;

            case R.id.text_delGuardian3:
                //linear_guardian3.setVisibility(View.GONE);
                dialogShow(1, content.getGUARDERLIST().get(2).getGUARDIANID(), "c");
                break;

            case R.id.relative_addMoreGuardian:
                // case R.id.text_addMoreGuardian:
                Intent intentGuardian1 = new Intent(mContext, GuardianActivity.class);
                intentGuardian1.putExtra("smartcareId", content.getSMARTCAREID());
                startActivityForResult(intentGuardian1, KEY);
                break;
            case R.id.rl_activity_position:
                Intent intentl = new Intent(mContext, MapActivity.class);
                startActivityForResult(intentl, LOCATION);
                break;
            case R.id.rl_alarm_distance:
                mDistanceSelector.showPopupWindow();
                break;
        }
    }

    private void backToSave() {

        String phone = text_olderPhone.getText().toString().trim();
        if (!CheckUtil.checkPhoneFormat(phone) || !CheckUtil.checkIdCard(identity, "身份证格式错误")) {
            return;
        }
        mProgressHUD.setMessage("提交中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        JSONObject jsonObject = new JSONObject();
        try {
            JSONObject jsonLrInfo = new JSONObject();
            jsonLrInfo.put("CUSTOMERNAME", text_olderName.getText().toString().trim());
            jsonLrInfo.put("CUSTOMERIDCARD", identity);
            jsonLrInfo.put("CUSTOMMOBILE", phone);
            jsonLrInfo.put("CUSTOMERADDRESS", text_olderAddress.getText().toString().trim());

            JSONObject jsonPhoto = new JSONObject();
            jsonPhoto.put("PHOTOID", change);
            jsonPhoto.put("CUSTOMERPHOTO", TextUtils.isEmpty(Constants.getBodyPhoto()) ? content.getPHOTOINFO()
                    .getCUSTOMERPHOTO() : Constants.getBodyPhoto());

//                jsonPhoto.put("CUSTOMERPHOTO", "abc");

            JSONObject jsonHealth = new JSONObject();
            jsonHealth.put("HEALTHCONDITION", text_olderHealth.getText().toString().trim().replace("  ", ","));
            jsonHealth.put("EMTNOTICE", text_olderRemarks.getText().toString().trim());

            JSONObject bindInfoObj = new JSONObject();
            bindInfoObj.put("BINDXQCODE", "");
            bindInfoObj.put("BINDUNITNAME", "");
            bindInfoObj.put("DEVTYPE", deviceType);
            bindInfoObj.put("DEVICEID", deviceId);

            JSONObject lrParamObj = new JSONObject();
            lrParamObj.put("CENTREPOINTLNG", lng);
            lrParamObj.put("CENTREPOINTLAT", lat);
            lrParamObj.put("RADIUS", alarmRadius);

            jsonObject.put("SMARTCAREID", smartcareId);
            jsonObject.put("LRINFO", jsonLrInfo);
            jsonObject.put("PHOTOINFO", jsonPhoto);
            jsonObject.put("CUSTMERHEALTHINFO", jsonHealth);
            jsonObject.put("BINDINFO", bindInfoObj);
            jsonObject.put("LRPARAM", lrParamObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mProgressHUD.show();
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "ModifyElder");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            Log.e(TAG, result);
                            try {
                                JSONObject json = new JSONObject(result);
                                int resultCode = json.getInt("ResultCode");
                                String resultText = MyUtils.initNullStr(json.getString("ResultText"));
                                if (resultCode == 0) {
                                    EventBus.getDefault().post(new MessageEvent("MainCareActivity"));
                                    mProgressHUD.dismiss();
                                    ToastUtil.showToast("修改成功");
                                    finish();
                                } else {
                                    mProgressHUD.dismiss();
                                    MyUtils.myToast(mContext, result);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mProgressHUD.dismiss();
                                MyUtils.myToast(mContext, "JSON解析出错");
                            }
                        } else {
                            mProgressHUD.dismiss();
                            MyUtils.myToast(mContext, "获取数据错误，请稍后重试！");
                        }
                    }
                });
    }

    private void generateQRcode(String q3, String targetType, String shareId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("QRType", q3);
            jsonObject.put("Type", targetType);
            jsonObject.put("ID", shareId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "GenerateQRcode");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                JSONObject json = new JSONObject(result);
                                int resultCode = json.getInt("ResultCode");
                                String resultText = MyUtils.initNullStr(json.getString("ResultText"));
                                if (resultCode == 0) {
                                    mProgressHUD.dismiss();
                                    String content = json.getString("Content");
                                    JSONObject object = new JSONObject(content);
                                    String QRCode = object.getString("QRCode");
                                    Intent intent = new Intent(mContext, QrCodeActivity.class);
                                    intent.putExtra("code", QRCode);
                                    intent.putExtra("name", name);
                                    intent.putExtra("idcard", hideIdentity);
                                    intent.putExtra("photo", photo);
                                    mContext.startActivity(intent);
                                } else {
                                    mProgressHUD.dismiss();
                                    MyUtils.myToast(mContext, resultText);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                mProgressHUD.dismiss();
                                MyUtils.myToast(mContext, "JSON解析出错");
                            }
                        } else {
                            mProgressHUD.dismiss();
                            MyUtils.myToast(mContext, "获取数据错误，请稍后重试！");
                        }
                    }
                });

    }

    private NiftyDialogBuilder dialogBuilder;
    private NiftyDialogBuilder.Effectstype effectstype;

    private void dialogShow(final int flag, final String guid, final String tag) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(this);

        if (flag == 0) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("点击确认分享至微信")
                    .isCancelableOnTouchOutside(false).withEffect(effectstype).withButton1Text("取消")
                    .setCustomView(R.layout.custom_view, mContext).withButton2Text("确认").setButton1Click(new View
                    .OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                    Log.e(TAG, "点击确认分享至微信");
//                    Intent intent = new Intent();
//                    intent.setClass(mContext, PerfectActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("activity", TAG);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
            }).show();
        } else if (flag == 1) {
            effectstype = NiftyDialogBuilder.Effectstype.Fadein;
            dialogBuilder.withTitle("提示").withTitleColor("#333333").withMessage("是否删除此监护人")
                    .isCancelableOnTouchOutside(false).withEffect(effectstype).withButton1Text("取消")
                    .setCustomView(R.layout.custom_view, mContext).withButton2Text("确认").setButton1Click(new View
                    .OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                }
            }).setButton2Click(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogBuilder.dismiss();
                    delGuardian(guid, tag);
                }
            }).show();
        }
    }

    private void delGuardian(String guid, final String tag) {
        mProgressHUD.setMessage("删除中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();
        JSONObject json = new JSONObject();
        try {
            json.put("GUARDIANID", guid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "DelGuardian");
        map.put("content", json.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            Log.e(TAG, result);
                            try {
                                JSONObject json = new JSONObject(result);
                                int resultCode = json.getInt("ResultCode");
                                String resultText = MyUtils.initNullStr(json.getString("ResultText"));
                                if (resultCode == 0) {
                                    mProgressHUD.dismiss();
                                    initNet();
//                            if (tag.equals("a")) {
//                                linear_guardian1.setVisibility(View.GONE);
//                            } else if (tag.equals("b")) {
//                                linear_guardian2.setVisibility(View.GONE);
//                            } else {
//                                linear_guardian3.setVisibility(View.GONE);
//                            }
                                } else {
                                    mProgressHUD.dismiss();
                                    MyUtils.myToast(mContext, resultText);
                                }
                            } catch (JSONException e) {
                                mProgressHUD.dismiss();
                                e.printStackTrace();
                                MyUtils.myToast(mContext, "JSON解析出错");
                            }
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOCATION:
                if (resultCode == RESULT_OK) {
                    String address = data.getStringExtra("address");
                    lat = data.getDoubleExtra("lat", 0.0);
                    lng = data.getDoubleExtra("lng", 0.0);
                    tv_activity_position.setText("(" + lat + "," + lng + ")");
                }

                break;

            case IMAGE_REQUEST_CODE:
                if (data == null) {
                    MyUtils.myToast(mContext, "没有取到图片");
                    return;
                }
                startPhotoZoom(data.getData());
                break;
            case CAMERA_REQUEST_CODE:
                if (MyUtils.hasSdcard()) {
                    File tempFile = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    MyUtils.myToast(mContext, "未找到存储卡，无法存储照片！");
                }

                break;
            case RESULT_REQUEST_CODE:
//                if (data != null) {
//                    getImageToView(data);
//                }
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_bodyPhoto.setImageBitmap(bitmap);
                    image_bodyPhoto.setEnabled(true);
                    base64Avatar = new String(ImageUtil.bitmapToBase64(bitmap));
                    Constants.setBodyPhoto(base64Avatar);
                }
                break;
            case KEY:
                if (resultCode == RESULT_OK) {
                    String activity = data.getStringExtra("activity");
                    String result = data.getStringExtra("result");
                    if (activity.equals("olderName")) {
                        if (!result.equals(text_olderName.getText().toString().trim())) {
                            isChanged = true;
                            text_olderName.setText(result);
                        }
                    } else if (activity.equals("olderPhone")) {
                        if (!result.equals(text_olderPhone.getText().toString().trim())) {
                            isChanged = true;
                            text_olderPhone.setText(result);
                        }
                    } else if (activity.equals("olderIdentity")) {
                        if (!result.toUpperCase().equals(identity)) {
                            isChanged = true;
                            identity = result.toUpperCase();
                            text_odlerIdentity.setText(MyUtils.hideID(result));
                        }
                    } else if (activity.equals("olderAddress")) {
                        if (!result.equals(text_olderAddress.getText().toString().trim())) {
                            isChanged = true;
                            text_olderAddress.setText(result);
                        }
                    } else if (activity.equals("olderRemarks")) {
                        if (!result.equals(text_olderRemarks.getText().toString().trim())) {
                            isChanged = true;
                            text_olderRemarks.setText(result);
                        }
                    } else if (activity.equals("healthActivity")) {
                        if (!result.equals(text_olderHealth.getText().toString().trim())) {
                            isChanged = true;
                            text_olderHealth.setText(result.replace(",", "  "));
                        }
                    } else if (activity.equals("guardianActivity")) {
                        initNet();
//                        String[] result1 = result.split(",");
//                        if (linear_guardian1.getVisibility() == View.GONE) {
//                            linear_guardian1.setVisibility(View.VISIBLE);
//                            text_guardianName1.setText(result1[0]);
//                            text_guardianPhone1.setText(result1[1]);
//                            text_guardianAddress1.setText(result1[2]);
//                        } else if (linear_guardian2.getVisibility() == View.GONE) {
//                            linear_guardian2.setVisibility(View.VISIBLE);
//                            text_guardianName2.setText(result1[0]);
//                            text_guardianPhone2.setText(result1[1]);
//                            text_guardianAddress2.setText(result1[2]);
//                        } else if (linear_guardian3.getVisibility() == View.GONE) {
//                            linear_guardian3.setVisibility(View.VISIBLE);
//                            text_guardianName3.setText(result1[0]);
//                            text_guardianPhone3.setText(result1[1]);
//                            text_guardianAddress3.setText(result1[2]);
//                        }
                    }
                }
            case REQUEST_CAMARA:
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_bodyPhoto.setImageBitmap(bitmap);
                    image_bodyPhoto.setEnabled(true);
                    base64Avatar = new String(ImageUtil.bitmapToBase64(bitmap));
                    Constants.setBodyPhoto(base64Avatar);
                }
                break;
            case REQUEST_PICTURE:
                if (resultCode == RESULT_OK && data != null) {
                    startPhotoZoom(data.getData());
                }
                break;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMARA);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        // intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            String url = getPath(mContext, uri);
            intent.setDataAndType(ImageUtil.getImageContentUri(this, new File(url)), "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, 2);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param data
     */
    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(photo);
            image_bodyPhoto.setImageDrawable(drawable);
            // facePhoto = drawable.toString();
            // 保存新头像
            isChanged = true;
            strPhoto = MyUtils.Byte2Str(MyUtils.Bitmap2Bytes(photo));
            Constants.setBodyPhoto(MyUtils.Byte2Str(MyUtils.Bitmap2Bytes(photo)));
            change = "CHANGED";

        }
    }

    private String change = "";

    // 以下是关键，原本uri返回的是file:///...来着的，android4.4返回的是content:///...
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Override
    public void onPopItemClick(int position, String tag) {
        switch (position) {
            case 0:
                takePhoto();
                break;
            case 1:
                getPhoto();
                break;
        }
    }

    private void getPhoto() {
        Intent intentFromGallery = new Intent();
        intentFromGallery.setType("image/*"); // 设置文件类型
        intentFromGallery.setAction(Intent.ACTION_PICK);
        startActivityForResult(intentFromGallery, REQUEST_PICTURE);
    }
}
