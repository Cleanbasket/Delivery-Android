package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressConstant;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Regid;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 24..
 */
public class FcmManager {

    private FcmService service;

    public interface FcmService {
        @POST(AddressConstant.UPDATE_REGID)
        Call<JsonData> updateRegId(@Body Regid regid);
    }

    public FcmManager() {
        service = RetrofitBase.getInstance().getRetrofit().create(FcmService.class);
    }

    public void sendRegIdToServer(Callback<JsonData> callback, String token) {
        Call<JsonData> call = service.updateRegId(new Regid(token));
        call.enqueue(callback);
    }



}
