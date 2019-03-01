package com.application.amrudesh.bakingapp.Activities;

import android.os.Bundle;

import com.application.amrudesh.bakingapp.Fragments.DetailsFragment;
import com.application.amrudesh.bakingapp.R;
import androidx.appcompat.app.AppCompatActivity;



public class StepActivity extends AppCompatActivity {

DetailsFragment detailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();


            bundle.putBoolean("tablet", false);
            detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detailsFragment, detailsFragment)
                    .commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getSupportFragmentManager().putFragment(outState,"fragment",detailsFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        detailsFragment = (DetailsFragment) getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
        if(detailsFragment.isAdded())
        {
            return;
        }
        getSupportFragmentManager().beginTransaction()
                .add(R.id.detailsFragment, detailsFragment)
                .commit();
    }
}
