package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

import java.util.ArrayList;

public class Order {
	public Integer oid = 0;
	public Integer uid = 0;
	public Integer adrid = 0;
	public Integer pickup_man = 0;
	public Integer dropoff_man = 0;
	public String order_number = "";
	public Integer state = 0;
	public String phone = "";
	public String address = "";
	public String addr_number = "";
	public String addr_building = "";
	public String addr_remainder = "";
	public String memo = "";
	public String note = "";
	public Integer price = 0;
	public Integer dropoff_price = 0;
	public String pickup_date = "";
	public String dropoff_date = "";
	public String rdate = "";
	public Integer mileage = 0;
	public Integer sale = 0;
	public Integer payment_method = 0;
	public Integer feedback = 0;
	public Deliverer pickupInfo = null;
	public Deliverer dropoffInfo = null;
	public ArrayList<Item> item = null;
	public ArrayList<Coupon> coupon = null;
}
