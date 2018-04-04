package id.henra.news.contract;

import java.util.List;
import java.util.Map;

import id.henra.news.model.news.ArticlesItem;
import id.henra.news.model.news.DataModel;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public interface NewsContract {
    interface NewsView {
        void showNewsList(List<ArticlesItem> articleItems);
        void showNewsError(String message);
    }

    interface NewsPresenter extends BasePresenter<NewsView>{
        void getNewsList(boolean isContinueLoad);
        void onNewsReceived(DataModel data);
        void onFailedReceiveNews(Throwable e);

        void setNewsFavorite(String author,String title,boolean isFavorite);
        List<String> getNewsFavorite();
    }

    interface NewsInteractor{
        void getNewsList(Map<String,String> query);
    }
}
