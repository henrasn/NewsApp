package id.henra.news;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.henra.news.adapter.NewsAdapter;
import id.henra.news.contract.NewsContract;
import id.henra.news.listener.ItemClickListener;
import id.henra.news.model.news.ArticlesItem;
import id.henra.news.presenter.NewsPresenter;

public class NewsActivity extends AppCompatActivity implements NewsContract.NewsView, SwipeRefreshLayout.OnRefreshListener, ItemClickListener {

    @BindView(R.id.newsList)
    RecyclerView newsList;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private NewsContract.NewsPresenter presenter;
    private NewsAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean isContinueLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setupPresenter();
        setupList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attachView(this);
    }

    @Override
    protected void onStop() {
        presenter.detachView();
        super.onStop();
    }

    @Override
    public void onRefresh() {
        isContinueLoad = false;
        presenter.getNewsList(isContinueLoad);
    }

    @Override
    public void showNewsList(List<ArticlesItem> articleItems) {
        if (articleItems != null) {
            adapter.setFavoriteItems(presenter.getNewsFavorite());
            adapter.setArticlesItems(articleItems, isContinueLoad);
        }
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showNewsError(String message) {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onItemClick(Object data, int position, int viewId, Boolean is) {
        String url = null;
        ArticlesItem article = null;
        if (data instanceof ArticlesItem) {
            article = (ArticlesItem) data;
            url = article.getUrl();
        }

        if (url != null && (viewId == R.id.newsItemContainer || viewId == R.id.headlinesItemContainer))
            startActivity((new Intent(this, DetailNewsActivity.class)).putExtra("url", url));

        if (viewId == R.id.favNewsIcon && article != null) {
            presenter.setNewsFavorite(article.getAuthor(), article.getTitle(), is);
            adapter.setFavoriteItems(presenter.getNewsFavorite());
            adapter.notifyItemChanged(position);
        }
    }

    private void setupPresenter() {
        presenter = new NewsPresenter();
        presenter.getNewsList(false);
    }

    private void setupList() {
        swipeRefresh.setOnRefreshListener(this);
        adapter = new NewsAdapter(this, this);
        layoutManager = new LinearLayoutManager(this);

        newsList.setHasFixedSize(true);
        newsList.setLayoutManager(layoutManager);
        newsList.setAdapter(adapter);
        newsList.addOnScrollListener(onScrollListener);
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (layoutManager.findFirstVisibleItemPosition() + recyclerView.getChildCount() == layoutManager.getItemCount() && dy > 0) {
                if (!swipeRefresh.isRefreshing()) {
                    isContinueLoad = true;
                    presenter.getNewsList(isContinueLoad);
                    swipeRefresh.setRefreshing(true);
                }
            }
        }
    };
}
