package id.henra.news.interactor;

import java.util.Map;

import id.henra.news.contract.NewsContract;
import id.henra.news.network.NetworkHandler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Henra Setia Nugraha on 04/04/2018.
 */

public class NewsInteractor extends BaseInteractor implements NewsContract.NewsInteractor {

    private NewsContract.NewsPresenter presenter;

    public NewsInteractor(NewsContract.NewsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getNewsList(Map<String,String> query) {
        getApiManager()
                .requestNews(new NetworkHandler(), query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        dataModel -> presenter.onNewsReceived(dataModel),
                        error -> presenter.onFailedReceiveNews(error)
                );
    }
}
