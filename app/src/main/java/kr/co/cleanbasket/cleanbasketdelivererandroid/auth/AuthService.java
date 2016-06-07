package kr.co.cleanbasket.cleanbasketdelivererandroid.auth;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressManager;
import kr.co.cleanbasket.cleanbasketdelivererandroid.vo.JsonData;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by gingeraebi on 2016. 6. 2..
 */
public interface AuthService {

    @POST(AddressManager.LOGIN)
    @FormUrlEncoded
    Call<JsonData> login(@Field("email") String email, @Field("password") String password, @Field("remember") Boolean remember, @Field("regid") String regid);

    @POST(AddressManager.LOGIN_CHECK)
    Call<JsonData> loginCheck();

    @Multipart
    @POST(AddressManager.DELIVERER_JOIN)
    Call<JsonData> requestJoin(@Part MultipartBody.Part file, @Part("email") String email, @Part("password") String password, @Part("name") String name, @Part("phone") String phone, @Part("birthday") String birthday);

}
