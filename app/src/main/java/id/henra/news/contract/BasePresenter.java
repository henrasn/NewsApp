package id.henra.news.contract;

/**
 * Created by Henra Setia Nugraha on 04/04/2018.
 */

public interface BasePresenter<T> {
    void attachView(T view);
    void detachView();
}
