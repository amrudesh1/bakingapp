package com.application.amrudesh.bakingapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.application.amrudesh.bakingapp.Data.Recipe;
import com.application.amrudesh.bakingapp.Fragments.DetailsFragment;
import com.application.amrudesh.bakingapp.Fragments.StepsFragment;
import com.application.amrudesh.bakingapp.R;
import com.application.amrudesh.bakingapp.Util.FragmentListerner;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;

public class IngredientsActivity extends AppCompatActivity implements FragmentListerner {

    private Recipe recipe;
    private int currentPosition;
    private Boolean tablet;

    FrameLayout frameLayout;
    DetailsFragment detailsFragment;
    StepsFragment stepsFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipe =(Recipe) getIntent().getParcelableExtra("recipe");
        frameLayout = (FrameLayout) findViewById(R.id.fragmentTwo);
        currentPosition = recipe.getId();
        tablet = true;
        Bundle bundle = new Bundle();
        bundle.putInt("index",currentPosition);
        bundle.putBoolean("tablet",(frameLayout != null));
        if (savedInstanceState == null) {

            stepsFragment = new StepsFragment();
            stepsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentOne, stepsFragment).commit();

            if (frameLayout == null) {
                tablet = false;
            }
            else {
                this.setStep(0);
            }
        } else {
            stepsFragment =(StepsFragment) getSupportFragmentManager().getFragment(savedInstanceState,"main");
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
            intent.putExtra("index",index);
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
        if (frameLayout==null)
        {
            tablet=false;
        }
    }
}
