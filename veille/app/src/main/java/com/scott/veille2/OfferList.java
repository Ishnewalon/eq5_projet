package com.scott.veille2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.scott.veille2.adapter.OfferAdapter;
import com.scott.veille2.model.Offer;
import com.scott.veille2.model.User;
import com.scott.veille2.service.OfferService;

import java.util.List;

public class OfferList extends AppCompatActivity {

    private ListView offerList;

    private OfferService offerService;
    private List<Offer> offers;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        offerList = findViewById(R.id.offer_list);

        offerService = new OfferService(this);
        getOffers();
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

    private void getOffers() {
        offerService.getValidOffers(((isSuccessful, response) -> {
            if (isSuccessful){
                offers = response;
                setOfferList();
            }else{
                finish();
            }
        }));
    }

    private void setOfferList() {
        OfferAdapter offerAdapter = new OfferAdapter(this.getApplicationContext(), offers);
        offerList.setAdapter(offerAdapter);

        offerList.setOnItemLongClickListener((parent, view, position, id) -> {
            Offer offer = offers.get(position);
            offerService.applyOnOffer(((isSuccessful, response) -> {
                System.out.println(response);
                if (isSuccessful){
                    Toast.makeText(this.getApplicationContext(), "Vous avez appliquer sur l'offre!",  Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this.getApplicationContext(), "Impossible d'appliquer sur cette offre!",  Toast.LENGTH_SHORT).show();
                }
            }), user.getId(), offer.getId());
            return true;
        });
    }



}