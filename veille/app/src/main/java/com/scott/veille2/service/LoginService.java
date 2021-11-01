package com.scott.veille2.service;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class LoginService {

    private static String API_URL = "http://10.0.0.36:8181";
    private Context context;
    private RequestQueue queue;

    public LoginService (Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    public void login(String email, String password, String userType) {
        String url = API_URL + "/" + userType + "/" + email + "/" + password;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    Toast.makeText(context, "Vous etes connecter!",  Toast.LENGTH_SHORT).show();
                }, error -> {
                    Toast.makeText(context, "Imposible de vous connecter!",  Toast.LENGTH_SHORT).show();
                });
        this.queue.add(jsonObjectRequest);
    }
}
