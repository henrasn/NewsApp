package id.henra.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.henra.news.R;
import id.henra.news.adapter.NewsAdapter;
import id.henra.news.contract.HeadlinesContract;
import id.henra.news.contract.NewsContract;
import id.henra.news.listener.ItemClickListener;
import id.henra.news.model.news.ArticlesItem;
import id.henra.news.presenter.HeadlinesPresenter;
import id.henra.news.presenter.NewsPresenter;

public class NewsActivity extends AppCompatActivity implements NewsContract.NewsView, SwipeRefreshLayout.OnRefreshListener, ItemClickListener, HeadlinesContract.HeadlinesView {

    @BindView(R.id.newsList)
    RecyclerView newsList;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;

    private NewsContract.NewsPresenter newsPresenter;
    private HeadlinesContract.HeadlinesPresenter headlinesPresenter;
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
        swipeRefresh.setRefreshing(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        newsPresenter.attachView(this);
        headlinesPresenter.attachView(this);
    }

    @Override
    protected void onStop() {
        newsPresenter.detachView();
        headlinesPresenter.detachView();
        super.onStop();
    }

    @Override
    public void onRefresh() {
        isContinueLoad = false;
        newsPresenter.getNewsList(isContinueLoad);
    }

    @Override
    public void showNewsList(List<ArticlesItem> articleItems) {
        if (articleItems != null) {
            adapter.setNewsFavoriteItems(newsPresenter.getNewsFavorite());
            adapter.setArticlesItems(articleItems, isContinueLoad);
        }
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showNewsError(String message) {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showHeadlinesList(List<ArticlesItem> articleItems) {
        adapter.setHeadlineItems(articleItems);
    }

    @Override
    public void showHeadlinesError(String message) {

    }

    @Override
    public void onItemClick(Object data, int position, int viewId, Boolean is, boolean isNews) {
        String url = null;
        ArticlesItem article = null;
        if (data instanceof ArticlesItem) {
            article = (ArticlesItem) data;
            url = article.getUrl();
        }

        if (url != null && (viewId == R.id.newsItemContainer || viewId == R.id.headlinesItemContainer))
            startActivity((new Intent(this, DetailNewsActivity.class)).putExtra("url", url));

        if (viewId == R.id.favNewsIcon && article != null) {
            if (isNews) {
                newsPresenter.setNewsFavorite(article.getAuthor(), article.getTitle(), is);
                adapter.setNewsFavoriteItems(newsPresenter.getNewsFavorite());
                adapter.notifyItemChanged(position);
            } else {
                headlinesPresenter.setHeadlinesFavorite(article.getAuthor(),article.getTitle(),is);
                adapter.setHeadlinesFavoriteItems(headlinesPresenter.getNewsFavorite());
            }
        }
    }

    private void setupPresenter() {
        headlinesPresenter = new HeadlinesPresenter();
        headlinesPresenter.getHeadlinesList(false);

        newsPresenter = new NewsPresenter();
        newsPresenter.getNewsList(false);
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
                    newsPresenter.getNewsList(isContinueLoad);
                    swipeRefresh.setRefreshing(true);
                }
            }
        }
    };
}
