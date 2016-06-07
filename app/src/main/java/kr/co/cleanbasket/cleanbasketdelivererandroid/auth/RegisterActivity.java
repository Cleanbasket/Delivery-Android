package kr.co.cleanbasket.cleanbasketdelivererandroid.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.ServerConstants;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.ImageManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.InputValidationChecker;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * RegisterActivity.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */

public class RegisterActivity extends AppCompatActivity {

    // UI Element

    private static final String TAG = LogUtils.makeTag(RegisterActivity.class);

    private ImageView btnProfileImage;

    private EditText etEmail;
    private EditText etPassword;
    private EditText etName;
    private EditText etBirth;
    private EditText etPhoneNumber;

    private Button btnRegister;

    private Gson gson = new Gson();

    File mFileTemp;
    boolean isProfilFilled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        gson = new Gson();

        btnProfileImage = (ImageView) findViewById(R.id.iv_register_profilimage);

        etEmail = (EditText) findViewById(R.id.et_register_email);
        etPassword = (EditText) findViewById(R.id.et_register_password);
        etName = (EditText) findViewById(R.id.et_register_name);
        etBirth = (EditText) findViewById(R.id.et_register_birth);
        etPhoneNumber = (EditText) findViewById(R.id.et_register_phonenumber);

        btnRegister = (Button) findViewById(R.id.iv_btn_register_confirm);

        BtnOnCLickListener btnOnCLickListener = new BtnOnCLickListener();
        btnProfileImage.setOnClickListener(btnOnCLickListener);
        btnRegister.setOnClickListener(btnOnCLickListener);

    }

    class BtnOnCLickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            ImageManager.setTempPhotoFileName("tempimage.jpg");
            switch (v.getId()) {
                case R.id.iv_register_profilimage:
                    alertDialogCamera();
                    break;
                case R.id.iv_btn_register_confirm:
                    requestRegistration();
                    break;

            }
        }
    }

    private void alertDialogCamera() {
        CharSequence[] array = {"카메라", "앨범"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("프로필 사진 업로드");
        dialog.setItems(array, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ImageManager.setTempPhotoFileName("tempimage.jpg");
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

    private void requestRegistration() {
        if (!checkInvalidation()) {
            return;
        }

        AuthService authService = Network.getInstance().getRetrofit().create(AuthService.class);

        File file = getTempFile();

        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<JsonData> call = authService.requestJoin(body, etEmail.getText().toString(), etPassword.getText().toString(), etName.getText().toString(), etPhoneNumber.getText().toString(), etBirth.getText().toString());

        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {

                JsonData jsonData = response.body();

                switch (jsonData.constant) {
                    case ServerConstants.ERROR:
                        Toast.makeText(RegisterActivity.this, "일시적인 오류로 회원가입 처리에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case ServerConstants.ACCOUNT_DUPLICATION:
                        Toast.makeText(RegisterActivity.this, "배달자 이메일 중복", Toast.LENGTH_SHORT).show();
                        break;
                    case ServerConstants.IMAGE_WRITE_ERROR:
                        Toast.makeText(RegisterActivity.this, "일시적인 오류로 회원가입 처리에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case ServerConstants.SUCCESS:
                        Toast.makeText(RegisterActivity.this, "회원가입에 성공했습니다. 로그인해주세요.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e(TAG, t.getLocalizedMessage());
            }
        });


    }

    private boolean checkInvalidation() {
        if (!isProfilFilled) {
            Toast.makeText(this, "프로필 이미지를 등록해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return InputValidationChecker.getInstance(this).isRegisterInputValid(btnProfileImage, etEmail.getText().toString(), etPassword.getText().toString(), etName.getText().toString(), etBirth.getText().toString(), etPhoneNumber.getText().toString());
    }

    private File getTempFile() {
        if (mFileTemp != null)
            return mFileTemp;

        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mFileTemp = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), ImageManager.TEMP_PHOTO_FILE_NAME);
        } else {
            mFileTemp = new File(getFilesDir(), ImageManager.TEMP_PHOTO_FILE_NAME);
        }

        return mFileTemp;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 취소시
        if (resultCode == 0) {
            return;
        }

        switch (requestCode) {
            case ImageManager.REQUEST_CODE_CAMERA:
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(getTempFile());
                    Bitmap b = (Bitmap) data.getExtras().get("data");
                    b.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    fileOutputStream.close();
                    // 이미지 크롭 화면으로 전환
                    ImageManager.startCropImage(this, getTempFile());
                } catch (Exception e) {

                }

                break;
            case ImageManager.REQUEST_CODE_GALLERY:
                try {
                    InputStream inputStream = getContentResolver().openInputStream(data.getData());
                    FileOutputStream fileOutputStream = new FileOutputStream(getTempFile());
                    ImageManager.copyStream(inputStream, fileOutputStream);
                    fileOutputStream.close();
                    inputStream.close();
                    // 이미지 크롭 화면으로 전환
                    ImageManager.startCropImage(this, getTempFile());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case ImageManager.REQUEST_CODE_CROP_IMAGE:
                String path = data.getStringExtra(ImageManager.IMAGE_PATH);
                if (path == null) {
                    return;
                }

                // 크롭 후 저장한 파일로부터 비트맵 생성
                Bitmap bitmap;
                if (requestCode == ImageManager.REQUEST_CODE_CROP_IMAGE) {
                    bitmap = BitmapFactory.decodeFile(getTempFile().getPath());
                } else {
                    bitmap = BitmapFactory.decodeFile(data.getStringExtra("path"));
                }
                // 비트맵 표시
                btnProfileImage.setImageBitmap(bitmap);
                isProfilFilled = true;

                break;
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}