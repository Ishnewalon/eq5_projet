package com.scott.veille2.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.scott.veille2.RequestSingleton;
import com.scott.veille2.model.User;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginService implements IServiceUtil {

    private final Context context;
    private final IRequestListener listener;

    public LoginService (Context context, IRequestListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void login(String email, String password, String userType) {
        String url = API_URL + "/" + userType + "/" + email + "/" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Toast.makeText(context, "Vous etes connecter!",  Toast.LENGTH_SHORT).show();
                    RequestSingleton
                            .getInstance(context.getApplicationContext())
                            .setUser(buildUserFromJson(response));
                    System.out.println(response);
                    listener.processFinished(true, response.toString());
                },
                error -> {
                    Toast.makeText(context, "Imposible de vous connecter!",  Toast.LENGTH_SHORT).show();
                    listener.processFinished(false, error.getMessage());
                    System.out.println("potato" + error.toString());
                });

        RequestSingleton
                .getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);
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
