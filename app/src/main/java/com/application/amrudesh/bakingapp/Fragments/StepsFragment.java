package com.application.amrudesh.bakingapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.amrudesh.bakingapp.Data.Ingredients;
import com.application.amrudesh.bakingapp.Data.Steps;
import com.application.amrudesh.bakingapp.Model.IngredientsAdapter;
import com.application.amrudesh.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsFragment extends Fragment {

    Boolean tablet;
    @BindView(R.id.stepsList)
    RecyclerView r1;

    @BindView(R.id.ingredientList)
    RecyclerView r2;

    ArrayList<? extends Ingredients> ingredientsList;
    ArrayList<? extends Steps> stepsList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_fragment, container, false);
        ingredientsList = new ArrayList<>();
        stepsList = new ArrayList<>();


        ButterKnife.bind(this,view);
        if (savedInstanceState == null)
        {
            Bundle bundle = getArguments();
            ingredientsList = bundle.getParcelableArrayList("ingredients");
            stepsList = bundle.getParcelableArrayList("steps");

        }
        else
        {
            ingredientsList = savedInstanceState.getParcelableArrayList("ingredients");
            tablet = savedInstanceState.getBoolean("tablet",false);
            stepsList = savedInstanceState.getParcelableArrayList("steps");

        }
        Log.i("TAG_FRAG",String.valueOf(ingredientsList.size()));
        r1.setLayoutManager(new LinearLayoutManager(getActivity()));
        r1.setAdapter(new IngredientsAdapter(getActivity(), (List<Ingredients>) ingredientsList));
        r1.getAdapter().notifyDataSetChanged();

        return view;
    }

}

