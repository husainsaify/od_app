package com.hackerkernel.ondew.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.hackerkernel.ondew.activity.OtpVerificationActivity;
import com.hackerkernel.ondew.extras.Keys;

/**
 * Class to read sms Automatically
 */
public class SmsReceiver extends BroadcastReceiver {

    private static final String TAG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try{
            if (bundle != null){
                Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null){
                    for (Object aPdubsobj : pdusObj){

                        //get current message
                        SmsMessage currentMessage;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            String format = bundle.getString("format");
                            currentMessage = SmsMessage.createFromPdu((byte[]) aPdubsobj,format);
                        }else{
                            currentMessage = SmsMessage.createFromPdu((byte[]) aPdubsobj);
                        }

                        String senderAddress = currentMessage.getDisplayOriginatingAddress();
                        String message = currentMessage.getDisplayMessageBody();

                        Log.d(TAG,"HUS: onReceive: Received SMS: "+message+" / "+senderAddress);

                        //check sms is from our gateway only
                        if (!senderAddress.toLowerCase().contains(Keys.OTP_SMS_SENDER_ID.toLowerCase())){
                            Log.d(TAG,"HUS: onReceive: SMS is not from our gateway");
                            return;
                        }

                        String otp = getOtpCodeFromMessage(message);

                        Log.d(TAG,"HUS: otp "+otp);

                        OtpVerificationActivity.getInstance().updateUI(otp);
                    }
                }else {
                    Log.d(TAG,"HUS: onReceive: pdus is null");
                }
            }
        }catch (Exception e){
            Log.e(TAG,"HUS: onReceive: "+e.getMessage());
        }

    }

    private String getOtpCodeFromMessage(String otp){
        return otp.substring(otp.length() - 4);
    }
}
