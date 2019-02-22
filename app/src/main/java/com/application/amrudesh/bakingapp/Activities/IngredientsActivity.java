package com.application.amrudesh.bakingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.application.amrudesh.bakingapp.Data.Ingredients;
import com.application.amrudesh.bakingapp.Data.Recipe;
import com.application.amrudesh.bakingapp.Fragments.DetailsFragment;
import com.application.amrudesh.bakingapp.Fragments.StepsFragment;
import com.application.amrudesh.bakingapp.R;
import com.application.amrudesh.bakingapp.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IngredientsActivity extends AppCompatActivity {

    private Recipe recipe;
    private int currentPosition;
    private Boolean tablet;
    private List<Ingredients> ingredientsList;
    private RequestQueue requestQueue;
    private JSONObject list,IngredientsJsonList;
    private JSONArray ingredientsArray;
    DetailsFragment detailsFragment;
    StepsFragment stepsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        requestQueue = Volley.newRequestQueue(this);
        recipe =(Recipe) getIntent().getSerializableExtra("recipe");
        currentPosition = recipe.getId();
        tablet = recipe.getTab();
        ingredientsList = new ArrayList<>();
        getIngredients(currentPosition);
    }












    private List<Ingredients> getIngredients(final int index) {

        JsonArrayRequest arrayRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i =index;i<=index;i++)
                        {
                            try {
                                list = response.getJSONObject(i);
                                ingredientsArray = new JSONArray(list.getString("ingredients"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j = 0; j <ingredientsArray.length();j++)
                        {
                            try {
                                IngredientsJsonList = ingredientsArray.getJSONObject(j);
                                Ingredients ingredients = new Ingredients();
                                ingredients.setQuantity(IngredientsJsonList.getString("quantity"));
                                ingredients.setMeasure(IngredientsJsonList.getString("measure"));
                                ingredients.setIngredient(IngredientsJsonList.getString("ingredient"));
                                ingredientsList.add(ingredients);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(arrayRequest);
        return ingredientsList;
    }
}
