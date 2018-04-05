package id.henra.news.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
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
import id.henra.news.listener.HeadlinePaging;
import id.henra.news.listener.ItemClickListener;
import id.henra.news.model.news.ArticlesItem;
import id.henra.news.utils.CustomFont;

/**
 * Created by Henra Setia Nugraha on 04/04/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ArticlesItem> articlesItems;
    private List<ArticlesItem> headlineItems;
    private Context context;
    private ItemClickListener clickListener;
    private String favoriteIcon;
    private String unFavoriteIcon;
    private List<String> newsFavoriteItems = new ArrayList<>();
    private List<String> headlinesFavoriteItems = new ArrayList<>();
    private int currentHeadlinePage = 1;
    private HeadlinePaging headlinePaging;
    HeadlinesAdapter headlinesAdapter;


    public NewsAdapter(Context context, ItemClickListener clickListener) {
        this.context = context;
        this.clickListener = clickListener;
        articlesItems = new ArrayList<>();
        headlineItems = new ArrayList<>();
        favoriteIcon = context.getString(R.string.favorite);
        unFavoriteIcon = context.getString(R.string.unFavorite);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
            return new NewsViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.headlines, parent, false);
            return new HeadlineViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == 0) {
            NewsViewHolder vh = (NewsViewHolder) holder;
            vh.title.setText(articlesItems.get(position).getTitle());
            Glide.with(context).load(articlesItems.get(position).getUrlToImage()).into(vh.image);

            if (newsFavoriteItems.contains(articlesItems.get(position).getAuthor() + articlesItems.get(position).getTitle())) {
                vh.favIcon.setText(favoriteIcon);
            } else {
                vh.favIcon.setText(unFavoriteIcon);
            }
        } else {
            HeadlineViewHolder vh1 = (HeadlineViewHolder) holder;
            int tempStart = (currentHeadlinePage * 5) - 5;
            int start = tempStart == 0 ? 0 : tempStart - 1;
            int end = (currentHeadlinePage * 5) - 1;
            if (position > 1 && position > currentHeadlinePage) {
                currentHeadlinePage = position - currentHeadlinePage;
            }
            vh1.adapter.setFavoriteItems(headlinesFavoriteItems);
//            if (!(end > headlineItems.size() - 1)) {
            vh1.adapter.setArticlesItems(headlineItems, false);
//            } else
//                vh1.adapter.setArticlesItems(headlineItems.subList(start, headlineItems.size() - 1), false);

            if (headlinePaging != null) {
                headlinePaging.onItemLoaded(currentHeadlinePage);
            }
            headlinesAdapter = vh1.adapter;
        }
    }

    @Override
    public int getItemCount() {
        return articlesItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }

    public ArticlesItem getItem(int position) {
        return articlesItems.get(position);
    }

    public void setArticlesItems(List<ArticlesItem> articlesItems, boolean isAddMore) {
        if (isAddMore) articlesItems.addAll(articlesItems);
        else this.articlesItems = articlesItems;
        notifyDataSetChanged();
    }

    public void setNewsFavoriteItems(List<String> newsFavoriteItems) {
        this.newsFavoriteItems = newsFavoriteItems;
    }

    public void setHeadlinesFavoriteItems(List<String> headlinesFavoriteItems) {
        this.headlinesFavoriteItems = headlinesFavoriteItems;
        if (headlinesAdapter != null) {
            headlinesAdapter.setFavoriteItems(headlinesFavoriteItems);
        }
    }

    public void setHeadlineItems(List<ArticlesItem> headlineItems) {
        this.headlineItems.addAll(headlineItems);
    }

    public void setHeadlinePaging(HeadlinePaging headlinePaging) {
        this.headlinePaging = headlinePaging;
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
            itemClicked(R.id.favNewsIcon, isFavorites);
        }

        private void itemClicked(int viewId, Boolean is) {
            try {
                clickListener.onItemClick(articlesItems.get(getAdapterPosition()), getAdapterPosition(), viewId, is, true);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

    }

    class HeadlineViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recyclerView)
        RecyclerView recyclerView;

        private HeadlinesAdapter adapter;

        public HeadlineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            adapter = new HeadlinesAdapter(itemView.getContext(), clickListener);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
        }
    }
}
