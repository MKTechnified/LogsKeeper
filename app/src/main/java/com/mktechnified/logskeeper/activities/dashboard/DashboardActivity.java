package com.mktechnified.logskeeper.activities.dashboard;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mktechnified.logskeeper.R;
import com.mktechnified.logskeeper.bottomsheets.EditLogBottomSheet;

public class DashboardActivity extends AppCompatActivity {

    FloatingActionButton imgBtnAddLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        imgBtnAddLog = findViewById(R.id.fabAddLog);
        imgBtnAddLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddLogModal();
            }
        });


    }

    private void openAddLogModal() {
        // Implement the logic to open the Add Log modal
        // You can use a BottomSheetDialogFragment or a custom dialog here
        // For simplicity, let's assume you have a fragment for the modal
        EditLogBottomSheet addLogModalFragment = new EditLogBottomSheet();
        addLogModalFragment.show(getSupportFragmentManager(), "EditLogBottomSheet");
    }

}
