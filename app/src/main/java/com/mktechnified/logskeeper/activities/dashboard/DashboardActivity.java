package com.mktechnified.logskeeper.activities.dashboard;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
    }

}
