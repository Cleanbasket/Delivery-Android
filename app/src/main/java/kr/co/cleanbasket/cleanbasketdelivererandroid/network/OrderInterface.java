package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import java.util.List;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressConstant;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Phone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public interface OrderInterface {

    @Headers("Content-Type: application/json")
    @GET(AddressConstant.DELIVERER_ORDER_PICKUP)
    Call<JsonData> getPickupData();

    @Headers("Content-Type: application/json")
    @GET(AddressConstant.DELIVERER_ORDER_DROPOFF)
    Call<JsonData> getDropOffData();

    @POST(AddressConstant.DELIVERER_ORDER)
    Call<JsonData> getAllOrderList(@Body OrderRequest oid);

    @GET(AddressConstant.DELIVERER_LIST)
    Call<JsonData> getDelievererList();

    @POST(AddressConstant.ASSIGN_DROPOFF)
    Call<JsonData> assignDropOff(@Body OrderRequest orderRequest);

    @POST(AddressConstant.ASSIGN_PICKUP)
    Call<JsonData> assignPickUP(@Body OrderRequest orderRequest);

    @POST(AddressConstant.DELIVERER_PICKUP)
    Call<JsonData> sendPickUpData();

    @POST(AddressConstant.DELIVERER_DROPOFF)
    Call<JsonData> sendDropOffData();

    @POST(AddressConstant.CONFIRM_PICKUP)
    Call<JsonData> sendPickupComplete(@Body OrderRequest orderRequest);

    @POST(AddressConstant.CONFIRM_DROPOFF)
    Call<JsonData> sendDropOffComplete(@Body OrderRequest orderRequest);

    @POST(AddressConstant.DELIVERER_TODAY_PIKUP)
    Call<JsonData> getTodayPickUp();

    @POST(AddressConstant.DELIVERER_TODAY_DROPOFF)
    Call<JsonData> getTodayDropOff();

    @GET(AddressConstant.GET_ORDER_ITEM)
    Call<JsonData> getOrderItems();

    @GET(AddressConstant.DELIVERER_ORDER)
    Call<JsonData> getOrderByOid();

    @GET(AddressConstant.DELIVERER_ORDER + "/{oid}")
    Call<JsonData> getOrderByOid(@Path("oid")int oid);

    @POST(AddressConstant.DELIVERER_ORDER + "/phone")
    Call<JsonData> getOrderByPhone(@Body Phone phone);

    @POST(AddressConstant.UPDATE_ITEM)
    Call<JsonData> updateItem(@Body List<Item> items);

    @POST(AddressConstant.UPDATE_ORDER)
    Call<JsonData> updateOrder(@Body Order order);


}


