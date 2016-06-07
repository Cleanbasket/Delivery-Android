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

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.DelivererInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;

/**
 * OrderDetailActivity.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 8..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class OrderDetailActivity extends Activity {

    private ArrayList<DelivererInfo> delivererInfo;
    private Gson gson = new Gson();
    private OrderInfo orderInfo;

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

        setContentView(R.layout.activity_order_detail);
//
//        initView();
//        getOrderData(getOid());
//        getPartnerList();

    }

//    private String getOid() {
//        Intent intent = getIntent();
//        String oid = intent.getStringExtra("oid");
//        Log.i("DEV_orderActivity", "oid : " + oid);
//        return oid;
//    }
//
//    private void initView() {
//        Log.i("DEV_orderActivity", "initView Start");
//        order_number = (TextView) findViewById(R.id.order_number);
//        price = (TextView) findViewById(R.id.price);
//        pickup_time = (TextView) findViewById(R.id.pickup_time);
//        dropoff_time = (TextView) findViewById(R.id.dropoff_time);
//        address = (TextView) findViewById(R.id.address);
//        item = (TextView) findViewById(R.id.item);
//        note = (TextView) findViewById(R.id.note);
//        memo = (TextView) findViewById(R.id.memo);
//        status = (TextView) findViewById(R.id.status);
//        pivkup_man = (TextView) findViewById(R.id.pivkup_man);
//        dropoff_man = (TextView) findViewById(R.id.dropoff_man);
//        assign = (Button) findViewById(R.id.assign);
//    }
//
//    private void getOrderData(String oid){
//
//        RequestParams params = new RequestParams();
//        params.setUseJsonStreamer(true);
//
//        HttpClientLaundryDelivery.get(AddressManager.DELIVERER_ORDER + "/" + oid, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.e("DEV_orderActivity", "POST TO " + AddressManager.DELIVERER_ORDER + "FAIL : " + throwable.getMessage());
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
//                String jsonToStringData =  String.valueOf(jsonData.data);
//                Log.i("DEV_orderActivity", "ORDER LIST FROM" + AddressManager.DELIVERER_ORDER + " : " + jsonToStringData);
//
//                //TODO JSONUtil에 함수로 빼기
//                String responseJson = "[" + jsonToStringData + "]";
//
//                ArrayList<OrderInfo> orderArrayList = gson.fromJson(responseJson, new TypeToken<ArrayList<OrderInfo>>() {
//                }.getType());
//
//                orderInfo = orderArrayList.get(0);
//
//                drawDetail();
//            }
//
//        });
//
//
//    }
//
//    private void drawDetail(){
//        order_number.setText(orderInfo.getOrder_number());
//        price.setText(orderInfo.getPrice() + " ("+ orderInfo.getPriceStatus()+")");
//        pickup_time.setText(orderInfo.getPickup_date());
//        dropoff_time.setText(orderInfo.getDropoff_date());
//        address.setText(orderInfo.getFullAddress());
//        item.setText(orderInfo.makeItem());
//        note.setText(orderInfo.getNote());
//        memo.setText(orderInfo.getMemo());
//        status.setText(orderInfo.getStatus());
//        pivkup_man.setText(orderInfo.getPickupMan());
//        dropoff_man.setText(orderInfo.getDropoffMan());
//    }
//
//    private void getPartnerList(){
//
//        delivererInfo = new ArrayList<DelivererInfo>();
//
//        RequestParams params = new RequestParams();
//        params.setUseJsonStreamer(true);
//
//
//        HttpClientLaundryDelivery.get(AddressManager.DELIVERER_LIST, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
//                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
//                Log.i("DEV_orderActivity", "RESPONSE FROM " + AddressManager.DELIVERER_LIST + " : \n" +
//                        String.valueOf(jsonData.data));
//                delivererInfo = gson.fromJson(jsonData.data, new TypeToken<ArrayList<DelivererInfo>>() {
//                }.getType());
//
//                Log.i("DEV_orderActivity", "TEST FOR delivererInfo(0) Name : " + delivererInfo.get(0).name);
//
//                assignView();
//            }
//
//        });
//
//
//    }
//
//    private void assignView(){
//        assign.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
//                        OrderDetailActivity.this,
//                        android.R.layout.select_dialog_singlechoice);
//
//                for (int i =0; i < delivererInfo.size(); i++){
//                    arrayAdapter.add(delivererInfo.get(i).name);
//                    Log.i("test", delivererInfo.get(i).name);
//                }
//
//                switch (orderInfo.getState()){
//                    case 0:
//                        builder.setTitle("수거 배정 하기");
//
//                        builder.setAdapter(arrayAdapter,
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int id) {
//
//                                        // AlertDialog 안에 있는 AlertDialog
//                                        String strName = arrayAdapter.getItem(id);
//                                        AlertDialog.Builder innBuilder = new AlertDialog.Builder(
//                                                OrderDetailActivity.this);
//
//                                        for (int i = 0;i < delivererInfo.size(); i++){
//                                            if(delivererInfo.get(i).name.equals(strName)){
////                                                innBuilder.setMessage(delivererInfo.get(i).uid);
//                                                assignOrder(delivererInfo.get(i).uid);
//                                            }
//                                        }
////                                        innBuilder.setMessage();
//                                        innBuilder.setTitle("당신이 선택한 것은 ");
//                                        innBuilder
//                                                .setPositiveButton(
//                                                        "확인",
//                                                        new DialogInterface.OnClickListener() {
//                                                            public void onClick(
//                                                                    DialogInterface dialog,
//                                                                    int which) {
//                                                                dialog.dismiss();
//                                                            }
//                                                        });
//                                        innBuilder.show();
//                                    }
//                                });
//                        builder.show();
//                        break;
//                    case 1:
//                        builder.setTitle("수거 배정 수정하기");
//                        break;
//                    case 2:
//                        builder.setTitle("배달 배정 하기");
//                        break;
//                    case 3:
//                        builder.setTitle("배달 배정 수정하기");
//                        break;
//                    case 4:
//                        break;
//                }
//            }
//        });
//    }
//
//    private void assignOrder(int uid){
//        int oid = orderInfo.getOid();
//
//        RequestParams params = new RequestParams();
//        params.setUseJsonStreamer(true);
//        params.put("oid", "" + oid);
//        params.put("uid", "" + uid);
//
//        Log.i("DEV_orderActivity", "REQUEST TO " + AddressManager.ASSIGN_PICKUP + " :\n " +
//                params.toString());
//
//        HttpClientLaundryDelivery.post(AddressManager.ASSIGN_PICKUP, params, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                Log.i("DEV_orderActivity", "FAILURE FROM " + AddressManager.ASSIGN_PICKUP + " :\n" +
//                        String.valueOf(responseString));
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
//                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
//
//                Log.i("DEV_orderActivity","RESPONSE FROM " + AddressManager.ASSIGN_PICKUP + " :\n"
//                        + String.valueOf(jsonData));
//            }
//        });
//
//    }



}
