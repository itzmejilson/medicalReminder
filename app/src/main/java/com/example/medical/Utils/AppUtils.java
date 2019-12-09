package com.example.medical.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

public class AppUtils {

    private Activity mActivity;
    private String message;
    private String TAG = "";

    public AppUtils(Activity activity) {
        mActivity = activity;
        TAG = activity.getClass().getSimpleName();
    }

    public void showShortToast(String message) {
        try {
            this.message = message;
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLongToast(String message) {
        try {
            this.message = message;
            Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isNotEmptyString(String str) {
        return (!TextUtils.isEmpty(str));
    }

    public boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    public boolean isValidMobile(String phone) {
        return !(TextUtils.isEmpty(phone) && phone.length() == 10);
    }

    public void writeErrorLog(String message) {
        Log.e(TAG, message);
    }

    public void writeInfoLog(String message) {
        Log.i(TAG, message);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
        if (!isConnected) showShortToast("Check your network connectivity");
        return isConnected;
    }
}
