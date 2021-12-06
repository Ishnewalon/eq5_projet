package com.scott.veille2.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.JsonObject;
import com.scott.veille2.RequestSingleton;
import com.scott.veille2.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatisticService implements IServiceUtil {

    private final Context context;

    public StatisticService(Context context) {
        this.context = context;
    }

    public void getAllStudents(IRequestListener<List<User>> listener) {
        String url = API_URL + "/student";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    listener.processFinished(true, buildListFromJson(response));
                },
                error -> {
                    Toast.makeText(context, "Impossible de chercher la liste des etudiants!", Toast.LENGTH_SHORT).show();
                    listener.processFinished(false, null);
                });

        RequestSingleton
                .getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonArrayRequest);
    }

    private List<User> buildListFromJson(JSONArray jsonArray) {
        List<User> users = new ArrayList<>();
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                users.add(buildUserFromJson(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return users;
    }

    private User buildUserFromJson(JSONObject jsonObject) {
        try {
            return new User(
                    jsonObject.getLong("id"),
                    jsonObject.getString("lastName"),
                    jsonObject.getString("firstName"),
                    jsonObject.getString("email"),
                    jsonObject.getString("phone")
            );
        } catch (JSONException e) {
            return null;
        }
    }
}
