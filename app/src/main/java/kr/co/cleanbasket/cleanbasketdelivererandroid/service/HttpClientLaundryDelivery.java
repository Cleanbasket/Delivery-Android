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

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.client.params.ClientPNames;
import cz.msebera.android.httpclient.cookie.Cookie;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.LogUtils;

/**
 *  HttpClientLaundryDelivery.java
 *  CleanBasket Deliverer Android
 *
 *  Created by Yongbin Cha
 *  Copyright (c) 2016 WashAppKorea. All rights reserved.
 *
 */

public class HttpClientLaundryDelivery {

    private static final String TAG = LogUtils.makeTag(HttpClientLaundryDelivery.class);

    private static final String BASE_URL = AddressManager.SERVER_ADDRESS;

    private static AsyncHttpClient client = new AsyncHttpClient();

    // POST

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, HttpEntity entity, String contentType, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), entity, contentType, responseHandler);
    }

    public static void post(String url, RequestParams params, TextHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(Context context, String url, RequestParams requestEntity, TextHttpResponseHandler handler ) {
        client.post(getAbsoluteUrl(url), requestEntity, handler);
    }

    // GET

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params ,responseHandler);
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
        Log.i(TAG, "initConfiguration");

        for(Cookie cookie : myCookieStore.getCookies()) {
            Log.i(TAG, cookie.getValue() + " / " +cookie.getName());
        }
    }

    public static void clearCookie() {
        client.setCookieStore(null);
    }

}
