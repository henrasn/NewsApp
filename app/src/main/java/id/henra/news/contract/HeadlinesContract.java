package id.henra.news.contract;

import java.util.List;

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
    
    interface HeadlinesPresenter{
        void getHeadlinesList(int page);
        void onHeadlinesReceived(DataModel data);
        void onFailedReceiveHeadlines(Throwable e);
    }
    
    interface HeadlinesInteractor{
        void getHeadlinesList(String page);
    }
}
