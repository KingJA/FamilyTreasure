package com.tdr.familytreasure.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tdr.familytreasure.dao.DatebaseManager;
import com.tdr.familytreasure.net.PoolManager;
import com.tdr.familytreasure.util.Constants;
import com.tdr.familytreasure.util.CrashHandler;

import org.xutils.x;


/**
 * Created by Linus_Xie on 2016/8/3.
 */
public class App extends Application {
    public static Context context;
    private static Context mAppContext;
    private static SharedPreferences mSharedPreferences;
    @Override
    public void onCreate() {

        super.onCreate();
        CrashHandler.getInstance().init(this);
        context = this;
        mAppContext = getApplicationContext();
        x.Ext.init(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);

        PoolManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                DatebaseManager.getInstance(getApplicationContext()).copyDataBase(Constants.DATABASE_NAME);
            }
        });
    }
    public static Context getContext() {
        return mAppContext;
    }
    public static SharedPreferences getSP() {
        return mSharedPreferences;
    }
}
