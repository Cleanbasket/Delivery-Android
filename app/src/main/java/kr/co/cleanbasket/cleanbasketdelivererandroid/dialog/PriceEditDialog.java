package kr.co.cleanbasket.cleanbasketdelivererandroid.dialog;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.activity.MainActivity;
import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressConstant;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.RetrofitBase;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 9..
 */
public class PriceEditDialog extends DialogFragment implements View.OnClickListener{

    private Order updatedOrder;
    private Order order;
    private EditText price, note;
    private Button realMoney, credit, inApp, transfer;

    private interface PriceEditService {
        @POST(AddressConstant.CONFIRM_DROPOFF)
        Call<JsonData> confirmDropOff(@Body Order order);
    }

    public static PriceEditDialog newInstance(Order order) {
        PriceEditDialog priceEditDialog = new PriceEditDialog();
        priceEditDialog.init(order);
        return priceEditDialog;
    }


    public void init(Order order) {
        this.order = order;
        this.updatedOrder = order;
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

        if(!price.getText().toString().equals("")) {
            updatedOrder.price = Integer.parseInt(price.getText().toString());
            updatedOrder.note = "[결제 노트] " + note.getText().toString() + "/ " +order.note;
        }

        switch (v.getId()) {
            case R.id.real_money :
                updatedOrder.payment_method = 0;
                break;
            case R.id.credit :
                updatedOrder.payment_method = 1;
                break;
            case R.id.transfer :
                updatedOrder.payment_method = 2;
                break;
            case R.id.in_app :
                updatedOrder.payment_method = 3;
                break;
        }

        confirmDropOff(updatedOrder);
    }


    private void confirmDropOff(Order order) {
        PriceEditService service = RetrofitBase.getInstance().getRetrofit().create(PriceEditService.class);
        Call<JsonData> call = service.confirmDropOff(order);
        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData data = response.body();
                switch (data.constant) {
                    case 1 :

                        Toast.makeText(getActivity(),"배달 완료 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case 2 :
                        Toast.makeText(getActivity(),"배달 완료 실패", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                Toast.makeText(getActivity(),"배달 완료 실패", Toast.LENGTH_SHORT).show();
                Log.e("DEV_priceEdit", t.getMessage());
            }
        });
    }

}
