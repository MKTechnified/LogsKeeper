package com.mktechnified.logskeeper.activities.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mktechnified.logskeeper.R;
import com.mktechnified.logskeeper.adapters.LogAdapter;
import com.mktechnified.logskeeper.models.LogModel;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    private RecyclerView recyclerViewLogs;
    private LogAdapter logAdapter;
    private ArrayList<LogModel> logList;
    private FloatingActionButton fabAddLog;
    private FirebaseFirestore firestore;
    private CollectionReference logsCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerViewLogs = findViewById(R.id.recyclerViewLogs);
        fabAddLog = findViewById(R.id.fabAddLog);
        recyclerViewLogs.setLayoutManager(new LinearLayoutManager(this));
        logList = new ArrayList<>();
        logAdapter = new LogAdapter(logList, this);
        recyclerViewLogs.setAdapter(logAdapter);

        firestore = FirebaseFirestore.getInstance();
        logsCollection = firestore.collection("logs");

        loadLogs();

        fabAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewLog();
            }
        });
    }

    private void loadLogs() {
        logsCollection.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(DashboardActivity.this, "Error loading logs", Toast.LENGTH_SHORT).show();
                    return;
                }
                logList.clear();
                for (QueryDocumentSnapshot document : value) {
                    LogModel log = document.toObject(LogModel.class);
                    logList.add(log);
                }
                logAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addNewLog() {
        // TODO: Implement log addition logic (e.g., open a dialog or new activity)
        Toast.makeText(this, "Add Log Clicked", Toast.LENGTH_SHORT).show();
    }
}
