package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OrderInfo.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 8..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 * "pickup_man\":0,\"dropoff_man\":0
 */

public class OrderInfo {
    public Integer oid;
    public Integer uid;
    public String pickup_man;
    public String dropoff_man;
    public String order_number;
    public Integer state ;
    public String phone;
    public String address;
    public String addr_number;
    public String addr_building;
    public String addr_remainder;
    public String note;
    public String memo;
    public Integer price;
    public Integer dropoff_price;
    public String pickup_date;
    public String dropoff_date ;
    public String rdate;
    public Integer payment_method;
    public JsonObject pickupInfo;
    public JsonObject dropoffInfo;
    public ArrayList<Item> item;
    public ArrayList<Coupon> coupon;

    public OrderInfo(){}

    @Override
    public String toString() {
        return "OrderInfo{" +
                "oid=" + oid +
                ", uid=" + uid +
                ", pickup_man='" + pickup_man + '\'' +
                ", dropoff_man='" + dropoff_man + '\'' +
                ", order_number='" + order_number + '\'' +
                ", state=" + state +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", addr_number='" + addr_number + '\'' +
                ", addr_building='" + addr_building + '\'' +
                ", addr_remainder='" + addr_remainder + '\'' +
                ", note='" + note + '\'' +
                ", memo='" + memo + '\'' +
                ", price=" + price +
                ", dropoff_price=" + dropoff_price +
                ", pickup_date='" + pickup_date + '\'' +
                ", dropoff_date='" + dropoff_date + '\'' +
                ", rdate='" + rdate + '\'' +
                ", payment_method=" + payment_method +
                ", pickupInfo=" + pickupInfo +
                ", dropoffInfo=" + dropoffInfo +
                ", item=" + item +
                ", coupon=" + coupon +
                '}';
    }

    public Integer getOid() {
        return oid;
    }

    public Integer getUid() {
        return uid;
    }

    public String getPickup_man() {
        return pickup_man;
    }

    public String getDropoff_man() {
        return dropoff_man;
    }

    public String getOrder_number() {
        return order_number;
    }

    public Integer getState() {
        return state;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getAddr_number() {
        return addr_number;
    }

    public String getAddr_building() {
        return addr_building;
    }

    public String getAddr_remainder() {
        return addr_remainder;
    }

    public String getNote() {
        return note;
    }

    public String getMemo() {
        return memo;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getDropoff_price() {
        return dropoff_price;
    }

    public String getPickup_date() {
        long time =  System.currentTimeMillis();
        Date date= new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        String today = sf.format(date);

        if(pickup_date.startsWith(today)) {
            pickup_date = "오늘 " + pickup_date.split(" ")[1];
        }
        return pickup_date;
    }

    public String getDropoff_date() {

        long time =  System.currentTimeMillis();
        Date date= new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

        String today = sf.format(date);

        if(dropoff_date.startsWith(today)) {
            dropoff_date = "오늘 " + dropoff_date.split(" ")[1];
        }

        return dropoff_date;
    }

    public String getRdate() {
        return rdate;
    }

    public Integer getPayment_method() {
        return payment_method;
    }

    public ArrayList<Item> getItem() {
        return item;
    }

    public ArrayList<Coupon> getCoupon() {
        return coupon;
    }

    public JsonObject getPickupInfo() {
        return pickupInfo;
    }

    public JsonObject getDropoffInfo() {
        return dropoffInfo;
    }

    public String getPickupMan() {

        String name = "";

        if (this.pickupInfo == null) {
            return "수거대기";
        } else {

            try {
                JSONObject json = new JSONObject(String.valueOf(this.pickupInfo));
                name = json.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (name.isEmpty()) {
                return "수거대기";
            } else {
                return name;
            }
        }

    }

    public String getDropoffMan() {

        String name = "";

        if (this.dropoffInfo == null) {
            return "배달대기";
        } else {
            try {
                JSONObject json = new JSONObject(String.valueOf(this.dropoffInfo));
                name = json.getString("name");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (name.isEmpty()) {
                return "배달대기";
            } else {
                return name;
            }
        }

    }

    public String getPriceStatus() {
        switch (this.payment_method) {
            case 0:
                return "현장 현금 결제";
            case 1:
                return "현장 카드 결제";
            case 2:
                return "계좌이체";
            case 3:
                return "인앱결제";
        }
        return "";
    }

    public String getStatus() {
        switch (this.state) {
            case 0:
                return "수거준비중";
            case 1:
                return "수거중";
            case 2:
                return "배달준비중";
            case 3:
                return "배달중";
            case 4:
                return "배달완료";
        }
        return "";
    }

    public String getFullAddress() {
        return this.address + this.addr_number + this.addr_building + this.addr_remainder;
    }

    //Item Not Found 추가
    public String makeItem() {

        List<Item> itmes = this.item;
        if (itmes != null) {
            String strItem = itmes.get(0).name + "(" + itmes.get(0).count + ")";
            for (int i = 1; i < this.item.size(); i++) {
                strItem += ", " + this.item.get(i).name + "(" + this.item.get(i).count + ")";
            }
            return strItem;
        }
        return "Item not found";
    }
}
