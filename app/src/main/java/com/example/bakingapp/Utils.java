package com.example.bakingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.example.bakingapp.objects.Ingredient;
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

    public static Ingredient[] parseIngredientsJSON(String json) {
        Gson gson = new Gson();
        Ingredient[] recipes = gson.fromJson(json, Ingredient[].class);
        return recipes;
    }

    public static String parseJSONIngredients(List<Ingredient> ingredients) {
        Gson gson = new Gson();
        String recipes = gson.toJson(ingredients);
        return recipes;
    }

    public static String ucFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        } else {
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }

    public static String createIngredientsList(List<Ingredient> ingredients) {
        String concatIngredients = "";
        for (int i = 0; i < ingredients.size(); i++) {
            concatIngredients += Utils.ucFirst(ingredients.get(i).getIngredient()) + " - " + ingredients.get(i).getQuantity() + " " + ingredients.get(i).getMeasure() + "\n";
        }
        return concatIngredients;
    }
}
