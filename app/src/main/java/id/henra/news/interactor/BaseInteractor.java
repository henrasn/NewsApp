package id.henra.news.interactor;

import id.henra.news.network.ApiManager;

/**
 * Created by Pc Game on 04/04/2018.
 */

public class BaseInteractor {
    private ApiManager apiManager;

    public BaseInteractor() {
        apiManager = new ApiManager();
    }

    public ApiManager getApiManager() {
        return apiManager;
    }
}
