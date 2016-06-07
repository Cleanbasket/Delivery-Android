package kr.co.cleanbasket.cleanbasketdelivererandroid.myorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.service.HttpClientLaundryDelivery;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
    private Gson gson;
    private MyOrderDropOffAdapter myOrderDropOffAdapter;

    private Network network;
    private Retrofit retrofit;
    private MyOrderService service;

    public MyOrderDropOffAdapter(Activity context,ArrayList<OrderInfo> orderArrayList){
        myOrderDropOffAdapter = this;
        this.context = context;
        this.orderArrayList = orderArrayList;
        gson = new Gson();

        network = new Network(context);
        retrofit = network.getRetrofit();
        service = retrofit.create(MyOrderService.class);
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

        DecimalFormat df = new DecimalFormat("#,##0");

        order_number.setText(orderArrayList.get(position).order_number);

        String price_str  = String.valueOf(df.format(orderArrayList.get(position).price));
        Log.i("MyDropOff", "PRICE : " + price_str);
        price.setText(price_str);

        dropoff_date.setText(orderArrayList.get(position).dropoff_date);
        address.setText(orderArrayList.get(position).address + orderArrayList.get(position).addr_number +
                orderArrayList.get(position).addr_building + orderArrayList.get(position).addr_remainder);
        item.setText(makeItem(position));
        memo.setText(orderArrayList.get(position).memo);

        if(orderArrayList.get(position).state == 4){
            row.setBackgroundColor(Color.GRAY);
        }

        return row;
    }

    public void showOrderDetailDialog(final int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        // Get the layout inflater
        LayoutInflater inflater = context.getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_order1, null);

        TextView order_number = (TextView) view.findViewById(R.id.order_number);
        TextView price = (TextView) view.findViewById(R.id.price);
        TextView pickup_date = (TextView) view.findViewById(R.id.pickup_date);
        TextView dropoff_date = (TextView) view.findViewById(R.id.dropoff_date);
        TextView address = (TextView) view.findViewById(R.id.address);
        TextView item = (TextView) view.findViewById(R.id.itemCodes);
        TextView memo = (TextView) view.findViewById(R.id.memo);
        TextView phone = (TextView) view.findViewById(R.id.phone);

        final EditText note = (EditText) view.findViewById(R.id.note);

        order_number.setText(orderArrayList.get(position).order_number);

        DecimalFormat df = new DecimalFormat("#,##0");

        order_number.setText(orderArrayList.get(position).order_number);

        String price_str = "";

        if (orderArrayList.get(position).coupon.isEmpty()){
            price_str = String.valueOf(df.format(orderArrayList.get(position).price) + " (" + getPriceStatus(position) + ")");
        }else {
            price_str = String.valueOf(df.format(orderArrayList.get(position).price)) + " (" + getPriceStatus(position) + "/" + orderArrayList.get(position).coupon.get(position).name + " )";
        }
        price.setText(price_str);

        pickup_date.setText(orderArrayList.get(position).pickup_date);
        dropoff_date.setText(orderArrayList.get(position).dropoff_date);
        address.setText(orderArrayList.get(position).address + orderArrayList.get(position).addr_number +
                orderArrayList.get(position).addr_building + orderArrayList.get(position).addr_remainder);
        item.setText(makeItem(position));
        memo.setText(orderArrayList.get(position).memo);
        phone.setText(orderArrayList.get(position).phone);

        if(!orderArrayList.get(position).note.equals("")){
            note.setText(orderArrayList.get(position).note);
        }

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
                // Add action buttons
                .setPositiveButton("배달완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String value = note.getText().toString();
                        Call<JsonData> response = service.sendDropOffComplete(new OrderRequest("" +orderArrayList.get(position).oid, value));
                        response.enqueue(new Callback<JsonData>() {
                            @Override
                            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                                JsonData jsonData = response.body();
                                myOrderDropOffAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<JsonData> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("번호 복사", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ClipboardManager clipboardManage = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clipData = ClipData.newPlainText(orderArrayList.get(position).phone,orderArrayList.get(position).phone);
                        clipboardManage.setPrimaryClip(clipData);
                        Toast.makeText(context,"번호 복사가 완료되었습니다.",Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderArrayList.get(position).phone));
//                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                            return;
//                        }
//                        context.startActivity(intent);
                    }
                });
        builder.create();
        builder.show();

    }

    private String getPriceStatus(int position){
        switch (orderArrayList.get(position).payment_method){
            case 0:
                return "현장 현금 결제";
            case 1:
                return "현장 카드 결제";
            case 2:
                return "계좌이체";
            case 3:
                return "인앱결제";
        }
        return "";
    }

    private String makeItem(int position){
        String strItem = orderArrayList.get(position).item.get(0).name + "(" + orderArrayList.get(position).item.get(0).count + ")";
        for(int i=1; i<orderArrayList.get(position).item.size(); i++) {
            strItem += ", " + orderArrayList.get(position).item.get(i).name + "(" + orderArrayList.get(position).item.get(i).count + ")";
        }
        return strItem;
    }
}
