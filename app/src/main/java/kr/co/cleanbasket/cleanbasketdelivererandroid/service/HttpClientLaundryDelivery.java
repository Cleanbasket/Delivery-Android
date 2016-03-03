package kr.co.cleanbasket.cleanbasketdelivererandroid.service;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.cookie.Cookie;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.AddressManager;

/**
 * Created by theodore on 16. 2. 29..
 */
public class HttpClientLaundryDelivery {
    private static final String BASE_URL = AddressManager.SERVER_ADDRESS;

    private static AsyncHttpClient client = new AsyncHttpClient();

    // POST

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(Context context, String url, RequestParams requestEntity, TextHttpResponseHandler handler ) {
        client.post(getAbsoluteUrl(url), requestEntity, handler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }


    public static void initConfiguration(Context c) {
        PersistentCookieStore myCookieStore = new PersistentCookieStore(c.getApplicationContext());
        client.setCookieStore(myCookieStore);
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.setThreadPool((ThreadPoolExecutor) Executors.newCachedThreadPool());
        client.setUserAgent("wash");
        Log.i("CleanBasket ::", "initConfiguration");

        for(Cookie cookie : myCookieStore.getCookies()) {
            Log.i("CleanBasket ::", cookie.getValue() + " / " +cookie.getName());
        }
    }

    public static void clearCookie() {
        client.setCookieStore(null);
    }

}
