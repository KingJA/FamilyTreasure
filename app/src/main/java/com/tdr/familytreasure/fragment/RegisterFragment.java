package com.tdr.familytreasure.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.activity.RegisterActivity;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.IsRegisterPhone;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.ui.MaterialEditText;
import com.tdr.familytreasure.util.CheckUtil;
import com.tdr.familytreasure.util.ToastUtil;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends BaseFragment {
    private MaterialEditText met_phone;
    private Button btn_getCode;
    private String phone;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        met_phone = (MaterialEditText) view.findViewById(R.id.met_phone);
        btn_getCode = (Button) view.findViewById(R.id.btn_getCode);
    }

    @Override
    public void initFragmentVariables() {

    }

    @Override
    public void initFragmentNet() {

    }

    @Override
    public void initFragmentData() {
        btn_getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = met_phone.getText().toString().trim();
                if (CheckUtil.checkPhoneFormat(phone)) {
                    checkIfRegistered();
                }
            }
        });
    }

    private void checkIfRegistered() {
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
                        if (bean.getContent().getCode() == 0) {//未注册过
                            RegisterActivity.goAcivity(getActivity(), phone);
                            getActivity().finish();
                        } else {
                            ToastUtil.showToast("该手机号码已注册过");
                        }
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    @Override
    public void setFragmentData() {

    }
}
