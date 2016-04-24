package com.hackerkernel.ondew.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.hackerkernel.ondew.Networks.MyVolley;
import com.hackerkernel.ondew.R;
import com.hackerkernel.ondew.Util.Util;
import com.hackerkernel.ondew.extras.ApiUrl;
import com.hackerkernel.ondew.extras.Keys;
import com.hackerkernel.ondew.model.SimplePojo;
import com.hackerkernel.ondew.parser.JsonParser;

import org.json.JSONException;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class OtpVerificationActivity extends AppCompatActivity {
    private static final String TAG = OtpVerificationActivity.class.getSimpleName();
    @Bind(R.id.layoutForSnackbar) View mLayoutForSnackbar;
    @Bind(R.id.otp_edittext) EditText mOtpView;
    @Bind(R.id.verify_button) Button mVerifyView;


    private String mMobile;
    private RequestQueue mRequestQueue;
    private ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        ButterKnife.bind(this);

        //init volley
        mRequestQueue = MyVolley.getInstance().getRequestQueue();

        //init pd
        pd = new ProgressDialog(this);
        pd.setMessage(getString(R.string.verifying_otp));
        pd.setCancelable(true);

        //get mobile from intent
        if (getIntent().hasExtra(Keys.COM_MOBILE)){
            mMobile = getIntent().getExtras().getString(Keys.COM_MOBILE);
            Toast.makeText(getApplicationContext(),mMobile,Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(getApplicationContext(), R.string.invalid_moble_unable_to_open_otp,Toast.LENGTH_LONG).show();
            finish();
        }

        mVerifyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInternetAndVerifyOtp();
            }
        });
    }

    /*
    * Method to check internet connection
    * perform validation
    * and call verifyOtpInBackground();
    *
    * */
    private void checkInternetAndVerifyOtp() {
        if (Util.isNetworkAvailable()){
            String otp = mOtpView.getText().toString();

            //validate otp
            if (otp.length() != 4){
                Util.showRedSnackbar(mLayoutForSnackbar,getString(R.string.otp_shoud_be_4_char));
                return;
            }

            verifyOtpInBackground(otp);
        }else {
            //no internet
            Util.showNoInternetSnackbar(getApplication(),mLayoutForSnackbar);
        }
    }

    /*
    * Method to verify otp from API
    * */
    private void verifyOtpInBackground(final String otp) {
        pd.show(); //show progressbar
        StringRequest request = new StringRequest(Request.Method.POST, ApiUrl.VERIFY_OTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss(); //hide progressbar
                parseOtpResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss(); //hide progressbar
                error.printStackTrace();
                Log.e(TAG,"HUS: verifyOtpInBackground: "+error.getMessage());
                //handle error
                String errorString = MyVolley.handleVolleyError(error);
                Util.showRedSnackbar(mLayoutForSnackbar,errorString);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put(Keys.COM_MOBILE,mMobile);
                params.put(Keys.COM_APIKEY, Keys.APIKEY);
                params.put(Keys.COM_OTP,otp);
                return params;
            }
        };
        mRequestQueue.add(request);
    }

    /*
    * Method to parse otp response
    * */
    private void parseOtpResponse(String response) {
        try {
            SimplePojo current = JsonParser.SimpleParser(response);
            if (current.isReturned()){
                //success
                Toast.makeText(getApplicationContext(),current.getMesssage(),Toast.LENGTH_LONG).show();
            }else {
                //error
                Util.showRedSnackbar(mLayoutForSnackbar,current.getMesssage());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG,"HUS: parseOtpResponse: unable to parse response/ "+e.getMessage());
            //TODO:: show unable to parse response dialog
        }
    }
}
