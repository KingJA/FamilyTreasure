package com.tdr.familytreasure.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.entiy.CheckElder;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.ui.ZProgressHUD;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.DataManager;
import com.tdr.familytreasure.util.MD5;
import com.tdr.familytreasure.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 修改密码
 * Created by Linus_Xie on 2016/8/4.
 */
public class EditPwdActivity extends Activity {
    private static final String TAG = "EditPwdActivity";

    private ZProgressHUD mProgressHUD;
    private MaterialEditText mEtOldPwd;
    private MaterialEditText mEtNewPwd;
    private MaterialEditText mEtRepeatPwd;
    private Button mBtnConfirm;
    private ImageView fl_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpwd);
        initView();
    }


    private void initView() {
        mProgressHUD = new ZProgressHUD(this);
        fl_menu = (ImageView) findViewById(R.id.fl_menu);
        mEtOldPwd = (MaterialEditText) findViewById(R.id.et_oldPwd);
        mEtNewPwd = (MaterialEditText) findViewById(R.id.et_newPwd);
        mEtRepeatPwd = (MaterialEditText) findViewById(R.id.et_repeatPwd);
        mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
        TextView text_title = (TextView) findViewById(R.id.text_title);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });
        text_title.setText("修改密码");
        fl_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 密码校验
     */
    private void checkData() {
        String oldPwd = mEtOldPwd.getText().toString().trim();
        String newPwd = mEtNewPwd.getText().toString().trim();
        String repeatPwd = mEtRepeatPwd.getText().toString().trim();
        if (CheckUtil.checkPasswordFormat(oldPwd,6,16,"原")
                &&CheckUtil.checkPasswordFormat(newPwd,6,16,"新")
                &&CheckUtil.checkPasswordFormat(repeatPwd,6,16,"重复")
                &&CheckUtil.checkEquil(newPwd,repeatPwd,"重复密码不相同，请重新输入")){
            try {
                oldPwd= MD5.getMD5(oldPwd);
                newPwd= MD5.getMD5(newPwd);
                upLoad(oldPwd,newPwd);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 密码修改
     * @param oldPwd
     * @param newPwd
     */
    private void upLoad(String oldPwd, String newPwd) {
        mProgressHUD.show();
        Map<String, Object> param = new HashMap<>();
        param.put("Phone", DataManager.getUserPhone());
        param.put("OldPassword",oldPwd);
        param.put("UserPassword", newPwd);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "", "ChangePasswordNoVerify", param)
                .setBeanType(CheckElder.class)
                .setCallBack(new WebServiceCallBack<CheckElder>() {
                    @Override
                    public void onSuccess(CheckElder bean) {
                        mProgressHUD.dismiss();
                        ToastUtil.showToast("修改密码成功");
                        finish();
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        mProgressHUD.dismiss();
                    }
                }).build().execute();
    }

}
