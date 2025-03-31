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
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mktechnified.logskeeper.R;
import com.mktechnified.logskeeper.models.LogModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditLogBottomSheet extends BottomSheetDialogFragment {
    private EditText etLogTitle, etLogDescription;
    private Button btnUpdate;
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.log_edit_modal, container, false);
        btnUpdate = view.findViewById(R.id.btnUpdate);

        etLogTitle = view.findViewById(R.id.etLogTitle);
        etLogDescription = view.findViewById(R.id.etLogDescription);

        firestore = FirebaseFirestore.getInstance();

        btnUpdate.setOnClickListener(view1 -> updateLog());

        return view;
    }

    private String extractHashtags() {
        String inputText = etLogDescription.getText().toString();
        StringBuilder tags = new StringBuilder();

        Pattern pattern = Pattern.compile("#\\w+");
        Matcher matcher = pattern.matcher(inputText);

        ArrayList<String> hashtags = new ArrayList<>();
        while (matcher.find()) {
            hashtags.add(matcher.group());
        }

        for (String hashtag : hashtags) {
            tags.append(hashtag).append(" ");
        }

        return tags.toString().trim();
    }

    private void updateLog() {
        String logTitle = etLogTitle.getText().toString().trim();
        String logDescription = etLogDescription.getText().toString().trim();

        if (logTitle.isEmpty() || logDescription.isEmpty()) {
            Toast.makeText(getContext(), "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        String logHashTags = extractHashtags();
        String userUid = getCurrentUserUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Generate Firestore Timestamp
        Timestamp currentTimestamp = Timestamp.now();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String formattedTimestamp = sdf.format(currentTimestamp.toDate());

        // 🔥 Generate custom logID
        String logID = logTitle + " " + logHashTags + " " + formattedTimestamp;

        // Create log model with timestamp
        LogModel logModel = new LogModel(logTitle, logDescription, logHashTags);
        logModel.setLogID(logTitle+" "+logHashTags+" "+formattedTimestamp);

        // 🔥 Manually use logID as Firestore document ID
        db.collection("users").document(userUid).collection("logs").document(logID)
                .set(logModel)
                .addOnSuccessListener(aVoid -> Toast.makeText(getContext(), "Log Added!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to add log: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }



    public String getCurrentUserUid() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        return (user != null) ? user.getUid() : null;
    }
}
