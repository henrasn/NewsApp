package id.henra.news.interactor;

import java.util.Map;

import id.henra.news.contract.HeadlinesContract;
import id.henra.news.contract.NewsContract;
import id.henra.news.network.NetworkHandler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Henra Setia Nugraha on 04/04/2018.
 */

public class HeadlinesInteractor extends BaseInteractor implements HeadlinesContract.HeadlinesInteractor {

    private HeadlinesContract.HeadlinesPresenter presenter;

    public HeadlinesInteractor(HeadlinesContract.HeadlinesPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void getHeadlinesList(Map<String ,String> query) {
        getApiManager()
                .requestHeadlines(new NetworkHandler(), query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(
                        dataModel -> presenter.onHeadlinesReceived(dataModel),
                        error -> presenter.onFailedReceiveHeadlines(error)
                );
    }
}
