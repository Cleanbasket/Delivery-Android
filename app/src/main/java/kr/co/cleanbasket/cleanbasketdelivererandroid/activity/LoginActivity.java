package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.ServerConstants;

/**
 *  LoginActivity.java
 *  CleanBasket Deliverer Android
 *
 *  Created by Yongbin Cha
 *  Copyright (c) 2016 WashAppKorea. All rights reserved.
 *
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LogUtils.makeTag(LoginActivity.class);

    // UI Element
    private EditText etEmail;
    private EditText etPassword;

    private Button btnLogin;
    private Button btnRegister;

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Cookie init
        PersistentCookieStore myCookieStore = new PersistentCookieStore(getApplicationContext());
        myCookieStore.clear();

        gson = new Gson();

        // Init UI Element
        etEmail = (EditText) findViewById(R.id.et_login_email);
        etPassword = (EditText) findViewById(R.id.et_login_password);

        btnLogin = (Button) findViewById(R.id.bt_login);
        btnRegister = (Button) findViewById(R.id.bt_sign_up);

        // set OnClickListener Button
        BtnOnClickListener btnOnClickListener = new BtnOnClickListener();
        btnLogin.setOnClickListener(btnOnClickListener);
        btnRegister.setOnClickListener(btnOnClickListener);

    }

    class BtnOnClickListener implements View.OnClickListener {

        private Intent intent;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_login:
                    login();
                    break;
                case R.id.bt_sign_up:
                    intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }


    private void login(){

        RequestParams requestParams = new RequestParams();
        requestParams.put("email", etEmail.getText().toString());
        requestParams.put("password", etPassword.getText().toString());
        requestParams.put("remember", true);
        requestParams.put("regid", "");


        HttpClientLaundryDelivery.post(AddressManager.LOGIN, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "인터넷 환경을 확인해주시길 바랍니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.v(TAG, responseString);

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                switch(jsonData.constant) {
                    case ServerConstants.SESSION_EXPIRED:

                        break;
                    case ServerConstants.EMAIL_ERROR:
                        Toast.makeText(LoginActivity.this, "존재하지 않는 이메일입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case ServerConstants.PASSWORD_ERROR:
                        Toast.makeText(LoginActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case ServerConstants.ACCOUNT_DISABLED:
                        Toast.makeText(LoginActivity.this, "승인되지 않은 사용자입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case ServerConstants.SUCCESS:
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                        break;
                }
            }
        });

    }
}
