package id.henra.news.network;

import id.henra.news.model.news.DataModel;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public interface ApiServices {

    @GET("everything?q=market+place&pageSize=10&apiKey={key}")
    Observable<DataModel> requestNews(
            @Query("page") String page,
            @Path("key") String serverKey);

    @GET("top-headlines?country=id&pageSize=5&apiKey={key}")
    Observable<DataModel> requestHeadline(
            @Query("page") String page,
            @Path("key") String serverKey);
}
