package kr.co.cleanbasket.cleanbasketdelivererandroid.service;

import android.app.Application;
import android.util.Log;

/**
 * Created by theodore on 16. 2. 29..
 */
public class ApplicationLaundryDelivery extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("CleanBasket ::", "Current Class ApplicationLaundryDelivery");

        HttpClientLaundryDelivery.initConfiguration(this);

    }

}
