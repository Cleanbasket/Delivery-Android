package kr.co.cleanbasket.cleanbasketdelivererandroid.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.ImageManager;

/**
 * Created by theodore on 16. 2. 29..
 */
public class RegisterActivity extends AppCompatActivity {

    // UI Element

    private ImageView btnProfileImage;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etName;
    private EditText etBirth;
    private EditText etPhoneNumber;

    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnProfileImage = (ImageView) findViewById(R.id.iv_register_profilimage);

        etEmail = (EditText) findViewById(R.id.et_register_email);
        etPassword = (EditText) findViewById(R.id.et_register_password);
        etName = (EditText) findViewById(R.id.et_register_name);
        etBirth = (EditText) findViewById(R.id.et_register_birth);
        etPhoneNumber = (EditText) findViewById(R.id.et_register_phonenumber);

        BtnOnCLickListener btnOnCLickListener = new BtnOnCLickListener();
        btnProfileImage.setOnClickListener(btnOnCLickListener);

    }

    class BtnOnCLickListener implements View.OnClickListener{

        private Intent intent;

        @Override
        public void onClick(View v) {
            ImageManager.setTempPhotoFileName("tempimage.jpg");
            switch(v.getId()) {
                case R.id.iv_register_profilimage:
                    alertDialogCamera();
                    break;
                case R.id.iv_btn_register_confirm:
                    break;
            }
        }
    }

    private void alertDialogCamera(){
        CharSequence[] array = {"카메라", "앨범"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Pick Colour");
        dialog.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                switch (which) {
                    case 0:
                        ImageManager.openCamera(RegisterActivity.this);
                        break;
                    case 1:
                        ImageManager.openGallery(RegisterActivity.this);
                        break;
                }

            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }
}