package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class ItemNetwork {
    private static final String TAG = "DEV_ItemNetwork";
    private Gson gson = new Gson();

    public void getOrderItem(TextHttpResponseHandler textHttpResponseHandler) {
        Log.i(TAG, "GET FROM " + AddressManager.GET_ORDER_ITEM + "START");

        RequestParams requestParams = new RequestParams();
        HttpClientLaundryDelivery.get(AddressManager.GET_ORDER_ITEM, requestParams, textHttpResponseHandler );

    }

    public void getOrderByOid(TextHttpResponseHandler textHttpResponseHandler, int oid) {
        Log.i(TAG,"GET ORDER FROM " + AddressManager.DELIVERER_ORDER + "/"+oid );

        RequestParams requestParams = new RequestParams();
        HttpClientLaundryDelivery.get(AddressManager.DELIVERER_ORDER + "/" +oid, requestParams, textHttpResponseHandler);
    }
}
