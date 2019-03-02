package com.application.amrudesh.bakingapp.Widget;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.application.amrudesh.bakingapp.Activities.MainActivity;
import com.application.amrudesh.bakingapp.Data.Ingredients;
import com.application.amrudesh.bakingapp.Data.Recipe;
import com.application.amrudesh.bakingapp.R;
import com.application.amrudesh.bakingapp.Util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class WidgetConfigration extends Activity {
    private ArrayList<Recipe> recipesList = new ArrayList<>();
    private ArrayList<Ingredients> ingredientsList = new ArrayList<>();
    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private JSONObject list;
    private JSONObject IngredientsJsonList;
    private JSONArray ingredientsArray;
    @BindView(R.id.spinner)
    Spinner spinner;
    @BindView(R.id.add_button)
    Button btn;
    RequestQueue queue;


    View.OnClickListener PassListListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            int position = spinner.getSelectedItemPosition();
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(WidgetConfigration.this);
            BakingWidget.updateAppWidget(WidgetConfigration.this,appWidgetManager, appWidgetId);
            RemoteViews views = new RemoteViews(getPackageName(),R.layout.baking_widget);
            WidgetRemoteService.setIngredientsArrayList(ingredientsList);
            Intent intent = new Intent(WidgetConfigration.this, WidgetRemoteService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.listViewWidget, intent);
            Log.i("SIZE",String.valueOf(ingredientsList.size()));
            views.setTextViewText(R.id.recipe_title, recipesList.get(position).getName());
            Intent appIntent = new Intent(getApplicationContext(), MainActivity.class);
            PendingIntent appPendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.parent_relative_layout, appPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetId,views);
            setResult(RESULT_OK, new Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId));
            finish();

        }
    };
    public WidgetConfigration() {
        super();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.widget_configration);
        ButterKnife.bind(this);

        queue = Volley.newRequestQueue(this);
        onResponse();
        btn.setOnClickListener(PassListListener);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras!=null)
        {
            appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        setResult(RESULT_CANCELED);
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID)
        {
            finish();
        }

    }
    public ArrayList<Recipe> onResponse()
    {
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray array) {
                        for (int i =0; i<array.length();i++)
                        {
                            try {
                                list = array.getJSONObject(i);
                                Recipe recipe = new Recipe();
                                recipe.setName(list.getString("name"));
                                recipesList.add(recipe);


                                Log.i("TAG",recipe.getName());
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        UIUpdate(recipesList);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG_ERROR",error.toString());

                    }
                });

        queue.add(jsonObjectRequest);
        return recipesList;
    }
    public void UIUpdate(ArrayList<Recipe> recipe)
    {
        String[]values = new String[recipe.size()];
        for(int i=0; i < recipe.size();i++)
        {
            values[i] = recipe.get(i).getName();
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, values);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.i("POSITION",String.valueOf(position));
                getIngredients(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private ArrayList<Ingredients> getIngredients(final int index) {

        JsonArrayRequest arrayRequest = new JsonArrayRequest
                (Request.Method.GET, Constants.BASE_URL, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = index; i <= index; i++) {
                            try {
                                list = response.getJSONObject(i);
                                ingredientsArray = new JSONArray(list.getString("ingredients"));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int j = 0; j < ingredientsArray.length(); j++) {
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
        queue.add(arrayRequest);
        return ingredientsList;
    }
}


