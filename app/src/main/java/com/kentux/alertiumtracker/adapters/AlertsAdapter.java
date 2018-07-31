package com.kentux.alertiumtracker.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kentux.alertiumtracker.R;
import com.kentux.alertiumtracker.models.Alert;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.AlertsViewHolder> {
    private Context mContext;
    private ArrayList<Alert> mAlerts;

    @NonNull
    @Override
    public AlertsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(R.layout.alerts_list_item, parent, false);
        return new AlertsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlertsViewHolder holder, final int position) {
        if (position < getItemCount()) {
            Alert alerts = mAlerts.get(position);
            String alertRewardThumb = alerts.getmAlertThumbnail();
            String alertMissionNode = alerts.getmAlertMissionNode();
            final int minLevel = alerts.getmAlertMinEnemyLevel();
            final int maxLevel = alerts.getmAlertMaxEnemyLevel();
            String alertMinMaxLevel = mContext.getString(R.string.enemy_level)
                    + " "
                    + minLevel
                    + "-"
                    + maxLevel;
            String alertMissionType = alerts.getmAlertMissionType();
            String alertMissionFaction = alerts.getmAlertMissionFaction();
            String alertMissionTypeAndFaction = alertMissionType
                    + " - "
                    + alertMissionFaction;
            String alertMissionReward = alerts.getmAlertMissionReward();
            String alertMissionRewardString = mContext.getString(R.string.mission_reward)
                    + " "
                    + alertMissionReward;
            String alertTimeLeft = alerts.getmTimeLeft().trim();
            /*int minutes = Integer.parseInt(alertTimeLeft.subSequence(0,
                    alertTimeLeft.indexOf("m")).toString());
            int seconds = Integer.parseInt(alertTimeLeft.subSequence(alertTimeLeft.indexOf("m") + 1,
                            alertTimeLeft.indexOf("s")).toString().trim());
            int totalMillis = (minutes * 60000) + (seconds * 1000);*/
            String reverseTimeLeft = new StringBuffer(alertTimeLeft).reverse().toString();
            String[] segments = reverseTimeLeft.split(" ");
            int size = segments.length;
            int days = 0;
            int hours = 0;
            int minutes = 0;
            int seconds = 0;
            switch (size) {
                case 4:
                    String sDays = new StringBuffer(segments[3]).reverse().toString().trim();
                    days = Integer.parseInt(sDays.substring(0, sDays.length() - 1));
                case 3:
                    String sHours = new StringBuffer(segments[2]).reverse().toString().trim();
                    hours = Integer.parseInt(sHours.substring(0, sHours.length() - 1));
                case 2:
                    String sMinutes = new StringBuffer(segments[1]).reverse().toString().trim();
                    minutes = Integer.parseInt(sMinutes.substring(0, sMinutes.length() - 1));
                case 1:
                    String sSeconds = new StringBuffer(segments[0]).reverse().toString().trim();
                    seconds = Integer.parseInt(sSeconds.substring(0, sSeconds.length() - 1));
                    break;
            }

            int totalMillis = (days * 24 * 3600 * 1000)
                    + (hours * 3600 * 1000)
                    + (minutes * 60000)
                    + (seconds * 1000);



            if (!alertRewardThumb.isEmpty()) {
                Picasso.get()
                        .load(alertRewardThumb)
                        .into(holder.mRewardThumbnail);
            }

            if (!alertMissionNode.isEmpty()) {
                holder.mMissionNodeText.setText(alertMissionNode);
            }

            if (minLevel != 0 && maxLevel != 0) {
                holder.mMinMaxEnemyLevelText.setText(alertMinMaxLevel);
            }

            if (!alertMissionType.isEmpty() && !alertMissionFaction.isEmpty()) {
                holder.mMissionTypeAndFactionText.setText(alertMissionTypeAndFaction);
            }

            if (!alertMissionReward.isEmpty()) {
                holder.mMissionRewardText.setText(alertMissionRewardString);
            }

            if (!alertTimeLeft.isEmpty()) {
                new CountDownTimer(totalMillis, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        String timeFormatted = String.format(Locale.getDefault(), "%d m %d sec",
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                        );
                        holder.mTimeLeftText.setText(timeFormatted);
                    }

                    @Override
                    public void onFinish() {
                        removeAt(holder.getAdapterPosition());
                    }
                };
            }
        }
    }

    @Override
    public int getItemCount() {
        return (mAlerts == null) ? 0 : mAlerts.size();
    }

    public AlertsAdapter() {

    }

    public class AlertsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reward_thumb)
        ImageView mRewardThumbnail;
        @BindView(R.id.mission_node_text_view)
        TextView mMissionNodeText;
        @BindView(R.id.min_max_level_text)
        TextView mMinMaxEnemyLevelText;
        @BindView(R.id.mission_type_faction_text)
        TextView mMissionTypeAndFactionText;
        @BindView(R.id.reward_text_view)
        TextView mMissionRewardText;
        @BindView(R.id.faction_thumb)
        ImageView mFactionThumbnail;
        @BindView(R.id.alert_time_left)
        TextView mTimeLeftText;

        public AlertsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setAlerts(ArrayList<Alert> alerts) {
        this.mAlerts = alerts;
        notifyDataSetChanged();
    }

    public void removeAt(int position) {
        mAlerts.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mAlerts.size());
    }
}
