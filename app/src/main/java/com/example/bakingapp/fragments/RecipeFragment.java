package com.example.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.Utils;
import com.example.bakingapp.activities.StepsActivity;
import com.example.bakingapp.adapters.RecipeAdapter;
import com.example.bakingapp.objects.Ingredient;
import com.example.bakingapp.objects.RecipesObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RecipeFragment extends Fragment implements RecipeAdapter.ListItemClickListener {

    private static final String TAG = "RecipeFragment";
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.tv_no_internet)
    TextView noInternetTextView;

    RecipeAdapter recipeAdapter;


    public RecipeFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_recipe, container, false);

        boolean isPhone = getResources().getBoolean(R.bool.is_phone);

        ButterKnife.bind(this, view);
        recyclerView.setHasFixedSize(true);
        if (isPhone) {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        }
        recipeAdapter = new RecipeAdapter();
        recyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setClickListener(this);

        getRecipes();

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getRecipes();
            }
        });

        return view;
    }

    private void getRecipes() {
        if (Utils.isNetworkAvailable(getContext())) {
            getRecipes(getString(R.string.url_recipes));
        } else {
            setNoInternetTextView();
        }

        swipeLayout.setRefreshing(false);
    }

    public void getRecipes(String url) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setNoInternetTextView();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String bodyResponse = response.body().string();
                final RecipesObject[] recipeObjects = Utils.parseRecipesJSON(bodyResponse);
                final List<RecipesObject> recipesList = new ArrayList<>(Arrays.asList(recipeObjects));
                //Log.d(TAG, recipesList.toString());
                updtateIngredientsWidget(recipesList.get(0).getIngredients());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        noInternetTextView.setVisibility(View.INVISIBLE);
                        recipeAdapter.update(recipesList);
                    }
                });
            }
        });

        swipeLayout.setRefreshing(false);
    }

    private void setNoInternetTextView() {
        recipeAdapter.clear();
        noInternetTextView.setText(getString(R.string.no_internet));
        noInternetTextView.setVisibility(View.VISIBLE);
    }

    private void updtateIngredientsWidget(List<Ingredient> ingredients) {
        SharedPreferences.Editor editor = this.getActivity().
                getSharedPreferences(this.getActivity().
                        getString(R.string.prefs), Context.MODE_PRIVATE).edit();
        editor.putString("Ingredients", Utils.createIngredientsList(ingredients));
        editor.apply();
    }

    @Override
    public void onListItemClick(int clickedItemIndex, RecipesObject recipe) {
        Intent intent = new Intent(getContext(), StepsActivity.class);
        intent.putExtra(getString(R.string.intent_recipe_object), recipe);
        startActivity(intent);
    }
}
