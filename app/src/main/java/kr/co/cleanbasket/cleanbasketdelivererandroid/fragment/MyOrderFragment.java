package kr.co.cleanbasket.cleanbasketdelivererandroid.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.adapter.MyOrderDropOffAdapter;
import kr.co.cleanbasket.cleanbasketdelivererandroid.adapter.MyOrderPickUpAdapter;

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

    private MyOrderDropOffAdapter myOrderDropOffAdapter;
    private MyOrderPickUpAdapter myOrderPickUpAdapter;

    private Activity context;

    public MyOrderFragment(Activity context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.my_order_fragment, container, false);

        tabHost = (MaterialTabHost) v.findViewById(R.id.materialTabHost);
        detail = (ListView) v.findViewById(R.id.lv_detail);

        myOrderDropOffAdapter = new MyOrderDropOffAdapter(context);
        myOrderPickUpAdapter = new MyOrderPickUpAdapter(context);

        detail.setAdapter(myOrderPickUpAdapter);

        tabHost.addTab(tabHost.newTab().setText("전체").setTabListener(this));
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
                detail.setAdapter(myOrderPickUpAdapter);
                break;
            case 1:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(myOrderPickUpAdapter);
                break;
            case 2:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(myOrderDropOffAdapter);
                break;
        }

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

        switch (tab.getPosition()){
            case 0:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(myOrderPickUpAdapter);
                break;
            case 1:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(myOrderPickUpAdapter);
                break;
            case 2:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(myOrderDropOffAdapter);
                break;
        }

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

}
