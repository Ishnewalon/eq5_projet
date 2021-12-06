package com.scott.veille2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.scott.veille2.adapter.ApplicationAdapter;
import com.scott.veille2.model.Offer;
import com.scott.veille2.model.OfferApplication;
import com.scott.veille2.model.User;
import com.scott.veille2.service.OfferService;

import java.util.List;

public class ApplicantsList extends AppCompatActivity {

    private ListView offerApplicationsView;

    private OfferService offerService;
    private List<OfferApplication> offerApplications;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applicants_list);

        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        offerApplicationsView = findViewById(R.id.offer_app_view);

        offerService = new OfferService(this);
        getOfferApplications();
    }

    private void getOfferApplications() {
        offerService.getApplicants((isSuccessful, response) -> {
            if (isSuccessful) {
                offerApplications = response;
                setOfferApplicationsList();
            }
        }, user.getEmail());
    }

    private void setOfferApplicationsList() {
        ApplicationAdapter applicationAdapter = new ApplicationAdapter(this, offerApplications);
        offerApplicationsView.setAdapter(applicationAdapter);
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

}