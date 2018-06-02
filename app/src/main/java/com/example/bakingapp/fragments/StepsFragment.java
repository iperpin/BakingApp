package com.example.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.activities.StepsActivity;
import com.example.bakingapp.activities.VideoActivity;
import com.example.bakingapp.adapters.RecipeAdapter;
import com.example.bakingapp.adapters.StepsAdapter;
import com.example.bakingapp.objects.Ingredient;
import com.example.bakingapp.objects.RecipesObject;
import com.example.bakingapp.objects.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsFragment extends Fragment implements StepsAdapter.ListItemClickListener {

    private static final String TAG = "StepsFragment";
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_internet)
    TextView noInternetTextView;

    StepsAdapter stepsAdapter;

    public StepsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, view);

        RecipesObject recipe = getArguments().getParcelable(getString(R.string.intent_recipe_object));

        String ingredientsList = createIngredientsList(recipe.getIngredients());
        Log.d(TAG, ingredientsList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        stepsAdapter = new StepsAdapter();
        recyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setClickListener(this);

        stepsAdapter.updateIngredients(recipe.getIngredients());
        stepsAdapter.updateSteps(recipe.getSteps());

        return view;
    }

    private String createIngredientsList(List<Ingredient> ingredients) {
        String concatIngredients = "";
        for (int i = 0; i < ingredients.size(); i++) {
            concatIngredients += ingredients.get(i).getIngredient() + " - " + ingredients.get(i).getQuantity() + " " + ingredients.get(i).getMeasure() + "\n";
        }
        return concatIngredients;
    }


    @Override
    public void onListItemClick(int clickedItemIndex, Step step) {
        Intent intent = new Intent(getContext(), VideoActivity.class);
        intent.putExtra(getString(R.string.intent_step_object), step);
        startActivity(intent);
    }
}
