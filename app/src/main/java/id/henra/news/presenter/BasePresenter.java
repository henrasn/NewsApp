package id.henra.news.presenter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import id.henra.news.utils.PrefManager;

/**
 * Created by Henra Setia Nugraha on 05/04/2018.
 */

public class BasePresenter {

    protected List<String> getFavList(String prefKey) {
        List<String> stringList = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<String>>() {
        }.getType();
        String data = PrefManager.getPreference().getString(prefKey);
        if (data != null)
            stringList = new Gson().fromJson(data, listType);
        return stringList;
    }

    protected void setFavoriteItem(String author, String title, boolean isFavorite, String prefKey) {
        List<String> stringList;
        stringList = getFavList(prefKey);
        if (isFavorite) {
            if (!stringList.contains(author + title))
                stringList.add(author + title);
        } else stringList.remove(author + title);
        PrefManager.getPreference().putString(prefKey, new Gson().toJson(stringList));
    }
}
