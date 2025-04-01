package com.mktechnified.logskeeper.activities.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.mktechnified.logskeeper.R;
import com.mktechnified.logskeeper.adapters.LogAdapter;
import com.mktechnified.logskeeper.bottomsheets.EditLogBottomSheet;
import com.mktechnified.logskeeper.models.LogModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LogAdapter logAdapter;
    private List<LogModel> logList;
    private FirebaseFirestore firestore;
    private FirebaseUser currentUser;
    private FloatingActionButton fabAddLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView = findViewById(R.id.logRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        logList = new ArrayList<>();
        logAdapter = new LogAdapter(logList, this);
        recyclerView.setAdapter(logAdapter);

        firestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        fabAddLog = findViewById(R.id.fabAddLog);

        if (currentUser != null) {
            loadLogs();
        } else {
            Toast.makeText(this, "User not logged in!", Toast.LENGTH_SHORT).show();
        }

        fabAddLog.setOnClickListener(view -> {
            EditLogBottomSheet editLogBottomSheet = new EditLogBottomSheet();
            editLogBottomSheet.show(getSupportFragmentManager(), editLogBottomSheet.getTag());
        });
    }

    private void loadLogs() {
        if (currentUser == null) return;

        String userId = currentUser.getUid();
        firestore.collection("users").document(userId).collection("logs")
                .orderBy("logTimestamp", Query.Direction.DESCENDING)
                .addSnapshotListener((value, error) -> {
                    if (error != null) {
                        Log.e("Firestore Error", error.getMessage());
                        return;
                    }

                    if (value != null) {
                        logList.clear();
                        for (DocumentSnapshot document : value.getDocuments()) {
                            LogModel log = document.toObject(LogModel.class);
                            if (log != null) {
                                if (document.contains("logTimestamp")) {
                                    log.setLogTimestamp(document.getTimestamp("logTimestamp"));
                                }
                                logList.add(log);
                            }
                        }
                        logAdapter.notifyDataSetChanged();
                    }
                });
    }
}
