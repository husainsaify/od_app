package com.hackerkernel.ondew.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackerkernel.ondew.network.MyVolley;
import com.hackerkernel.ondew.R;
import com.hackerkernel.ondew.Util.Util;
import com.hackerkernel.ondew.extras.ApiUrl;
import com.hackerkernel.ondew.extras.Keys;
import com.hackerkernel.ondew.model.SimplePojo;
import com.hackerkernel.ondew.parser.JsonParser;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    //views
    @Bind(R.id.number_edittext) EditText mNumberView;
    @Bind(R.id.login_button) Button mLoginView;
    @Bind(R.id.layoutForSnackbar) View mLayoutForSnackbar;

    //member var
    private RequestQueue mRequestQueue;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //init volley
        mRequestQueue = MyVolley.getInstance().getRequestQueue();

        //init pd
        pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.requesting_otp));
        pd.setCancelable(true);

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
            //validate mobile number
            if (mobile.length() != 10){
                Util.showRedSnackbar(mLayoutForSnackbar,getString(R.string.invalid_moble_number));
                return;
            }

            doLoginInBackground(mobile);
        }else{
           Util.showNoInternetSnackbar(getApplication(),mLayoutForSnackbar);
        }
    }

    /*
    * Method to do login in background
    * */
    private void doLoginInBackground(final String mobile){
        pd.show(); //show progressbar
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss(); //hide progressbar
                parseLoginResponse(response,mobile);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss(); //hide progressbar
                error.printStackTrace();
                Log.e(TAG,"HUS: doLoginInBackground: "+error.getMessage());
                //handle volley error
                String errorString = MyVolley.handleVolleyError(error);
                Util.showRedSnackbar(mLayoutForSnackbar,errorString);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Keys.COM_APIKEY,Keys.APIKEY);
                params.put(Keys.COM_MOBILE,mobile);
                return params;
            }
        };
        mRequestQueue.add(request);
    }

    /*
    * Method to parse login response
    * */
    private void parseLoginResponse(String response,String mobile) {
        try {
            SimplePojo current = JsonParser.SimpleParser(response);
            if (current.isReturned()){
                //success OTP send
                //go to OTP verification activity
                Intent intent = new Intent(this,OtpVerificationActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra(Keys.COM_MOBILE,mobile);
                startActivity(intent);
            }else{
                //error
                Util.showRedSnackbar(mLayoutForSnackbar,current.getMesssage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"HUS: parseLoginResponse: unable to parse response/ "+e.getMessage());
            //TODO:: show unable to parse response dialog
        }
    }


}
