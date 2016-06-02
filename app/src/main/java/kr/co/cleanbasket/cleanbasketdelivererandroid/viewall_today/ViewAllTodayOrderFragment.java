package kr.co.cleanbasket.cleanbasketdelivererandroid.viewall_today;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.dialog.OrderDetailDialog;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;

public class ViewAllTodayOrderFragment extends android.app.Fragment  implements MaterialTabListener {

    private MaterialTabHost tabHost;
    private ListView detail;
    private ArrayList<OrderInfo> orderArrayList;

    private ViewAllTodayOrderAdapter viewAllTodayOrderAdapter;

    private Gson gson;
    private Activity context;

    private static final String TAG = "DEV_todayOrderFragment";


    public ViewAllTodayOrderFragment(Activity context) {
        // Required empty public constructor
        gson = new Gson();
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my_today_order, container, false);
        detail = (ListView) v.findViewById(R.id.listView);
        initTabView(v);

        showTodayPickups();

        detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOrderDetailDialog(position);
            }
        });

        return v;
    }

    private void initTabView(View v) {
        tabHost = (MaterialTabHost) v.findViewById(R.id.materialTabHost);
        tabHost.addTab(tabHost.newTab().setText("수거").setTabListener(this));
        tabHost.addTab(tabHost.newTab().setText("배달").setTabListener(this));
    }

    //Tab 관련

    @Override
    public void onTabSelected(MaterialTab tab) {
//        pager.setCurrentItem(tab.getPosition());

        switch (tab.getPosition()){
            case 0:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                showTodayPickups();
                break;
            case 1:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                showTodayDropoffs();
                break;
        }

    }

    @Override
    public void onTabReselected(MaterialTab tab) {

        switch (tab.getPosition()){
            case 0:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                showTodayPickups();
                break;
            case 1:
                Log.i("TabSelect", String.valueOf(tab.getPosition()));
                showTodayDropoffs();
                break;
        }

    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    //DATA 관련 시작

    private ArrayList<OrderInfo> showTodayPickups(){
        orderArrayList = new ArrayList<OrderInfo>();

        HttpClientLaundryDelivery.post(AddressManager.DELIVERER_TODAY_PIKUP, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG,"POST FAILED TO " + AddressManager.DELIVERER_TODAY_PIKUP);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.i("test pickup", responseString);

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                orderArrayList = gson.fromJson(jsonData.data, new TypeToken<ArrayList<OrderInfo>>() {
                }.getType());


                setMyOrderPickUpAdapter();

            }
        });
        return orderArrayList;
    }

    private void setMyOrderPickUpAdapter() {
        viewAllTodayOrderAdapter = new ViewAllTodayOrderAdapter(context, orderArrayList);
        detail.setAdapter(viewAllTodayOrderAdapter);
    }



    private ArrayList<OrderInfo> showTodayDropoffs(){
        orderArrayList = new ArrayList<kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo>();

        HttpClientLaundryDelivery.post(AddressManager.DELIVERER_TODAY_DROPOFF, new RequestParams(), new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                Log.i("test pickup", responseString);

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);

                orderArrayList = gson.fromJson(jsonData.data, new TypeToken<ArrayList<kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo>>() {
                }.getType());

                setMyOrderPickUpAdapter();
            }
        });
        return orderArrayList;
    }

    //자세히 보기 다이얼로그 띄우기
    public void showOrderDetailDialog(int position) {
        OrderDetailDialog orderDetailDialog = new OrderDetailDialog(context, viewAllTodayOrderAdapter);
        orderDetailDialog.getDialogBuilder(orderArrayList.get(position)).show();

    }

}
