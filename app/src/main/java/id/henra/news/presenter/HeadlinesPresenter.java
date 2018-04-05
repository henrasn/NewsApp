package id.henra.news.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.henra.news.BuildConfig;
import id.henra.news.contract.HeadlinesContract;
import id.henra.news.contract.NewsContract;
import id.henra.news.interactor.HeadlinesInteractor;
import id.henra.news.interactor.NewsInteractor;
import id.henra.news.model.news.DataModel;
import id.henra.news.utils.PrefManager;

/**
 * Created by Henra Setia Nugraha on 04/04/2018.
 */

public class HeadlinesPresenter extends BasePresenter implements HeadlinesContract.HeadlinesPresenter {

    private final String HEADLINES_KEY = "item_headlines";
    private HeadlinesContract.HeadlinesView view;
    private HeadlinesContract.HeadlinesInteractor interactor;
    private int currentPage = 1;
    private Map<String, String> query;

    public HeadlinesPresenter() {
        interactor = new HeadlinesInteractor(this);
        setDefaultQuery();
    }

    @Override
    public void attachView(HeadlinesContract.HeadlinesView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getHeadlinesList(boolean isContinueLoad) {
        if (isContinueLoad) currentPage++;
        else currentPage = 1;
        query.put("page", String.valueOf(currentPage));

        interactor.getHeadlinesList(query);
    }

    @Override
    public void onHeadlinesReceived(DataModel data) {
        if (view != null)
            view.showHeadlinesList(data.getArticles());
        Log.i(HeadlinesPresenter.class.getCanonicalName(), "onNewsReceived: " + new Gson().toJson(data));
    }

    @Override
    public void onFailedReceiveHeadlines(Throwable e) {
        Log.i(HeadlinesPresenter.class.getCanonicalName(), "onFailedReceiveHeadlines: " + new Gson().toJson(e));
    }

    @Override
    public void setHeadlinesFavorite(String author, String title, boolean isFavorite) {
        setFavoriteItem(author, title, isFavorite, HEADLINES_KEY);
    }

    @Override
    public List<String> getNewsFavorite() {
        return getFavList(HEADLINES_KEY);
    }

    private void setDefaultQuery() {
        query = new HashMap<>();
        query.put("pageSize", "5");
        query.put("country", "id");
        query.put("apiKey", BuildConfig.KEY);
        query.put("page", "1");
    }
}
