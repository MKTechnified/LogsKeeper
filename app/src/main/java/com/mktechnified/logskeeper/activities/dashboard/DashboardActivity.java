package com.mktechnified.logskeeper.activities.dashboard;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mktechnified.logskeeper.R;
import com.mktechnified.logskeeper.adapters.LogAdapter;
import com.mktechnified.logskeeper.bottomsheets.EditLogBottomSheet;
import com.mktechnified.logskeeper.managers.LogManager;
import com.mktechnified.logskeeper.models.LogModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView logRecyclerView;
    private LogAdapter logAdapter;
    private List<LogModel> logList;
    private LogManager logManager;

    private FloatingActionButton fabAddLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        logRecyclerView = findViewById(R.id.logRecyclerView);
        logRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fabAddLog = findViewById(R.id.fabAddLog);
        logList = new ArrayList<>();
        logAdapter = new LogAdapter(logList);
        logRecyclerView.setAdapter(logAdapter);

        logManager = new LogManager();
        loadLogs("yourUniqueUserID"); // Replace with actual User ID

        fabAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditLogBottomSheet editLogBottomSheet = new EditLogBottomSheet();
                editLogBottomSheet.show(getSupportFragmentManager(), editLogBottomSheet.getTag());
            }
        });

    }

    private void loadLogs(String uniqueID) {
        logManager.getLogs(uniqueID, new LogManager.LogCallback() {
            @Override
            public void onCallback(List<LogModel> logs) {
                if (logs != null) {
                    logList.clear();
                    logList.addAll(logs);
                    logAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}

