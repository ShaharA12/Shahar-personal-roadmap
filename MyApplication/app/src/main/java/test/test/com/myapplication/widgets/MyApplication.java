package test.test.com.myapplication.widgets;

import android.content.Context;
import android.support.multidex.MultiDexApplication;

/**
 * Created by ShaharAlush on 29/01/2017.
 */

public class MyApplication extends MultiDexApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
