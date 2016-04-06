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
import kr.co.cleanbasket.cleanbasketdelivererandroid.adapter.ViewAllOrderAdapter;

/**
 * ViewAllOrderFragment.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 6..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class ViewAllOrderFragment extends Fragment implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private ListView detail;

    private ViewAllOrderAdapter viewAllOrderAdapter;

    private Activity context;

    public ViewAllOrderFragment(Activity context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.view_all_fragment, container, false);

        tabHost = (MaterialTabHost) v.findViewById(R.id.materialTabHost);
        detail = (ListView) v.findViewById(R.id.lv_detail);

        viewAllOrderAdapter = new ViewAllOrderAdapter(context);

        detail.setAdapter(viewAllOrderAdapter);

        tabHost.addTab(tabHost.newTab().setText("시간순").setTabListener(this));
        tabHost.addTab(tabHost.newTab().setText("주문번호순").setTabListener(this));

        return v;
    }


    @Override
    public void onTabSelected(MaterialTab tab) {
//        pager.setCurrentItem(tab.getPosition());

        switch (tab.getPosition()){
            case 0:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(viewAllOrderAdapter);
                break;
            case 1:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(viewAllOrderAdapter);
                break;
        }

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

        switch (tab.getPosition()){
            case 0:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(viewAllOrderAdapter);
                break;
            case 1:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                detail.setAdapter(viewAllOrderAdapter);
                break;
        }

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

}