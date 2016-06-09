package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import android.app.Application;
import android.os.Bundle;

import kr.co.cleanbasket.cleanbasketdelivererandroid.R;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;

/**
 * Created by gingeraebi on 2016. 6. 7..
 */
public class DeliveryApplication extends Application {
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public void onCreate( ) {
        super.onCreate();
    }
}
