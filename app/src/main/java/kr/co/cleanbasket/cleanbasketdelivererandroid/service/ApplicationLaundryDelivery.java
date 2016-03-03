package kr.co.cleanbasket.cleanbasketdelivererandroid.service;

import android.app.Application;
import android.util.Log;

import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;

/**
 *  ApplicationLaundryDelivery.java
 *  CleanBasket Deliverer Android
 *
 *  Created by Yongbin Cha
 *  Copyright (c) 2016 WashAppKorea. All rights reserved.
 *
 */

public class ApplicationLaundryDelivery extends Application {

    private static final String TAG = LogUtils.makeTag(ApplicationLaundryDelivery.class);

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(TAG, "Running Application 'Laundry Delivery Application'");

        HttpClientLaundryDelivery.initConfiguration(this);

    }

}
