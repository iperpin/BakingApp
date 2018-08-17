package com.example.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.objects.Step;

import java.util.ArrayList;
import java.util.List;


public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "StepsAdapter";
    private static final int INGREDIENTS_ITEM = 0;
    private static final int STEPS_ITEM = 1;

    private ListItemClickListener mOnClickListener;
    private List<Step> steps;
    private String ingredients;
    private Context context;

    public StepsAdapter() {
        steps = new ArrayList<>();
    }

    public void setClickListener(ListItemClickListener itemClickListener) {
        this.mOnClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        context = viewGroup.getContext();
        int layoutIdForFirstItem = R.layout.list_item_ingredient;
        int layoutIdForListItem = R.layout.list_item_step;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        switch (viewType) {
            case INGREDIENTS_ITEM:
                final View view = inflater.inflate(layoutIdForFirstItem, viewGroup, shouldAttachToParentImmediately);
                return new IngredientsHolder(view);
            case STEPS_ITEM:
                final View view2 = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
                return new StepHolder(view2);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IngredientsHolder) {
            IngredientsHolder ingredientsHolder = (IngredientsHolder) holder;
            ingredientsHolder.ingredientsTagTv.setText(context.getString(R.string.ingredients));
            ingredientsHolder.ingredientsListTv.setText(ingredients.toString());
        } else if (holder instanceof StepHolder) {
            StepHolder stepHolder = (StepHolder) holder;
            String shortDescription = steps.get(position-1).getShortDescription();
            String description = steps.get(position-1).getDescription();
            stepHolder.stepDescTv.setText(description);
            stepHolder.stepResumeTv.setText(shortDescription);
        }
    }



    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return INGREDIENTS_ITEM;
        } else return STEPS_ITEM;
    }

    public void updateSteps(List<Step> items) {
        if (items != null && items.size() > 0) {
            steps.clear();
            steps.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void updateIngredients(String items) {
            ingredients=items;
            notifyDataSetChanged();
    }

    public void clear() {
        steps.clear();
        ingredients="";
        notifyDataSetChanged();
    }

public interface ListItemClickListener {
    void onListItemClick(int clickedItemIndex, Step step);
}

class IngredientsHolder extends RecyclerView.ViewHolder {

    TextView ingredientsTagTv;
    TextView ingredientsListTv;

    public IngredientsHolder(View itemView) {
        super(itemView);
        ingredientsTagTv = itemView.findViewById(R.id.ingredient_tag_tv);
        ingredientsListTv = itemView.findViewById(R.id.ingredient_list_tv);
    }

}

class StepHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    TextView stepResumeTv;
    TextView stepDescTv;

    public StepHolder(View itemView) {
        super(itemView);
        stepResumeTv = itemView.findViewById(R.id.step_resume_tv);
        stepDescTv = itemView.findViewById(R.id.step_desc_tv);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        if (clickedPosition != RecyclerView.NO_POSITION) {
            Step step = steps.get(clickedPosition-1);
            mOnClickListener.onListItemClick(clickedPosition-1, step);
        }
    }
}
}
