package com.example.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.Utils;
import com.example.bakingapp.adapters.StepsAdapter;
import com.example.bakingapp.objects.RecipesObject;
import com.example.bakingapp.objects.Step;

import butterknife.BindView;
import butterknife.ButterKnife;


public class StepsFragment extends Fragment implements StepsAdapter.ListItemClickListener {

    private static final String TAG = "StepsFragment";
    @BindView(R.id.rv_steps)
    RecyclerView recyclerView;

    StepsAdapter stepsAdapter;

    private StepClickListener stepListener;

    public StepsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(
                R.layout.fragment_steps, container, false);

        ButterKnife.bind(this, view);

        String ingredientsList = "";
        RecipesObject recipe = null;
        if (getArguments() != null) {
            if (getArguments().getParcelable(getString(R.string.intent_recipe_object)) != null) {
                recipe = getArguments().getParcelable(getString(R.string.intent_recipe_object));

                ingredientsList = Utils.createIngredientsList(recipe.getIngredients());
            }
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(), 1));
        stepsAdapter = new StepsAdapter();
        recyclerView.setAdapter(stepsAdapter);
        stepsAdapter.setClickListener(this);

        stepsAdapter.updateIngredients(ingredientsList);
        if (recipe != null) {
            stepsAdapter.updateSteps(recipe.getSteps());
        }

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        stepListener = (StepClickListener) context;
    }

    @Override
    public void onListItemClick(int clickedItemIndex, Step step) {
//        Intent intent = new Intent(getContext(), VideoActivity.class);
//        intent.putExtra(getString(R.string.intent_step_object), step);
//        startActivity(intent);
        stepListener.onStepClickListener(step);
    }

    public interface StepClickListener {
        void onStepClickListener(Step step);
    }
}
