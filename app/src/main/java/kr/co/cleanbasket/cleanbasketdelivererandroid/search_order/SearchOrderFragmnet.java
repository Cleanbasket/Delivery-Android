package kr.co.cleanbasket.cleanbasketdelivererandroid.search_order;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.RetrofitOrder;
import kr.co.cleanbasket.cleanbasketdelivererandroid.oder_detail.OrderDetailActivity;
import kr.co.cleanbasket.cleanbasketdelivererandroid.viewall.ViewAllOrderAdapter;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Phone;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * ViewAllOrderFragment.java
 * CleanBasket Deliverer Android
 * <p>
 * Created by Yongbin Cha on 16. 4. 6..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class SearchOrderFragmnet extends Fragment {
    private Activity context;
    private ListView history;
    private EditText orderNumberEdit;
    private Button find;

    private RetrofitOrder retrofitOrder;
    private Gson gson = new Gson();

    private ViewAllOrderAdapter viewAllOrderAdapter;

    private ArrayList<OrderInfo> orderInfos;

    public SearchOrderFragmnet(Activity context) {
        this.context = context;
        retrofitOrder = new RetrofitOrder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_search_order, container, false);
        history = (ListView) v.findViewById(R.id.history);
        orderNumberEdit = (EditText) v.findViewById(R.id.orderNumInput);
        find = (Button) v.findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                find();
            }
        });

        history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("oid", orderInfos.get(position).getOid());
                startActivity(intent);
            }
        });

        return v;
    }

    private void find() {
        int orderNum = getOrderNumFromEdit();
        orderInfos = new ArrayList<>();

        if (orderNum == -1) {
            return;
        }
        if (orderNum > 999999999) {
            getOrderByPhone(orderNum);
        }else {
            getOrderByOid(orderNum);
        }
    }

    private void getOrderByOid(int orderNum) {
        retrofitOrder.getOrderByOid(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                switch (jsonData.constant) {
                    case 1:
                        Order order = gson.fromJson(jsonData.data, Order.class);
                        orderInfos.add(order);

                        viewAllOrderAdapter = new ViewAllOrderAdapter(context, orderInfos);
                        history.setAdapter(viewAllOrderAdapter);
                        break;
                    case 2:
                        Toast.makeText(context, "주문 번호를 확인하세요", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        }, orderNum);

    }

    private void getOrderByPhone(int orderNum) {
        retrofitOrder.getOrderByPhoneNum(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                switch (jsonData.constant) {
                    case 1:
                        orderInfos = gson.fromJson(jsonData.data, new TypeToken<List<Order>>(){}.getType());
                        viewAllOrderAdapter = new ViewAllOrderAdapter(context, orderInfos);
                        history.setAdapter(viewAllOrderAdapter);
                        break;
                    case 2:
                        Toast.makeText(context, "전화 번호를 확인하세요", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        }, new Phone(orderNum));
    }



    private int getOrderNumFromEdit() {
        try {
            return Integer.parseInt(orderNumberEdit.getText().toString());
        } catch (Exception e) {
            Toast.makeText(context, "숫자만 입력하세요", Toast.LENGTH_SHORT).show();
            return -1;
        }
    }

}