package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.viewall.ViewAllService;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class PD {

    ViewAllService service;
    ArrayList<DelivererInfo> delivererInfo;
    Gson gson = new Gson();

    public PD() {
        service = Network.getInstance().getRetrofit().create(ViewAllService.class);
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
