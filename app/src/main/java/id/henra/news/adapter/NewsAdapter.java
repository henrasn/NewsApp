package id.henra.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.henra.news.R;
import id.henra.news.listener.ItemClickListener;
import id.henra.news.model.news.ArticlesItem;
import id.henra.news.utils.CustomFont;

/**
 * Created by Henra Setia Nugraha on 04/04/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArticlesItem> articlesItems;
    private Context context;
    private ItemClickListener clickListener;
    private String favoriteIcon;
    private String unFavoriteIcon;
    private List<String> favoriteItems;

    public NewsAdapter(Context context, ItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        articlesItems = new ArrayList<>();
        favoriteIcon = context.getString(R.string.favorite);
        unFavoriteIcon = context.getString(R.string.unFavorite);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsViewHolder vh = (NewsViewHolder) holder;
        if (vh != null) {
            vh.title.setText(articlesItems.get(position).getTitle());
            Glide.with(context).load(articlesItems.get(position).getUrlToImage()).into(vh.image);
        }

        if (favoriteItems.contains(articlesItems.get(position).getAuthor() + articlesItems.get(position).getTitle())) {
            vh.favIcon.setText(favoriteIcon);
        } else {
            vh.favIcon.setText(unFavoriteIcon);
        }
    }

    @Override
    public int getItemCount() {
        return articlesItems.size();
    }

    public void setArticlesItems(List<ArticlesItem> articlesItems, boolean isAddMore) {
        if (isAddMore) articlesItems.addAll(articlesItems);
        else this.articlesItems = articlesItems;
        notifyDataSetChanged();
    }

    public void setFavoriteItems(List<String> favoriteItems) {
        this.favoriteItems = favoriteItems;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.image)
        ImageView image;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.favNewsIcon)
        CustomFont favIcon;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClicked(R.id.newsItemContainer, null);
        }

        @OnClick(R.id.favNewsIcon)
        public void onFavClicked() {
            boolean isFavorites = favIcon.getText().toString().equals(context.getString(R.string.unFavorite));
////            if (isFavorites) favIcon.setText(favoriteIcon);
////            else favIcon.setText(unFavoriteIcon);
//            String author = articlesItems.get(getAdapterPosition()).getAuthor();
//            String title = articlesItems.get(getAdapterPosition()).getTitle();
//
//            if (!favoriteItems.contains(author + title))
//                favoriteItems.add(author + title);
//            notifyItemChanged(getAdapterPosition());

            itemClicked(R.id.favNewsIcon, isFavorites);
        }

        private void itemClicked(int viewId, Boolean is) {
            try {
                clickListener.onItemClick(articlesItems.get(getAdapterPosition()), getAdapterPosition(), viewId, is);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
