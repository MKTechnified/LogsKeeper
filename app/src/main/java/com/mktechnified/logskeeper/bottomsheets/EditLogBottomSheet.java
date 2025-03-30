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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mktechnified.logskeeper.R;
import com.mktechnified.logskeeper.models.LogModel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditLogBottomSheet extends BottomSheetDialogFragment {
    private EditText etLogTitle, etLogDescription, etLogType;
    private Button btnUpdate;
    private FirebaseFirestore firestore;
    private String logTimestamp; // Firestore Document ID


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_edit_modal, container, false);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLog();
            }
        });

        return view;
    }

    private void loadLogDetails() {
        firestore.collection("logs").document(logTimestamp).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        etLogTitle.setText(documentSnapshot.getString("title"));
                        etLogDescription.setText(documentSnapshot.getString("description"));
                    }
                });
    }
    private String extractHashtags() {
        String inputText = etLogDescription.getText().toString();
        String tags = "";

        // Extract hashtags using regular expression
        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(inputText);

        // ArrayList to store the extracted hashtags
        ArrayList<String> hashtags = new ArrayList<>();

        // Loop through the matcher results and add hashtags to the ArrayList
        while (matcher.find()) {
            hashtags.add(matcher.group());
        }

        // Convert ArrayList to a string array
        String[] hashtagArray = hashtags.toArray(new String[0]);

        // Print or use the extracted hashtags as needed
        for (String hashtag : hashtagArray) {
            Toast.makeText(requireContext(), "" + hashtag, Toast.LENGTH_SHORT).show();
            tags = hashtag;
        }

        return tags;
    }
    private void updateLog() {
        String logTitle = etLogTitle.getText().toString().trim();
        String logDescription = etLogDescription.getText().toString().trim();

        if (logTitle.isEmpty() || logDescription.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        LogModel logModel = new LogModel(logTitle, logDescription, extractHashtags());

        firestore.collection("users").document(Objects.requireNonNull(getCurrentUserUid()))
                .collection("logs").document(logModel.getLogID()).set(logModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(requireContext(), "Log Added", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(requireContext(), "Failed to add logs: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public String getCurrentUserUid() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid(); // Get the UID of the logged-in user
        } else {
            return null; // No user is logged in
        }
    }
}
