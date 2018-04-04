package id.henra.news.network;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.HttpException;
import rx.Observable;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public class BaseApiManager<T> {

    private ApiServices apiService;
    private static final String SERVER_KEY = "7611baf082194e2a9e848185109f93a6";

    public BaseApiManager() {
        apiService = RetrofitFactory.getRetrofitFactory();
    }

    public ApiServices getApiService() {
        return apiService;
    }

    public static String getServerKey() {
        return SERVER_KEY;
    }

    protected <T> Observable<T> addHandler(Observable<T> observable, final NetworkHandlerContract networkHandler) {
        return observable.doOnError(throwable -> onNetworkError(throwable, networkHandler));
    }

    private void onNetworkError(Throwable throwable, NetworkHandlerContract networkHandler) {
        if (throwable instanceof UnknownHostException)
            networkHandler.onNoInternetConnection();
        else if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            networkHandler.onFailedProcessRequest(httpException.response());
        }
    }
}
