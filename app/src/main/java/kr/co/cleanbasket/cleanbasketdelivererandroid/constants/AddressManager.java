package kr.co.cleanbasket.cleanbasketdelivererandroid.constants;

import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.Config;

/**
 *  AddressManager.java
 *  CleanBasket Deliverer Android
 *
 *  Created by Yongbin Cha
 *  Copyright (c) 2016 WashAppKorea. All rights reserved.
 *
 */

public class AddressManager {
	public final static String SERVER_ADDRESS = Config.SERVER_ADDRESS;

	public final static String LOGIN = "auth";
	public final static String DELIVERER_PICKUP = "deliverer/pickup";
	public final static String DELIVERER_DROPOFF = "deliverer/dropoff";
	public final static String DELIVERER_JOIN = "deliverer/join";
	public final static String DELIVERER_ORDER = "deliverer/order";
	public final static String DELIVERER_LIST = "deliverer/list";
	public final static String DELIVERER_TODAY_PIKUP = "deliverer/pickup/today";
	public final static String DELIVERER_TODAY_DROPOFF = "deliverer/dropoff/today";

	public final static String ASSIGN_PICKUP = "deliverer/pickup/assign";
	public final static String ASSIGN_DROPOFF = "deliverer/dropoff/assign";


	public final static String LOGIN_CHECK = "auth/check";
	public final static String CONFIRM_PICKUP = "deliverer/pickup/complete";
	public final static String CONFIRM_DROPOFF = "deliverer/dropoff/complete";
	
	public final static String LOGOUT = "logout";
}
