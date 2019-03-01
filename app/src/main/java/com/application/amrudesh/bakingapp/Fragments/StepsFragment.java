package com.application.amrudesh.bakingapp.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.application.amrudesh.bakingapp.Data.Ingredients;
import com.application.amrudesh.bakingapp.Data.Steps;
import com.application.amrudesh.bakingapp.Model.IngredientsAdapter;
import com.application.amrudesh.bakingapp.Model.StepsAdapter;
import com.application.amrudesh.bakingapp.R;
import com.application.amrudesh.bakingapp.Util.Constants;
import com.application.amrudesh.bakingapp.Util.FragmentListerner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {
    RequestQueue queue;
    Boolean tablet;
    FragmentListerner fragmentListerner;
    private JSONObject list,IngredientsJsonList,StepsJsonList;
    private JSONArray ingredientsArray,StepsArray;

    int index;
    @BindView(R.id.stepsList)
    RecyclerView r2;

    @BindView(R.id.ingredientList)
    RecyclerView r1;

    private List<Ingredients> ingredientsList;
    private List<Steps> stepsList;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        Activity activity = (Activity) context;
        fragmentListerner = (FragmentListerner) activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment, container, false);
        ingredientsList = new ArrayList<>();
        stepsList = new ArrayList<>();
        queue = Volley.newRequestQueue(getActivity());
        ButterKnife.bind(this,view);



        if (savedInstanceState == null)
        {
            Bundle bundle = getArguments();
            index = bundle.getInt("index");
            ingredientsList = getIngredients(index);
            stepsList=getSteps(index);
        }
        else
        {
            ingredientsList = getIngredients(index);
            stepsList = getSteps(index);
            tablet = savedInstanceState.getBoolean("tablet",false);
        }

        return view;

    }


    private void initialiseRec(List<Ingredients> ingredientsList) {



        r1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(getActivity(),ingredientsList);
        r1.setAdapter(ingredientsAdapter);
        r1.getAdapter().notifyDataSetChanged();

    }
    private void initialiseRec2(List<Steps> stepsList) {
        r2.setLayoutManager(new LinearLayoutManager(getActivity()));
        StepsAdapter stepsAdapter = new StepsAdapter(getActivity(),stepsList,fragmentListerner);
        r2.setAdapter(stepsAdapter);
        r2.getAdapter().notifyDataSetChanged();
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





                            }

                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                           initialiseRec2(stepsList);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(stepsRequest);
        return stepsList;
    }




    private List<Ingredients> getIngredients(final int index) {

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
                            initialiseRec(ingredientsList);
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

