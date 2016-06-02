package kr.co.cleanbasket.cleanbasketdelivererandroid.viewall;

import android.app.Activity;

import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public class AssignProxy {
    Network network;
    Retrofit retrofit;
    ViewAllService service;

    public AssignProxy(Activity context) {
        network = new Network(context);
        retrofit = network.getRetrofit();
        service = retrofit.create(ViewAllService.class);
    }

    public void assignDropOff(OrderRequest request, Callback<JsonData> assignCallback) {
        Call<JsonData> call = service.assignDropOff(request);
        call.enqueue(assignCallback);
    }

    public void assignPickUp(OrderRequest request, Callback<JsonData> assignCallback) {
        Call<JsonData> call = service.assignPickUP(request);
        call.enqueue(assignCallback);
    }


}
