package com.hackerkernel.ondew.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.hackerkernel.ondew.infrastructure.MyApplication;

/**
 * Singleton class for shared preference
 */
public class MySharedPreference {
    private static SharedPreferences sp;
    private static MySharedPreference instance;
    //private static Context context;

    private String KEY_PREF_NAME = "ondew";
    //user details key
    private String KEY_USER_MOBILE = "mobile",
            KEY_USER_IS_LOGIN = "is_login";

    protected MySharedPreference() {
        Context context = MyApplication.getAppContext();
        sp = context.getSharedPreferences(KEY_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static MySharedPreference getInstance() {
        if (instance == null) {
            instance = new MySharedPreference();
        }
        return instance;
    }

    //getter & setter for mobile
    public void setMobile(String mobile) {
        sp.edit().putString(KEY_USER_MOBILE, mobile).apply();
    }

    public String getMobile() {
        return sp.getString(KEY_USER_MOBILE, "");
    }

    //set & get login status
    public void setLoginStatus(){
        sp.edit().putBoolean(KEY_USER_IS_LOGIN,true).apply();
    }

    public boolean getLoginStatus(){
        return sp.getBoolean(KEY_USER_IS_LOGIN,false);
    }
}
