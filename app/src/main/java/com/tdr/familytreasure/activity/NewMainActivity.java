package com.tdr.familytreasure.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.base.BaseActivity;
import com.tdr.familytreasure.util.FragmentUtil;

/**
 * Description:TODO
 * Create Time:2018/3/28 10:01
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class NewMainActivity extends BaseActivity implements View.OnClickListener {
    private Fragment mCurrentFragment;
    private int mSelectedPosition;
    private int mCurrentPosition;
    private FrameLayout mFlNewContent;
    private LinearLayout mLlTabHome;
    private ImageView mIvTabHome;
    private TextView mTvTabHome;
    private LinearLayout mLlTabLove;
    private ImageView mIvTabLove;
    private TextView mTvTabLove;
    private LinearLayout mLlTabMsg;
    private ImageView mIvTabMsg;
    private TextView mTvTabMsg;
    private LinearLayout mLlTabMe;
    private ImageView mIvTabMe;
    private TextView mTvTabMe;


    @Override
    protected void initVariables() {
        mFlNewContent = (FrameLayout) findViewById(R.id.fl_new_content);
        mLlTabHome = (LinearLayout) findViewById(R.id.ll_tab_home);
        mIvTabHome = (ImageView) findViewById(R.id.iv_tab_home);
        mTvTabHome = (TextView) findViewById(R.id.tv_tab_home);
        mLlTabLove = (LinearLayout) findViewById(R.id.ll_tab_love);
        mIvTabLove = (ImageView) findViewById(R.id.iv_tab_love);
        mTvTabLove = (TextView) findViewById(R.id.tv_tab_love);
        mLlTabMsg = (LinearLayout) findViewById(R.id.ll_tab_msg);
        mIvTabMsg = (ImageView) findViewById(R.id.iv_tab_msg);
        mTvTabMsg = (TextView) findViewById(R.id.tv_tab_msg);
        mLlTabMe = (LinearLayout) findViewById(R.id.ll_tab_me);
        mIvTabMe = (ImageView) findViewById(R.id.iv_tab_me);
        mTvTabMe = (TextView) findViewById(R.id.tv_tab_me);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_new_main;
    }

    @Override
    protected void initView() {
        mCurrentFragment = FragmentUtil.getFragment(0);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_new_content, mCurrentFragment).commit();
    }

    @Override
    protected void initNet() {

    }

    @Override
    protected void initData() {
        mFlNewContent.setOnClickListener(this);
        mLlTabHome.setOnClickListener(this);
        mLlTabLove.setOnClickListener(this);
        mLlTabMsg.setOnClickListener(this);
        mLlTabMe.setOnClickListener(this);
    }

    @Override
    protected void setData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tab_home:
                selectTab(0);
                break;
            case R.id.ll_tab_love:
                selectTab(1);
                break;
            case R.id.ll_tab_msg:
                selectTab(2);
                break;
            case R.id.ll_tab_me:
                selectTab(3);
                break;
            default:
                break;

        }
    }

    private void selectTab(int position) {
        mSelectedPosition = position;
        if (mSelectedPosition == mCurrentPosition) {
            return;
        }
        mCurrentFragment = FragmentUtil.switchFragment(getSupportFragmentManager(), mCurrentFragment, FragmentUtil
                .getFragment(position));
        mCurrentPosition = mSelectedPosition;
        setStatus(position);
    }

    private void setStatus(int index) {
        resetBottom();
        switch (index) {
            case 0:
                mIvTabHome.setImageResource(R.mipmap.ic_tab_home_sel);
                mTvTabHome.setTextColor(getResources().getColor(R.color.font_blue));
                break;
            case 1:
                mIvTabLove.setImageResource(R.mipmap.ic_tab_love_sel);
                mTvTabLove.setTextColor(getResources().getColor(R.color.font_blue));
                break;
            case 2:
                mIvTabMsg.setImageResource(R.mipmap.ic_tab_msg_sel);
                mTvTabMsg.setTextColor(getResources().getColor(R.color.font_blue));
                break;
            case 3:
                mIvTabMe.setImageResource(R.mipmap.ic_tab_me_sel);
                mTvTabMe.setTextColor(getResources().getColor(R.color.font_blue));
                break;
            default:
                break;
        }
    }
    private void resetBottom() {
        mIvTabHome.setImageResource(R.mipmap.ic_tab_home_nor);
        mIvTabLove.setImageResource(R.mipmap.ic_tab_love_nor);
        mIvTabMsg.setImageResource(R.mipmap.ic_tab_msg_nor);
        mIvTabMe.setImageResource(R.mipmap.ic_tab_me_nor);
        mTvTabHome.setTextColor(getResources().getColor(R.color.font_9));
        mTvTabLove.setTextColor(getResources().getColor(R.color.font_9));
        mTvTabMsg.setTextColor(getResources().getColor(R.color.font_9));
        mTvTabMe.setTextColor(getResources().getColor(R.color.font_9));
    }
}
