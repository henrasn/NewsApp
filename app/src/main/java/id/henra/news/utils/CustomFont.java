package id.henra.news.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Henra Setia Nugraha on 04/04/2018.
 */

public class CustomFont extends TextView {
    public CustomFont(Context context) {
        super(context);
    }

    public CustomFont(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFont(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/ionicons.ttf");
            setTypeface(typeface);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
