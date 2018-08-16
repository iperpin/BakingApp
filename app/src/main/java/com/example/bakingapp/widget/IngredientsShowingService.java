package com.example.bakingapp.widget;

import android.app.IntentService;
import android.app.Notification;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;

import com.example.bakingapp.R;

public class IngredientsShowingService extends IntentService{


    private static final String UPDATE_INGREDIENTS = "com.example.bakingapp.action.update_ingredients";

    public IngredientsShowingService() {
        super("IngredientsShowingService");
    }

    public static void startActionUpdateIngredients(Context context) {
        Intent intent = new Intent(context, IngredientsShowingService.class);
        intent.setAction(UPDATE_INGREDIENTS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(1, new Notification());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (UPDATE_INGREDIENTS.equals(action)) {
                handleUpdateIngredients();
            }
        }
    }

    private void handleUpdateIngredients() {

        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int widgetsIds[] = widgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
        widgetManager.notifyAppWidgetViewDataChanged(widgetsIds, R.id.widget_grid_view);
        RecipeWidgetProvider.updateIngredientsWidget(this, widgetManager, widgetsIds);
    }




}
