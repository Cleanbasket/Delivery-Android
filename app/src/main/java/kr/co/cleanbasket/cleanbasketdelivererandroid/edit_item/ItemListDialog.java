package kr.co.cleanbasket.cleanbasketdelivererandroid.edit_item;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.check_item.ItemCheckDialog;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.RetrofitOrder;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.BusProvider;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.ItemCode;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.ItemInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 6. 1..
 */
public class ItemListDialog extends DialogFragment {

    private static final String TAG = "DEV_DialogFragment";

    private ArrayList<ItemCode> itemCodes;
    private ArrayList<Item> items;
    private Order order;
    private Gson gson = new Gson();

    private ListView itemList;
    private TextView tv_orderNumber;

    private String orderNumber;
    private ItemListAdapter itemListAdapter;
    private OnTransferListener onTransferListener;

    private RetrofitOrder retrofitOrder;

    private int oid;


    public interface OnTransferListener {
        void onTransfer(ItemListDialog dialog);
    }


    public static ItemListDialog newInstance(OnTransferListener onTransferListener, String orderNumber) {
        ItemListDialog itemListDialog = new ItemListDialog();
        itemListDialog.init(onTransferListener, orderNumber);
        return itemListDialog;
    }


    public void init(OnTransferListener onTransferListener, String orderNumber) {
        this.onTransferListener = onTransferListener;
        this.orderNumber = orderNumber;
        this.oid = Integer.parseInt(orderNumber.split("-")[1]);
        BusProvider.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_item_list, container, false);
        tv_orderNumber = (TextView) rootView.findViewById(R.id.orderNumber);
        itemList = (ListView) rootView.findViewById(R.id.listView);

        tv_orderNumber.setText(orderNumber);

        retrofitOrder = new RetrofitOrder();
        itemCodes = new ArrayList<>();
        items = new ArrayList<>();

        retrofitOrder.getOrderItem(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                ItemInfo itemInfo = gson.fromJson(jsonData.data, ItemInfo.class);
                setItemCodes(itemInfo);
                getOrderByOid(oid);
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e(TAG, "GET Item List Fail");
            }
        });

        //Next버튼
        rootView.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitOrder.updateItem(new Callback<JsonData>() {
                    @Override
                    public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                        JsonData jsonData = response.body();
                        Order updatedOrder = gson.fromJson(jsonData.data , Order.class);
                        ItemCheckDialog itemCheckDialog = ItemCheckDialog.newInstance(updatedOrder);
                        itemCheckDialog.show(getFragmentManager(), "ItemCheckDialog");
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<JsonData> call, Throwable t) {

                    }
                }, itemListAdapter.getItems());
            }
        });

        //Cancel버튼
        rootView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return rootView;
    }

    private void setItemCodes(ItemInfo itemInfo) {
        for (ItemCode itemCode : itemInfo.orderItems) {
            itemCodes.add(itemCode);
        }
    }

    private void getOrderByOid(int oid) {
        retrofitOrder.getOrderByOid(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData jsonData = response.body();
                Order order = gson.fromJson(jsonData.data, Order.class);
                items = order.item;
                itemListAdapter = new ItemListAdapter(getActivity(), itemCodes, items);
                itemList.setAdapter(itemListAdapter);
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Log.e(TAG, "GET Order By Order Id Fail");
            }
        }, oid);
    }
}
