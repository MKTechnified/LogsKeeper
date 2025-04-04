package com.mktechnified.logskeeper.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mktechnified.logskeeper.R;
import com.mktechnified.logskeeper.models.LogModel;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewHolder> {
    private List<LogModel> logList;
    private Context context;

    public LogAdapter(List<LogModel> logList, Context context) {
        this.logList = logList;
        this.context = context;
    }

    @NonNull
    @Override
    public LogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_log, parent, false);
        return new LogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogViewHolder holder, int position) {
        LogModel log = logList.get(position);
        holder.tvTitle.setText(log.getLogTitle());
        holder.tvDescription.setText(log.getLogDescription());
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO Delete log from Firestore
                Toast.makeText(context, "Deleted!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public static class LogViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDescription;
        Button btnDelete;

        public LogViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvLogTitle);
            tvDescription = itemView.findViewById(R.id.tvLogDescription);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
