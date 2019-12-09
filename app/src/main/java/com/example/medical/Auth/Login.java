package com.example.medical.Auth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.medical.AppController;
import com.example.medical.R;
import com.example.medical.User.MainActivity;
import com.example.medical.Utils.AppConfig;
import com.example.medical.Utils.AppPreferences;
import com.example.medical.Utils.AppUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.roger.catloadinglibrary.CatLoadingView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {
    @BindView(R.id.txtMail)
    EditText email;
    @BindView(R.id.txtPass)
    EditText pass;
    @BindView(R.id.btnLogin)
    FloatingActionButton btnLogin;
    CatLoadingView mView;
    String uname, password, usertype = "User";
    AppPreferences ap;
    AppUtils utils;
    ProgressDialog pDialog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        ap = AppPreferences.getInstance(Login.this);
        utils = new AppUtils(Login.this);
        mView = new CatLoadingView();
    }
    @OnClick(R.id.btnLogin)
    public void btnLoginClick(){
        uname = email.getText().toString();
        password = pass.getText().toString();
        if (utils.isNetworkAvailable()) {
            if (utils.isNotEmptyString(uname)) {
                if (utils.isNotEmptyString(password)) {
                    showLoader(true);
                    //checkLogin();
                    startActivity(new Intent(Login.this, MainActivity.class));
                } else {
                    utils.showShortToast("Missing Password");
                }
            } else {
                utils.showShortToast("Missing Username");
            }
        }
    }
    public void btnForgotClick(View view){
        System.out.println("Forgot Password Clicked");
    }
    public void btnSignUpClicked(View view){
        System.out.println("Sign Up Clicked");
        startActivity(new Intent(Login.this, Register.class));
    }
    private void checkLogin() {
        String tag_string_req = "req_login";
        pDialog = new ProgressDialog(Login.this);
        pDialog.setMessage("Logging in ...");
//        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_SLOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                utils.writeErrorLog("Login Response: " + response.toString());
                showLoader(false);
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session
                        utils.showShortToast("Login successful");
                        ap.setIsLoggedIn(true);
                        // Now store the user in SQLite
                        //JSONObject user = jObj.getJSONObject("user");
                        final String user_name = jObj.optString("user_name");
                        final String lastname = jObj.optString("lastname");
                        final String mob = jObj.optString("mob");
                        final String user_mail_id = jObj.optString("user_mail_id");
                        final String address = jObj.optString("address");
                        final String user_ans = jObj.optString("user_ans");
                        final String user_pass = jObj.optString("user_pass");
                        final String user_type = jObj.optString("user_type");
                        final String user_id = jObj.optString("userid");
                        final String place = jObj.optString("place");

                        // Inserting row in users table
                        ap.LoginSession(user_name, lastname, mob, user_mail_id, address, user_ans, user_pass, user_type, user_id, place);

                        if (user_type.equals("User")) {
                            startActivity(new Intent(Login.this, MainActivity.class));
                        }
                        finish();


                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("message");
                        utils.showShortToast(errorMsg);
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    utils.showShortToast("Something went wrong");
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                showLoader(false);
                utils.showShortToast(error.getMessage());
//                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", uname);
                params.put("password", password);
                params.put("usertype", usertype);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
    private void showLoader(boolean f) {
        try {
            if (mView != null) {
                if (f)
                    mView.show(getSupportFragmentManager(), "");
                else
                    mView.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
