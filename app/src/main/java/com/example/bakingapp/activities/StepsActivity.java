package com.example.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.bakingapp.R;
import com.example.bakingapp.fragments.RecipeFragment;
import com.example.bakingapp.fragments.StepsFragment;
import com.example.bakingapp.objects.RecipesObject;

import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity {

    private static final String TAG = "StepsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();


        Intent i = getIntent();
        if (i != null && i.hasExtra(getString(R.string.intent_recipe_object))) {
            RecipesObject recipe = i.getParcelableExtra(getString(R.string.intent_recipe_object));
            Log.d(TAG, recipe.toString());
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.intent_recipe_object), recipe);
            StepsFragment stepsFragment = new StepsFragment();
            stepsFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .add(R.id.master_fragment, stepsFragment).commit();

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(recipe.getName());
            }
        }



    }
}
