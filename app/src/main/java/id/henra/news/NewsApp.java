package id.henra.news;

import android.app.Application;

import id.henra.news.utils.PrefManager;

/**
 * Created by Henra Setia Nugraha on 4/4/2018.
 */

public class NewsApp extends Application {

    private static NewsApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        PrefManager.setPreference(this, NewsApp.class.getSimpleName());
    }

    public static NewsApp getInstance() {
        return instance;
    }
}
