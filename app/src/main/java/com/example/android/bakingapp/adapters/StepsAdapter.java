package com.example.android.bakingapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.RecipeStep;

import java.util.ArrayList;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {

    private ArrayList<RecipeStep> mRecipeSteps;
    OnStepClickListener mListener;

    public interface OnStepClickListener
    {
        void onStepClick(int position);
    }

    public StepsAdapter(OnStepClickListener listener)
    {
        this.mListener = listener;
    }
    @NonNull
    @Override
    public StepsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.steps_list_item, viewGroup, false);
        return new StepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapterViewHolder stepsAdapterViewHolder, final int i) {
        stepsAdapterViewHolder.mStepTextView.setText(mRecipeSteps.get(i).getShortDescription());
    }

    @Override
    public int getItemCount() {
        if (mRecipeSteps == null) {
            return 0;
        } else {
            return mRecipeSteps.size();
        }
    }

    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final TextView mStepTextView;

        public StepsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mStepTextView = itemView.findViewById(R.id.steps_short_desc_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mListener.onStepClick(adapterPosition);
        }
    }

    public void setRecipeSteps(ArrayList<RecipeStep> recipeSteps) {
        mRecipeSteps = recipeSteps;
        notifyDataSetChanged();
    }
}
