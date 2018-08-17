package com.example.bakingapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.example.bakingapp.R;
import com.example.bakingapp.fragments.VideoFragment;
import com.example.bakingapp.objects.Step;

import butterknife.ButterKnife;

public class VideoActivity extends AppCompatActivity {

    private static final String TAG = "VideoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();


        Intent i = getIntent();
        if (i != null && i.hasExtra(getString(R.string.intent_step_object))) {
            Step step = i.getParcelableExtra(getString(R.string.intent_step_object));

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setTitle(step.getShortDescription());
            }

            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.intent_step_object), step);
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.video_fragment, videoFragment).commit();
        }


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
