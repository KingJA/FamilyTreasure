package com.tdr.familytreasure.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kingja.ui.popupwindow.BasePopupWindow;
import com.kingja.ui.wheelview.AbstractWheelTextAdapter;
import com.kingja.ui.wheelview.OnWheelChangedListener;
import com.kingja.ui.wheelview.OnWheelScrollListener;
import com.kingja.ui.wheelview.WheelView;
import com.tdr.familytreasure.R;
import com.tdr.familytreasure.dao.DbDaoXutils3;
import com.tdr.familytreasure.entiy.Basic_XingZhengQuHua_Kj;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：派出所选择框
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/4/11 16:34
 * 修改备注：
 */
public class PaichusuoBottomWheelView extends BasePopupWindow<List<Basic_XingZhengQuHua_Kj>> {
    private int maxTextSize = 24;
    private int minTextSize = 14;

    private OnPopItemClickListener onPopItemClickListener;
    private String selectItem;
    private List<Basic_XingZhengQuHua_Kj> data;
    private List<String> mItemList = new ArrayList<>();
    private List<String> mItemIdList = new ArrayList<>();
    private String selectItemId;

    public PaichusuoBottomWheelView(View parentView, Activity activity, List<Basic_XingZhengQuHua_Kj> data) {
        super(parentView, activity, data);
        this.data = data;
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setAnimationStyle(R.style.PopupBottomAnimation);
    }


    @Override
    public View setPopupView(Activity activity) {
        popupView = View.inflate(activity, R.layout.bottom_wheelview, null);
        return popupView;
    }

    @Override
    public void initChildView() {
        WheelView wv_single = (WheelView) popupView.findViewById(R.id.wv_single);
        TextView tv_confirm = (TextView) popupView.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onPopItemClickListener != null) {
                    Log.e("onPopItemClickListener", "selectItemId: "+selectItemId +" selectItem: "+selectItem);
                    onPopItemClickListener.onPopItemClick(selectItemId,selectItem);
                }
            }
        });
        initData();
        initWheelView(wv_single);
    }

    private void initWheelView(WheelView wv_single) {
        final TimeAdapter mHourAdapter = new TimeAdapter(activity, mItemList, 0, maxTextSize, minTextSize);
        wv_single.setVisibleItems(5);
        wv_single.setViewAdapter(mHourAdapter);
        wv_single.setCurrentItem(0);
        wv_single.addChangingListener(new OnWheelChangedListener() {

            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                selectItem = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                selectItemId = mItemIdList.get(wheel.getCurrentItem());
                setTextviewSize(selectItem, mHourAdapter);
            }
        });

        wv_single.addScrollingListener(new OnWheelScrollListener() {

            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                selectItem = (String) mHourAdapter.getItemText(wheel.getCurrentItem());
                selectItemId = mItemIdList.get(wheel.getCurrentItem());
                setTextviewSize(selectItem, mHourAdapter);
            }
        });
    }

    private void initData() {
        List<Basic_XingZhengQuHua_Kj> data = (List<Basic_XingZhengQuHua_Kj>) DbDaoXutils3.getInstance().sleectAllDb(Basic_XingZhengQuHua_Kj.class);
        for (Basic_XingZhengQuHua_Kj bean : data) {
            mItemList.add(bean.getDMMC());
            mItemIdList.add(bean.getDMZM());
        }
    }

    /**
     * 设置字体大小
     *
     * @param curriteItemText
     * @param adapter
     */
    public void setTextviewSize(String curriteItemText, TimeAdapter adapter) {
        ArrayList<View> arrayList = adapter.getTestViews();
        int size = arrayList.size();
        String currentText;
        for (int i = 0; i < size; i++) {
            TextView textvew = (TextView) arrayList.get(i);
            currentText = textvew.getText().toString();
            if (curriteItemText.equals(currentText)) {
                textvew.setTextSize(maxTextSize);
            } else {
                textvew.setTextSize(minTextSize);
            }
        }
    }

    private class TimeAdapter extends AbstractWheelTextAdapter {
        List<String> list;

        protected TimeAdapter(Context context, List<String> list, int currentItem, int maxsize, int minsize) {
            super(context, R.layout.item_birth_year, NO_RESOURCE, currentItem, maxsize, minsize);
            this.list = list;
            setItemTextResource(R.id.tempValue);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return list.size();
        }

        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index) + "";
        }
    }

    @Override
    public void OnChildClick(View v) {

    }

    public interface OnPopItemClickListener {
        void onPopItemClick(String id, String tag);
    }

    public void setOnPopItemClickListener(OnPopItemClickListener onPopItemClickListener) {
        this.onPopItemClickListener = onPopItemClickListener;
    }

}
