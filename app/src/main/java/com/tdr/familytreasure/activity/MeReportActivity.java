package com.tdr.familytreasure.activity;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.base.BackTitleActivity;
import com.tdr.familytreasure.util.ToastUtil;

/**
 * Description:TODO
 * Create Time:2018/4/16 15:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MeReportActivity extends BackTitleActivity {
    @Override
    protected void initVariables() {

    }

    @Override
    protected void initContentView() {

    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_me_report;
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        setTitle("反馈");
        setOnRightClickListener(new OnRightClickListener() {
            @Override
            public void onRightClick() {
                ToastUtil.showToast("提交");
            }
        }, "提交");

    }
}
