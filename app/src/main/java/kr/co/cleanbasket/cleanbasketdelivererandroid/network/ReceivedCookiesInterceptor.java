package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public class ReceivedCookiesInterceptor implements Interceptor {

    private Activity context;

    public ReceivedCookiesInterceptor(Activity context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreferences pref = context.getSharedPreferences("Cookies", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            // Preference에 cookies를 넣어주는 작업을 수행
            editor.putStringSet("Cookies",cookies);
            editor.commit();
        }

        return originalResponse;
    }
}