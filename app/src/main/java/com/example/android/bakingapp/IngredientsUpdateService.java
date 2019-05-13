package com.example.android.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

public class IngredientsUpdateService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS  = "updtae_ingredients";

    public IngredientsUpdateService()
    {
        super("IngredientsUpdateService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent!=null)
        {
            final String action = intent.getAction();
            if(action.equals(ACTION_UPDATE_INGREDIENTS))
            {
                handleUpdateIngredients();
            }
        }
    }

    private void handleUpdateIngredients()
    {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, IngredientsWidgetProvider.class));
        //Trigger data update to handle the GridView widgets and force a data refresh
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_ingredients_list_view);
        //Now update all widgets
        IngredientsWidgetProvider.updateIngredientWidgets(this,appWidgetManager,appWidgetIds);
    }

    public static void startActionUpdateIngredients(Context context)
    {
        Intent in = new Intent(context,IngredientsWidgetProvider.class);
        in.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(in);
    }
}
