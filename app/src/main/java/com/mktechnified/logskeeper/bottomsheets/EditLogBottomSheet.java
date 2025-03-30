package com.mktechnified.logskeeper.bottomsheets;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mktechnified.logskeeper.R;

import java.util.HashMap;
import java.util.Map;

public class EditLogBottomSheet extends BottomSheetDialogFragment {
    private EditText etLogTitle, etLogDescription, etLogType;
    private Button btnUpdate;
    private FirebaseFirestore firestore;
    private String logId; // Firestore Document ID

    public EditLogBottomSheet(String logId) {
        this.logId = logId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_edit_modal, container, false);

        etLogTitle = view.findViewById(R.id.etLogTitle);
        etLogDescription = view.findViewById(R.id.etLogDescription);
        btnUpdate = view.findViewById(R.id.btnUpdate);
        firestore = FirebaseFirestore.getInstance();

        loadLogDetails(); // Load existing log data

        btnUpdate.setOnClickListener(v -> updateLog());

        return view;
    }

    private void loadLogDetails() {
        firestore.collection("logs").document(logId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        etLogTitle.setText(documentSnapshot.getString("title"));
                        etLogDescription.setText(documentSnapshot.getString("description"));
                        etLogType.setText(documentSnapshot.getString("type"));
                    }
                });
    }

    private void updateLog() {
        String newTitle = etLogTitle.getText().toString().trim();
        String newDescription = etLogDescription.getText().toString().trim();
        String newType = etLogType.getText().toString().trim();

        if (newTitle.isEmpty() || newDescription.isEmpty() || newType.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("title", newTitle);
        updatedData.put("description", newDescription);
        updatedData.put("type", newType);

        firestore.collection("logs").document(logId)
                .update(updatedData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(getContext(), "Log updated successfully", Toast.LENGTH_SHORT).show();
                    dismiss(); // Close modal after update
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Update failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
