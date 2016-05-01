package com.hackerkernel.ondew.Util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.hackerkernel.ondew.R;
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
    public static void showNoInternetSnackbar(final Context context, View view){
        Snackbar.make(view,R.string.NO_INTERNET_AVAILABLE,Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.setting, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                }).show();
    }
}
