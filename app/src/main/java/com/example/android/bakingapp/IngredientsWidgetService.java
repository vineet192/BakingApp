package com.example.android.bakingapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Set;
public class IngredientsWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsWidgetItemFactory(getApplicationContext());
    }

    class IngredientsWidgetItemFactory implements RemoteViewsFactory {

        private final Context mContext;
        Set<String> mIngredientsSet;
        SharedPreferences mPreference;

        public IngredientsWidgetItemFactory(Context context)
        {
             mContext = context;
        }
        @Override
        public void onCreate() {
            mPreference = getSharedPreferences(getString(R.string.ingredients_shared_pref),MODE_PRIVATE);
        }

        @Override
        public void onDataSetChanged() {

            mIngredientsSet = mPreference.getStringSet(getString(R.string.ingredients_tag),null);
            Toast.makeText(getApplicationContext(), "WIDGET HAS BEEN UPDATED", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return mIngredientsSet.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(),R.layout.ingredients_widget_item);
            views.setTextViewText(R.id.ingredients_widget_text_item,mIngredientsSet.toArray()[i].toString());
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
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
