package com.kentux.alertiumtracker.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kentux.alertiumtracker.MainActivity;
import com.kentux.alertiumtracker.R;
import com.kentux.alertiumtracker.adapters.NewsAdapter;
import com.kentux.alertiumtracker.interfaces.RetrofitInterfaces;
import com.kentux.alertiumtracker.models.News;
import com.kentux.alertiumtracker.utils.NoConnectivityException;
import com.kentux.alertiumtracker.utils.RetrofitUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    @BindView(R.id.news_recycler_view)
    RecyclerView mNewsRecyclerView;
    @BindView(R.id.news_swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.news_progress_bar)
    ProgressBar mNewsProgressBar;
    @BindView(R.id.news_empty_state)
    TextView mEmptyStateTextView;

    private Unbinder mUnbinder;
    private NewsAdapter mNewsAdapter;
    private ArrayList<News> mNews;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String string);
    }

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mNewsRecyclerView.setLayoutManager(layoutManager);

        mNewsAdapter = new NewsAdapter();
        mNewsRecyclerView.setAdapter(mNewsAdapter);

        mNewsAdapter.setNews(mNews);
        mNewsAdapter.notifyDataSetChanged();

        loadNews();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void loadNews() {
        try {
            mEmptyStateTextView.setVisibility(View.GONE);

            RetrofitInterfaces.NewsRetrofitInterface newsInterface = RetrofitUtils.getClient(getContext())
                    .create(RetrofitInterfaces.NewsRetrofitInterface.class);

            final Call<ArrayList<News>> newsArrayListCall = newsInterface.getNews();

            newsArrayListCall.enqueue(new Callback<ArrayList<News>>() {
                @Override
                public void onResponse(Call<ArrayList<News>> call, Response<ArrayList<News>> response) {
                    int responseCode = response.code();

                    if (response.isSuccessful()) {
                        postDataLoad(true, "");
                        mNews = response.body();
                        mNewsAdapter.setNews(mNews);
                        mNewsAdapter.notifyDataSetChanged();
                    } else {
                        mEmptyStateTextView.setText(getString(R.string.no_connection));
                        postDataLoad(false, getString(R.string.load_data_failed));
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<News>> call, Throwable t) {
                    postDataLoad(false, getString(R.string.load_data_failed));
                }
            });
        } catch (NoConnectivityException e) {
            postDataLoad(false, getString(R.string.load_data_failed));
            Log.e(LOG_TAG, getString(R.string.load_data_failed));
        }
    }

    public void postDataLoad(boolean isLoadSuccessful, String message) {
        if (mNewsProgressBar.getVisibility() == View.VISIBLE) {
            mNewsProgressBar.setVisibility(View.INVISIBLE);
        }

        if (isLoadSuccessful) {
            mEmptyStateTextView.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
        } else {
            swipeRefreshLayout.setRefreshing(false);
            mEmptyStateTextView.setText(message);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}

