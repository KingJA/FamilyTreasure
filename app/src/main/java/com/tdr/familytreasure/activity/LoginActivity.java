package com.tdr.familytreasure.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.tdr.familytreasure.fragment.LoginFragment;
import com.tdr.familytreasure.fragment.RegisterFragment;
import com.tdr.familytreasure.update.GetVersionCodeAsynckTask;
import com.tdr.familytreasure.update.UpdateManager;
import com.tdr.familytreasure.util.ActivityManager;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.Utils;
import com.tdr.familytreasure.util.WebServiceUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class LoginActivity extends IndicatorFragmentActivity implements Handler.Callback {

    public static final int FRAGMENT_ONE = 0;
    public static final int FRAGMENT_TWO = 1;
    private Handler mHandler;
    private String TAG=getClass().getSimpleName();
    private int updateCount;// 更新次数

    @Override
    protected int supplyTabs(List<TabInfo> tabs) {
        tabs.add(new TabInfo(FRAGMENT_ONE, "登录", LoginFragment.class));
        tabs.add(new TabInfo(FRAGMENT_TWO, "注册", RegisterFragment.class));
        return FRAGMENT_ONE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getAppManager().addActivity(this);
        mHandler = new Handler(this);
        GetVersionCodeAsynckTask asynckTask = new GetVersionCodeAsynckTask(this, mHandler);
        asynckTask.execute("FamilyTreasure.apk");
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case Constants.HANDLER_KEY_GETVERSION_SUCCESS:
                double newVersion = Double.parseDouble(msg.obj.toString());
                try {
                    double vCode = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode;
                    if (vCode < newVersion && updateCount > 0) {
                        finish();
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                updateCount++;
                UpdateManager updateManager = new UpdateManager(newVersion, this);
                updateManager.checkUpdate();// 开始检查版本更新
                break;
            case Constants.HANDLER_KEY_GETVERSION_FAIL:
                Log.e(TAG, "获取更新版本失败");
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getAppManager().finishActivity(this);
    }
}
