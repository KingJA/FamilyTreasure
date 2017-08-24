package com.tdr.familytreasure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tdr.familytreasure.R;
import com.tdr.familytreasure.entiy.GetElderMonitorData;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2017/8/24 11:19
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FootprintAdapter extends BaseLvAdapter<GetElderMonitorData.ContentBean.MONITORDATALISTBean> {

    public FootprintAdapter(Context context, List<GetElderMonitorData.ContentBean.MONITORDATALISTBean> list) {
        super(context, list);
    }


    @Override
    public View simpleGetView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View
                    .inflate(context, R.layout.item_footprint, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_footprint_address.setText(list.get(position).getBASESTATIONNAME());
        viewHolder.tv_footprint_date.setText(list.get(position).getBASESATTIONTIME());

        return convertView;
    }


    public class ViewHolder {
        public final TextView tv_footprint_address;
        public final TextView tv_footprint_date;
        public final View root;

        public ViewHolder(View root) {
            tv_footprint_address = (TextView) root.findViewById(R.id.tv_footprint_address);
            tv_footprint_date = (TextView) root.findViewById(R.id.tv_footprint_date);
            this.root = root;
        }
    }
}
