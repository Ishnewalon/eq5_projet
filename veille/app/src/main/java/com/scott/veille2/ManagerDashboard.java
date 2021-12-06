package com.scott.veille2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.scott.veille2.model.User;

public class ManagerDashboard extends AppCompatActivity {

    private TextView managerName;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);

        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        managerName = findViewById(R.id.manager_name);

        managerName.setText(getString(R.string.student_name, user.getLastName(), user.getFirstName()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.dashboard_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            RequestSingleton.getInstance(this.getApplicationContext()).clearUser();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void viewAllStudents(View view) {
        startActivity(new Intent(ManagerDashboard.this, StudentList.class));
    }
}