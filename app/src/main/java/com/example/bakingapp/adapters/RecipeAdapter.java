package com.example.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.R;
import com.example.bakingapp.objects.RecipesObject;

import java.util.ArrayList;
import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    private static final String TAG = RecipeAdapter.class.getName();
    private ListItemClickListener mOnClickListener;
    private List<RecipesObject> recipes;

    public RecipeAdapter() {
        recipes = new ArrayList<>();
    }

    public void setClickListener(ListItemClickListener itemClickListener) {
        this.mOnClickListener = itemClickListener;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.list_item_recipe;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        RecipeHolder viewHolder = new RecipeHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeHolder holder, int position) {
        String name = recipes.get(position).getName();
        String image = recipes.get(position).getImage();
        Log.d(TAG, name + " " + image);
        holder.imageTv.setText(image);
        holder.nameTv.setText(name);
    }


    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }


    public void update(List<RecipesObject> items) {
        Log.d(TAG,"Update: "+ items.size());
        if (items != null && items.size() > 0) {
            recipes.clear();
            recipes.addAll(items);
            notifyDataSetChanged();
        }
    }

    public void clear() {
        recipes.clear();
        notifyDataSetChanged();
    }

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex, RecipesObject recipe);
    }

    class RecipeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        TextView nameTv;
        TextView imageTv;

        public RecipeHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.author_tv);
            imageTv = itemView.findViewById(R.id.comment_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            if (clickedPosition != RecyclerView.NO_POSITION) {
                RecipesObject recipe = recipes.get(clickedPosition);
                mOnClickListener.onListItemClick(clickedPosition, recipe);
            }

        }
    }
}
