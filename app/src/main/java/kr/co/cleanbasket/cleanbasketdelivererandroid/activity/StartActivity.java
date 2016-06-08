package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashSet;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.auth.LoginActivity;
import kr.co.cleanbasket.cleanbasketdelivererandroid.auth.AuthService;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.ServerConstants;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.SharedPreferenceBase;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.LoginInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.RetrofitPD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private Gson gson = new Gson();

    private AuthService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundrydelivery);

        //SharedPreference init
        SharedPreferenceBase.init(this);
        HashSet<String> managerSet = new HashSet();
        managerSet.add("2219");
        managerSet.add("5310");
        managerSet.add("7602");
        managerSet.add("8701");
        managerSet.add("16781");
        managerSet.add("22114");
        managerSet.add("11130");
        managerSet.add("12923");
        managerSet.add("12530");
        managerSet.add("4199");
        managerSet.add("4197");
        managerSet.add("12721");

        SharedPreferenceBase.putSharedPreference("MANAGER",managerSet);
        service = Network.getInstance().getRetrofit().create(AuthService.class);
        RetrofitPD.getInstance();
        Handler delayStartActivityHandler = new Handler();
        delayStartActivityHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loginCheck();
            }
        }, 2000);
    }

    private void loginCheck(){

        Call<JsonData> response = service.loginCheck();
        response.enqueue(new Callback<JsonData>() {
            Intent intent = null;
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                switch (jsonData.constant) {
                    case ServerConstants.SESSION_EXPIRED:
                        Toast.makeText(StartActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                        intent = new Intent(StartActivity.this, LoginActivity.class);
                        break;
                    case ServerConstants.SESSION_VALID:
                        intent = new Intent(StartActivity.this, MainActivity.class);
                        if(SharedPreferenceBase.getSharedPreference("MANAGER", new HashSet<String>()).contains(jsonData.data.toString())){
                            SharedPreferenceBase.putSharedPreference("IsManager",true);
                        }else {
                            SharedPreferenceBase.putSharedPreference("IsManager",false);
                        }
                        intent.putExtra("userID",jsonData.data.toString());
                        break;
                }
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Toast.makeText(StartActivity.this, "세션이 만료되었습니다.", Toast.LENGTH_SHORT).show();
                intent = new Intent(StartActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
