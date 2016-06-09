package kr.co.cleanbasket.cleanbasketdelivererandroid.vo;

import java.io.Serializable;

public class Order extends OrderInfo {
	public Integer adrid = 0;
	public Integer mileage = 0;
	public Integer sale = 0;
	public Integer feedback = 0;

	@Override
	public String toString() {
		return super.toString() +
				"Order{" +
				"adrid=" + adrid +
				", mileage=" + mileage +
				", sale=" + sale +
				", feedback=" + feedback +
				'}';
	}
}
