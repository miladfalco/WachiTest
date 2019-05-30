package milad.com.wachitest.network;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("/")
    Call<ResponseBody> sendRequest();
}
