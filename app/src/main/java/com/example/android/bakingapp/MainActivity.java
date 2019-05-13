package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingapp.fragments.RecipeMasterFragment;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements RecipeMasterFragment.OnRecipeClickListener {
    private static String url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static JSONArray mRootJsonArray;
    private static Bundle mRecipebundle;
    private static String[] mRecipeNames;
    RecipeMasterFragment masterFragment;
    public RequestQueue mQueue;
    private static FragmentManager mManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mRootJsonArray = response;
                try {
                    mRecipeNames = JsonParseUtils.getrecipeList(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setUpFragment();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);








    }

    public  JSONArray getRootJsonArray() {
        return mRootJsonArray;
    }

    private void setUpFragment()
    {
        mRecipebundle = new Bundle();
        mManager = getSupportFragmentManager();
        masterFragment = new RecipeMasterFragment();
        mRecipebundle.putStringArray("recipeNames",mRecipeNames);
        masterFragment.setArguments(mRecipebundle);
        mManager.beginTransaction()
                .add(R.id.recipe_container,masterFragment)
                .commit();
    }


    @Override
    public void onRecipeSelected(int position) {
        Intent in = new Intent(MainActivity.this,IngredientsStepsActivity.class);
        in.putExtra("index",position);
        in.putExtra("root_json",mRootJsonArray.toString());
        startActivity(in);
    }
}
