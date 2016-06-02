package kr.co.cleanbasket.cleanbasketdelivererandroid.activity;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public interface LoginService {

    @POST(AddressManager.LOGIN)
    @FormUrlEncoded
    Call<JsonData> login(@Field("email") String email, @Field("password") String password, @Field("remember") Boolean remember, @Field("regid") String regid);

    @POST(AddressManager.LOGIN_CHECK)
    Call<JsonData> loginCheck();

}
