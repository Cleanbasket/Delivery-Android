package kr.co.cleanbasket.cleanbasketdelivererandroid.oder_detail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.activity.DeliveryApplication;
import kr.co.cleanbasket.cleanbasketdelivererandroid.dialog.ItemEditEvent;
import kr.co.cleanbasket.cleanbasketdelivererandroid.dialog.ItemListDialog;
import kr.co.cleanbasket.cleanbasketdelivererandroid.dialog.PriceEditDialog;
import kr.co.cleanbasket.cleanbasketdelivererandroid.myorder.MyOrderService;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.RetrofitOrder;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.BusProvider;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.SharedPreferenceBase;
import kr.co.cleanbasket.cleanbasketdelivererandroid.viewall.AssignProxy;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.DelivererInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.RetrofitPD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * OrderDetailActivity.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 8..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 */
public class OrderDetailActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "DEV_orderDetailActivity";

    private boolean isManager;

    private ArrayList<DelivererInfo> delivererInfo;
    private Gson gson = new Gson();
    private Order order;

    private TextView order_number;
    private TextView price;
    private TextView pickup_time;
    private TextView dropoff_time;
    private TextView address;
    private TextView item;
    private TextView note;
    private EditText memo;
    private TextView status;
    private TextView pickup_man;
    private TextView dropoff_man;

    private TextView edit;
    private Button complete;
    private Button update;
    private Button copy;

    private int oid;
    private RetrofitOrder retrofitOrder;

    private ArrayAdapter<String> pdAdapter;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        BusProvider.getInstance().register(this);
        context = this;
        retrofitOrder = new RetrofitOrder();
        delivererInfo = RetrofitPD.getInstance().getDelivererInfo();
        isManager = SharedPreferenceBase.getSharedPreference("IsManager", false);
        initView();
        //Data를 불러오는 동시에 화면을 그림
        getOrderData();
        setPDAdapter();
    }

    private int getOid() {
        Intent intent = getIntent();
        oid = intent.getIntExtra("oid", 0);
        Log.i(TAG, "oid : " + oid);
        return oid;
    }

    private void initView() {
        order_number = (TextView) findViewById(R.id.order_number);
        price = (TextView) findViewById(R.id.price);
        pickup_time = (TextView) findViewById(R.id.pickup_time);
        dropoff_time = (TextView) findViewById(R.id.dropoff_time);
        address = (TextView) findViewById(R.id.address);
        item = (TextView) findViewById(R.id.item);
        note = (TextView) findViewById(R.id.note);
        memo = (EditText) findViewById(R.id.memo);
        status = (TextView) findViewById(R.id.status);
        pickup_man = (TextView) findViewById(R.id.pivkup_man);
        dropoff_man = (TextView) findViewById(R.id.dropoff_man);

        complete = (Button) findViewById(R.id.complete);
        update = (Button) findViewById(R.id.update);
        copy = (Button) findViewById(R.id.copy);
        edit = (TextView) findViewById(R.id.edit);

        complete.setOnClickListener(this);
        update.setOnClickListener(this);
        copy.setOnClickListener(this);
        edit.setOnClickListener(this);

        if(!isManager) {
            edit.setClickable(false);
            edit.setVisibility(View.INVISIBLE);
        }

    }

    private void getOrderData() {
        getOid();
        retrofitOrder.getOrderByOid(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                order = gson.fromJson(jsonData.data, Order.class);
                drawDetail();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        }, oid);
    }

    private void drawDetail() {
        order_number.setText(order.getOrder_number());
        price.setText("" + order.getPrice());
        pickup_time.setText(order.getPickup_date());
        dropoff_time.setText(order.getDropoff_date());
        address.setText(order.getFullAddress());
        item.setText(order.makeItem());
        note.setText(order.getMemo());
        memo.setText(order.getNote());
        status.setText(order.getStatus());
        pickup_man.setText(order.getPickupMan());
        dropoff_man.setText(order.getDropoffMan());

        switch (order.getState()) {
            case 0:
                if (!isManager) {
                    complete.setClickable(false);
                } else {
                    complete.setClickable(true);
                }
                complete.setText("수거 배정");
                break;
            case 1:
                complete.setClickable(true);
                complete.setText("수거 완료");
                break;
            case 2:
                if (!isManager) {
                    complete.setClickable(false);
                } else {
                    complete.setClickable(true);
                }
                complete.setText("배달 배정");
                break;
            case 3:
                complete.setClickable(true);
                complete.setText("배달 완료");
                break;
            case 4:
                complete.setClickable(true);
                complete.setText("추가 수거");
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete:
                doComplete();
                break;
            case R.id.update:
                doUpdate();
                break;
            case R.id.copy:
                doCopy();
                break;
            case R.id.edit:
                DeliveryApplication app = (DeliveryApplication) getApplicationContext();
                app.setOrder(order);
                Intent intent = new Intent(this, OrderEditActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void doComplete() {
        switch (order.getState()) {
            case 0:
                showAssignPickupDialog();
                break;
            case 1:
                sendPickUpComplete();
                finish();
                break;
            case 2:
                showAssignDropoffDialog();
                break;
            case 3:
                PriceEditDialog priceEditDialog = PriceEditDialog.newInstance(order);
                priceEditDialog.show(getFragmentManager(),"결제 정보");
                break;
            case 4:
                Toast.makeText(context, "준비중인 기능입니다", Toast.LENGTH_SHORT).show();
                break;
        }
    }

//----------------------------------------------------------------------------------------------
// ------------------------------------- case 0 -------------------------------------------------
//수거 배정 Dialog 띄우기
    private void showAssignPickupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ArrayList<String> pdList = RetrofitPD.getInstance().getPDList();

        builder.setTitle("수거 배정 하기");
        pdAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice, pdList);
        builder.setAdapter(pdAdapter,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        String strName = pdAdapter.getItem(id);

                        for (int i = 0; i < delivererInfo.size(); i++) {
                            if (delivererInfo.get(i).name.equals(strName)) {
                                pickup_man.setText(strName);
                                assignOrderPickup(delivererInfo.get(i).uid);
                            }
                        }
                    }
                });
        builder.show();
    }

    private void assignOrderPickup(int uid) {
        int oid = order.oid;
        OrderRequest orderRequest = new OrderRequest("" + oid, uid);
        AssignProxy proxy = new AssignProxy();
        proxy.assignPickUp(orderRequest, new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                Toast.makeText(context, "수거 배정 성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });

    }

// ------------------------------------- case 1 -------------------------------------------------
// 수거 완료 서버에 전송

    private void sendPickUpComplete() {
        String value = memo.getText().toString();
        MyOrderService service = Network.getInstance().getRetrofit().create(MyOrderService.class);
        Call<JsonData> response = service.sendPickupComplete(new OrderRequest("" + order.getOid(), value));
        response.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });
    }

    // ------------------------------------ case 2 ----------------------------------------------------
    //배달 배정 다이얼로그 띄우기
    private void showAssignDropoffDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("배달 배정 하기");
        builder.setAdapter(pdAdapter,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int id) {
                        String strName = pdAdapter.getItem(id);

                        for (int i = 0; i < delivererInfo.size(); i++) {
                            if (delivererInfo.get(i).name.equals(strName)) {
                                dropoff_man.setText(strName);
                                assignOrderDropoff(delivererInfo.get(i).uid);
                            }
                        }
                    }
                });
        builder.show();
    }

    private void assignOrderDropoff(int uid) {
        int oid = order.oid;
        OrderRequest orderRequest = new OrderRequest("" + oid, uid);
        AssignProxy proxy = new AssignProxy();
        proxy.assignDropOff(orderRequest, new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                Toast.makeText(context, "배달 배정 성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });


    }

// --------------------------------------------case done ------------------------------------------------------
// ------------------------------------------------------------------------------------------------------


    private void setPDAdapter() {
        ArrayList<String> pdList = RetrofitPD.getInstance().getPDList();
        pdAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, pdList);
    }


    private void doUpdate() {
        ItemListDialog dialog = ItemListDialog.newInstance(new ItemListDialog.OnTransferListener() {
            @Override
            public void onTransfer(ItemListDialog dialog) {

            }
        }, order.getOrder_number());
        dialog.show(getFragmentManager(),"품목 수정");
    }


    private void doCopy() {

    }
}
