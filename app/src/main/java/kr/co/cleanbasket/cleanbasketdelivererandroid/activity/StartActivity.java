package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.ServerConstants;

/**
 *  StartActivity.java
 *  CleanBasket Deliverer Android
 *
 *  Created by Yongbin Cha
 *  Copyright (c) 2016 WashAppKorea. All rights reserved.
 *
 */

public class StartActivity extends AppCompatActivity {

    private static final String TAG = LogUtils.makeTag(StartActivity.class);

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundrydelivery);

        Handler delayStartActivityHandler = new Handler();
        delayStartActivityHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginCheck();
            }
        }, 2000);
    }

    private void loginCheck(){
        gson = new Gson();
        HttpClientLaundryDelivery.post(AddressManager.LOGIN_CHECK, new TextHttpResponseHandler() {
            Intent intent = null;

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(StartActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.v(TAG, responseString);
                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
                switch (jsonData.constant) {
                    case ServerConstants.SESSION_EXPIRED:
                        Toast.makeText(StartActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(StartActivity.this, LoginActivity.class);
                        break;
                    case ServerConstants.SESSION_VALID:
                        intent = new Intent(StartActivity.this, MainActivity.class);
                        intent.putExtra("userID",jsonData.data);
                        break;
                }
                startActivity(intent);
                finish();
            }
        });
    }
}
