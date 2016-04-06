package kr.co.cleanbasket.cleanbasketdelivererandroid.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;

/**
 * MyOrderDropOffAdapter.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 3. 11..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class MyOrderDropOffAdapter extends BaseAdapter {

    private Activity context;


    public MyOrderDropOffAdapter(Activity context){

        this.context = context;
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View row = inflater.inflate(R.layout.drop_off_detail, null, true);

        return row;
    }
}
