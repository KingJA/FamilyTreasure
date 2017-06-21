package com.tdr.familytreasure.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.base.BackTitleActivity;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.IsRegisterPhone;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Description：忘记密码-输入手机号码
 * Create Time：2017/1/17 14:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ForgetActivityPhone extends BackTitleActivity implements TextWatcher {
    private MaterialEditText mMetPhone;
    private TextView mTvForgetStepPhone;


    @Override
    protected void initVariables() {

    }

    @Override
    protected void initContentView() {
        mMetPhone = (MaterialEditText) findViewById(R.id.met_phone);
        mTvForgetStepPhone = (TextView) findViewById(R.id.tv_forget_step_phone);
    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_forget_phone;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mMetPhone.addTextChangedListener(this);
        mTvForgetStepPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mMetPhone.getText().toString().trim();
                if (CheckUtil.checkPhoneFormat(phone)) {
                    checkRegistered(phone);
//                    ForgetActivityCode.goActivity(ForgetActivityPhone.this,phone);
                }

            }
        });
    }

    private void checkRegistered(final String phone) {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("phone", phone);
        new ThreadPoolTask.Builder()
                .setGeneralParam("", "", "IsRegisterPhone", param)
                .setBeanType(IsRegisterPhone.class)
                .setCallBack(new WebServiceCallBack<IsRegisterPhone>() {
                    @Override
                    public void onSuccess(IsRegisterPhone bean) {
                        setProgressDialog(false);
                        if (bean.getContent().getCode() == 1) {
                            ForgetActivityCode.goActivity(ForgetActivityPhone.this,phone);
                        }else{
                            ToastUtil.showToast("该手机号码未注册过");
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    protected void setData() {
        setTitle("忘记密码");
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_getCode:
                ToastUtil.showToast("获取验证码");
                break;
            default:
                break;

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
        if (s.length() ==11) {
            mTvForgetStepPhone.setBackgroundResource(R.drawable.btn_blue_match);
        }else{
            mTvForgetStepPhone.setBackgroundResource(R.drawable.btn_gray_match);
        }
    }
}
