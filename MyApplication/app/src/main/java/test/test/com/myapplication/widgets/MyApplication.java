package test.test.com.myapplication.widgets;

import android.app.Application;
import android.content.Context;

/**
 * Created by ShaharAlush on 29/01/2017.
 */

public class MyApplication extends Application {

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
