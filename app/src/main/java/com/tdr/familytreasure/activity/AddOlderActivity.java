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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.flyco.dialog.widget.NormalDialog;
import com.kingja.ui.popupwindow.BottomListPop;
import com.tdr.familytreasure.R;
import com.tdr.familytreasure.amap.MapActivity;
import com.tdr.familytreasure.dao.DbDaoXutils3;
import com.tdr.familytreasure.entiy.Basic_PaiChuSuo_Kj;
import com.tdr.familytreasure.entiy.Basic_XingZhengQuHua_Kj;
import com.tdr.familytreasure.entiy.BindInfo;
import com.tdr.familytreasure.entiy.GuardianInfo;
import com.tdr.familytreasure.entiy.OlderInfo;
import com.tdr.familytreasure.ui.PaichusuoBottomWheelView;
import com.tdr.familytreasure.ui.SelectPicPopupWindow;
import com.tdr.familytreasure.ui.ZProgressHUD;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.DialogUtil;
import com.tdr.familytreasure.util.ImageUtil;
import com.tdr.familytreasure.util.MyUtils;
import com.tdr.familytreasure.util.WebServiceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tdr.familytreasure.R.id.image_bodyPhoto;

/**
 * 添加老人
 * Created by Linus_Xie on 2016/8/16.
 */
public class AddOlderActivity extends Activity implements View.OnClickListener, BottomListPop.OnPopItemClickListener {
    private static final String TAG = "AddOlderActivity";
    private static final int REQUEST_CAMARA = 1005;
    private static final int REQUEST_PICTURE = 1008;
    private Context mContext;

    private SelectPicPopupWindow mSelectPicPopupWindow;

    /* 头像名称 */
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    /* 请求码 */
    private static final int IMAGE_REQUEST_CODE = 0;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;

    private String alarmDistance = "";//预警距离
    private String hypertension = " ";//高血压
    private String diabetes = " ";//糖尿病
    private String heartdisease = " ";//心脏病

    //获取老人活动中心点的回调值
    private final static int LOCPOSITION = 1991;

    private String address = "";
    private double lat = 0.0;//纬度
    private double lng = 0.0;//经度

    private OlderInfo mInfo = new OlderInfo();
    private GuardianInfo mGuardianInfo = new GuardianInfo();
    private BindInfo mBindInfo = new BindInfo();

    private List<String> disease = new ArrayList<>();

    private String SmartId = "";
    private ZProgressHUD mProgressHUD;
    private RelativeLayout rl_lovedMovementArea;
    private NormalDialog quitDialog;
    private LinearLayout ll_select_area;
    private LinearLayout ll_select_police;
    private TextView tv_add_area;
    private TextView tv_add_police;
    private String mPaichusuoCDZM;
    private PaichusuoBottomWheelView mPaichusuoBottomWheelView;
    private List<Basic_PaiChuSuo_Kj> paichusuoDbList;
    private TextView tv_add_watchType;
    private TextView tv_deviceType;
    private String deviceType;
    private String deviceName;
    private String base64Avatar;
    private BottomListPop mBottomListPop;
    private RelativeLayout relative_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: ");
        setContentView(R.layout.activity_addoler);

        mContext = this;
//        initAreaPop();
        initView();
        String deviceCode = getIntent().getStringExtra("deviceCode");
        deviceType = getIntent().getStringExtra("deviceType");
        deviceName = getIntent().getStringExtra("deviceName");
        SmartId = getIntent().getStringExtra("SmartId");
        text_code.setText(deviceCode);
        tv_deviceType.setText(deviceName);

