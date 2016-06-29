package kr.co.cleanbasket.cleanbasketdelivererandroid.network;

import kr.co.cleanbasket.cleanbasketdelivererandroid.constants.AddressConstant;
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
public interface AuthInterface {

    @POST(AddressConstant.LOGIN)
    @FormUrlEncoded
    Call<JsonData> login(@Field("email") String email, @Field("password") String password, @Field("remember") Boolean remember, @Field("regid") String regid);

    @POST(AddressConstant.LOGIN_CHECK)
    Call<JsonData> loginCheck();

    @Multipart
    @POST(AddressConstant.DELIVERER_JOIN)
    Call<JsonData> requestJoin(@Part MultipartBody.Part file, @Part("email") String email, @Part("password") String password, @Part("name") String name, @Part("phone") String phone, @Part("birthday") String birthday);

}
