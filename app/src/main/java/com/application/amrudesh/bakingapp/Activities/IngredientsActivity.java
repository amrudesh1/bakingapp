package com.application.amrudesh.bakingapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

import android.content.Context;
import android.content.Intent;
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
import com.application.amrudesh.bakingapp.Util.FragmentListerner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IngredientsActivity extends AppCompatActivity implements FragmentListerner {

    private Recipe recipe;
    private int currentPosition;
    private Boolean tablet;



    @BindView(R.id.fragmentTwo)
    FrameLayout frameLayout;
    DetailsFragment detailsFragment;
    StepsFragment stepsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipe =(Recipe) getIntent().getParcelableExtra("recipe");
        currentPosition = recipe.getId();
        frameLayout = (FrameLayout) findViewById(R.id.fragmentTwo);
        tablet = true;
        Bundle bundle = new Bundle();
        bundle.putInt("index",currentPosition);
        bundle.putBoolean("tablet",tablet);
        if (savedInstanceState == null) {

            stepsFragment = new StepsFragment();
            stepsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentOne, stepsFragment, null).commit();

            if (frameLayout == null) {
                tablet = false;
            }
        } else {
            recipe = savedInstanceState.getParcelable("recipe");
            currentPosition = savedInstanceState.getInt("current");
            stepsFragment =(StepsFragment) getSupportFragmentManager().getFragment(savedInstanceState,"Step");
            stepsFragment = new StepsFragment();
            if (!stepsFragment.isAdded())
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentOne, stepsFragment).commit();

            if(detailsFragment !=null)
            {
                detailsFragment= (DetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState,"detail");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentTwo, detailsFragment).commit();
            }
        }


    }


    @Override
    public void setStep(int index) {
        if (!tablet) {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra("current", currentPosition);
            startActivity(intent);
        }
        else
        {
            detailsFragment = new DetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", index);
            bundle.putInt("current",currentPosition);
            bundle.putBoolean("tablet", true);
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentTwo, detailsFragment).commit();
        }
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "main", stepsFragment);
        outState.putInt("current",currentPosition);
        outState.putParcelable("recipe",recipe);

        if (tablet && detailsFragment!=null)
        {
            try{
                getSupportFragmentManager().putFragment(outState, "detail", detailsFragment);
            }catch (NullPointerException e) {}

        }

    }
    @Override
    protected void onResume() {
        super.onResume();
        if (detailsFragment == null) {
            tablet = false;
        }
    }

    }
