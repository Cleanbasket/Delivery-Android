package kr.co.cleanbasket.cleanbasketdelivererandroid.viewall;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;
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

    public ViewAllOrderAdapter(Activity context, ArrayList<OrderInfo> orderArrayList) {
        this.orderArrayList = orderArrayList;
        this.context = context;
        gson = new Gson();
    }

    public void addList(ArrayList<OrderInfo> orderArrayList) {
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


        LayoutInflater inflater = context.getLayoutInflater();

        convertView = inflater.inflate(R.layout.item_view_all, null, true);

        TextView tv_order_number = (TextView) convertView.findViewById(R.id.order_number);
        TextView tv_pickup_time = (TextView) convertView.findViewById(R.id.pickup_time);
        TextView tv_dropoff_time = (TextView) convertView.findViewById(R.id.dropoff_time);
        TextView tv_account = (TextView) convertView.findViewById(R.id.account);
        TextView tv_deliverer_man = (TextView) convertView.findViewById(R.id.deliverer_man);

        OrderInfo orderInfo = orderArrayList.get(position);

        tv_order_number.setText(orderInfo.getOrder_number());
        tv_pickup_time.setText(orderInfo.getPrettyPickUpDate());
        tv_dropoff_time.setText(orderInfo.getPrettyDropOffDate());
        tv_account.setText(orderInfo.getFullAddress());
        switch (orderArrayList.get(position).state) {
            case 0:
                tv_deliverer_man.setText(orderInfo.getPickupMan());
                break;
            case 1:
                tv_deliverer_man.setText(orderInfo.getPickupMan());
                convertView.setBackgroundColor(Color.GRAY);
                break;
            case 2:
                tv_deliverer_man.setText(orderInfo.getDropoffMan());
                break;
            case 3:
                tv_deliverer_man.setText(orderInfo.getDropoffMan());
                convertView.setBackgroundColor(Color.GRAY);
                break;
            case 4:
                tv_deliverer_man.setText("배달완료");
                convertView.setBackgroundColor(Color.GRAY);
                break;
        }

        return convertView;
    }

}
