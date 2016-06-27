package kr.co.cleanbasket.cleanbasketdelivererandroid.fcm;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 6. 20..
 */
public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token", "Refreshed token: " + refreshedToken);
        sendRegIdToServer(refreshedToken);
    }

    private void sendRegIdToServer(final String token) {
        FcmRetrofit retrofit = new FcmRetrofit();
        retrofit.sendRegIdToServer(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                Log.d("Token", "Refreshed token: " + token + "\n success");
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.d("Token", "Refreshed token: " + token + "\n FAIL");
            }
        }, token);

    }

}
