package kr.co.cleanbasket.cleanbasketdelivererandroid.fcm;

import com.google.gson.JsonObject;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 24..
 */
public class FcmRetrofit {

    private FcmService service;

    public interface FcmService {
        @POST(AddressManager.UPDATE_REGID)
        Call<JsonData> updateRegId(@Body Regid regid);
    }

    public FcmRetrofit() {
        service = Network.getInstance().getRetrofit().create(FcmService.class);
    }

    public void sendRegIdToServer(Callback<JsonData> callback, String token) {
        Call<JsonData> call = service.updateRegId(new Regid(token));
        call.enqueue(callback);
    }
}
