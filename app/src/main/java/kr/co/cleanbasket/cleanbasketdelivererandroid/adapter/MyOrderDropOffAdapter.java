package kr.co.cleanbasket.cleanbasketdelivererandroid.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.OrderManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;

/**
 * MyOrderDropOffAdapter.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 3. 11..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class MyOrderDropOffAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<OrderInfo> orderArrayList;
    private Gson gson = new Gson();
    private MyOrderDropOffAdapter myOrderDropOffAdapter;

    private OrderManager orderManager;

    public MyOrderDropOffAdapter(Activity context, ArrayList<OrderInfo> orderArrayList) {
        myOrderDropOffAdapter = this;
        this.context = context;
        this.orderArrayList = orderArrayList;
        orderManager = new OrderManager();
    }

    @Override
    public int getCount() {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View row = inflater.inflate(R.layout.item_drop_off, null, true);

        TextView order_number = (TextView) row.findViewById(R.id.order_number);
        TextView price = (TextView) row.findViewById(R.id.price);
        TextView dropoff_date = (TextView) row.findViewById(R.id.dropoff_date);
        TextView address = (TextView) row.findViewById(R.id.address);
        TextView item = (TextView) row.findViewById(R.id.item);
        TextView memo = (TextView) row.findViewById(R.id.memo);

        TextView t1 = (TextView) row.findViewById(R.id.t1);
        TextView t2 = (TextView) row.findViewById(R.id.t2);
        TextView t3 = (TextView) row.findViewById(R.id.t3);
        TextView t4 = (TextView) row.findViewById(R.id.t4);
        TextView t5 = (TextView) row.findViewById(R.id.t5);
        TextView t6 = (TextView) row.findViewById(R.id.t6);

        DecimalFormat df = new DecimalFormat("#,##0");

        order_number.setText(orderArrayList.get(position).order_number);

        String price_str = String.valueOf(df.format(orderArrayList.get(position).price));
        Log.i("MyDropOff", "PRICE : " + price_str);
        price.setText(price_str);

        dropoff_date.setText(orderArrayList.get(position).getPrettyDropOffDate());
        address.setText(orderArrayList.get(position).getFullAddress());
        item.setText(orderArrayList.get(position).makeItem());
        memo.setText(orderArrayList.get(position).memo);

        if (orderArrayList.get(position).state == 4) {
            order_number.setTextColor(row.getResources().getColor(R.color.gray));
            dropoff_date.setTextColor(row.getResources().getColor(R.color.gray));
            address.setTextColor(row.getResources().getColor(R.color.gray));
            price.setTextColor(row.getResources().getColor(R.color.gray));
            memo.setTextColor(row.getResources().getColor(R.color.gray));
            item.setTextColor(row.getResources().getColor(R.color.gray));

            t1.setTextColor(row.getResources().getColor(R.color.gray));
            t2.setTextColor(row.getResources().getColor(R.color.gray));
            t3.setTextColor(row.getResources().getColor(R.color.gray));
            t4.setTextColor(row.getResources().getColor(R.color.gray));
            t5.setTextColor(row.getResources().getColor(R.color.gray));
            t6.setTextColor(row.getResources().getColor(R.color.gray));
        }

        return row;
    }

}
