package milad.com.wachitest.util;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit INSTANCE = null;
    private static String authToken;

    public static Retrofit getINSTANCE(final String username, final String password, String url) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            authToken = Credentials.basic(username, password);
        }
        OkHttpClient authorization = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request();
                                Request authorization = request.newBuilder()
                                        .addHeader("Authorization", authToken)
                                        .build();
                                return chain.proceed(authorization);
                            }
                        }
                )
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();


        if (INSTANCE == null) {
            INSTANCE = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(authorization)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return INSTANCE;
    }

}
