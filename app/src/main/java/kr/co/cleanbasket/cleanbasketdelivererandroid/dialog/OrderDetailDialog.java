package kr.co.cleanbasket.cleanbasketdelivererandroid.dialog;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.DelivererInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.PD;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class OrderDetailDialog {
    private Activity context;
    private OrderInfo orderInfo;
    private Gson gson = new Gson();
    private PD pd;
    private ArrayList<DelivererInfo> delivererInfo;
    private ArrayAdapter<String> pdAdapter;
    private BaseAdapter adapter;

    public OrderDetailDialog(Activity context, BaseAdapter adapter) {
        this.context = context;
        this.pd = new PD();

        pd.getDelivererInfo(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.i("PD", "GET PD SUCCESS");
                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
                delivererInfo = gson.fromJson(jsonData.data, new TypeToken<ArrayList<DelivererInfo>>() {
                }.getType());
            }
        });
        this.adapter = adapter;
    }

    public AlertDialog.Builder getDialogBuilder(final OrderInfo orderInfo) {
        this.orderInfo = orderInfo;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = context.getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_order2, null);

        TextView order_number = (TextView) view.findViewById(R.id.order_number);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView pickup_date = (TextView) view.findViewById(R.id.pickup_date);
        TextView dropoff_date = (TextView) view.findViewById(R.id.dropoff_date);
        TextView address = (TextView) view.findViewById(R.id.address);
        TextView item = (TextView) view.findViewById(R.id.items);
        TextView memo = (TextView) view.findViewById(R.id.memo);
        final TextView status = (TextView) view.findViewById(R.id.status);
        TextView phone = (TextView) view.findViewById(R.id.phone);
        TextView note = (TextView) view.findViewById(R.id.note);
        final TextView pickup_man = (TextView) view.findViewById(R.id.pickup_man);
        final TextView dropoff_man = (TextView) view.findViewById(R.id.dropoff_man);

        ImageView pickup_assign = (ImageView) view.findViewById(R.id.bt_pickup);
        ImageView dropoff_assign = (ImageView) view.findViewById(R.id.bt_dropoff);


        setOrderDetailInfo(order_number, price, pickup_date, dropoff_date, address, item, memo, status, phone, note, pickup_man, dropoff_man);
        pickup_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAssignPickupDialog(pickup_man);
            }
        });

        dropoff_assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAssignDropoffDialog(dropoff_man);
            }
        });

        builder.setView(view)
                // Add action buttons
                .setPositiveButton("배정완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton("전화연결", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderInfo.getPhone()));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        context.startActivity(intent);
                    }
                });
        builder.create();
        return builder;
    }

    //주문내역 가져다 꼽기
    private void setOrderDetailInfo(TextView order_number, TextView price, TextView pickup_date, TextView dropoff_date, TextView address, TextView item, TextView memo, TextView status, TextView phone, TextView note, TextView pickup_man, TextView dropoff_man) {

        DecimalFormat df = new DecimalFormat("#,##0");
        //주문 번호 세팅
        order_number.setText(orderInfo.getOrder_number());

        //가격 정보 세팅
        String price_str = "";
        if (orderInfo.getCoupon().isEmpty()) {
            price_str = String.valueOf(df.format(orderInfo.getPrice()));
        } else {
            price_str = String.valueOf(df.format(orderInfo.getPrice())) + " (" + orderInfo.getCoupon().get(0).name + " )";
        }
        price.setText(price_str);


        pickup_date.setText(orderInfo.getPickup_date());

        dropoff_date.setText(orderInfo.getDropoff_date());

        address.setText(orderInfo.getFullAddress());
        item.setText(orderInfo.makeItem());
        memo.setText(orderInfo.getMemo());
        note.setText(orderInfo.getNote());
        status.setText(orderInfo.getStatus());
        phone.setText(orderInfo.getPhone());
        pickup_man.setText(orderInfo.getPickupMan());
        dropoff_man.setText(orderInfo.getDropoffMan());
    }

    //수거 배정 다이얼로그 띄우기
    private void showAssignDropoffDialog(final TextView dropoff_man) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ArrayList<String> pdList = pd.getPDList(delivererInfo);
        builder.setTitle("배달 배정 하기");
        pdAdapter = new ArrayAdapter<String>(context, android.R.layout.select_dialog_singlechoice, pdList);

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

    //배달 배정 다이얼로그 띄우기
    private void showAssignPickupDialog(final TextView pickup_man) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ArrayList<String> pdList = pd.getPDList(delivererInfo);

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

    //network 통신 관련 함수 2개
    private void assignOrderPickup(int uid) {
        int oid = orderInfo.oid;

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("oid", orderInfo.oid.toString());
            jsonParams.put("uid", String.valueOf(uid));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity params = null;
        try {
            params = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("Json test", params.toString());

        Log.i("test", oid + "  " + uid);

        HttpClientLaundryDelivery.post(context, AddressManager.ASSIGN_PICKUP, params, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("test fail", String.valueOf(responseString));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
                adapter.notifyDataSetChanged();

                Log.i("test success", String.valueOf(jsonData));
            }
        });

    }

    private void assignOrderDropoff(int uid) {
        int oid = orderInfo.oid;

        JSONObject jsonParams = new JSONObject();
        try {
            jsonParams.put("oid", orderInfo.oid.toString());
            jsonParams.put("uid", String.valueOf(uid));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        StringEntity params = null;
        try {
            params = new StringEntity(jsonParams.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.i("Json test", params.toString());

        Log.i("test", oid + "  " + uid);

        HttpClientLaundryDelivery.post(context, AddressManager.ASSIGN_DROPOFF, params, "application/json", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.i("test fail", String.valueOf(responseString));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
                adapter.notifyDataSetChanged();
                Log.i("test success", String.valueOf(jsonData));
            }
        });

    }

}
