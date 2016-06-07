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

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.ServerConstants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * LoginActivity.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LogUtils.makeTag(LoginActivity.class);

    // UI Element
    private EditText etEmail;
    private EditText etPassword;

    private Button btnLogin;
    private Button btnRegister;

    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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


    private void login() {

        LoginService service = Network.getInstance().getRetrofit().create(LoginService.class);
        Call<JsonData> result= service.login(etEmail.getText().toString(),etPassword.getText().toString(),true,"");
        result.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();

                switch (jsonData.constant) {
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

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "인터넷 환경을 확인해주시길 바랍니다.", Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.toString());
            }
        });

    }
}
