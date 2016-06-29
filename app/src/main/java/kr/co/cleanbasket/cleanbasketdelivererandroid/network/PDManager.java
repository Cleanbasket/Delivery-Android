package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.DelivererInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class PdManager {
    private static PdManager pdManager;
    private ArrayList<DelivererInfo> delivererInfo = new ArrayList<>();
    private ArrayList<String> pdList = new ArrayList<>();
    private Gson gson = new Gson();

    public static PdManager getInstance() {
        if (pdManager == null) {
            pdManager = new PdManager();
        }
        return pdManager;
    }

    private PdManager() {
        OrderInterface service = RetrofitBase.getInstance().getRetrofit().create(OrderInterface.class);
        Call<JsonData> response = service.getDelievererList();
        response.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                Log.i("PdManager", "GET PdManager SUCCESS");
                JsonData jsonData = response.body();
                delivererInfo = gson.fromJson(jsonData.data, new TypeToken<ArrayList<DelivererInfo>>() {
                }.getType());

                for (int i = 0; i < delivererInfo.size(); i++) {
                    pdList.add(delivererInfo.get(i).name);
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    public ArrayList<DelivererInfo> getDelivererInfo() {
        return delivererInfo;
    }

    public ArrayList<String> getPdList() {
        return pdList;
    }

}
