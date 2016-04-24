package com.hackerkernel.ondew.Util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.view.View;

import com.hackerkernel.ondew.R;
import com.hackerkernel.ondew.activity.HomeActivity;
import com.hackerkernel.ondew.infrastructure.MyApplication;

/**
 * Util methods
 */
public class Util {

    /*
    * Class to check network state
    * */
    public static Boolean isNetworkAvailable (){
        Context context = MyApplication.getAppContext();
        final ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public static void showNoInternetSnackbar(final Application application, View view){
        Snackbar.make(view,R.string.NO_INTERNET_AVAILABLE,Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Settings.ACTION_SETTINGS);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        application.startActivity(intent);
                    }
                }).show();
    }

    public static void showRedSnackbar(View layout,String message){
        Snackbar snackbar = Snackbar.make(layout,message,Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public static void goToHomeActivity(Application application){
        Intent intent = new Intent(application, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        application.startActivity(intent);
    }
}
