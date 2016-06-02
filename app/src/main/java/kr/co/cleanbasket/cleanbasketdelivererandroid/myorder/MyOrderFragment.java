package kr.co.cleanbasket.cleanbasketdelivererandroid.myorder;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * MyOrderFragment.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 3. 9..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class MyOrderFragment extends Fragment implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private ListView detail;

    private Network network;
    private Retrofit retrofit;
    private MyOrderService service;

    private Gson gson;
    private ArrayList<OrderInfo> dropoffList;
    private ArrayList<OrderInfo> pickUpList;

    private MyOrderDropOffAdapter myOrderDropOffAdapter;
    private MyOrderPickUpAdapter myOrderPickUpAdapter;

    private Activity context;

    private static final String TAG = "DEV_myOrderFragment";

    public MyOrderFragment() {

    }

    public MyOrderFragment(Activity context) {
        gson = new Gson();
        this.context = context;
        network = new Network(context);
        retrofit = network.getRetrofit();
        service = retrofit.create(MyOrderService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_my_order, container, false);

        tabHost = (MaterialTabHost) v.findViewById(R.id.materialTabHost);
        detail = (ListView) v.findViewById(R.id.lv_detail);

        pickupData();

        tabHost.addTab(tabHost.newTab().setText("수거").setTabListener(this));
        tabHost.addTab(tabHost.newTab().setText("배달").setTabListener(this));

        return v;
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
//        pager.setCurrentItem(tab.getPosition());

        switch (tab.getPosition()) {
            case 0:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                pickupData();
                break;
            case 1:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                dropoffData();
                break;
        }

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

        switch (tab.getPosition()) {
            case 0:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                pickupData();
                break;
            case 1:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                dropoffData();
                break;
        }

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    private ArrayList<OrderInfo> pickupData() {
        pickUpList = new ArrayList<OrderInfo>();
        Call<JsonData> result = service.sendPickUpData();
        result.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {

                JsonData result = response.body();
                pickUpList = gson.fromJson(result.data, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());

                setMyOrderPickUpAdapter();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e(TAG, "POST FAILED TO " + AddressManager.DELIVERER_PICKUP + " : " + t.getMessage());
            }
        });

        return pickUpList;
    }

    private void setMyOrderPickUpAdapter() {
        myOrderPickUpAdapter = new MyOrderPickUpAdapter(context, pickUpList);
        detail.setAdapter(myOrderPickUpAdapter);

        detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myOrderPickUpAdapter.showOrderDetailDialog(position);
            }
        });
    }

    private ArrayList<OrderInfo> dropoffData() {
        dropoffList = new ArrayList<OrderInfo>();

        Call<JsonData> result = service.sendDropOffData();
        result.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                dropoffList = gson.fromJson(jsonData.data, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());
                Log.i("TEST", dropoffList.get(0).dropoff_date);
                setOrderDropOffAdatper();

            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e(TAG, "POST FAILED TO " + AddressManager.DELIVERER_DROPOFF + " : " + t.getMessage());
            }
        });

        return dropoffList;
    }

    private void setOrderDropOffAdatper() {
        myOrderDropOffAdapter = new MyOrderDropOffAdapter(context, dropoffList);
        detail.setAdapter(myOrderDropOffAdapter);

        detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                myOrderDropOffAdapter.showOrderDetailDialog(position);
            }
        });
    }

}
