package com.scott.veille2.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.scott.veille2.RequestSingleton;

public class LoginService implements IServiceUtil {

    private final Context context;

    public LoginService (Context context) {
        this.context = context;
    }

    public void login(String email, String password, String userType) {
        String url = API_URL + "/" + userType + "/" + email + "/" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> Toast.makeText(context, "Vous etes connecter!",  Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Imposible de vous connecter!",  Toast.LENGTH_SHORT).show());

        RequestSingleton
                .getInstance(context.getApplicationContext())
                .addToRequestQueue(jsonObjectRequest);
    }
}
