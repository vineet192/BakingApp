package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.bakingapp.Ingredient;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeStep;
import com.example.android.bakingapp.adapters.IngredientsAdapter;
import com.example.android.bakingapp.adapters.StepsAdapter;

import java.util.ArrayList;

public class IngredientsStepsFragment extends Fragment implements StepsAdapter.OnStepClickListener{

    private ArrayList<Ingredient> mIngredientList;
    private ArrayList<RecipeStep> mRecipeSteps;
    private int stepCounter;
    OnStepFragmentClickListener mListener;


    public void IngredientsStepsfragment(){}

    public void setIngredientList(ArrayList<Ingredient> ingredientList)
    {
        mIngredientList = ingredientList;
    }

    public void setRecipeSteps(ArrayList<RecipeStep> recipeSteps)
    {
        mRecipeSteps = recipeSteps;
    }

    @Override
    public void onStepClick(int position) {
        mListener.onFragmentStepClick(position);
    }


    public interface OnStepFragmentClickListener
    {
        public void onFragmentStepClick(int position);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.ingredients_steps_list,container,false);

        //Setting up the ingredients list
        RecyclerView ingredientsRecyclerView = (RecyclerView)rootView.findViewById(R.id.ingredients_recycler_view);
        LinearLayoutManager ingredientsManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        ingredientsRecyclerView.setLayoutManager(ingredientsManager);
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
        ingredientsAdapter.setIngredients(mIngredientList);
        ingredientsRecyclerView.setAdapter(ingredientsAdapter);

        //Setting up recipe step description view
        RecyclerView stepsRecyclerView = (RecyclerView)rootView.findViewById(R.id.steps_recycler_view);
        LinearLayoutManager stepsManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        stepsRecyclerView.setLayoutManager(stepsManager);
        StepsAdapter stepsAdapter = new StepsAdapter(new StepsAdapter.OnStepClickListener() {
            @Override
            public void onStepClick(int position) {
                mListener.onFragmentStepClick(position);
            }
        });
        stepsAdapter.setRecipeSteps(mRecipeSteps);
        stepsRecyclerView.setAdapter(stepsAdapter);


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try
        {
            mListener = (OnStepFragmentClickListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+" Must implement OnStepFragmentClickListener");
        }
    }


}
