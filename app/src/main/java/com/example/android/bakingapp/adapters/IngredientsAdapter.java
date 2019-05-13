package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Ingredient;
import com.example.android.bakingapp.R;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsAdapterViewHolder> {

    private Context mContext;
    private ArrayList<Ingredient> mIngredients;


    public void IngredientsAdapter(Context context) {
        mContext = context;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        mIngredients = ingredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IngredientsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        View view = inflater.inflate(R.layout.ingredients_list_item, viewGroup, false);
        return new IngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapterViewHolder ingredientsAdapterViewHolder, int i) {
        ingredientsAdapterViewHolder.mIngredientsTextView.setText((i + 1) + ".) " + mIngredients.get(i).getIngredientName());
    }

    @Override
    public int getItemCount() {
        if (mIngredients == null) {
            return 0;
        } else {
            return mIngredients.size();
        }
    }

    public class IngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mIngredientsTextView;

        public IngredientsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mIngredientsTextView = (TextView) itemView.findViewById(R.id.ingredient_text_view);
        }
    }
}
