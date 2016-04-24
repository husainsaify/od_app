package com.hackerkernel.ondew.infrastructure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hackerkernel.ondew.Util.Util;
import com.hackerkernel.ondew.storage.MySharedPreference;

/**
 * Class to send logged in user to home activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if user login send him to Home activity
        if (MySharedPreference.getInstance().getLoginStatus()){
            Util.goToHomeActivity(getApplication());
        }
    }
}
