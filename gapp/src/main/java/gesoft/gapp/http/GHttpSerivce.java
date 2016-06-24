package gesoft.gapp.http;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by yhr on 2016/6/22.
 *
 */
public interface GHttpSerivce {

    @GET("mobile.do")
    Call<JSONObject> ajaxGet(@Query("sign") String sign, @QueryMap Map<String, String> options);

    @FormUrlEncoded
    @POST(GHttp.URL)
    Call<JSONObject> ajaxPost(@Field("sign") String sign, @FieldMap Map<String, String> options);

    @Multipart
    @POST(GHttp.URL)
    Call<JSONObject> ajaxUpload(@Part("sign") String sign, @PartMap Map<String, RequestBody> params);

}
