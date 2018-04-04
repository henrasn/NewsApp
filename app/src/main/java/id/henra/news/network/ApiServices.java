package id.henra.news.network;

import java.util.Map;

import id.henra.news.model.news.DataModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public interface ApiServices {

    @GET("everything")
    Observable<DataModel> requestNews(@QueryMap Map<String ,String> query);

    @GET("top-headlines")
    Observable<DataModel> requestHeadline(@Query("page") String page);
}
