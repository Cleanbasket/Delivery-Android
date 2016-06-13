package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.unuse.viewall.ViewAllService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class RetrofitPD {
    private static RetrofitPD retrofitPd = new RetrofitPD();
    ViewAllService service;
    public static ArrayList<DelivererInfo> delivererInfo;
    Gson gson = new Gson();

    public static RetrofitPD getInstance() {
        return retrofitPd != null ? retrofitPd : new RetrofitPD();
    }

    private RetrofitPD() {
        getDelivererInfo();
    }


    //서버에서 PD정보를 받아와 String List로 반환
    public ArrayList<String> getPDList() {
        ArrayList<String> pdList = new ArrayList<String>();
        if (delivererInfo == null) {
            getDelivererInfo();
        }

        for (int i = 0; i < delivererInfo.size(); i++) {
            pdList.add(delivererInfo.get(i).name);
        }

        return pdList;
    }

    public ArrayList<DelivererInfo> getDelivererInfo() {
        service = Network.getInstance().getRetrofit().create(ViewAllService.class);
        delivererInfo = new ArrayList<DelivererInfo>();
        Call<JsonData> response = service.getDelievererList();
        response.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                Log.i("RetrofitPD", "GET RetrofitPD SUCCESS");
                JsonData jsonData = response.body();
                delivererInfo = gson.fromJson(jsonData.data, new TypeToken<ArrayList<DelivererInfo>>() {
                }.getType());
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });

        return delivererInfo;
    }


}
