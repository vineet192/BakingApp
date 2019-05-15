package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.bakingapp.fragments.IngredientsStepsFragment;
import com.example.android.bakingapp.fragments.VideoFragment;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class IngredientsStepsActivity extends AppCompatActivity implements IngredientsStepsFragment.OnStepFragmentClickListener, VideoFragment.OnNextClickListener, VideoFragment.OnPrevclickListener {

    private IngredientsStepsFragment mIngredientStepFragment;
    private VideoFragment mVideoFragment;
    private static FragmentManager mManager;
    private ArrayList<Ingredient> mIngredients;
    private ArrayList<RecipeStep> mRecipeSteps;
    private int mIndex;
    private boolean mTwoPane;
    private Button mAddToWidgetButton;
    private static final String INGREDIENTS_SHARED_PREF = "ingredients_shared_preference";
    private static final String INGREDIENT_TAG = "ingredient";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_steps);
        mAddToWidgetButton = (Button) findViewById(R.id.add_to_widget_button);
        int test = getIntent().getIntExtra("index", 0);
        try {
            mIngredients = JsonParseUtils.getIngredients(new JSONArray(getIntent().getStringExtra("root_json")), getIntent().getIntExtra("index", 0));
            mRecipeSteps = JsonParseUtils.getRecipeSteps(new JSONArray(getIntent().getStringExtra("root_json")), getIntent().getIntExtra("index", 0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mManager = getSupportFragmentManager();
        if (findViewById(R.id.tablet_video_container) != null) {

            mTwoPane = true;

            //Setting up video and step description fragment
            mVideoFragment = new VideoFragment();
            mVideoFragment.setUrl(mRecipeSteps.get(mIndex).getVideoUrl());
            mVideoFragment.setDescription(mRecipeSteps.get(mIndex).getDescription());
            mVideoFragment.setTabletMode(true);
            mManager.beginTransaction()
                    .add(R.id.tablet_video_container, mVideoFragment)
                    .commit();


            //Setting up ingredients and steps fragment
            mIngredientStepFragment = new IngredientsStepsFragment();
            mIngredientStepFragment.setIngredientList(mIngredients);
            mIngredientStepFragment.setRecipeSteps(mRecipeSteps);
            mManager.beginTransaction()
                    .add(R.id.ingredients_steps_container, mIngredientStepFragment)
                    .commit();

        } else {
            mTwoPane = false;
            mIngredientStepFragment = new IngredientsStepsFragment();
            mIngredientStepFragment.setIngredientList(mIngredients);
            mIngredientStepFragment.setRecipeSteps(mRecipeSteps);
            mManager = getSupportFragmentManager();
            mManager.beginTransaction()
                    .add(R.id.ingredients_steps_container, mIngredientStepFragment)
                    .commit();
        }

        mAddToWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Stores the ingredients in a sharedPreference and updates every time it is clicked.
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.ingredients_shared_pref),MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> sharedIngredients = new HashSet<String>();
                for(int i=0;i<mIngredients.size();i++)
                {
                    sharedIngredients.add(mIngredients.get(i).getIngredientName());
                }

                editor.putStringSet(getString(R.string.ingredients_tag),sharedIngredients);
                editor.apply();
                AppWidgetManager manager = AppWidgetManager.getInstance(IngredientsStepsActivity.this);
                int[] appWidgetIds = manager.getAppWidgetIds(new ComponentName(IngredientsStepsActivity.this,IngredientsWidgetProvider.class));
                manager.notifyAppWidgetViewDataChanged(appWidgetIds,R.id.widget_ingredients_list_view);
                Toast.makeText(IngredientsStepsActivity.this, "Ingredients added successfully !", Toast.LENGTH_SHORT).show();


            }
        });




    }

    @Override
    public void onFragmentStepClick(int position) {
        if (mTwoPane) {

            mVideoFragment = new VideoFragment();
            mVideoFragment.setUrl(mRecipeSteps.get(position).getVideoUrl());
            mVideoFragment.setDescription(mRecipeSteps.get(position).getDescription());
            mVideoFragment.setTabletMode(true);
            mManager.beginTransaction()
                    .replace(R.id.tablet_video_container, mVideoFragment)
                    .commit();

        } else {
            Intent in = new Intent(IngredientsStepsActivity.this, RecipeVideoActivity.class);
            in.putParcelableArrayListExtra("steps_array", mRecipeSteps);
            in.putExtra("selected_index", position);
            startActivity(in);
        }
    }

    @Override
    public void onClickNext() {

    }

    @Override
    public void OnClickPrev() {

    }
}
