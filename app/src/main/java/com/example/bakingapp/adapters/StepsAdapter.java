package com.example.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.objects.Ingredient;
import com.example.bakingapp.objects.Step;

import java.util.ArrayList;
import java.util.List;


public class StepsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = StepsAdapter.class.getName();
    private static final int TYPE_FIRST_ITEM = 0;
    private static final int TYPE_ITEM = 1;

    private ListItemClickListener mOnClickListener;
    private List<Step> steps;
    private List<Ingredient> ingredients;

    public StepsAdapter() {
        steps = new ArrayList<>();
        ingredients = new ArrayList<>();
    }

    public void setClickListener(ListItemClickListener itemClickListener) {
        this.mOnClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForFirstItem = R.layout.list_item_recipe;
        int layoutIdForListItem = R.layout.list_item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        switch (viewType) {
            case TYPE_FIRST_ITEM:
                final View view = inflater.inflate(layoutIdForFirstItem, viewGroup, shouldAttachToParentImmediately);
                return new IngredientsHolder(view);
            case TYPE_ITEM:
                final View view2 = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
                return new StepHolder(view2);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_FIRST_ITEM:
                IngredientsHolder ingredientsHolder = (IngredientsHolder) holder;
                String ingredient = ingredients.get(position).getIngredient();
                String quantity = ingredients.get(position).getQuantity()+" "+ingredients.get(position).getMeasure();
                ingredientsHolder.imageTv.setText(quantity);
                ingredientsHolder.nameTv.setText(ingredient);
                break;
            case TYPE_ITEM:
                StepHolder stepHolder = (StepHolder) holder;
                String shortDescription = steps.get(position).getShortDescription();
                String description = steps.get(position).getShortDescription();
                stepHolder.imageTv.setText(description);
                stepHolder.nameTv.setText(shortDescription);
                break;
        }
    }


    @Override
    public int getItemCount() {
        return steps == null ? 0 : steps.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_FIRST_ITEM;
        } else return TYPE_ITEM;
    }

    public void updateSteps(List<Step> items) {
        Log.d(TAG, "Update: " + items.size());
        if (items != null && items.size() > 0) {
            steps.clear();
            steps.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void updateIngredients(List<Ingredient> items) {
        Log.d(TAG, "Update: " + items.size());
        if (items != null && items.size() > 0) {
            ingredients.clear();
            ingredients.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        steps.clear();
        ingredients.clear();
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex, Step step);

        void onIngredientsListItemClick(int clickedItemIndex, Ingredient ingredient);
    }

    class IngredientsHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView nameTv;
        TextView imageTv;

        public IngredientsHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.author_tv);
            imageTv = itemView.findViewById(R.id.comment_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION) {
                Ingredient ingredient = ingredients.get(clickedPosition);
                mOnClickListener.onIngredientsListItemClick(clickedPosition, ingredient);
            }

        }
    }

    class StepHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView nameTv;
        TextView imageTv;

        public StepHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.author_tv);
            imageTv = itemView.findViewById(R.id.comment_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION) {
                Step step = steps.get(clickedPosition);
                mOnClickListener.onListItemClick(clickedPosition, step);
            }
        }
    }
}
