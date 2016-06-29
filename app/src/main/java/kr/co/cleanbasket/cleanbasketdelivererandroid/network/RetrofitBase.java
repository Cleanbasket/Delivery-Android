package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public class RetrofitBase {

    private Retrofit retrofit;

    private static RetrofitBase instance;

    public static RetrofitBase getInstance() {
        if(instance == null) {
            instance = new RetrofitBase();
        }

        return instance;
    }

    private RetrofitBase() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new AddCookieInterceptor());
        httpClient.addInterceptor(new ReceivedCookiesInterceptor());

        retrofit = new Retrofit.Builder()
                .baseUrl("http://cp.cleanbasket.co.kr")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
