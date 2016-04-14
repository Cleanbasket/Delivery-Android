package kr.co.cleanbasket.cleanbasketdelivererandroid.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import it.neokree.materialtabs.MaterialTabHost;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.adapter.ViewAllOrderAdapter;
import kr.co.cleanbasket.cleanbasketdelivererandroid.json.DelivererInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.json.OrderInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;

/**
 * ViewAllOrderFragment.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 6..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class ViewAllOrderFragment extends Fragment{

    private static final String TAG = LogUtils.makeTag(ViewAllOrderFragment.class);

    private MaterialTabHost tabHost;
    private ListView detail;

    private ArrayList<String> pdList;
    private ArrayAdapter<String> pdAdapter;
    private ArrayList<DelivererInfo> delivererInfo;
    private ViewAllOrderAdapter viewAllOrderAdapter;

    private Activity context;

    private Gson gson;

    private boolean mLockListView;

    private ArrayList<OrderInfo> orderArrayList;
    private ArrayList<OrderInfo> moreArrayList;

    public ViewAllOrderFragment(Activity context){
        this.context = context;
        gson = new Gson();
        mLockListView = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_all, container, false);

        detail = (ListView) v.findViewById(R.id.lv_detail);

        getOrderData();
        getPDList();

        detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(context, OrderDetailActivity.class);
//                intent.putExtra("oid", String.valueOf(orderArrayList.get(position).oid));
//                Log.i("test", String.valueOf(orderArrayList.get(position).oid));
//                startActivity(intent);

                showOrderDetailDialog(position);
            }
        });

        detail.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                Log.i(TAG, "현재 뷰는  " + firstVisibleItem + "  " + visibleItemCount + "  " + totalItemCount);
                if (totalItemCount - firstVisibleItem - visibleItemCount == 1 && mLockListView == false) {
                    getMoreData();
                }
            }
        });


        return v;
    }


    private void getPDList(){

//        arraylist = new ArrayList<String>();
//        arraylist.add("data0");
//        arraylist.add("data1");
//        arraylist.add("data2");
//        arraylist.add("data3");
//
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_dropdown_item, arraylist);
//        //스피너 속성
//        Spinner sp = (Spinner) this.findViewById(R.id.spinner);
//        sp.setPrompt("골라봐"); // 스피너 제목
//        sp.setAdapter(pdAdapter);
//        sp.setOnItemSelectedListener(this);

//        for (int i =0; i < delivererInfo.size(); i++){
//            arrayAdapter.add(delivererInfo.get(i).name);
//            Log.i("test", delivererInfo.get(i).name);
//        }

        pdList = new ArrayList<String>();
        pdAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice, pdList);

//        pdList.add(0, "크린파트너를 선택해주세요");

        delivererInfo = new ArrayList<DelivererInfo>();

        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);

        HttpClientLaundryDelivery.get(AddressManager.DELIVERER_LIST, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                Log.i("test", String.valueOf(jsonData.data));
                delivererInfo = gson.fromJson(jsonData.data, new TypeToken<ArrayList<DelivererInfo>>() {
                }.getType());

                Log.i("test", delivererInfo.get(0).name);

                for (int i =0; i < delivererInfo.size(); i++){
                    pdList.add(delivererInfo.get(i).name);
                }

            }

        });


    }

    private void getMoreData(){

        mLockListView = true;

        moreArrayList = new ArrayList<OrderInfo>();

        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);
        int size = orderArrayList.size();
        Log.d("_oid", String.valueOf(orderArrayList.get(size - 1).oid));
        params.put("oid", orderArrayList.get(size - 1).oid);
//        params.put("oid", 0);

        HttpClientLaundryDelivery.post(AddressManager.DELIVERER_ORDER, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i(TAG, responseString);

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                Log.i(TAG, jsonData.data);

                moreArrayList = gson.fromJson(jsonData.data, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());

                for (int i = 0; i < moreArrayList.size(); i++) {
                    orderArrayList.add(moreArrayList.get(i));
                }

                viewAllOrderAdapter.addList(orderArrayList);
                viewAllOrderAdapter.notifyDataSetChanged();
                mLockListView = false;
            }
        });
    }

    private ArrayList<OrderInfo> getOrderData(){

        orderArrayList = new ArrayList<OrderInfo>();
        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);
        params.put("oid", "0");

        HttpClientLaundryDelivery.post(AddressManager.DELIVERER_ORDER, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG, throwable.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.i(TAG, responseString);

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                Log.i(TAG, jsonData.data);

                orderArrayList = gson.fromJson(jsonData.data, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());

                int count = 0;
                for (int i = 0; i < orderArrayList.size(); i++) {
                    if (orderArrayList.get(i).state == 1) {
                        count++;
                    }
                }

                Log.i(TAG, "수거대기중인 항목이 " + count + "개 있습니다.");
                Log.i(TAG, "전체 주문이 " + orderArrayList.size() + "개 있습니다.");

                viewAllOrderAdapter = new ViewAllOrderAdapter(context, orderArrayList);
                detail.setAdapter(viewAllOrderAdapter);

            }
        });

        return orderArrayList;
    }


    public void showOrderDetailDialog(final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = context.getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_order2, null);

        TextView order_number = (TextView) view.findViewById(R.id.order_number);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView pickup_date = (TextView) view.findViewById(R.id.pickup_date);
        TextView dropoff_date = (TextView) view.findViewById(R.id.dropoff_date);
        TextView address = (TextView) view.findViewById(R.id.address);
        TextView item = (TextView) view.findViewById(R.id.items);
        TextView memo = (TextView) view.findViewById(R.id.memo);
        final TextView status = (TextView) view.findViewById(R.id.status);
        TextView phone = (TextView) view.findViewById(R.id.phone);
        TextView note = (TextView) view.findViewById(R.id.note);
        final TextView pickup_man = (TextView) view.findViewById(R.id.pickup_man);
        final TextView dropoff_man = (TextView) view.findViewById(R.id.dropoff_man);

        ImageView pickup_assign = (ImageView) view.findViewById(R.id.bt_pickup);
        ImageView dropoff_assign = (ImageView) view.findViewById(R.id.bt_dropoff);


        order_number.setText(orderArrayList.get(position).order_number);

        DecimalFormat df = new DecimalFormat("#,##0");

        order_number.setText(orderArrayList.get(position).order_number);

        String price_str = "";

        if (orderArrayList.get(0).coupon.isEmpty()){
            price_str = String.valueOf(df.format(orderArrayList.get(0).price));
        }else {
            price_str = String.valueOf(df.format(orderArrayList.get(0).price)) + " (" + orderArrayList.get(position).coupon.get(0).name + " )";
        }
        price.setText(price_str);

        pickup_date.setText(orderArrayList.get(position).pickup_date);
        dropoff_date.setText(orderArrayList.get(position).dropoff_date);
        address.setText(orderArrayList.get(position).address + orderArrayList.get(position).addr_number +
                orderArrayList.get(position).addr_building + orderArrayList.get(position).addr_remainder);
        item.setText(makeItem(position));
        memo.setText(orderArrayList.get(position).memo);
        note.setText(orderArrayList.get(position).note);
        status.setText(getStatus(position));
        phone.setText(orderArrayList.get(position).phone);

        pickup_man.setText(getPickupMan(position));
        dropoff_man.setText(getDropoffMan(position));

        pickup_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("수거 배정 하기");

                builder.setAdapter(pdAdapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                // AlertDialog 안에 있는 AlertDialog
                                String strName = pdAdapter.getItem(id);

                                for (int i = 0;i < delivererInfo.size(); i++){
                                    if(delivererInfo.get(i).name.equals(strName)){
//                                                innBuilder.setMessage(delivererInfo.get(i).uid);
                                        Log.i("aaaaaaa : ", String.valueOf(delivererInfo.get(i).uid));
                                        pickup_man.setText(strName);
                                        assignOrderPickup(delivererInfo.get(i).uid, position);
                                    }
                                }
                            }
                        });
                builder.show();


            }
        });

        dropoff_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle("배달 배정 하기");

                builder.setAdapter(pdAdapter,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {

                                // AlertDialog 안에 있는 AlertDialog
                                String strName = pdAdapter.getItem(id);

                                for (int i = 0;i < delivererInfo.size(); i++){
                                    if(delivererInfo.get(i).name.equals(strName)){
//                                                innBuilder.setMessage(delivererInfo.get(i).uid);
                                        Log.i("aaaaaaa : ", String.valueOf(delivererInfo.get(i).uid));
                                        dropoff_man.setText(strName);
                                        assignOrderDropoff(delivererInfo.get(i).uid, position);
                                    }
                                }
                            }
                        });
                builder.show();

            }
        });

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("배정완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("전화연결", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderArrayList.get(position).phone));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        context.startActivity(intent);
                    }
                });
        builder.create();
        builder.show();

    }

    private void assignOrderPickup(int uid, int position){
        int oid = orderArrayList.get(position).oid;

//        RequestParams params = new RequestParams();
//        params.setUseJsonStreamer(true);
//        params.put("oid", orderArrayList.get(0).oid.toString());
//        params.put("uid", String.valueOf(uid));

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("oid", orderArrayList.get(position).oid.toString());
            jsonParams.put("uid",  String.valueOf(uid));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity params = null;
        try {
            params = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("Json test", params.toString());

        Log.i("test", oid + "  " + uid);

        HttpClientLaundryDelivery.post(context, AddressManager.ASSIGN_PICKUP, params,  "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("test fail", String.valueOf(responseString));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
                viewAllOrderAdapter.notifyDataSetChanged();

                Log.i("test success", String.valueOf(jsonData));
            }
        });

    }

    private void noti_test(){

    }

    private void assignOrderDropoff(int uid, int position){
        int oid = orderArrayList.get(position).oid;

//        RequestParams params = new RequestParams();
//        params.setUseJsonStreamer(true);
//        params.put("oid", orderArrayList.get(0).oid.toString());
//        params.put("uid", String.valueOf(uid));

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("oid", orderArrayList.get(position).oid.toString());
            jsonParams.put("uid",  String.valueOf(uid));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity params = null;
        try {
            params = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("Json test", params.toString());

        Log.i("test", oid + "  " + uid);

        HttpClientLaundryDelivery.post(context, AddressManager.ASSIGN_DROPOFF, params,  "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("test fail", String.valueOf(responseString));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
                viewAllOrderAdapter.notifyDataSetChanged();
                Log.i("test success", String.valueOf(jsonData));
            }
        });

    }

    private String getPickupMan(int position){

        String name = "";

        if(orderArrayList.get(position).pickupInfo == null){
            return "수거대기";
        } else {

            try {
                JSONObject json  = new JSONObject(String.valueOf(orderArrayList.get(position).pickupInfo));
                name = json.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (name.isEmpty()) {
                return "수거대기";
            } else {
                return name;
            }
        }

    }

    private String getDropoffMan(int position){

        String name = "";

        if(orderArrayList.get(position).dropoffInfo == null){
            return "배달대기";
        } else {
            try {
                JSONObject json  = new JSONObject(String.valueOf(orderArrayList.get(position).dropoffInfo));
                name = json.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (name.isEmpty()) {
                return "배달대기";
            } else {
                return name;
            }
        }

    }

    private String getStatus(int position){
        switch (orderArrayList.get(position).state){
            case 0:
                return "수거준비중";
            case 1:
                return "수거중";
            case 2:
                return "배달준비중";
            case 3:
                return "배달중";
            case 4:
                return "배달완료";
        }
        return "";
    }

    private String makeItem(int position){
        String strItem = orderArrayList.get(position).item.get(0).name + "(" + orderArrayList.get(position).item.get(0).count + ")";
        for(int i=1; i<orderArrayList.get(position).item.size(); i++) {
            strItem += ", " + orderArrayList.get(position).item.get(i).name + "(" + orderArrayList.get(position).item.get(i).count + ")";
        }
        return strItem;
    }
}