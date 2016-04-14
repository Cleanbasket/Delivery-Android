package kr.co.cleanbasket.cleanbasketdelivererandroid.json;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * OrderInfo.java
 * CleanBasket Deliverer Android
 * <p/>
 * Created by Yongbin Cha on 16. 4. 8..
 * Copyright (c) 2016 WashAppKorea. All rights reserved.
 * "pickup_man\":0,\"dropoff_man\":0
 */

public class OrderInfo {
    public Integer oid = 0;
    public Integer uid = 0;
    public String pickup_man = "";
    public String dropoff_man = "";
    public String order_number = "";
    public Integer state = 0;
    public String phone = "";
    public String address = "";
    public String addr_number = "";
    public String addr_building = "";
    public String addr_remainder = "";
    public String note = "";
    public String memo = "";
    public Integer price = 0;
    public Integer dropoff_price = 0;
    public String pickup_date = "";
    public String dropoff_date = "";
    public String rdate = "";
    public Integer payment_method = 0;
    public ArrayList<Item> item = null;
    public ArrayList<Coupon> coupon = null;
    public JsonObject pickupInfo = null;
    public JsonObject dropoffInfo = null;
}
