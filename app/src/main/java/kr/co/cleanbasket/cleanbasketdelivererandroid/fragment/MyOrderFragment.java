package kr.co.cleanbasket.cleanbasketdelivererandroid.fragment;

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
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.adapter.MyOrderDropOffAdapter;
import kr.co.cleanbasket.cleanbasketdelivererandroid.adapter.MyOrderPickUpAdapter;
import kr.co.cleanbasket.cleanbasketdelivererandroid.json.OrderInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.JsonData;

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

    private Gson gson;
    private ArrayList<OrderInfo> orderArrayList;

    private MyOrderDropOffAdapter myOrderDropOffAdapter;
    private MyOrderPickUpAdapter myOrderPickUpAdapter;

    private Activity context;

    public MyOrderFragment(Activity context){
        gson = new Gson();
        this.context = context;
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

        switch (tab.getPosition()){
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

        switch (tab.getPosition()){
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

    private void pickupData(){
        orderArrayList = new ArrayList<OrderInfo>();

        HttpClientLaundryDelivery.post(AddressManager.DELIVERER_PICKUP, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.i("test pickup", responseString);

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                orderArrayList = gson.fromJson(jsonData.data, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());

                myOrderPickUpAdapter = new MyOrderPickUpAdapter(context, orderArrayList);
                detail.setAdapter(myOrderPickUpAdapter);

                detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        myOrderPickUpAdapter.showOrderDetailDialog(position);
                    }
                });

            }
        });
    }

    private void dropoffData(){
        orderArrayList = new ArrayList<OrderInfo>();

        HttpClientLaundryDelivery.post(AddressManager.DELIVERER_DROPOFF, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.i("test pickup", responseString);

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                orderArrayList = gson.fromJson(jsonData.data, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());

                myOrderDropOffAdapter = new MyOrderDropOffAdapter(context, orderArrayList);
                detail.setAdapter(myOrderDropOffAdapter);

                detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        myOrderDropOffAdapter.showOrderDetailDialog(position);
                    }
                });

            }
        });
    }

}
