package com.application.amrudesh.bakingapp.Activities;


import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.application.amrudesh.bakingapp.Data.Recipe;
import com.application.amrudesh.bakingapp.Model.RecipeAdapter;
import com.application.amrudesh.bakingapp.R;
import com.application.amrudesh.bakingapp.Util.Constants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    private List<Recipe> nameList;
    private JSONObject list;
    private Boolean tablet;
    @BindView(R.id.recipesList) RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(this);
        screenSize();
        nameList = new ArrayList<>();
        getRecipieData();
        recipeAdapter = new RecipeAdapter(this, nameList);
        recyclerView.setAdapter(recipeAdapter);

    }
    private void screenSize() {
        if (findViewById(R.id.tablet_linear_layout)!= null)
        {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 3);
            tablet = true;
            recyclerView.setLayoutManager(gridLayoutManager);
        }
        else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            tablet = false;
        }
    }


    private List<Recipe> getRecipieData() {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray array) {
                        for (int i =0; i<array.length();i++)
                        {
                            try {
                                list = array.getJSONObject(i);
                                Recipe recipe = new Recipe();
                                recipe.setId(i);
                                recipe.setName(list.getString("name"));
                                recipe.setImage(list.getString("image"));
                                recipe.setTab(tablet);
                                nameList.add(recipe);


                                Log.i("TAG",recipe.getName());
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                            recipeAdapter.notifyDataSetChanged();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                       Log.i("TAG_ERROR",error.toString());

                    }
                });

        requestQueue.add(jsonObjectRequest);
        return nameList;
    }

}
