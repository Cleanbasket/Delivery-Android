package kr.co.cleanbasket.cleanbasketdelivererandroid.search_order;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Phone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gingeraebi on 2016. 6. 3..
 */
public interface SearchOrderService {
    @GET(AddressManager.DELIVERER_ORDER + "/{oid}")
    Call<JsonData> getOrderByOid(@Path("oid")int oid);

    @POST(AddressManager.DELIVERER_ORDER + "/phone")
    Call<JsonData> getOrderByPhone(@Body Phone phone);

}
