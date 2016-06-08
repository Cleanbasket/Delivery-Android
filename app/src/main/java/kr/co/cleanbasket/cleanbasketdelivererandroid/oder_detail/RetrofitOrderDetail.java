package kr.co.cleanbasket.cleanbasketdelivererandroid.oder_detail;

import android.util.Log;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;
import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 8..
 */
public class RetrofitOrderDetail {

    public interface OrderDetailService {
        @POST(AddressManager.DELIVERER_ORDER)
        Call<JsonData> getTodayPickUp();

        @POST(AddressManager.DELIVERER_TODAY_DROPOFF)
        Call<JsonData> getTodayDropOff();
    }


}
