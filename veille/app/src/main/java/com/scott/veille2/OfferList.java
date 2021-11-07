package com.scott.veille2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.scott.veille2.model.Offer;
import com.scott.veille2.service.IRequestListener;
import com.scott.veille2.service.OfferService;

import org.json.JSONArray;

import java.util.List;

public class OfferList extends AppCompatActivity {

    ListView offerList;

    OfferService offerService;
    List<Offer> offers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_list);

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
    }



}