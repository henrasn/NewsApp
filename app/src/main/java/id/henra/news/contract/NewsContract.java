package id.henra.news.contract;

import java.util.List;

import id.henra.news.model.news.ArticlesItem;
import id.henra.news.model.news.DataModel;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public interface NewsContract {
    interface NewsView{
        void showNewsList(List<ArticlesItem> articleItems);
        void showNewsError(String message);
    }

    interface NewsPresenter{
        void getNewsList(int page);
        void onNewsReceived(DataModel data);
        void onFailedReceiveNews(Throwable e);
    }

    interface NewsInteractor{
        void getNewsList(String page);
    }
}
