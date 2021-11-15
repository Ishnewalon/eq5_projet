package com.scott.veille2;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.view.View;
import android.widget.TextView;

import com.scott.veille2.model.User;
import com.scott.veille2.service.CurriculumService;

public class StudentDashboard extends AppCompatActivity {

    private TextView studentName;

    private User user;

    private CurriculumService curriculumService;
    private ActivityResultLauncher<Intent> arl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        studentName = findViewById(R.id.student_name);

        studentName.setText(getString(R.string.student_name, user.getLastName(), user.getFirstName()));

        curriculumService = new CurriculumService(this.getApplicationContext());

        createActivityLauncher();
    }

    private void createActivityLauncher() {
        this.arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent data = result.getData();

                curriculumService.uploadFile(data.getData(), user.getId());
            }
        });
    }

    public void openFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");

        this.arl.launch(intent);
    }

    public void viewOffers(View view) {
        startActivity(new Intent(StudentDashboard.this, OfferList.class));
    }

    public void logout(View view) {
        RequestSingleton.getInstance(this.getApplicationContext()).clearUser();
        finish();
    }
}