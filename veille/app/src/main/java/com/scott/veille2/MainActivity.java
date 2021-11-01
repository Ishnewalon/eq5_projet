package com.scott.veille2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.scott.veille2.service.LoginService;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity implements OnItemSelectedListener {

    private LoginService loginService;
    private EditText username;
    private EditText password;
    private Spinner userType;

    private String userSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginService = new LoginService(this);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        userType = findViewById(R.id.user_type);

        userSelection = "student";
        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.user_array , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter);
        userType.setOnItemSelectedListener(this);
    }

    public void login(View view) {
        loginService.login(
                username.getText().toString(),
                password.getText().toString(),
                userSelection);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        userSelection = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}