package com.tdr.familytreasure.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolylineOptions;
import com.tdr.familytreasure.R;
import com.tdr.familytreasure.adapter.FootprintAdapter;
import com.tdr.familytreasure.base.BackTitleActivity;
import com.tdr.familytreasure.entiy.ErrorResult;
import com.tdr.familytreasure.entiy.GetElderMonitorData;
import com.tdr.familytreasure.net.ThreadPoolTask;
import com.tdr.familytreasure.net.WebServiceCallBack;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.DataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.id.list;

/**
 * Description:TODO
 * Create Time:2017/8/24 7:11
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class FootprintActivity extends BackTitleActivity implements AMap.OnMapLoadedListener, AMap
        .OnInfoWindowClickListener, AdapterView.OnItemClickListener {

    private AMap mAmap;
    private String smartcareId;
    private MapView mMapView;
    private ArrayList<MarkerOptions> markerOptionses;
    private ArrayList<Marker> markers=new ArrayList<>();
    private List<GetElderMonitorData.ContentBean.MONITORDATALISTBean> footprints=new ArrayList<>();
    private FootprintAdapter mFootprintAdapter;

    @Override
    protected void initVariables() {
        smartcareId = getIntent().getStringExtra("smartcareId");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        if (mAmap == null) {
            mAmap = mMapView.getMap();
        }
        mAmap.getUiSettings().setZoomControlsEnabled(true);
        mAmap.getUiSettings().setCompassEnabled(true);
        mAmap.getUiSettings().setScaleControlsEnabled(true);
        mAmap.setOnMapLoadedListener(this);
        mAmap.setOnInfoWindowClickListener(this);// 设置点击infoWindow事件监听器
    }

    @Override
    protected void initContentView() {
        ListView mLvFootprint = (ListView) findViewById(R.id.lv_footprint);
        mFootprintAdapter = new FootprintAdapter(this, footprints);
        mLvFootprint.setAdapter(mFootprintAdapter);
        mLvFootprint.setOnItemClickListener(this);

    }

    @Override
    protected int getBackContentView() {
        return R.layout.activity_footprint;
    }

    @Override
    protected void initNet() {
        setProgressDialog(true);
        Map<String, Object> param = new HashMap<>();
        param.put("SMARTCAREID", smartcareId);
        param.put("PAGEINDEX", 0);
        param.put("PAGESIZE", 20);
        new ThreadPoolTask.Builder()
                .setGeneralParam(DataManager.getToken(), "1005", "GetElderMonitorData",
                        param)
                .setBeanType(GetElderMonitorData.class)
                .setCallBack(new WebServiceCallBack<GetElderMonitorData>() {
                    @Override
                    public void onSuccess(GetElderMonitorData bean) {
                        setProgressDialog(false);
                        footprints = bean.getContent()
                                .getMONITORDATALIST();
                        showFootprint(footprints);
                    }

                    @Override
                    public void onErrorResult(ErrorResult errorResult) {
                        setProgressDialog(false);
                    }
                }).build().execute();
    }

    private void showFootprint(List<GetElderMonitorData.ContentBean.MONITORDATALISTBean> footprints) {
        if (footprints.size() == 0) {
            return;
        }
        addPoints(footprints);
        addPolyLine(footprints);
        mFootprintAdapter.setData(footprints);
    }

    private void addPolyLine(List<GetElderMonitorData.ContentBean.MONITORDATALISTBean> footprints) {
        List<LatLng> latLngs = new ArrayList<>();
        for (GetElderMonitorData.ContentBean.MONITORDATALISTBean footprint : footprints) {
            latLngs.add(new LatLng(Double.valueOf(footprint.getLAT()), Double.valueOf(footprint.getLNG())));
        }
        mAmap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(8).color(0xff37af4d));
    }

    private void addPoints(List<GetElderMonitorData.ContentBean.MONITORDATALISTBean> footprints) {
        markerOptionses = new ArrayList<>();
        for (int i = 0; i < footprints.size(); i++) {
            MarkerOptions markerOption = new MarkerOptions();
            markerOption.position(new LatLng(Double.valueOf(footprints.get(i).getLAT()), Double.valueOf(footprints.get(i).getLNG())));
            markerOption.title(footprints.get(i).getBASESTATIONNAME()).snippet(footprints.get(i).getBASESATTIONTIME());
            markerOption.draggable(true);//设置Marker可拖动
            if (i == 0) {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.end_point)));
            } else if (i == footprints.size() - 1) {
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.start_point)));
            }else{
                markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),R.mipmap.middle_point)));
            }
            markerOption.setFlat(true);//设置marker平贴地图效果
            markerOptionses.add(markerOption);
        }
        markers = mAmap.addMarkers(markerOptionses, true);
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {
        setTitle("老人轨迹");
    }

    public static void goActivity(Context context, String smartcareId) {
        Intent intent = new Intent(context, FootprintActivity.class);
        intent.putExtra("smartcareId", smartcareId);
        context.startActivity(intent);
    }

    @Override
    public void onMapLoaded() {



    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        markers.get(position).showInfoWindow();
    }
}
