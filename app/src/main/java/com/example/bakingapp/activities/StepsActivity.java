package com.example.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.bakingapp.R;
import com.example.bakingapp.fragments.StepsFragment;
import com.example.bakingapp.fragments.VideoFragment;
import com.example.bakingapp.objects.RecipesObject;
import com.example.bakingapp.objects.Step;

import butterknife.ButterKnife;

public class StepsActivity extends AppCompatActivity implements StepsFragment.StepClickListener {

    private static final String TAG = "StepsActivity";
    private boolean twoPane = false;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        ButterKnife.bind(this);

        fragmentManager = getSupportFragmentManager();

        Intent i = getIntent();
        if (i != null && i.hasExtra(getString(R.string.intent_recipe_object))) {

            RecipesObject recipe = i.getParcelableExtra(getString(R.string.intent_recipe_object));

            updateStepsFragment(recipe);

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(recipe.getName());
            }

            if (findViewById(R.id.video_fragment) != null) {
                twoPane = true;
                Step step = recipe.getSteps().get(0);
                updateVideoFragment(step);

            } else {
                twoPane = false;
            }
        }

    }

    @Override
    public void onStepClickListener(Step step) {
        if (twoPane) {
            updateVideoFragment(step);
        } else {
            Intent intent = new Intent(this, VideoActivity.class);
            intent.putExtra(getString(R.string.intent_step_object), step);
            startActivity(intent);
        }
    }

    private void updateVideoFragment(Step step) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.intent_step_object), step);
        bundle.putBoolean(getString(R.string.initialize), true);
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.video_fragment, videoFragment).commit();
    }

    private void updateStepsFragment(RecipesObject recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(getString(R.string.intent_recipe_object), recipe);
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(R.id.steps_fragment, stepsFragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
