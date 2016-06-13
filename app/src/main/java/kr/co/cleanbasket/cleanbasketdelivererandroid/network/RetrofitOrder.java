package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Phone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class RetrofitOrder {
    private static final String TAG = "DEV_ItemNetwork";
    private Gson gson = new Gson();
    private ItemService itemService;

    public RetrofitOrder() {
        itemService = Network.getInstance().getRetrofit().create(ItemService.class);
    }

    public interface ItemService {
        @GET(AddressManager.GET_ORDER_ITEM)
        Call<JsonData> getOrderItems();

        @GET(AddressManager.DELIVERER_ORDER)
        Call<JsonData> getOrderByOid();

        @GET(AddressManager.DELIVERER_ORDER + "/{oid}")
        Call<JsonData> getOrderByOid(@Path("oid")int oid);

        @POST(AddressManager.DELIVERER_ORDER + "/phone")
        Call<JsonData> getOrderByPhone(@Body Phone phone);

        @POST(AddressManager.UPDATE_ITEM)
        Call<JsonData> updateItem(@Body List<Item> items);

        @POST(AddressManager.UPDATE_ORDER)
        Call<JsonData> updateOrder(@Body Order order);

    }

    public void getOrderItem(Callback<JsonData> getOrderCallback) {
        Log.i(TAG, "GET FROM " + AddressManager.GET_ORDER_ITEM + "START");

        Call<JsonData> call = itemService.getOrderItems();
        call.enqueue(getOrderCallback);

    }

    public void getOrderByOid(Callback<JsonData> getOrderCallback, int oid) {
        Log.i(TAG,"GET ORDER FROM " + AddressManager.DELIVERER_ORDER + "/"+oid );

        Call<JsonData> call = itemService.getOrderByOid(oid);
        call.enqueue(getOrderCallback);

    }

    public void getOrderByPhoneNum(Callback<JsonData> getOdrderByPhoneCallback, Phone phone){
        Log.i(TAG,"GET ORDER FROM " + AddressManager.DELIVERER_ORDER + "/phone");

        Call<JsonData> call = itemService.getOrderByPhone(phone);
        call.enqueue(getOdrderByPhoneCallback);
    }

    public void updateItem(Callback<JsonData> updateItemCallback, List<Item> items) {
        Log.i(TAG,"POST ORDER FROM " + AddressManager.UPDATE_ITEM);

        Call<JsonData> call = itemService.updateItem(items);
        call.enqueue(updateItemCallback);
    }

    public void updateOrder(Callback<JsonData> updateOrderCallback, Order order) {
        Log.i(TAG,"POST ORDER FROM " + AddressManager.UPDATE_ORDER);

        Call<JsonData> call = itemService.updateOrder(order);
        call.enqueue(updateOrderCallback);

    }
}
