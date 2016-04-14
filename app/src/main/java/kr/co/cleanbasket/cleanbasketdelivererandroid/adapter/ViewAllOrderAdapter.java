package kr.co.cleanbasket.cleanbasketdelivererandroid.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.json.OrderInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;

/**
 * ViewAllOrderAdapter.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 6..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class ViewAllOrderAdapter extends BaseAdapter {

    private static final String TAG = LogUtils.makeTag(ViewAllOrderAdapter.class);

    private Activity context;
    private Gson gson;
    private ArrayList<OrderInfo> orderArrayList = new ArrayList<OrderInfo>();

    public ViewAllOrderAdapter(Activity context, ArrayList<OrderInfo> orderArrayList){
        this.orderArrayList = orderArrayList;
        this.context = context;
        gson = new Gson();
    }

    public void addList(ArrayList<OrderInfo> orderArrayList){
        this.orderArrayList = orderArrayList;

    }

    @Override
    public int getCount() {
        Log.i(TAG, String.valueOf(orderArrayList.size()));
        return orderArrayList.size();
    }


    @Override
    public Object getItem(int position) {
        return orderArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        if ( convertView == null ) {

        LayoutInflater inflater = context.getLayoutInflater();

        convertView = inflater.inflate(R.layout.item_view_all, null, true);

        TextView tv_order_number = (TextView) convertView.findViewById(R.id.order_number);
        TextView tv_pickup_time = (TextView) convertView.findViewById(R.id.pickup_time);
        TextView tv_dropoff_time = (TextView) convertView.findViewById(R.id.dropoff_time);
        TextView tv_account = (TextView) convertView.findViewById(R.id.account);

        TextView tv_deliverer_man = (TextView) convertView.findViewById(R.id.deliverer_man);

        Log.d("order_number", orderArrayList.get(position).order_number);
        Log.d("order_number", String.valueOf(orderArrayList.get(position).price));

        Log.d("order_number", String.valueOf(orderArrayList.get(position).pickupInfo));

        tv_order_number.setText(orderArrayList.get(position).order_number);
        tv_pickup_time.setText(orderArrayList.get(position).pickup_date);
        tv_dropoff_time.setText(orderArrayList.get(position).dropoff_date);
        tv_account.setText(makeAddress(position));
        switch (orderArrayList.get(position).state){
            case 0:
                tv_deliverer_man.setText(getPickupMan(position));
                break;
            case 1:
                tv_deliverer_man.setText(getPickupMan(position));
                convertView.setBackgroundColor(Color.GRAY);
                break;
            case 2:
                tv_deliverer_man.setText(getDropoffMan(position));

                break;
            case 3:
                tv_deliverer_man.setText(getDropoffMan(position));
                convertView.setBackgroundColor(Color.GRAY);
                break;
            case 4:
                tv_deliverer_man.setText("배달완료");
                convertView.setBackgroundColor(Color.GRAY);
                break;
        }


//        }

        return convertView;
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

    private void noti_test(){
        notifyDataSetChanged();
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


}
