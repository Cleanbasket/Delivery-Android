package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressConstant;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Phone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public class OrderManager {
    private static final String TAG = "DEV_OrderManager";

    private OrderInterface service;
    private Gson gson = new Gson();

    public OrderManager() {
        service = RetrofitBase.getInstance().getRetrofit().create(OrderInterface.class);
    }


    //Order 정보를 Oid혹은 PhoneNumber를 토대로 가져온다

    public void getOrderByOid(Callback<JsonData> getOrderCallback, int oid) {
        Log.i(TAG, "GET ORDER FROM " + AddressConstant.DELIVERER_ORDER + "/" + oid);

        Call<JsonData> call = service.getOrderByOid(oid);
        call.enqueue(getOrderCallback);

    }

    public void getOrderByPhoneNum(Callback<JsonData> getOdrderByPhoneCallback, Phone phone) {
        Log.i(TAG, "GET ORDER FROM " + AddressConstant.DELIVERER_ORDER + "/phone");

        Call<JsonData> call = service.getOrderByPhone(phone);
        call.enqueue(getOdrderByPhoneCallback);
    }

    //수거 혹은 배달 배정

    public void assignDropOff(OrderRequest request, Callback<JsonData> assignCallback) {
        Call<JsonData> call = service.assignDropOff(request);
        call.enqueue(assignCallback);
    }

    public void assignPickUp(OrderRequest request, Callback<JsonData> assignCallback) {
        Call<JsonData> call = service.assignPickUP(request);
        call.enqueue(assignCallback);
    }

    // 오늘 수거 / 배달 가져오기
    public void getTodayPickUp(Callback<JsonData> getTodayPickUpCallback) {
        Call<JsonData> call = service.getTodayPickUp();
        call.enqueue(getTodayPickUpCallback);
    }

    public void getTodayDropOff(Callback<JsonData> getTodayDropOffCallback) {
        Call<JsonData> call = service.getTodayPickUp();
        call.enqueue(getTodayDropOffCallback);
    }

    //서버에서 제공하는 아이템 정보를 가져온다.
    public void getOrderItem(Callback<JsonData> getOrderCallback) {
        Log.i(TAG, "GET FROM " + AddressConstant.GET_ORDER_ITEM + "START");

        Call<JsonData> call = service.getOrderItems();
        call.enqueue(getOrderCallback);

    }

    //특정 주문에 대한 아이템 정보를 수정한다.
    public void updateItem(Callback<JsonData> updateItemCallback, List<Item> items) {
        Log.i(TAG, "POST ORDER FROM " + AddressConstant.UPDATE_ITEM);

        Call<JsonData> call = service.updateItem(items);
        call.enqueue(updateItemCallback);
    }

    //특정 주문을 수정한다.
    public void updateOrder(Callback<JsonData> updateOrderCallback, Order order) {
        Log.i(TAG, "POST ORDER FROM " + AddressConstant.UPDATE_ORDER);

        Call<JsonData> call = service.updateOrder(order);
        call.enqueue(updateOrderCallback);

    }

    //서버에 pickUp을 완료했다고 데이터 전송
    public void sendPickUpComplete(Order order, String value, Callback<JsonData> callback) {
        Call<JsonData> response = service.sendPickupComplete(new OrderRequest("" + order.getOid(), value));
        response.enqueue(callback);
    }

    public void sendDropOffComplete(OrderRequest orderRequest, Callback<JsonData> callback) {
        Call<JsonData> response = service.sendDropOffComplete(orderRequest);
        response.enqueue(callback);
    }


    public void sendPickUpData(Callback callback) {
        Call<JsonData> response = service.sendPickUpData();
        response.enqueue(callback);
    }

    public void sendDropOffData(Callback callback) {
        Call<JsonData> response = service.sendDropOffData();
        response.enqueue(callback);
    }

}
