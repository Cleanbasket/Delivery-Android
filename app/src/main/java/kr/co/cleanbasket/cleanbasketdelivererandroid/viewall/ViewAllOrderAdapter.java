package kr.co.cleanbasket.cleanbasketdelivererandroid.viewall;

import android.app.Activity;
import android.app.Application;
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
import kr.co.cleanbasket.cleanbasketdelivererandroid.activity.DeliveryApplication;
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

        View view = inflater.inflate(R.layout.item_view_all, null, true);

        TextView pickup_ko = (TextView) view.findViewById(R.id.pickup);
        TextView pickup = (TextView) view.findViewById(R.id.pickup_time);
        TextView dropoff_ko = (TextView) view.findViewById(R.id.dropoff);
        TextView dropoff = (TextView) view.findViewById(R.id.dropoff_time);
        TextView address = (TextView) view.findViewById(R.id.address);
        TextView account = (TextView) view.findViewById(R.id.account);
        TextView memo = (TextView) view.findViewById(R.id.memo);
        TextView momo_ko = (TextView) view.findViewById(R.id.memo_ko);
        TextView orderNumber = (TextView) view.findViewById(R.id.order_number);
        TextView delivererMan = (TextView) view.findViewById(R.id.deliverer_man);

        OrderInfo orderInfo = orderArrayList.get(position);

        orderNumber.setText(orderInfo.getOrder_number());
        pickup.setText(orderInfo.getPrettyPickUpDate());
        dropoff.setText(orderInfo.getPrettyDropOffDate());
        account.setText(orderInfo.getFullAddress());
        switch (orderArrayList.get(position).state) {
            case 0:
                delivererMan.setText(orderInfo.getPickupMan());
                break;
            case 1:
                delivererMan.setText(orderInfo.getPickupMan());
                setGray(view);
                break;
            case 2:
                delivererMan.setText(orderInfo.getDropoffMan());
                break;
            case 3:
                delivererMan.setText(orderInfo.getDropoffMan());
                setGray(view);
                break;
            case 4:
                delivererMan.setText("배달완료");
                setGray(view);
                break;
        }

        return view;
    }

    private void setGray(View view) {
        TextView pickup_ko = (TextView) view.findViewById(R.id.pickup);
        TextView pickup = (TextView) view.findViewById(R.id.pickup_time);
        TextView dropoff_ko = (TextView) view.findViewById(R.id.dropoff);
        TextView dropoff = (TextView) view.findViewById(R.id.dropoff_time);
        TextView address = (TextView) view.findViewById(R.id.address);
        TextView account = (TextView) view.findViewById(R.id.account);
        TextView memo = (TextView) view.findViewById(R.id.memo);
        TextView momo_ko = (TextView) view.findViewById(R.id.memo_ko);
        TextView orderNumber = (TextView) view.findViewById(R.id.order_number);
        TextView delivererMan = (TextView) view.findViewById(R.id.deliverer_man);

        pickup.setTextColor(view.getResources().getColor(R.color.gray));
        pickup_ko.setTextColor(view.getResources().getColor(R.color.gray));
        dropoff.setTextColor(view.getResources().getColor(R.color.gray));
        dropoff_ko.setTextColor(view.getResources().getColor(R.color.gray));
        address.setTextColor(view.getResources().getColor(R.color.gray));
        account.setTextColor(view.getResources().getColor(R.color.gray));
        memo.setTextColor(view.getResources().getColor(R.color.gray));
        momo_ko.setTextColor(view.getResources().getColor(R.color.gray));
        orderNumber.setTextColor(view.getResources().getColor(R.color.gray));
        delivererMan.setTextColor(view.getResources().getColor(R.color.gray));

    }

}
