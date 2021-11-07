package com.scott.veille2.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.scott.veille2.RequestSingleton;
import com.scott.veille2.model.Offer;
import com.scott.veille2.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OfferService implements IServiceUtil {

    private final Context context;

    public OfferService (Context context) {
        this.context = context;
    }

    public void getValidOffers(IRequestListener<List<Offer>> listener) {
        String url = API_URL + "/offers/valid";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    listener.processFinished(true, buildListFromJson(response));
                },
                error -> {
                    Toast.makeText(context, "Imposible de chercher la liste des offres!",  Toast.LENGTH_SHORT).show();
                    listener.processFinished(false, null);
                });

        RequestSingleton
                .getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonArrayRequest);
    }

    private List<Offer> buildListFromJson(JSONArray jsonArray) {
        List<Offer> offers = new ArrayList<>();
        try {
            for(int i = 0; i< jsonArray.length(); i++){
                offers.add(buildOfferFromJson(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return offers;
    }

    private Offer buildOfferFromJson(JSONObject jsonObject) {
        try {
            return new Offer(
                    jsonObject.getLong("id"),
                    jsonObject.getString("department"),
                    jsonObject.getString("title"),
                    jsonObject.getString("description"),
                    jsonObject.getDouble("salary")
            );
        } catch (JSONException e) {
            return null;
        }
    }
}
