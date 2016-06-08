package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import android.util.Log;

import com.google.gson.Gson;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
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
}
