package kr.co.cleanbasket.cleanbasketdelivererandroid.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.ItemNetwork;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.ItemCode;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.ItemInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;

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

    private ItemNetwork itemNetwork;

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

        itemNetwork = new ItemNetwork();
        itemCodes = new ArrayList<>();
        items = new ArrayList<>();
        itemNetwork.getOrderItem(new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Log.e(TAG,"GET Item List Fail");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.v(TAG, responseString);

                JsonData jsonData = gson.fromJson(responseString, JsonData.class);
                ItemInfo itemInfo = gson.fromJson(jsonData.data, ItemInfo.class);
                setItemCodes(itemInfo);
                getOrderByOid(oid);
            }
        });
        //Next버튼
        rootView.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
        itemNetwork.getOrderByOid(
                new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.e(TAG,"GET Order By Order Id Fail");
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        JsonData jsonData = gson.fromJson(responseString, JsonData.class);
                        Order order = gson.fromJson(jsonData.data, Order.class);
                        items = order.item;
                        itemListAdapter = new ItemListAdapter(getActivity(), itemCodes, items);
                        itemList.setAdapter(itemListAdapter);
                    }
                }
                , oid);
    }
}