package kr.co.cleanbasket.cleanbasketdelivererandroid.oder_detail;

import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.Order;

/**
 * Created by gingeraebi on 2016. 6. 9..
 */
public class OrderPushEvent {

    private Order order;

    public OrderPushEvent(Order order) {
        this.order = order;
    }

    public Order getOrder(){
        return order;
    }
}