        mBindInfo.setDEVICEID(deviceCode);
        mBindInfo.setDEVTYPE(deviceType);
        initData();
    }

    private void initData() {
        if (SmartId.equals("")) {
            Log.e(TAG, "不处理");
        } else {
            mProgressHUD.show();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("SMARTCAREID", SmartId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("token", Constants.getToken());
            map.put("cardType", "1005");
            map.put("taskId", "");
            map.put("DataTypeCode", "CheckElderUnregedit");
            map.put("content", jsonObject.toString());

            WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                    WebServiceUtils.WebServiceCallBack() {
                        @Override
                        public void callBack(String result) {
                            if (result != null) {
                                Log.e(TAG + "对象列表：", result);
                                try {
                                    JSONObject jsonObject = new JSONObject(result);
                                    int resultCode = jsonObject.getInt("ResultCode");
                                    String resultText = MyUtils.initNullStr(jsonObject.getString("ResultText"));
                                    if (resultCode == 0) {
                                        String content = jsonObject.getString("Content");
                                        JSONObject json = new JSONObject(content);
                                        String lrInfo = json.getString("LRINFO");
                                        JSONObject lrObj = new JSONObject(lrInfo);
                                        edit_lovedName.setText(MyUtils.initNullStr(lrObj.getString("CUSTOMERNAME")));
                                        edit_lovedIdentity.setText(MyUtils.initNullStr(lrObj.getString
                                                ("CUSTOMERIDCARD")));
                                        edit_lovedPhone.setText(MyUtils.initNullStr(lrObj.getString("CUSTOMMOBILE")));
                                        edit_lovedAddress.setText(MyUtils.initNullStr(lrObj.getString
                                                ("CUSTOMERADDRESS")));

                                        String guardianInfo = json.getString("GUARDIANINFO");
                                        JSONArray array = new JSONArray(guardianInfo);
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject object = array.getJSONObject(0);
                                            mGuardianInfo.setGuardianName(MyUtils.initNullStr(object.getString
                                                    ("GUARDIANNAME")));
                                            mGuardianInfo.setGuardianIdCard(MyUtils.initNullStr(object.getString
                                                    ("GUARDIANIDCARD")));
                                            mGuardianInfo.setGuardianMobile(MyUtils.initNullStr(object.getString
                                                    ("GUARDIANMOBILE")));
                                            mGuardianInfo.setGuardianAddress(MyUtils.initNullStr(object.getString
                                                    ("GUARDIANADDRESS")));
                                            mGuardianInfo.setEnmergencyCall(MyUtils.initNullStr(object.getString
                                                    ("ENMERGENCYCALL")));
                                        }
                                        mProgressHUD.dismiss();
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
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private TextView text_code, text_lovedMovementArea;
    private EditText edit_lovedName, edit_lovedIdentity, edit_lovedPhone, edit_lovedAddress, edit_remarks;
    private RadioGroup radio_alarmDistance, radio_illnessDesc;
    private RadioButton radio_oneKM, radio_twoKM, radio_threeKM;
    private ImageView image_loc, image_bodyphoto;
    private CheckBox check_hypertension, check_diabetes, check_heartdisease;
    private Map<String, List<String>> paichusuoMap = new HashMap<>();

    private void initView() {
        tv_add_watchType = (TextView) findViewById(R.id.tv_add_watchType);
        tv_add_area = (TextView) findViewById(R.id.tv_add_area);
        tv_add_police = (TextView) findViewById(R.id.tv_add_police);
        tv_deviceType = (TextView) findViewById(R.id.tv_deviceType);
        ll_select_area = (LinearLayout) findViewById(R.id.ll_select_area);
        ll_select_police = (LinearLayout) findViewById(R.id.ll_select_police);
        relative_title = (RelativeLayout) findViewById(R.id.relative_title);
        image_back = (ImageView) findViewById(R.id.fl_menu);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("添加老人");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setOnClickListener(this);
        ll_select_area.setOnClickListener(this);
        ll_select_police.setOnClickListener(this);
        text_deal.setVisibility(View.VISIBLE);

        text_code = (TextView) findViewById(R.id.text_code);
        text_lovedMovementArea = (TextView) findViewById(R.id.text_lovedMovementArea);
        rl_lovedMovementArea = (RelativeLayout) findViewById(R.id.rl_lovedMovementArea);
        edit_lovedName = (EditText) findViewById(R.id.edit_lovedName);
        edit_lovedIdentity = (EditText) findViewById(R.id.edit_lovedIdentity);
        edit_lovedPhone = (EditText) findViewById(R.id.edit_lovedPhone);
        edit_lovedAddress = (EditText) findViewById(R.id.edit_lovedAddress);
        edit_remarks = (EditText) findViewById(R.id.edit_remarks);

        image_loc = (ImageView) findViewById(R.id.image_loc);
        image_loc.setOnClickListener(this);
        rl_lovedMovementArea.setOnClickListener(this);
        radio_alarmDistance = (RadioGroup) findViewById(R.id.radio_alarmDistance);
        radio_oneKM = (RadioButton) findViewById(R.id.radio_oneKM);
        radio_oneKM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmDistance = "1";
                }
            }
        });
        radio_twoKM = (RadioButton) findViewById(R.id.radio_twoKM);
        radio_twoKM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmDistance = "2";
                }
            }
        });
        radio_threeKM = (RadioButton) findViewById(R.id.radio_threeKM);
        radio_threeKM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    alarmDistance = "3";
                }
            }
        });
        image_bodyphoto = (ImageView) findViewById(R.id.image_bodyphoto);
        image_bodyphoto.setOnClickListener(this);

        radio_illnessDesc = (RadioGroup) findViewById(R.id.radio_illnessDesc);
        check_hypertension = (CheckBox) findViewById(R.id.check_hypertension);
        check_hypertension.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    hypertension = "高血压";
                    disease.add(hypertension);
                } else {
                    hypertension = "";
                    disease.remove("高血压");
                }
            }
        });
        check_diabetes = (CheckBox) findViewById(R.id.check_diabetes);
        check_diabetes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    diabetes = "糖尿病";
                    disease.add(diabetes);
                } else {
                    diabetes = "";
                    disease.remove("糖尿病");
                }
            }
        });
        check_heartdisease = (CheckBox) findViewById(R.id.check_heartdisease);
        check_heartdisease.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    heartdisease = "心脏病";
                    disease.add(heartdisease);
                } else {
                    heartdisease = "";
                    disease.add("心脏病");
                }
            }
        });

        mProgressHUD = new ZProgressHUD(mContext);
        mProgressHUD.setMessage("请求数据中...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mBottomListPop = new BottomListPop(relative_title, this, Arrays.asList("拍照", "相册"));
        mBottomListPop.setOnPopItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_menu:
                DialogUtil.showQuitDialog(this);
                break;

            case R.id.rl_lovedMovementArea:

                Log.e(TAG, "onClick1: ");
                Intent intentl = new Intent(mContext, MapActivity.class);
                startActivityForResult(intentl, LOCPOSITION);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.text_deal:
                String lovedName = edit_lovedName.getText().toString().trim();
                if (lovedName.equals("")) {
                    MyUtils.myToast(mContext, "请输入被关爱人姓名");
                    break;
                }
                String lovedIdentity = edit_lovedIdentity.getText().toString().toUpperCase().trim();
//                int length = lovedIdentity.length();
//                if (length == 18) {//出现15位身份证就直接过去
//                    if (lovedIdentity.equals("")) {
//                        MyUtils.myToast(mContext, "请输入身份证号码");
//                        break;
//                    }
//                    if (!MyUtils.isIDCard18(lovedIdentity)) {
//                        MyUtils.myToast(mContext, "请输入正确身份证号");
//                        break;
//                    }
//                }
                if (!CheckUtil.checkIdCard(lovedIdentity, "身份证格式错误")) {
                    break;
                }
                String phone = edit_lovedPhone.getText().toString().toUpperCase().trim();
                if (!CheckUtil.checkPhoneFormat(phone)) {
                    break;
                }

                String lovedAddress = edit_lovedAddress.getText().toString().trim();
                if (lovedAddress.equals("")) {
                    MyUtils.myToast(mContext, "请输入联系地址");
                    break;
                }

                String lovedMovementArea = text_lovedMovementArea.getText().toString().trim();
                if (lovedMovementArea.equals("")) {
                    MyUtils.myToast(mContext, "请选择关爱人活动中心点");
                    break;
                }

                if (alarmDistance.equals("")) {
                    MyUtils.myToast(mContext, "请选择关爱人预警距离");
                    break;
                }

                String bodyPhoto = Constants.getBodyPhoto();
                if (bodyPhoto.equals("")) {
                    MyUtils.myToast(mContext, "请拍摄被关爱人半身照");
                    break;
                }

                mInfo.setCareNumber(text_code.getText().toString().trim());
                mInfo.setCustomerName(edit_lovedName.getText().toString().trim());
                mInfo.setCustomerIdCard(edit_lovedIdentity.getText().toString().trim());
                mInfo.setCustomMobile(edit_lovedPhone.getText().toString().trim());
                mInfo.setCustomerAddress(edit_lovedAddress.getText().toString().trim());
                mInfo.setMovementArea(text_lovedMovementArea.getText().toString());
                mInfo.setCentrePointLng(String.valueOf(lng));
                mInfo.setCentrePointLat(String.valueOf(lat));
                mInfo.setRadius(alarmDistance);
                mInfo.setHealthCondition(MyUtils.listToString(disease));
                mInfo.setEmtnotice(edit_remarks.getText().toString().trim());

                Intent intent1 = new Intent(AddOlderActivity.this, AddGuardianActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("olderInfo", (Serializable) mInfo);
                bundle.putSerializable("guardianInfo", (Serializable) mGuardianInfo);
                bundle.putSerializable("bindInfo", (Serializable) mBindInfo);
                intent1.putExtra("bundle", bundle);
                startActivity(intent1);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.image_loc:
                Log.e(TAG, "onClick: ");
                Intent intent = new Intent(mContext, MapActivity.class);
                startActivityForResult(intent, LOCPOSITION);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;

            case R.id.image_bodyphoto:
//                // 实例化SelectPicPopupWindow
//                mSelectPicPopupWindow = new SelectPicPopupWindow(AddOlderActivity.this, this);
//                // 显示窗口
//                mSelectPicPopupWindow.showAtLocation(AddOlderActivity.this.findViewById(R.id.fl_root),
//                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置

//                takePhoto();
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
                Intent intentFromGallery = new Intent();
                intentFromGallery.setType("image/*"); // 设置文件类型
                intentFromGallery.setAction(Intent.ACTION_PICK);
                startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);
                mSelectPicPopupWindow.dismiss();
                break;
            case R.id.ll_select_police:
//                if (TextUtils.isEmpty(mPaichusuoCDZM)) {
//                    ToastUtil.showToast("请选择辖区");
//                }else{
//                    initPolicePop();
//                }
                break;
            case R.id.ll_select_area:
//                mPaichusuoBottomWheelView.showPopupWindow();
                break;
        }
    }

    private void initPolicePop() {
        List<Basic_PaiChuSuo_Kj> selectPaichusuoList = new ArrayList<>();
        for (Basic_PaiChuSuo_Kj bean : paichusuoDbList) {
            if (bean.getDMZM().startsWith(mPaichusuoCDZM)) {
                selectPaichusuoList.add(bean);
            }
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMARA);
    }

    private void initAreaPop() {
        List<Basic_XingZhengQuHua_Kj> xingZhengQuHuaDbList = (List<Basic_XingZhengQuHua_Kj>) DbDaoXutils3.getInstance
                ().sleectAllDb(Basic_XingZhengQuHua_Kj.class);
        paichusuoDbList = (List<Basic_PaiChuSuo_Kj>) DbDaoXutils3.getInstance().sleectAllDb(Basic_PaiChuSuo_Kj.class);

        mPaichusuoBottomWheelView = new PaichusuoBottomWheelView(ll_select_area, this, xingZhengQuHuaDbList);
        mPaichusuoBottomWheelView.setOnPopItemClickListener(new PaichusuoBottomWheelView.OnPopItemClickListener() {
            @Override
            public void onPopItemClick(String id, String tag) {
                mPaichusuoCDZM = id;
                tv_add_area.setText(tag);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
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
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_bodyphoto.setImageBitmap(bitmap);
                    image_bodyphoto.setEnabled(true);
                    base64Avatar = new String(ImageUtil.bitmapToBase64(bitmap));
                    Constants.setBodyPhoto(base64Avatar);
                }
                break;
            case LOCPOSITION:
                if (resultCode == RESULT_OK) {
                    address = data.getStringExtra("address");
                    text_lovedMovementArea.setText(address);
                    lat = data.getDoubleExtra("lat", 0.0);
                    lng = data.getDoubleExtra("lng", 0.0);
                    Log.e(TAG + "取到坐标：", address + lat + lng);
                }

                break;
            case REQUEST_CAMARA:
                if (resultCode == RESULT_OK) {
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    image_bodyphoto.setImageBitmap(bitmap);
                    image_bodyphoto.setEnabled(true);
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
            image_bodyphoto.setImageDrawable(drawable);
            // facePhoto = drawable.toString();
            // 保存新头像
            Constants.setBodyPhoto(MyUtils.Byte2Str(MyUtils.Bitmap2Bytes(photo)));

        }
    }

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
    public void onBackPressed() {
        DialogUtil.showQuitDialog(this);
    }


    public static void goActivity(Context context, String deviceCode, String deviceType, String deviceName, String
            SmartId) {
        Intent intent = new Intent(context, AddOlderActivity.class);
        intent.putExtra("deviceCode", deviceCode);
        intent.putExtra("deviceType", deviceType);
        intent.putExtra("deviceName", deviceName);
        intent.putExtra("SmartId", SmartId);
        context.startActivity(intent);
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
