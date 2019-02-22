package com.application.amrudesh.bakingapp.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.amrudesh.bakingapp.Data.Steps;
import com.application.amrudesh.bakingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    Context ctx;
    List<Steps> stepsList;

    public StepsAdapter(Context ctx, List<Steps> stepsList) {
        this.ctx = ctx;
        this.stepsList = stepsList;
    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list, parent, false);
        return new StepsAdapter.ViewHolder(ctx,view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(Context context,View itemView) {
            super(itemView);
        }
    }
}
