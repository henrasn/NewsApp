package id.henra.news.contract;

import java.util.List;
import java.util.Map;

import id.henra.news.model.news.ArticlesItem;
import id.henra.news.model.news.DataModel;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public interface HeadlinesContract {
    interface HeadlinesView{
        void showHeadlinesList(List<ArticlesItem> articleItems);
        void showHeadlinesError(String message);
    }

    interface HeadlinesPresenter extends BasePresenter<HeadlinesView>{
        void getHeadlinesList(boolean isContinueLoad);
        void onHeadlinesReceived(DataModel data);
        void onFailedReceiveHeadlines(Throwable e);

        void setHeadlinesFavorite(String author, String title, boolean isFavorite);
        List<String> getNewsFavorite();
    }

    interface HeadlinesInteractor{
        void getHeadlinesList(Map<String ,String> query);
    }
}
