package com.tdr.familytreasure.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.daimajia.swipe.util.Attributes;
import com.google.gson.Gson;
import com.kingja.ui.popupwindow.BottomListPop;
import com.orhanobut.logger.Logger;
import com.tdr.familytreasure.R;
import com.tdr.familytreasure.adapter.ImagePagerAdapter;
import com.tdr.familytreasure.adapter.MainCareAdapter;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.MessageEvent;
import com.tdr.familytreasure.entiy.OlderInfo;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.CircleFlowIndicator;
import com.tdr.familytreasure.ui.ViewFlow;
import com.tdr.familytreasure.ui.ZProgressHUD;
import com.tdr.familytreasure.ui.niftydialog.NiftyDialogBuilder;
import com.tdr.familytreasure.util.AppUtil;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.GoUtil;
import com.tdr.familytreasure.util.MyUtils;
import com.tdr.familytreasure.util.WebServiceUtils;
import com.tdr.familytreasure.util.ZeusManager;
import com.tdr.familytreasure.zbar.CaptureActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 老人关爱主界面
 * Created by Linus_Xie on 2016/8/15.
 */
public class MainCareActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener,
        ZeusManager.OnPermissionCallback, BottomListPop.OnPopItemClickListener {
    private static final String TAG = "MainCareActivity";

    private final static int SCANNIN_GREQUEST_CODE = 2002;

    private Context mContext;

    private int mCurrPos;
    private ArrayList<String> imageUrlList = new ArrayList<String>();
    private ArrayList<String> linkUrlArray = new ArrayList<String>();
    private ArrayList<String> titleList = new ArrayList<String>();

    private List<OlderInfo> listOlder = new ArrayList<>();

    private MainCareAdapter mainCareAdapter;

    private ZProgressHUD mProgressHUD;


    //列表分类展示
    private ArrayList<OlderInfo> data = new ArrayList<>();
    private ArrayList<OlderInfo> data1 = new ArrayList<>();
    private ArrayList<OlderInfo> data2 = new ArrayList<>();

    private String SmartId = "";
    private final String[] permissionArr = {Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
    private ZeusManager mZeusManager;
    private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincare);
        EventBus.getDefault().register(this);
        checkAppPermission();
        mContext = this;
        Log.e(TAG, "onCreate: " );
        initView();
        initData();//请求关爱对象列表
    }

    private void checkAppPermission() {
        mZeusManager = new ZeusManager(this);
        mZeusManager.setOnPermissionCallback(this);
        mZeusManager.checkPermissions(permissionArr, false);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mZeusManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private FrameLayout fl_menu;
    private TextView text_title;
    private ImageView image_scan;

    private ListView list_care;
    private ViewFlow view_pic;
    private CircleFlowIndicator indicator_point;

    private RelativeLayout relative_noData;
    private BottomListPop mBottomListPop;

    private void initView() {

        fl_menu = (FrameLayout) findViewById(R.id.fl_menu);
        fl_menu.setOnClickListener(this);
        mBottomListPop = new BottomListPop(fl_menu, this, Arrays.asList("退出登录", "修改密码"));
        mBottomListPop.setOnPopItemClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        image_scan = (ImageView) findViewById(R.id.image_scan);
        srl = (SwipeRefreshLayout) findViewById(R.id.srl);
        image_scan.setOnClickListener(this);

        list_care = (ListView) findViewById(R.id.list_care);
        mainCareAdapter = new MainCareAdapter(mContext, data);
        list_care.setAdapter(mainCareAdapter);
        mainCareAdapter.setMode(Attributes.Mode.Multiple);


        relative_noData = (RelativeLayout) findViewById(R.id.relative_noData);

        view_pic = (ViewFlow) findViewById(R.id.view_pic);
        indicator_point = (CircleFlowIndicator) findViewById(R.id.indicator_point);

        mProgressHUD = new ZProgressHUD(mContext);
        srl.setColorSchemeResources(R.color.bg_black);
        srl.setProgressViewOffset(false, 0, AppUtil.dp2px(24));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getOlderList();
            }
        });
    }

    private void initData() {
        getOlderList();
//        getBanner();
    }

    private void getBanner() {
        HashMap<String, String> map1 = new HashMap<>();
        map1.put("token", Constants.getToken());
        map1.put("cardType", "1005");
        map1.put("taskId", "1");
        map1.put("DataTypeCode", "GetBannerInfo");
        map1.put("content", "");
        Gson gson = new Gson();
        Logger.json(gson.toJson(map1));
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map1, new
                WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int resultCode = jsonObject.getInt("ResultCode");
                                String resultText = MyUtils.initNullStr(jsonObject.getString("ResultText"));
                                if (resultCode == 0) {

                                    String content = jsonObject.getString("Content");
                                    JSONObject json = new JSONObject(content);
                                    String bannerList = json.getString("BANNERLIST");
                                    JSONArray array = new JSONArray(bannerList);
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        String pictureUrl = object.getString("PICTUREURL");
                                        String urlAdd = object.getString("URLADD");
                                        String titleAdd = object.getString("TITLE");
                                        imageUrlList.add(pictureUrl);
                                        linkUrlArray.add(urlAdd);
                                        titleList.add(titleAdd);
                                    }
                                    initBanner(imageUrlList);//初始化banner
                                } else {
                                    Log.e(TAG + "获取图片信息", resultText);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e(TAG + "获取图片信息", "JSON解析出错");
                            }
                        } else {
                            Log.e("获取banner", "获取数据错误，请稍后重试！");
                        }
                    }
                });
    }

    private void getOlderList() {
        srl.setRefreshing(true);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("USERPHONE", Constants.getUserPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "1");
        map.put("encryption", "0");
        map.put("DataTypeCode", "CheckConcernList");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                JSONObject jsonObject = new JSONObject(result);
                                int resultCode = jsonObject.getInt("ResultCode");
                                String resultText = MyUtils.initNullStr(jsonObject.getString("ResultText"));
                                if (resultCode == 0) {
                                    getBanner();
                                    listOlder.clear();
                                    data.clear();
                                    data1.clear();
                                    data2.clear();
                                    String content = jsonObject.getString("Content");
                                    JSONObject json = new JSONObject(content);
                                    String concernList = json.getString("CONCERNLIST");
                                    JSONArray array = new JSONArray(concernList);
                                    if (array.length() > 0) {
                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject obj = array.getJSONObject(i);
                                            OlderInfo mInfo = new OlderInfo();
                                            mInfo.setAGE(MyUtils.initNullStr(obj.getString("AGE")));
                                            mInfo.setSEX(MyUtils.initNullStr(obj.getString("SEX")));
                                            mInfo.setCustomerPhoto(MyUtils.initNullStr(obj.getString("CUSTOMERPHOTO")));
                                            mInfo.setCareNumber(MyUtils.initNullStr(obj.getString("SMARTCAREID")));
                                            mInfo.setCustomerName(MyUtils.initNullStr(obj.getString("CUSTOMERNAME")));
                                            mInfo.setTargetType(MyUtils.initNullStr(obj.getString("TARGETTYPES")));
                                            mInfo.setIsRegedit(MyUtils.initNullStr(obj.getString("ISREGEDIT")));
                                            mInfo.setPersonType(MyUtils.initNullStr(obj.getString("PERSONTYPE")));
                                            //0是登记人有编辑权，1是关联人无编辑权
                                            listOlder.add(mInfo);
                                        }

                                        for (int i = 0; i < listOlder.size(); i++) {
                                            if (listOlder.get(i).getIsRegedit().equals("0")) {
                                                data1.add(listOlder.get(i));
                                            } else {
                                                data2.add(listOlder.get(i));
                                            }
                                        }

                                        for (int i = 0; i < data1.size(); i++) {
                                            data.add(data1.get(i));
                                        }
                                        for (int i = 0; i < data2.size(); i++) {
                                            data.add(data2.get(i));
                                        }


                                        relative_noData.setVisibility(View.GONE);
                                        list_care.setVisibility(View.VISIBLE);
                                        mainCareAdapter.notifyDataSetChanged();
                                    } else {
                                        //没有了老人列表做处理
                                        relative_noData.setVisibility(View.VISIBLE);
                                        list_care.setVisibility(View.GONE);

                                    }

                                    srl.setRefreshing(false);
                                } else {
                                    srl.setRefreshing(false);
                                    MyUtils.myToast(mContext, resultText);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                srl.setRefreshing(false);
                                MyUtils.myToast(mContext, "JSON解析出错");
                            }
                        } else {
                            srl.setRefreshing(false);
                            MyUtils.myToast(mContext, "获取数据错误，请稍后重试！");
                        }
                    }
                });
    }

    private void initBanner(ArrayList<String> imageUrlList) {
        indicator_point.setVisibility(View.VISIBLE);
        view_pic.setAdapter(new ImagePagerAdapter(this, imageUrlList, linkUrlArray, titleList).setInfiniteLoop(true));
        view_pic.setmSideBuffer(imageUrlList.size()); // 实际图片张数，
        view_pic.setFlowIndicator(indicator_point);
        view_pic.setTimeSpan(4500);
        view_pic.setSelection(imageUrlList.size() * 1000); // 设置初始位置
        view_pic.startAutoFlowTimer(); // 启动自动播放
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fl_menu:
                mBottomListPop.showPopupWindow();
                break;
            case R.id.image_scan:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("activity", "mainCare");
                bundle.putString("SmartId", SmartId);
                intent.putExtras(bundle);
                intent.setClass(this, CaptureActivity.class);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.list_care:
                mainCareAdapter.notifyDataSetChanged();
                String smartcareId = listOlder.get(position).getCareNumber();
                Intent intent = new Intent(MainCareActivity.this, OlderSelectActivity.class);
                intent.putExtra("smartcareId", smartcareId);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                startActivity(intent);
                break;
        }

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (data == null) {
                    MyUtils.myToast(mContext, "没有扫描到二维码");
                    break;
                } else {
                    mProgressHUD.setMessage("二维码解析中...");
                    mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
                    mProgressHUD.show();
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("result");
                    SmartId = bundle.getString("SmartId");
                    Log.e("二维码内容：", scanResult);
                    MyUtils.myToast(mContext, "二维码扫描成功，请稍候...");
                    String url = "http://ga.iotone.cn/";
                    if (scanResult.contains(url)) {
                        JSONObject object = new JSONObject();
                        try {
                            object.put("QRCode", scanResult);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        HashMap<String, String> map = new HashMap<>();
                        map.put("token", Constants.getToken());
                        map.put("cardType", "1005");
                        map.put("taskId", "");
                        map.put("DataTypeCode", "ParseQRcode");
                        map.put("content", object.toString());
                        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map,
                                new WebServiceUtils.WebServiceCallBack() {
                                    @Override
                                    public void callBack(String result) {
                                        if (result != null) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(result);
                                                int resultCode = jsonObject.getInt("ResultCode");
                                                String resultText = jsonObject.getString("ResultText");
                                                if (resultCode == 0) {
                                                    String content = jsonObject.getString("Content");
                                                    JSONObject obj = new JSONObject(content);
                                                    String QRType = obj.getString("QRType");
                                                    String type = obj.getString("Type");
                                                    String Id = obj.getString("ID");
                                                    if (QRType.equals("Q1")) {
                                                        mProgressHUD.dismiss();
                                                        checkDevice(Id,type);
                                                    } else if (QRType.equals("Q2")) {
                                                        mProgressHUD.dismiss();
                                                        MyUtils.myToast(mContext, "此设备为网关设备，请到关爱对象的设备配置页面进行添加");
                                                    } else if (QRType.equals("Q3")) {
                                                        mProgressHUD.dismiss();
                                                        getShare(Id);
                                                    }
                                                } else {
                                                    mProgressHUD.dismiss();
                                                    MyUtils.myToast(mContext, resultText);
                                                }
                                            } catch (JSONException e) {
                                                mProgressHUD.dismiss();
                                                e.printStackTrace();
                                                MyUtils.myToast(mContext, "JSON解析出错");
                                            }
                                        } else {
                                            mProgressHUD.dismiss();
                                            MyUtils.myToast(mContext, "请确认设备是否为关爱设备");
                                        }
                                    }
                                });

                    } else if (scanResult.contains("?U=")) {
                        final int i = scanResult.indexOf("?U=");
                        String content = scanResult.substring(i + 3);

                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("StrString", content);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("token", Constants.getToken());
                        map.put("cardType", "1005");
                        map.put("taskId", "1");
                        map.put("DataTypeCode", "AESDecrypt");
                        map.put("content", jsonObject.toString());

                        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map,
                                new WebServiceUtils.WebServiceCallBack() {
                                    @Override
                                    public void callBack(String result) {
                                        if (result != null) {
                                            JSONObject object = null;
                                            try {
                                                object = new JSONObject(result);
                                                int resultCode = object.getInt("ResultCode");
                                                String resultText = object.getString("ResultText");
                                                if (resultCode == 0) {
                                                    mProgressHUD.dismiss();
                                                    String content = object.getString("Content");
                                                    JSONObject obj = new JSONObject(content);
                                                    String code = obj.getString("StrString");
                                                    checkDevice(code, "8010");
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
                    } else {
                        mProgressHUD.dismiss();
                        MyUtils.myToast(mContext, "非指定亲情关爱设备");
                        break;
                    }
                }
                break;
        }
    }

    /**
     * 检查设备编码
     *
     * @param id
     * @param type
     */
    private void checkDevice(final String id, String type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DEVICEID", id);
            jsonObject.put("devicetype", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "1");
        map.put("DataTypeCode", "CheckDeviceNumber");
        map.put("content", jsonObject.toString());

        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                JSONObject json = new JSONObject(result);
                                int resultCode = json.getInt("ResultCode");
                                String resultText = json.getString("ResultText");
                                if (resultCode == 0) {
                                    String content = json.getString("Content");
                                    JSONObject object = new JSONObject(content);
                                    String isMultiuse = object.getString("ISMULTIUSE");
                                    String isOccupied = object.getString("ISOCCUPIED");
                                    String deviceType = object.getString("DEVICETYPE");
                                    String deviceName = object.getString("DEVICETYPENAME");
                                    if (isOccupied.equals("1")) {
                                        MyUtils.myToast(mContext, "该设备已被使用");
                                    } else {
                                        AddOlderActivity.goActivity(MainCareActivity.this, id, deviceType,
                                                deviceName, SmartId);
                                    }
                                } else {
                                    MyUtils.myToast(mContext, resultText);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                MyUtils.myToast(mContext, "JSON解析出错");
                            }
                        } else {
                            MyUtils.myToast(mContext, "获取数据错误，请稍后重试！");
                        }
                    }
                });
    }

    private void getShare(final String shareGuid) {
        mProgressHUD.setMessage("请求关爱对象...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SHAREID", shareGuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "");
        map.put("DataTypeCode", "GetShare_Target");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                JSONObject json = new JSONObject(result);
                                int resultCode = json.getInt("ResultCode");
                                String resultText = json.getString("ResultText");
                                if (resultCode == 0) {
                                    String content = json.getString("Content");
                                    JSONObject obj = new JSONObject(content);
                                    String smartCardId = obj.getString("SMARTCAREID");
                                    String targetType = obj.getString("TARGETTYPE");
                                    String operatorName = obj.getString("OPERATORNAME");
                                    String customerIdCard = obj.getString("CUSTOMERIDCARD");
                                    String photo = obj.getString("PHOTO");
                                    String customerName = obj.getString("CUSTOMERNAME");
                                    String customerAddress = obj.getString("CUSTOMERADDRESS");
                                    mProgressHUD.dismiss();
                                    dialogShow(smartCardId, targetType, operatorName, customerIdCard, photo,
                                            customerName,
                                            customerAddress);
                                } else {
                                    MyUtils.myToast(mContext, resultText);
                                    mProgressHUD.dismiss();
                                }
                            } catch (JSONException e) {
                                mProgressHUD.dismiss();
                                e.printStackTrace();
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

    private void dialogShow(final String smartCardId, final String targetTypes, final String operatorName, final
    String customerIdCard, final String photo, final String customerName, final String customerAddress) {
        if (dialogBuilder != null && dialogBuilder.isShowing())
            return;

        dialogBuilder = NiftyDialogBuilder.getInstance(mContext);

        effectstype = NiftyDialogBuilder.Effectstype.Fadein;
        LayoutInflater mInflater = LayoutInflater.from(this);
        View relatedEdler = mInflater.inflate(R.layout.layout_relatedelder, null);
        ImageView image_elderBody = (ImageView) relatedEdler.findViewById(R.id.image_elderBody);
        image_elderBody.setImageBitmap(MyUtils.stringtoBitmap(photo));
        TextView text_layoutTitle = (TextView) relatedEdler.findViewById(R.id.text_layoutTitle);
        text_layoutTitle.setText(operatorName + "向您分享一位老人，信息如下:");
        TextView text_olderName = (TextView) relatedEdler.findViewById(R.id.text_olderName);
        text_olderName.setText(customerName);
        TextView text_olderIdentity = (TextView) relatedEdler.findViewById(R.id.text_olderIdentity);
        text_olderIdentity.setText(MyUtils.hideID(customerIdCard));
        TextView text_olderAddress = (TextView) relatedEdler.findViewById(R.id.text_olderAddress);
        text_olderAddress.setText(customerAddress);
        dialogBuilder.withTitle("关联老人").withTitleColor("#333333").withMessage(null)
                .isCancelableOnTouchOutside(false).withEffect(effectstype).withButton1Text("取消")
                .setCustomView(relatedEdler, mContext).withButton2Text("关联").setButton1Click(new View.OnClickListener
                () {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        }).setButton2Click(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
                addConcerned(smartCardId, targetTypes, operatorName, customerIdCard, photo, customerName,
                        customerAddress);

            }
        }).show();
    }

    /**
     * 关联对象
     *
     * @param smartCardId
     */
    private void addConcerned(final String smartCardId, final String targetTypes, final String operatorName, final
    String customerIdCard, final String photo, final String customerName, final String customerAddress) {
        mProgressHUD.setMessage("关联关爱对象...");
        mProgressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        mProgressHUD.show();

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("SMARTCAREID", smartCardId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("token", Constants.getToken());
        map.put("cardType", "1005");
        map.put("taskId", "1");
        map.put("DataTypeCode", "AddConcerned");
        map.put("content", jsonObject.toString());
        WebServiceUtils.callWebService(Constants.WEBSERVER_URL, Constants.WEBSERVER_REREQUEST, map, new
                WebServiceUtils.WebServiceCallBack() {
                    @Override
                    public void callBack(String result) {
                        if (result != null) {
                            try {
                                JSONObject json = new JSONObject(result);
                                int resultCode = json.getInt("ResultCode");
                                String resultText = json.getString("ResultText");
                                if (resultCode == 0) {
                                    mProgressHUD.dismiss();
                                    MyUtils.myToast(mContext, "关联对象成功");
                                    getOlderList();
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

    private long firstTime;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if ((secondTime - firstTime) > 2000) {
            Toast.makeText(this, "长按两次退出", Toast.LENGTH_SHORT).show();
            firstTime = secondTime;
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onAllow() {

    }

    @Override
    public void onClose() {
        Toast.makeText(this, "必要权限未获取，应用自动关闭", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPopItemClick(int position, String tag) {
        switch (position) {
            case 0:
                GoUtil.goActivityAndFinish(this, LoginActivity.class);
                break;
            case 1:
                GoUtil.goActivity(this, EditPwdActivity.class);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        getOlderList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getBannerInfo() {
        new ThreadPoolTask.Builder()
                .setGeneralParam(Constants.getToken(), "1005", "GetBannerInfo", null)
                .setBeanType(Object.class)
                .setCallBack(new WebServiceCallBack<Object>() {
                    @Override
                    public void onSuccess(Object bean) {
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                    }
                }).build().execute();
    }
}
