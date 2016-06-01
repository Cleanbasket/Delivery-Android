package kr.co.cleanbasket.cleanbasketdelivererandroid.viewall;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import it.neokree.materialtabs.MaterialTabHost;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.dialog.OrderDetailDialog;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;

/**
 * ViewAllOrderFragment.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 6..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class ViewAllOrderFragment extends Fragment {

    private static final String TAG = LogUtils.makeTag(ViewAllOrderFragment.class);

    private MaterialTabHost tabHost;
    private ListView detail;
    private ViewAllOrderAdapter viewAllOrderAdapter;

    private Activity context;

    private Gson gson;

    private boolean mLockListView;

    private ArrayList<OrderInfo> orderArrayList;
    private ArrayList<OrderInfo> moreArrayList;

    public ViewAllOrderFragment(Activity context) {
        this.context = context;
        gson = new Gson();
        mLockListView = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_all, container, false);

        detail = (ListView) v.findViewById(R.id.lv_detail);

        getOrderData();
        //pdList = new PD().getPDList();
        detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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


    //현재 서버에서 10개씩 데이터를 보내주기 때문에 더 많은 데이터를 불러올 때 사용
    private void getMoreData() {

        mLockListView = true;

        moreArrayList = new ArrayList<OrderInfo>();

        RequestParams params = new RequestParams();
        params.setUseJsonStreamer(true);
        int size = orderArrayList.size();
        Log.d("_oid", String.valueOf(orderArrayList.get(size - 1).oid));
        params.put("oid", orderArrayList.get(size - 1).oid);

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

    //처음에 데이터를 불러올 때 사용
    private ArrayList<OrderInfo> getOrderData() {

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

    //자세히 보기 다이얼로그 띄우기
    public void showOrderDetailDialog(int position) {
        OrderDetailDialog orderDetailDialog = new OrderDetailDialog(context, viewAllOrderAdapter);
        orderDetailDialog.getDialogBuilder(orderArrayList.get(position)).show();

    }


}