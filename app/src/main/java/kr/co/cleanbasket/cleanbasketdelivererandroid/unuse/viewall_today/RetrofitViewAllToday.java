package kr.co.cleanbasket.cleanbasketdelivererandroid.unuse.viewall_today;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 7..
 */
public class RetrofitViewAllToday {
    private ViewAllTodayService service;

    public RetrofitViewAllToday() {
        this.service = Network.getInstance().getRetrofit().create(ViewAllTodayService.class);
    }

    public interface ViewAllTodayService {
        @POST(AddressManager.DELIVERER_TODAY_PIKUP)
        Call<JsonData> getTodayPickUp();

        @POST(AddressManager.DELIVERER_TODAY_DROPOFF)
        Call<JsonData> getTodayDropOff();
    }

    public void getTodayPickUp(Callback<JsonData> getTodayPickUpCallback) {
        Call<JsonData> call = service.getTodayPickUp();
        call.enqueue(getTodayPickUpCallback);
    }

    public void getTodayDropOff(Callback<JsonData> getTodayDropOffCallback) {
        Call<JsonData> call = service.getTodayPickUp();
        call.enqueue(getTodayDropOffCallback);
    }
}
