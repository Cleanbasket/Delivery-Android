package kr.co.cleanbasket.cleanbasketdelivererandroid.check_item;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.activity.MainActivity;
import kr.co.cleanbasket.cleanbasketdelivererandroid.edit_item.ItemEditEvent;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.BusProvider;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;

/**
 * Created by gingeraebi on 2016. 6. 13..
 */
public class ItemCheckDialog extends DialogFragment implements View.OnClickListener {

    private Order order;
    private TextView complete, deliverPrice, totalPrice;
    private ListView listView;
    private OnTransferListener onTransferListener;

    public interface OnTransferListener {
        void onTransfer();
    }

    public static ItemCheckDialog newInstance(OnTransferListener onTransferListener ,Order order) {
        ItemCheckDialog itemCheckDialog = new ItemCheckDialog();
        itemCheckDialog.init(onTransferListener, order);
        return itemCheckDialog;
    }


    public void init(OnTransferListener onTransferListener ,Order order) {
        this.onTransferListener = onTransferListener;
        this.order = order;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_item_check, container, false);
        complete = (TextView) rootView.findViewById(R.id.complete);
        deliverPrice = (TextView) rootView.findViewById(R.id.deliver_price);
        totalPrice = (TextView) rootView.findViewById(R.id.total_price);
        listView = (ListView) rootView.findViewById(R.id.listView);

        ItemCheckAdapter itemCheckAdapter = new ItemCheckAdapter(getActivity(), R.layout.row_item_check, order.item);
        listView.setAdapter(itemCheckAdapter);

        DecimalFormat df = new DecimalFormat("#,##0");
        String str_totalPrice  = String.valueOf(df.format(order.getPrice()));
        totalPrice.setText(str_totalPrice+"원");

        String str_deliverPrice  = String.valueOf(df.format(order.getDropoff_price()));
        deliverPrice.setText(str_deliverPrice + "원");

        complete.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.complete:
                doComplete();
                break;

        }
    }

    private void doComplete() {
        onTransferListener.onTransfer();
        BusProvider.getInstance().post(new ItemEditEvent(order.item));
        Handler delayHandler = new Handler();
        delayHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dismiss();
            }
        }, 100);
    }
}
