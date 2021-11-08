package com.scott.veille2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.scott.veille2.model.Offer;
import com.scott.veille2.model.User;
import com.scott.veille2.service.OfferService;

import java.util.List;

public class OfferList extends AppCompatActivity {

    ListView offerList;

    OfferService offerService;
    List<Offer> offers;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

        user = RequestSingleton.getInstance(this.getApplicationContext()).getUser();
        if (user == null)
            finish();

        offerService = new OfferService(this);
        getOffers();

        offerList = findViewById(R.id.offer_list);
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
        ArrayAdapter<Offer> arrayAdapter = new ArrayAdapter<Offer>(this, android.R.layout.simple_list_item_1, offers);
        offerList.setAdapter(arrayAdapter);
        offerList.setOnItemLongClickListener((parent, view, position, id) -> {
            Offer offer = offers.get(position);
            offerService.applyOnOffer(((isSuccessful, response) -> {
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