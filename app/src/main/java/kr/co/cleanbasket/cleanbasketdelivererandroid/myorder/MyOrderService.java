package kr.co.cleanbasket.cleanbasketdelivererandroid.myorder;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public interface MyOrderService {

    @POST(AddressManager.DELIVERER_PICKUP)
    Call<JsonData> sendPickUpData();

    @POST(AddressManager.DELIVERER_DROPOFF)
    Call<JsonData> sendDropOffData();

    @POST(AddressManager.CONFIRM_PICKUP)
    Call<JsonData> sendPickupComplete(@Body OrderRequest orderRequest);

    @POST(AddressManager.CONFIRM_DROPOFF)
    Call<JsonData> sendDropOffComplete(@Body OrderRequest orderRequest);
}
