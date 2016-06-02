package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.viewall.ViewAllService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class PD {

    private Network network;
    private ViewAllService service;
    private Retrofit retrofit;

    ArrayList<DelivererInfo> delivererInfo;
    Gson gson = new Gson();

    public PD(Activity context) {
        network = new Network(context);
        retrofit = network.getRetrofit();
        service = retrofit.create(ViewAllService.class);
    }


    //서버에서 PD정보를 받아와 String List로 반환
    public ArrayList<String> getPDList(ArrayList<DelivererInfo> delivererInfo) {
        ArrayList<String> pdList = new ArrayList<String>();
        for (int i = 0; i < delivererInfo.size(); i++) {
            pdList.add(delivererInfo.get(i).name);
            Log.d("PD", "PD : " + delivererInfo.get(i).name);
        }

        return pdList;
    }

    public void getDelivererInfo(Callback<JsonData> getDelivererCallback) {
        delivererInfo = new ArrayList<DelivererInfo>();
        Call<JsonData> response = service.getDelievererList();
        response.enqueue(getDelivererCallback);
    }


}
