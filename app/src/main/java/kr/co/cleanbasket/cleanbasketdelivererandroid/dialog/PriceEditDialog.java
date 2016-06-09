package kr.co.cleanbasket.cleanbasketdelivererandroid.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.myorder.MyOrderService;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.RetrofitOrder;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.BusProvider;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Item;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.ItemInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gingeraebi on 2016. 6. 9..
 */
public class PriceEditDialog extends DialogFragment implements View.OnClickListener{

    private Order order;
    private EditText price, note;
    private Button realMoney, credit, inApp, transfer;

    public static PriceEditDialog newInstance(Order order) {
        PriceEditDialog priceEditDialog = new PriceEditDialog();
        priceEditDialog.init(order);
        return priceEditDialog;
    }


    public void init(Order order) {
        this.order = order;
        BusProvider.getInstance().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getShowsDialog()) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        View rootView = inflater.inflate(R.layout.dialog_price_edit, container, false);
        price = (EditText) rootView.findViewById(R.id.price);
        note = (EditText) rootView.findViewById(R.id.note);

        realMoney = (Button) rootView.findViewById(R.id.real_money);
        credit = (Button) rootView.findViewById(R.id.credit);
        inApp = (Button) rootView.findViewById(R.id.in_app);
        transfer = (Button) rootView.findViewById(R.id.transfer);

        realMoney.setOnClickListener(this);
        credit.setOnClickListener(this);
        inApp.setOnClickListener(this);
        transfer.setOnClickListener(this);

        price.setHint(order.getPrice() + "원" + "(" + order.getPriceStatus() + ")");

        return rootView;
    }

    @Override
    public void onClick(View v) {
        //만약 가격정보가 변경되었는데 메모가 바뀌지 않으면 리턴하여 버튼이 눌리지 않게 함
        if(!price.getText().toString().equals("") && note.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "가격정보가 변경되면 메모를 하셔야 합니다.",Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()) {
            case R.id.real_money :

        }
    }

    // 배달 완료시 서버에 전송
    private void snedDropOffComplete() {
        String value = note.getText().toString();
        MyOrderService service = Network.getInstance().getRetrofit().create(MyOrderService.class);
        Call<JsonData> response = service.sendDropOffComplete(new OrderRequest("" + order.oid, value));
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



}
