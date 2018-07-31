package com.kentux.alertiumtracker.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.kentux.alertiumtracker.adapters.AlertsAdapter;
import com.kentux.alertiumtracker.interfaces.RetrofitInterfaces;
import com.kentux.alertiumtracker.models.Alert;
import com.kentux.alertiumtracker.utils.NoConnectivityException;
import com.kentux.alertiumtracker.utils.RetrofitUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private String LOG_TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.alerts_recycler_view)
    RecyclerView mAlertsRecyclerView;
    @BindView(R.id.alerts_swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.alerts_progress_bar)
    ProgressBar mAlertsProgressBar;
    @BindView(R.id.alerts_empty_state)
    TextView mEmptyStateTextView;

    private Unbinder mUnbinder;
    private AlertsAdapter mAlertsAdapter;
    private ArrayList<Alert> mAlerts;

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String string);
    }


    public AlertsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_alerts, container, false);
        mUnbinder = ButterKnife.bind(this, rootView);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAlerts();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mAlertsRecyclerView.setLayoutManager(layoutManager);

        mAlertsAdapter = new AlertsAdapter();
        mAlertsRecyclerView.setAdapter(mAlertsAdapter);

        mAlertsAdapter.setAlerts(mAlerts);
        mAlertsAdapter.notifyDataSetChanged();

        loadAlerts();

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

    public void loadAlerts() {
        try {
            mEmptyStateTextView.setVisibility(View.GONE);

            RetrofitInterfaces.AlertsRetrofitInterface alertsRetrofitInterface = RetrofitUtils.getClient(getContext())
                    .create(RetrofitInterfaces.AlertsRetrofitInterface.class);

            final Call<ArrayList<Alert>> alertsArrayListCall = alertsRetrofitInterface.getAlerts();

            alertsArrayListCall.enqueue(new Callback<ArrayList<Alert>>() {
                @Override
                public void onResponse(Call<ArrayList<Alert>> call, Response<ArrayList<Alert>> response) {
                    int responseCode = response.code();

                    if (response.isSuccessful()) {
                        postDataLoad(true, "");
                        mAlerts = response.body();
                        mAlertsAdapter.setAlerts(mAlerts);
                        mAlertsAdapter.notifyDataSetChanged();
                    } else {
                        mEmptyStateTextView.setText(getString(R.string.no_connection));
                        postDataLoad(false, getString(R.string.load_data_failed));
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Alert>> call, Throwable t) {
                    postDataLoad(false, getString(R.string.load_data_failed));
                }
            });
        } catch (NoConnectivityException e) {
            postDataLoad(false, getString(R.string.load_data_failed));
            Log.e(LOG_TAG, getString(R.string.load_data_failed));
        }
    }

    public void postDataLoad(boolean isLoadSuccessful, String message) {
        if (mAlertsProgressBar.getVisibility() == View.VISIBLE) {
            mAlertsProgressBar.setVisibility(View.INVISIBLE);
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
