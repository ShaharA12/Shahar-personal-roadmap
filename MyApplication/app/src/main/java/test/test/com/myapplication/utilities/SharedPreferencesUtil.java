package test.test.com.myapplication.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import test.test.com.myapplication.R;
import test.test.com.myapplication.widgets.MyApplication;

/**
 * Created by ShaharAlush on 01/29/17.
 */
public class SharedPreferencesUtil {


    public static String GAS_PRICE="gas_price";
    public static String KILOMETER="kilometer";
    public static String LITER="liter";

    private static SharedPreferences getSharedPreferences() {
        return MyApplication.getContext().getSharedPreferences(MyApplication.getContext().getString(R.string.app_name), Context.MODE_PRIVATE);
    }

    public static void remove(String key) {
        getSharedPreferences().edit().remove(key).apply();
    }

    public static void saveBoolean(String key, boolean value) {
        getSharedPreferences().edit().putBoolean(key, value).apply();
    }


    public static void saveString(String key, String value) {
        getSharedPreferences().edit().putString(key, value).apply();
    }

    public static void saveInt(String key, int value) {
        getSharedPreferences().edit().putInt(key, value).apply();
    }

    public static void saveLong(String key, long value) {

        getSharedPreferences().edit().putLong(key, value).commit();

    }
    public static void saveFloat(String key, float value) {

        getSharedPreferences().edit().putFloat(key, value).commit();

    }

    public static long loadLongWithDefault(String key, long defaultValue) {

        return getSharedPreferences().getLong(key, defaultValue);
    }
    public static float loadFloatWithDefault(String key, float defaultValue){
    return getSharedPreferences().getFloat(key, defaultValue);}

    public static boolean loadBooleanWithDefault(String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static String loadString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static int loadIntWithDefault(String key, int defaultString) {
        return getSharedPreferences().getInt(key, defaultString);
    }

    public static boolean contains(String key) {
        return getSharedPreferences().contains(key);
    }

    public static String loadStringWithDefault(String key, String defaultString) {
        return getSharedPreferences().getString(key, defaultString);

    }

}
