package com.example.android.bakingapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.android.bakingapp.IngredientsStepsActivity;
import com.example.android.bakingapp.MainActivity;
import com.example.android.bakingapp.R;

import java.util.List;

public class RecipeMasterFragment extends Fragment {
    private String[] mRecipes;

    OnRecipeClickListener mRecipeListener;

    public interface OnRecipeClickListener
    {
        void onRecipeSelected(int position);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.recipe_master_list,container,false);
        mRecipes = getArguments().getStringArray("recipeNames");
        GridView recipelist = (GridView) rootView.findViewById(R.id.recipe_grid_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.recipe_list_item,mRecipes);
        recipelist.setAdapter(adapter);
        recipelist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mRecipeListener.onRecipeSelected(i);
            }
        });


        return rootView;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mRecipeListener = (OnRecipeClickListener)context;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(context.toString()+" Must implement OnRecipeClickListener");
        }
    }

    public RecipeMasterFragment()
    { }
}
