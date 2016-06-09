package kr.co.cleanbasket.cleanbasketdelivererandroid.oder_detail;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.activity.DeliveryApplication;
import kr.co.cleanbasket.cleanbasketdelivererandroid.dialog.ItemListDialog;
import kr.co.cleanbasket.cleanbasketdelivererandroid.myorder.MyOrderService;
import kr.co.cleanbasket.cleanbasketdelivererandroid.network.Network;
import kr.co.cleanbasket.cleanbasketdelivererandroid.viewall.AssignProxy;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.DelivererInfo;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderRequest;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.RetrofitPD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderEditActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DEV_orderEditActivity";

    private Context context;

    private Order order;
    private Order updatedOrder;

    private TextView order_number;
    private EditText price;
    private TextView pickup_time;
    private TextView dropoff_time;
    private EditText address;
    private TextView item;
    private TextView note;
    private EditText memo;
    private TextView status;
    private TextView pickup_man;
    private TextView dropoff_man;

    private ImageView editDropOffMan, editPickUpMan, editDropOff, editPickUp;
    private Button complete, update;

    private ArrayAdapter<String> pdAdapter;
    private ArrayList<DelivererInfo> delivererInfo;

    int year, month, day, hour, minute;
    String date;

    private int mode = 0;
    private static int PICK_UP = 1;
    private static int DROP_OFF = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_edit);
        DeliveryApplication app = (DeliveryApplication) getApplicationContext();
        order = app.getOrder();
        context =  this;
        updatedOrder = order;
        initView();
        setCalendar();
        setPDAdapter();
    }


    private void initView() {
        order_number = (TextView) findViewById(R.id.order_number);
        price = (EditText) findViewById(R.id.price);
        pickup_time = (TextView) findViewById(R.id.pickup_time);
        dropoff_time = (TextView) findViewById(R.id.dropoff_time);
        address = (EditText) findViewById(R.id.address);
        item = (TextView) findViewById(R.id.item);
        note = (TextView) findViewById(R.id.note);
        memo = (EditText) findViewById(R.id.memo);
        status = (TextView) findViewById(R.id.status);
        pickup_man = (TextView) findViewById(R.id.pivkup_man);
        dropoff_man = (TextView) findViewById(R.id.dropoff_man);

        order_number.setText(order.getOrder_number());
        price.setText(order.getPrice() + "");
        pickup_time.setText(order.getPickup_date());
        dropoff_time.setText(order.getDropoff_date());
        address.setText(order.getFullAddress());
        item.setText(order.makeItem());
        note.setText(order.getMemo());
        memo.setText(order.getNote());
        status.setText(order.getStatus());
        pickup_man.setText(order.getPickupMan());
        dropoff_man.setText(order.getDropoffMan());

        editDropOff = (ImageView) findViewById(R.id.editDropOff);
        editPickUp = (ImageView) findViewById(R.id.editPickUp);
        editDropOffMan = (ImageView) findViewById(R.id.editDropOffMan);
        editPickUpMan = (ImageView) findViewById(R.id.editPickupMan);

        editDropOff.setOnClickListener(this);
        editDropOffMan.setOnClickListener(this);
        editPickUp.setOnClickListener(this);
        editPickUpMan.setOnClickListener(this);

        update = (Button) findViewById(R.id.update);
        complete = (Button) findViewById(R.id.complete);

        update.setOnClickListener(this);
        complete.setOnClickListener(this);
    }

    private void setCalendar() {
        GregorianCalendar calendar = new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.editPickUp:
                new DatePickerDialog(this, dateSetListener, year, month, day).show();
                mode = OrderEditActivity.PICK_UP;
                break;
            case R.id.editDropOff :
                new DatePickerDialog(this, dateSetListener, year, month, day).show();
                mode = OrderEditActivity.DROP_OFF;
                break;
            case R.id.editDropOffMan :
                showAssignDropoffDialog();
                break;
            case R.id.editPickupMan :
                showAssignPickupDialog();;
                break;
            case R.id.complete :
                Order sendOrder = makeOrder();
                finish();
                break;
            case R.id.update :
                doUpdate();
                break;
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            Date result = new Date(year - 1900, monthOfYear, dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = simpleDateFormat.format(result);
            Toast.makeText(OrderEditActivity.this, date, Toast.LENGTH_SHORT).show();
            new TimePickerDialog(OrderEditActivity.this, timeSetListener, hour, minute, false).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            String hour = String.format("%02d", hourOfDay);
            String min = String.format("%02d", minute);
            date += " " + hour + ":" + min +":00.0";
            Toast.makeText(OrderEditActivity.this, date, Toast.LENGTH_SHORT).show();
            if(mode == PICK_UP) {
                updatedOrder.pickup_date = date;
                pickup_time.setText(date);
            }else {
                updatedOrder.dropoff_date = date;
                dropoff_time.setText(date);
            }
        }
    };

    //수거 배정 Dialog 띄우기
    private void showAssignPickupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("수거 배정 하기");
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

// ------------------------------------- case 2 -------------------------------------------------
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

    // ------------------------------------ case 3 ----------------------------------------------------
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
                Toast.makeText(context , "배달 배정 성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {

            }
        });


    }


    // ----------------------------------- case 4 ------------------------------------------------------
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


    private void setPDAdapter() {
        ArrayList<String> pdList = RetrofitPD.getInstance().getPDList();
        pdAdapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice, pdList);
        delivererInfo = RetrofitPD.getInstance().getDelivererInfo();
    }

    public Order makeOrder() {
        updatedOrder.memo = memo.getText().toString();
        updatedOrder.address = address.getText().toString();
        updatedOrder.price = Integer.parseInt(price.getText().toString());
        return updatedOrder;
    }

    public void doUpdate() {
        ItemListDialog dialog = ItemListDialog.newInstance(new ItemListDialog.OnTransferListener() {
            @Override
            public void onTransfer(ItemListDialog dialog) {

            }
        }, order.getOrder_number());
        dialog.show(getFragmentManager(),"품목 수정");
    }

}
