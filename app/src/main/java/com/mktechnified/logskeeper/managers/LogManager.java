package com.mktechnified.logskeeper.managers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mktechnified.logskeeper.models.LogModel;

import java.util.ArrayList;
import java.util.List;

public class LogManager {

    private static final String TAG = "LogManager";
    private FirebaseFirestore db;

    public LogManager() {
        db = FirebaseFirestore.getInstance();
    }

    public void getLogs(String uniqueID, final LogCallback callback) {
        CollectionReference logsRef = db.collection("users")
                .document(uniqueID)
                .collection("logs");

        logsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<LogModel> logList = new ArrayList<>();
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Fetch data fields from Firestore
                        String logTitle = document.getString("logTitle");
                        String logDescription = document.getString("logDescription");
                        String logHashTags = document.getString("logHashTags");

                        // Create LogModel object and add it to the list
                        LogModel log = new LogModel(logTitle, logDescription, logHashTags);
                        logList.add(log);
                    }
                    callback.onCallback(logList);
                } else {
                    Log.e(TAG, "Error getting logs: ", task.getException());
                    callback.onCallback(null);
                }
            }
        });
    }

    public interface LogCallback {
        void onCallback(List<LogModel> logList);
    }
}
