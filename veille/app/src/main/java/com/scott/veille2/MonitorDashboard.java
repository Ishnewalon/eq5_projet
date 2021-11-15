package com.scott.veille2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scott.veille2.model.User;

public class MonitorDashboard extends AppCompatActivity {

    private TextView monitorName;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_dashboard);

        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        monitorName = findViewById(R.id.monitor_name);

        monitorName.setText(getString(R.string.student_name, user.getLastName(), user.getFirstName()));
    }

    public void logout(View view) {
        RequestSingleton.getInstance(this.getApplicationContext()).clearUser();
        finish();
    }
}