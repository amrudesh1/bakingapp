package com.application.amrudesh.bakingapp.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.amrudesh.bakingapp.Data.Steps;
import com.application.amrudesh.bakingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
                .inflate(R.layout.steps_list, parent, false);
        return new StepsAdapter.ViewHolder(ctx,view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, int position) {
            Steps steps = stepsList.get(position);
            holder.step.setText(steps.getShortdescription());
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.steps_text)
        TextView step;

        public ViewHolder(Context context,View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            ctx = context;
        }
    }
}
