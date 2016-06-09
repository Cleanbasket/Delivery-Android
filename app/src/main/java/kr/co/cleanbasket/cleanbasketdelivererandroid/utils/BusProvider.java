package kr.co.cleanbasket.cleanbasketdelivererandroid.utils;

import com.squareup.otto.Bus;

/**
 * Created by gingeraebi on 2016. 6. 9..
 */
public class BusProvider {
    private static final Bus BUS = new Bus();

    public static Bus getInstance() {
        return BUS;
    }

    private BusProvider() {
        // No instances.
    }

}
