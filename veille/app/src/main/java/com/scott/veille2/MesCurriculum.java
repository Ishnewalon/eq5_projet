package com.scott.veille2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.scott.veille2.adapter.CurriculumAdapter;
import com.scott.veille2.adapter.OfferAdapter;
import com.scott.veille2.model.Curriculum;
import com.scott.veille2.model.Offer;
import com.scott.veille2.model.StudentCurriculumDTO;
import com.scott.veille2.model.User;
import com.scott.veille2.service.CurriculumService;
import com.scott.veille2.service.OfferService;

import java.util.List;

public class MesCurriculum extends AppCompatActivity {

    private ListView listView_cv;
    private TextView principal_name;

    private CurriculumService curriculumService;
    private StudentCurriculumDTO curriculumDTO;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mes_curriculums);

        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        listView_cv = findViewById(R.id.listview_cv);
        principal_name = findViewById(R.id.principal_name);

        curriculumService = new CurriculumService(this);
        getCurriculums();
    }

    private void setPrincipal() {
        principal_name.setText(curriculumDTO.getPrincipal() == null ?
                "Aucun C.V. de choisi" : curriculumDTO.getPrincipal().getName());
    }

    private void getCurriculums() {
        curriculumService.getAllCvs(((isSuccessful, response) -> {
            if (isSuccessful){
                curriculumDTO = response;
                setCurriculumList();
                setPrincipal();
            }else{
                finish();
            }
        }), user.getId());
    }

    private void setCurriculumList() {
        CurriculumAdapter curriculumAdapter = new CurriculumAdapter(this.getApplicationContext(), curriculumDTO.getCurriculumList());
        listView_cv.setAdapter(curriculumAdapter);

        listView_cv.setOnItemLongClickListener((parent, view, position, id) -> {
            Curriculum curriculum = curriculumDTO.getCurriculumList().get(position);
            curriculumService.setPrincipal(((isSuccessful, response) -> {
                if (isSuccessful){
                    Toast.makeText(this.getApplicationContext(), "Vous avez choisi votre C.V. principal!",  Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this.getApplicationContext(), "Impossible de choisir ce C.V.!",  Toast.LENGTH_SHORT).show();
                }
            }), user.getId(), curriculum.getId());
            return true;
        });
    }
}