package kr.co.cleanbasket.cleanbasketdelivererandroid.unuse.viewall;

import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public class AssignProxy {
    ViewAllService service;

    public AssignProxy() {
        service = Network.getInstance().getRetrofit().create(ViewAllService.class);
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
