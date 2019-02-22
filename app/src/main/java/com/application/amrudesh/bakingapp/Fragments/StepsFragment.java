package com.application.amrudesh.bakingapp.Fragments;

import com.application.amrudesh.bakingapp.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class StepsFragment extends Fragment {

    @BindView(R.id.stepsList)
    RecyclerView r1;

    @BindView(R.id.ingredientList)
    RecyclerView r2;
}
