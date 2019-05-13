package com.example.android.bakingapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParseUtils {

    public static String[] getrecipeList(JSONArray rootJsonarray) throws JSONException {
        JSONObject recipeObject;
        String[] recipeNames = new String[rootJsonarray.length()];
        for (int i = 0; i < rootJsonarray.length(); i++) {
            recipeObject = rootJsonarray.getJSONObject(i);
            recipeNames[i] = recipeObject.getString("name");
        }
        return recipeNames;
    }

    public static ArrayList<Ingredient> getIngredients(JSONArray rootJsonArray, int ingredientIndex) throws JSONException {
        JSONObject recipeObject;
        JSONObject ingredientJsonObject;
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();

        recipeObject = rootJsonArray.getJSONObject(ingredientIndex);

        for (int i = 0; i < recipeObject.getJSONArray("ingredients").length(); i++) {

            ingredientJsonObject = recipeObject.getJSONArray("ingredients").getJSONObject(i);
            ingredients.add(new Ingredient(ingredientJsonObject.getInt("quantity"), ingredientJsonObject.getString("measure"), ingredientJsonObject.getString("ingredient")));
        }

        return ingredients;
    }

    public static ArrayList<RecipeStep> getRecipeSteps(JSONArray rootJsonArray, int recipeStepIndex) throws JSONException {
        JSONObject recipeObject;
        JSONObject recipeStepJsonObject;
        ArrayList<RecipeStep> recipeSteps = new ArrayList<RecipeStep>();

        recipeObject = rootJsonArray.getJSONObject(recipeStepIndex);

        for(int i =0 ;i<recipeObject.getJSONArray("steps").length();i++)
        {
            recipeStepJsonObject = recipeObject.getJSONArray("steps").getJSONObject(i);
            recipeSteps.add(new RecipeStep(recipeStepJsonObject.getString("shortDescription"),recipeStepJsonObject.getString("description"),recipeStepJsonObject.getString("videoURL")));
        }
        return recipeSteps;
    }
}
