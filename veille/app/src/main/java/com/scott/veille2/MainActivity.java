package com.scott.veille2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.scott.veille2.service.IRequestListener;
import com.scott.veille2.service.LoginService;

public class MainActivity extends AppCompatActivity {

    private LoginService loginService;
    private EditText username;
    private EditText password;
    private TabLayout tabLayout;

    private final String[] userTypes = {
            "student",
            "monitor",
            "manager"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginService = new LoginService(this, setupLoginListener());

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        tabLayout = findViewById(R.id.tabLayout);
    }

    private IRequestListener<String> setupLoginListener() {
        return (isSuccessful, message) -> {
            if (isSuccessful){
                switch (getUserType()){
                    case "student" :
                        startActivity(new Intent(MainActivity.this, StudentDashboard.class));
                        break;
                    case "monitor" :
                        startActivity(new Intent(MainActivity.this, MonitorDashboard.class));
                        break;
                    case "manager" :
                        startActivity(new Intent(MainActivity.this, ManagerDashboard.class));
                        break;
                }
            }
        };
    }

    private String getUserType() {
        return userTypes[tabLayout.getSelectedTabPosition()];
    }

    public void login(View view) {
        loginService.login(
                username.getText().toString(),
                password.getText().toString(),
                getUserType()
        );
    }
}