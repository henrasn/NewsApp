package id.henra.news.network;

import android.content.Context;
import android.widget.Toast;

import id.henra.news.NewsApp;
import id.henra.news.R;
import retrofit2.Response;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public class NetworkHandler implements NetworkHandlerContract {
    @Override
    public void onNoInternetConnection() {
        Context context = NewsApp.getInstance();
        Toast.makeText(context, R.string.noInet, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkProblem() {

    }

    @Override
    public void onFailedProcessRequest(Response response) {

    }
}
