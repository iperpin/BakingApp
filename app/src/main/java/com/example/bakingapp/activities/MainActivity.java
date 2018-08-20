package com.example.bakingapp.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.bakingapp.R;
import com.example.bakingapp.fragments.RecipeFragment;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeFragment recipeFragment = new RecipeFragment();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_fragment, recipeFragment).commit();
    }
}
