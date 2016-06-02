package kr.co.cleanbasket.cleanbasketdelivererandroid.viewall;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.DelivererInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public interface ViewAllService {

    @POST(AddressManager.DELIVERER_ORDER)
    Call<JsonData> getAllOrderList(@Body OrderRequest oid);

    @GET(AddressManager.DELIVERER_LIST)
    Call<JsonData> getDelievererList();

    @POST(AddressManager.ASSIGN_DROPOFF)
    Call<JsonData> assignDropOff(@Body OrderRequest orderRequest);

    @POST(AddressManager.ASSIGN_PICKUP)
    Call<JsonData> assignPickUP(@Body OrderRequest orderRequest);

}
