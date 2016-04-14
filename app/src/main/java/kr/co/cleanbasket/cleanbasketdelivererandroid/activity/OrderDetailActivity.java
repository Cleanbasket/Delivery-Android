package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.json.DelivererInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.json.OrderInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.JsonData;

/**
 * OrderDetailActivity.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 8..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class OrderDetailActivity extends Activity {

    private ArrayList<OrderInfo> orderArrayList;
    private ArrayList<DelivererInfo> delivererInfo;
    private Gson gson;

    private TextView order_number;
    private TextView price;
    private TextView pickup_time;
    private TextView dropoff_time;
    private TextView address;
    private TextView item;
    private TextView note;
    private TextView memo;
    private TextView status;
    private TextView pivkup_man;
    private TextView dropoff_man;
    private Button assign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_view_detail);

        gson = new Gson();

        Intent intent = getIntent();
        String oid = intent.getStringExtra("oid");

        Log.i("test", String.valueOf(oid));
        getOrderData(oid);
        getPDList();

        order_number = (TextView) findViewById(R.id.order_number);
        price = (TextView) findViewById(R.id.price);
        pickup_time = (TextView) findViewById(R.id.pickup_time);
        dropoff_time = (TextView) findViewById(R.id.dropoff_time);
        address = (TextView) findViewById(R.id.address);
        item = (TextView) findViewById(R.id.item);
        note = (TextView) findViewById(R.id.note);
        memo = (TextView) findViewById(R.id.memo);
        status = (TextView) findViewById(R.id.status);
        pivkup_man = (TextView) findViewById(R.id.pivkup_man);
        dropoff_man = (TextView) findViewById(R.id.dropoff_man);
        assign = (Button) findViewById(R.id.assign);

    }


    private void assignView(){
        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        OrderDetailActivity.this,
                        android.R.layout.select_dialog_singlechoice);

                for (int i =0; i < delivererInfo.size(); i++){
                    arrayAdapter.add(delivererInfo.get(i).name);
                    Log.i("test", delivererInfo.get(i).name);
                }



                switch (orderArrayList.get(0).state){
                    case 0:
                        builder.setTitle("수거 배정 하기");

                        builder.setAdapter(arrayAdapter,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int id) {

                                        // AlertDialog 안에 있는 AlertDialog
                                        String strName = arrayAdapter.getItem(id);
                                        AlertDialog.Builder innBuilder = new AlertDialog.Builder(
                                                OrderDetailActivity.this);

                                        for (int i = 0;i < delivererInfo.size(); i++){
                                            if(delivererInfo.get(i).name.equals(strName)){
//                                                innBuilder.setMessage(delivererInfo.get(i).uid);
                                                assignOrder(delivererInfo.get(i).uid);
                                            }
                                        }
//                                        innBuilder.setMessage();
                                        innBuilder.setTitle("당신이 선택한 것은 ");
                                        innBuilder
                                                .setPositiveButton(
                                                        "확인",
                                                        new DialogInterface.OnClickListener() {
                                                            public void onClick(
                                                                    DialogInterface dialog,
                                                                    int which) {
                                                                dialog.dismiss();
                                                            }
                                                        });
                                        innBuilder.show();
                                    }
                                });
                        builder.show();
                        break;
                    case 1:
                        builder.setTitle("수거 배정 수정하기");
                        break;
                    case 2:
                        builder.setTitle("배달 배정 하기");
                        break;
                    case 3:
                        builder.setTitle("배달 배정 수정하기");
                        break;
                    case 4:
                        break;
                }
            }
        });
    }

    private void assignOrder(int uid){
        int oid = orderArrayList.get(0).oid;

        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);
        params.put("oid", orderArrayList.get(0).oid.toString());
        params.put("uid", String.valueOf(uid));

        Log.i("Json test", params.toString());

        Log.i("test", oid + "  " + uid);

        HttpClientLaundryDelivery.post(AddressManager.ASSIGN_PICKUP, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("test", String.valueOf(responseString));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                Log.i("test", String.valueOf(jsonData));
            }
        });

    }
    private void getPDList(){

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

                assignView();
            }

        });


    }

    private void drawDatail(){
        order_number.setText(orderArrayList.get(0).order_number);
        price.setText(String.valueOf(orderArrayList.get(0).price) + " ("+getPriceStatus()+")");
        pickup_time.setText(orderArrayList.get(0).pickup_date);
        dropoff_time.setText(orderArrayList.get(0).dropoff_date);
        address.setText(makeAddress(0));
        item.setText(makeItem(0));
        note.setText(orderArrayList.get(0).note);
        memo.setText(orderArrayList.get(0).memo);
        status.setText(getStatus());
        pivkup_man.setText(getPickupMan(0));
        dropoff_man.setText(getDropoffMan(0));
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

    private String getPriceStatus(){
        switch (orderArrayList.get(0).payment_method){
            case 0:
                return "현장 현금 결제";
            case 1:
                return "현장 카드 결제";
            case 2:
                return "계좌이체";
            case 3:
                return "인앱결제";
        }
        return "";
    }

    private String getStatus(){
        switch (orderArrayList.get(0).state){
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

    private String makeAddress(int position){

        String address = orderArrayList.get(position).address + orderArrayList.get(position).addr_number + orderArrayList.get(position).addr_building + orderArrayList.get(position).addr_remainder;
        return address;
    }

    private String makeItem(int position){

        String strItem = orderArrayList.get(position).item.get(0).name + "(" + orderArrayList.get(position).item.get(0).count + ")";
        for(int i=1; i<orderArrayList.get(position).item.size(); i++) {
            strItem += ", " + orderArrayList.get(position).item.get(i).name + "(" + orderArrayList.get(position).item.get(i).count + ")";
        }
        return strItem;
    }

    private void getOrderData(String oid){

        orderArrayList = new ArrayList<OrderInfo>();

        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);

        Log.i("test", AddressManager.DELIVERER_ORDER + "/" + oid);

        HttpClientLaundryDelivery.get(AddressManager.DELIVERER_ORDER + "/" + oid, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                String responseJson = "[" + String.valueOf(jsonData.data) + "]";

                Log.i("test", String.valueOf(jsonData.data));
                orderArrayList = gson.fromJson(responseJson, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());

                drawDatail();
            }

        });


    }

}
