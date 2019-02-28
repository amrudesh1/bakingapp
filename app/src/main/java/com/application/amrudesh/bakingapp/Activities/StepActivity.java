package com.application.amrudesh.bakingapp.Activities;

import android.content.res.Configuration;
import android.os.Bundle;

import com.application.amrudesh.bakingapp.Fragments.DetailsFragment;
import com.application.amrudesh.bakingapp.R;
import com.application.amrudesh.bakingapp.Util.IOnFocusListenable;

import androidx.appcompat.app.AppCompatActivity;



public class StepActivity extends AppCompatActivity {

DetailsFragment detailsFragment;
int orientation;

@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        orientation = getResources().getConfiguration().orientation;
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();

            if (bundle.containsKey("name")) {
                getSupportActionBar().setTitle(bundle.getString("name") + " Steps");
            }
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
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && orientation ==Configuration.ORIENTATION_LANDSCAPE)
        {
            if(detailsFragment instanceof IOnFocusListenable)
            {
                ((IOnFocusListenable) detailsFragment).onWindowFocusChanged(hasFocus);
            }
        }
    }
}
