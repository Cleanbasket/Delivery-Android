package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import android.app.Application;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.ItemCode;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;

/**
 * Created by gingeraebi on 2016. 6. 7..
 */
public class DeliveryApplication extends Application {
    private Order order;
    private ArrayList<ItemCode> itemCodes;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
