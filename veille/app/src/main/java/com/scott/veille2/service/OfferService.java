package com.scott.veille2.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.scott.veille2.RequestSingleton;
import com.scott.veille2.model.Curriculum;
import com.scott.veille2.model.Offer;
import com.scott.veille2.model.OfferAppDTO;
import com.scott.veille2.model.OfferApplication;
import com.scott.veille2.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
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

    public void applyOnOffer(IRequestListener<String> listener, long studentID, long offerID) {
        String url = API_URL + "/applications/apply";
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("idOffer", offerID);
            jsonObject.put("idStudent", studentID);
        }catch (JSONException e){
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                response -> {
                    listener.processFinished(true, response.toString());
                },
                error -> {
                    listener.processFinished(false, error.getMessage());
                });

        RequestSingleton
                .getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);
    }

    public void getApplicants(IRequestListener<List<OfferApplication>> listener, String email) {
        String url = API_URL + "/applications/applicants/" + email;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    listener.processFinished(true, buildOfferAppListFromJson(response));
                },
                error -> {
                    Toast.makeText(context, "Impossible de chercher la liste des applicants!",  Toast.LENGTH_SHORT).show();
                    listener.processFinished(false, null);
                });

        RequestSingleton
                .getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonArrayRequest);
    }

    private String getStudentNameFromCurriculum(JSONObject jsonObject) {
        String firstName = "";
        String lastName = "";
        try {
            firstName = jsonObject.getJSONObject("student").getString("firstName");
            lastName = jsonObject.getJSONObject("student").getString("lastName");
        } catch (JSONException e) {
            return null;
        }
        return lastName + ", " + firstName;
    }

    private List<OfferApplication> buildOfferAppListFromJson(JSONArray jsonArray) {
        List<OfferApplication> offerAppDTOList = new ArrayList<>();
        try {
            for(int i = 0; i< jsonArray.length(); i++){
                offerAppDTOList.add(buildOfferAppDtoFromJson(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return offerAppDTOList;
    }

    private OfferApplication buildOfferAppDtoFromJson(JSONObject jsonObject) {
        try {
            Offer offer = buildOfferFromJson(jsonObject.getJSONObject("offer"));
            String studentName = getStudentNameFromCurriculum(jsonObject.getJSONObject("curriculum"));
            String status = jsonObject.getString("status");
            return new OfferApplication(offer, studentName, status);
        } catch (JSONException e) {
            return null;
        }
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
