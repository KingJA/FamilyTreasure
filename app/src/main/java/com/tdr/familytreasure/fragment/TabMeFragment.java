package com.tdr.familytreasure.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.activity.MeReportActivity;
import com.tdr.familytreasure.activity.MeSettingActivity;
import com.tdr.familytreasure.util.GoUtil;

/**
 * Description:TODO
 * Create Time:2018/4/16 10:04
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TabMeFragment extends BaseFragment implements View.OnClickListener {
    private LinearLayout mLlMeDevices;
    private LinearLayout mLlMeSetting;
    private LinearLayout mLlMeReport;

    @Override
    public void initFragmentVariables() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.ft_tab_me;
    }

    @Override
    public void initFragmentView(View view, Bundle savedInstanceState) {
        mLlMeDevices = (LinearLayout) view.findViewById(R.id.ll_me_devices);
        mLlMeSetting = (LinearLayout) view.findViewById(R.id.ll_me_setting);
        mLlMeReport = (LinearLayout) view.findViewById(R.id.ll_me_report);
    }

    @Override
    public void initFragmentNet() {

    }

    @Override
    public void initFragmentData() {
        mLlMeDevices.setOnClickListener(this);
        mLlMeSetting.setOnClickListener(this);
        mLlMeReport.setOnClickListener(this);
    }

    @Override
    public void setFragmentData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_me_devices:
                break;
            case R.id.ll_me_setting:
                GoUtil.goActivity(getActivity(), MeSettingActivity.class);
                break;
            case R.id.ll_me_report:
                GoUtil.goActivity(getActivity(), MeReportActivity.class);
                break;
            default:
                break;

        }
    }
}
