package com.example.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.Utils;
import com.example.bakingapp.objects.Ingredient;

public class GridWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }
}

class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    Context context;
    Ingredient[] ingredientsList;

    public GridRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences prefs = context.getSharedPreferences(context.getString(R.string.prefs), Context.MODE_PRIVATE);
        String ingredients = prefs.getString("Ingredients", "");
        if (ingredients!=null && !ingredients.equalsIgnoreCase("")) {
            Ingredient[] ingredientsList = Utils.parseIngredientsJSON(ingredients);
            this.ingredientsList = ingredientsList;
        }
    }

    @Override
    public void onDestroy() {
        this.ingredientsList = null;
    }

    @Override
    public int getCount() {
        if (this.ingredientsList == null) {
            return 0;
        } else {
            return this.ingredientsList.length;
        }
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (ingredientsList == null) {
            return null;
        }

        String ingredient = this.ingredientsList[position].getIngredient();
        String quantity = String.valueOf(this.ingredientsList[position].getQuantity());
        String measure = this.ingredientsList[position].getMeasure();

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_provider);

        views.setTextViewText(R.id.appwidget_text, Utils.ucFirst(ingredient) + " " + quantity + " " + measure);
        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
