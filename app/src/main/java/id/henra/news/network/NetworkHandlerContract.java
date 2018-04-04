package id.henra.news.network;

import retrofit2.Response;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public interface NetworkHandlerContract {
    void onNoInternetConnection();
    void onNetworkProblem();
    void onFailedProcessRequest(Response response);
}
