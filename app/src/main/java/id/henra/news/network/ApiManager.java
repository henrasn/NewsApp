package id.henra.news.network;

import java.util.Map;

import id.henra.news.model.news.DataModel;
import rx.Observable;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public class ApiManager extends BaseApiManager<ApiServices> {
    public Observable<DataModel> requestNews(NetworkHandlerContract networkHandler, Map<String,String> query) {
        return addHandler(getApiService().requestNews(query), networkHandler);
    }

//    public Observable<DataModel> requestHeadlines(NetworkHandlerContract networkHandler, String page) {
//        return addHandler(getApiService().requestHeadline(page, getServerKey()), networkHandler);
//    }
}
