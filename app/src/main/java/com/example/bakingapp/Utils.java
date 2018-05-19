package com.example.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;

import com.example.bakingapp.objects.RecipesObject;
import com.google.gson.Gson;

import java.util.List;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return (connectivityManager.getActiveNetworkInfo() != null) && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public static RecipesObject[] parseRecipesJSON(String json) {
        Gson gson = new Gson();
        RecipesObject[] recipes = gson.fromJson(json, RecipesObject[].class);
        return recipes;
    }
}
