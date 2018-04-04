package id.henra.news.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public class RetrofitFactory {
    private static final String BASE_URL = "https://newsapi.org/v2/";

    public static synchronized ApiServices getRetrofitFactory() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return builder.build().create(ApiServices.class);
    }
}
