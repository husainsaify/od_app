package com.hackerkernel.ondew.infrastructure;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;

import com.hackerkernel.ondew.activity.MainActivity;
import com.hackerkernel.ondew.storage.MySharedPreference;

/**
 * Class to check login status
 * if user is not logged in send him to login activity
 */
public abstract class BaseAuthActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!MySharedPreference.getInstance().getLoginStatus()){
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
