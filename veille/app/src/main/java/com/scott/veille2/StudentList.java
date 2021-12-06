package com.scott.veille2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.scott.veille2.adapter.UserAdapter;
import com.scott.veille2.model.User;
import com.scott.veille2.service.StatisticService;

import java.util.List;

public class StudentList extends AppCompatActivity {

    private User user;
    private ListView studentListView;
    private StatisticService statisticService;

    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);
        
        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        studentListView = findViewById(R.id.student_list);

        statisticService = new StatisticService(this);
        getStudentList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.back) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getStudentList() {
        statisticService.getAllStudents((isSuccessful, response) -> {
            if (isSuccessful) {
                userList = response;
                setListView();
            }else {
                finish();
            }
        });
    }

    private void setListView() {
        UserAdapter userAdapter = new UserAdapter(this, userList);
        studentListView.setAdapter(userAdapter);
    }
}