package com.example.android.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.bakingapp.fragments.VideoFragment;

import java.util.ArrayList;

public class RecipeVideoActivity extends AppCompatActivity implements VideoFragment.OnPrevclickListener, VideoFragment.OnNextClickListener {

    private VideoFragment mVideoFragment;
    private int mIndex;
    private ArrayList<RecipeStep> mRecipeSteps;
    private static FragmentManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_video);
        if (savedInstanceState == null) {
            mIndex = getIntent().getIntExtra("selected_index", 0);
        } else {
            mIndex = savedInstanceState.getInt("index");
        }
        mRecipeSteps = getIntent().getParcelableArrayListExtra("steps_array");
        mVideoFragment = new VideoFragment();
        mVideoFragment.setUrl(mRecipeSteps.get(mIndex).getVideoUrl());
        mVideoFragment.setDescription(mRecipeSteps.get(mIndex).getDescription());
        mManager = getSupportFragmentManager();
        mManager.beginTransaction()
                .add(R.id.recipe_video_description_container, mVideoFragment)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("index", mIndex);
    }

    @Override
    public void onClickNext() {
        if (mIndex != mRecipeSteps.size() - 1) {
            mIndex++;
        } else {
            Toast.makeText(this, "No more steps !", Toast.LENGTH_SHORT).show();
            return;
        }

        mVideoFragment = new VideoFragment();
        mVideoFragment.setUrl(mRecipeSteps.get(mIndex).getVideoUrl());
        mVideoFragment.setDescription(mRecipeSteps.get(mIndex).getDescription());
        mManager.beginTransaction()
                .replace(R.id.recipe_video_description_container, mVideoFragment)
                .commit();

    }

    @Override
    public void OnClickPrev() {
        if (mIndex != 0) {
            mIndex--;
        } else {
            return;
        }

        mVideoFragment = new VideoFragment();
        mVideoFragment.setUrl(mRecipeSteps.get(mIndex).getVideoUrl());
        mVideoFragment.setDescription(mRecipeSteps.get(mIndex).getDescription());
        mManager.beginTransaction()
                .replace(R.id.recipe_video_description_container, mVideoFragment)
                .commit();
    }

}
