package com.scott.veille2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class MesCurriculum extends AppCompatActivity {

    ListView listView_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_curriculums);

        listView_cv = findViewById(R.id.listview_cv);
    }
}