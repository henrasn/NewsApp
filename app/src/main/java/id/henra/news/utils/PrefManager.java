package id.henra.news.utils;

import android.content.Context;

/**
 * Created by Henra Setia Nugraha on 05/04/2018.
 */

public class PrefManager {
    public static PrefManager pref;
    private PrefHelper helper;

    public static PrefManager getInstance() {
        if (pref == null) {
            synchronized (PrefManager.class) {
                if (pref == null) {
                    pref = new PrefManager();
                }
            }
        }
        return pref;
    }

    private void setPrefHelper(Context context, String preferences) {
        if (helper == null) {
            helper = new PrefHelper(context, preferences);
        }
    }

    private PrefHelper getPrefHelper() {
        return helper;
    }

    public static void setPreference(Context context, String preferences) {
        getInstance().setPrefHelper(context, preferences);
    }

    public static PrefHelper getPreference() {
        return getInstance().getPrefHelper();
    }
}
