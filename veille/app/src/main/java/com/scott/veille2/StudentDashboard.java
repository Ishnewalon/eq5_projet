package com.scott.veille2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.scott.veille2.model.User;

public class StudentDashboard extends AppCompatActivity {

    private TextView studentName;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        studentName = findViewById(R.id.student_name);

        studentName.setText(getString(R.string.student_name, user.getLastName(), user.getFirstName()));
    }

    public void viewOffers(View view) {
        startActivity(new Intent(StudentDashboard.this, OfferList.class));
    }

    public void logout(View view) {
        RequestSingleton.getInstance(this.getApplicationContext()).clearUser();
        finish();
    }
}