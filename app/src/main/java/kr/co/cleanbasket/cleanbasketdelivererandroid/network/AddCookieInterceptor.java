package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import kr.co.cleanbasket.cleanbasketdelivererandroid.utils.SharedPreferenceBase;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public class AddCookieInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        // Preference에서 cookies를 가져오는 작업을 수행
        Set<String> preferences = SharedPreferenceBase.getSharedPreference("Cookies",new HashSet<String>());

        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
        }

        // Web,Android,iOS 구분을 위해 User-Agent세팅
        builder.removeHeader("User-Agent").addHeader("User-Agent", "wash");


        return chain.proceed(builder.build());
    }
}
