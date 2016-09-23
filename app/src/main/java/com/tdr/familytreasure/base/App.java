package com.tdr.familytreasure.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


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
        context = this;
        mAppContext = getApplicationContext();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mAppContext);
    }
    public static Context getContext() {
        return mAppContext;
    }
    public static SharedPreferences getSP() {
        return mSharedPreferences;
    }
}
