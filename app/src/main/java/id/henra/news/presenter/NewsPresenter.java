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
import id.henra.news.contract.NewsContract;
import id.henra.news.interactor.NewsInteractor;
import id.henra.news.model.news.DataModel;
import id.henra.news.utils.PrefManager;

/**
 * Created by Henra Setia Nugraha on 04/04/2018.
 */

public class NewsPresenter implements NewsContract.NewsPresenter {

    private final String NEWS_KEY = "news";
    private final String HEADLINES_KEY = "headlines";
    private NewsContract.NewsView view;
    private NewsContract.NewsInteractor interactor;
    private int currentPage = 1;
    private Map<String, String> query;

    public NewsPresenter() {
        interactor = new NewsInteractor(this);
        setDefaultQuery();
    }

    @Override
    public void attachView(NewsContract.NewsView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void getNewsList(boolean isContinueLoad) {
        if (isContinueLoad) currentPage++;
        else currentPage = 1;
        query.put("page", String.valueOf(currentPage));

        interactor.getNewsList(query);
    }

    @Override
    public void onNewsReceived(DataModel data) {
        if (view != null)
            view.showNewsList(data.getArticles());
        Log.i(NewsPresenter.class.getCanonicalName(), "onNewsReceived: " + new Gson().toJson(data));
    }

    @Override
    public void onFailedReceiveNews(Throwable e) {
        new Gson().toJson(e);
    }

    @Override
    public void setNewsFavorite(String author, String title, boolean isFavorite) {
        List<String> stringList;
        stringList = getFavList(true);
        if (isFavorite) {
            if (!stringList.contains(author + title))
                stringList.add(author + title);
        } else stringList.remove(author + title);
        PrefManager.getPreference().putString(NEWS_KEY, new Gson().toJson(stringList));
    }

    private List<String> getFavList(boolean isNews) {
        List<String> stringList = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        String data = PrefManager.getPreference().getString(isNews ? NEWS_KEY : HEADLINES_KEY);
        if (data != null)
            stringList = new Gson().fromJson(data, listType);
        return stringList;
    }

    @Override
    public List<String> getNewsFavorite() {
        return getFavList(true);
    }

    private void setDefaultQuery() {
        query = new HashMap<>();
        query.put("pageSize", "10");
        query.put("q", "market place");
        query.put("apiKey", BuildConfig.KEY);
        query.put("page", "1");
    }
}
