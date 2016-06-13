package kr.co.cleanbasket.cleanbasketdelivererandroid.unuse.viewall;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
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

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTabHost;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.unuse.OrderDetailDialog;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.oder_detail.OrderDetailActivity;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private ViewAllService service;

    public ViewAllOrderFragment(Activity context) {
        this.context = context;
        gson = new Gson();
        mLockListView = false;

        service = Network.getInstance().getRetrofit().create(ViewAllService.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_all, container, false);

        detail = (ListView) v.findViewById(R.id.lv_detail);

        getOrderData();
        //pdList = new RetrofitPD().getPDList();
        detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context ,OrderDetailActivity.class);
                int oid = orderArrayList.get(position).getOid();
                Log.i(TAG,"oid :" + oid);
                intent.putExtra("oid", oid);
                startActivity(intent);
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

        int size = orderArrayList.size();
        Call<JsonData> result = service.getAllOrderList(new OrderRequest( orderArrayList.get(size - 1).oid + ""));
        result.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {

                JsonData jsonData = response.body();

                Log.i(TAG, jsonData.data.toString());

                moreArrayList = gson.fromJson(jsonData.data, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());

                for (int i = 0; i < moreArrayList.size(); i++) {
                    orderArrayList.add(moreArrayList.get(i));
                }

                viewAllOrderAdapter.addList(orderArrayList);
                viewAllOrderAdapter.notifyDataSetChanged();
                mLockListView = false;
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    //처음에 데이터를 불러올 때 사용
    private ArrayList<OrderInfo> getOrderData() {

        orderArrayList = new ArrayList<OrderInfo>();

        Call<JsonData> result =  service.getAllOrderList(new OrderRequest("0"));
        result.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {

                JsonData jsonData = response.body();

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

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e(TAG,"POST FAILED TO " + AddressManager.DELIVERER_PICKUP + " : " + t.getMessage());
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