package kr.co.cleanbasket.cleanbasketdelivererandroid.unuse.viewall_today;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.OrderInfo;

/**
 * Created by gingeraebi on 2016. 5. 30..
 */
public class ViewAllTodayOrderAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<OrderInfo> orderInfos;
    private Gson gson;

    public ViewAllTodayOrderAdapter(Activity context, ArrayList<OrderInfo> orderInfos) {
        this.context = context;
        this.orderInfos = orderInfos;
        gson = new Gson();
    }

    @Override
    public int getCount() {
        return orderInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(R.layout.item_view_all, null, true);

        TextView tv_order_number = (TextView) convertView.findViewById(R.id.order_number);
        TextView tv_pickup_time = (TextView) convertView.findViewById(R.id.pickup_time);
        TextView tv_dropoff_time = (TextView) convertView.findViewById(R.id.dropoff_time);
        TextView tv_account = (TextView) convertView.findViewById(R.id.account);

        TextView tv_deliverer_man = (TextView) convertView.findViewById(R.id.deliverer_man);


        OrderInfo orderInfo = orderInfos.get(position);

        tv_order_number.setText(orderInfo.getOrder_number());
        tv_pickup_time.setText(orderInfo.getPickup_date());
        tv_dropoff_time.setText(orderInfo.getDropoff_date());
        tv_account.setText(orderInfo.getFullAddress());
        switch (orderInfo.getState()){
            case 0:
                tv_deliverer_man.setText(orderInfo.getPickupMan());
                break;
            case 1:
                tv_deliverer_man.setText(orderInfo.getPickupMan());
                convertView.setBackgroundColor(Color.GRAY);
                break;
            case 2:
                tv_deliverer_man.setText(orderInfo.getDropoffMan());

                break;
            case 3:
                tv_deliverer_man.setText(orderInfo.getDropoffMan());
                convertView.setBackgroundColor(Color.GRAY);
                break;
            case 4:
                tv_deliverer_man.setText("배달완료");
                convertView.setBackgroundColor(Color.GRAY);
                break;
        }


        return convertView;
    }


}
