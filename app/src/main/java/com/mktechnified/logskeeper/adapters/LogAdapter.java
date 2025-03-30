package com.mktechnified.logskeeper.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mktechnified.logskeeper.R;
import com.mktechnified.logskeeper.models.LogModel;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {

    private List<LogModel> logList;

    public LogAdapter(List<LogModel> logList) {
        this.logList = logList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_log, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogModel log = logList.get(position);
        holder.logTitle.setText(log.getLogTitle());
        holder.logDescription.setText(log.getLogDescription());
        holder.logTimestamp.setText("Timestamp: " + log.getLogTimestamp());
        holder.logHashTags.setText("Tags: " + log.getLogHashTags());
    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView logTitle, logDescription, logTimestamp, logHashTags;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            logTitle = itemView.findViewById(R.id.etLogTitle);
            logDescription = itemView.findViewById(R.id.etLogDescription);
        }
    }
}
