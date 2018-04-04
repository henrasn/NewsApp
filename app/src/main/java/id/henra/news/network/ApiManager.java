package id.henra.news.network;

import id.henra.news.model.news.DataModel;
import rx.Observable;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public class ApiManager extends BaseApiManager<ApiServices> {
    public Observable<DataModel> requestNews(NetworkHandlerContract networkHandler, String page) {
        return addHandler(getApiService().requestNews(page, getServerKey()), networkHandler);
    }

    public Observable<DataModel> requestHeadlines(NetworkHandlerContract networkHandler, String page) {
        return addHandler(getApiService().requestHeadline(page, getServerKey()), networkHandler);
    }
}
