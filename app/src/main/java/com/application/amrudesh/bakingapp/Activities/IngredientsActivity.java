package com.application.amrudesh.bakingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.application.amrudesh.bakingapp.Data.Ingredients;
import com.application.amrudesh.bakingapp.Data.Recipe;
import com.application.amrudesh.bakingapp.Data.Steps;
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
    private List<Steps> stepsList;
    private RequestQueue requestQueue;
    private JSONObject list,IngredientsJsonList,StepsJsonList;
    private JSONArray ingredientsArray,StepsArray;

    @BindView(R.id.fragmentTwo)
    FrameLayout frameLayout;
    DetailsFragment detailsFragment;
    StepsFragment stepsFragment;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        requestQueue = Volley.newRequestQueue(this);
        recipe =(Recipe) getIntent().getParcelableExtra("recipe");
        currentPosition = recipe.getId();
        tablet = true;
        ingredientsList = new ArrayList<>();
        stepsList = new ArrayList<>();
        getIngredients(currentPosition);
        getSteps(currentPosition);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) ingredientsList);
        bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) stepsList);
        bundle.putBoolean("tablet",tablet);
        if (savedInstanceState == null) {

            stepsFragment = new StepsFragment();
            stepsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().addToBackStack("TAG").add(R.id.fragmentOne, stepsFragment, null).commit();

            if (frameLayout == null) {
                tablet = false;
            }
        } else {
            stepsFragment =(StepsFragment) getSupportFragmentManager().getFragment(savedInstanceState,"Step");

        }

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
                        Log.i("TAG",String.valueOf(ingredientsList.size()));
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue.add(arrayRequest);
        return ingredientsList;
    }


    private List<Steps> getSteps(final int index)
    {
        JsonArrayRequest stepsRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i =index;i<=index;i++)
                        {
                            try {
                                list = response.getJSONObject(i);
                                StepsArray = new JSONArray(list.getString("steps"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j =0;j <StepsArray.length();j++)
                        {
                            try {
                                StepsJsonList = StepsArray.getJSONObject(j);
                                Steps steps = new Steps();
                                steps.setDescription(StepsJsonList.getString("description"));
                                steps.setShortdescription(StepsJsonList.getString("shortDescription"));
                                steps.setVideoURL(StepsJsonList.getString("videoURL"));
                                steps.setThumbnailURL(StepsJsonList.getString("thumbnailURL"));
                                stepsList.add(steps);

                                Log.i("Steps",steps.getDescription());
                                Log.i("Steps",steps.getShortdescription());



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
        requestQueue.add(stepsRequest);
        return stepsList;
    }

}
