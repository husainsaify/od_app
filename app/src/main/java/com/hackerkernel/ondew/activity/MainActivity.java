package com.hackerkernel.ondew.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hackerkernel.ondew.R;
import com.hackerkernel.ondew.Util.Util;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.number_edittext) EditText mNumberView;
    @Bind(R.id.login_button) Button mLoginView;
    @Bind(R.id.layoutForSnackbar) View mLayoutForSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mLoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetAndDoLogin();
            }
        });
    }
    /*
    * METHOD TO CHECK INTERNET AND
    * CALL LOGIN IN BACKGROUND
    * */
    private void checkInternetAndDoLogin() {
        String mobile = mNumberView.getText().toString().trim();
        //check internet state
        if (Util.isNetworkAvailable()){

        }else{
           Util.showNoInternetSnackbar(getApplicationContext(),mLayoutForSnackbar);
        }
    }


}
