package com.application.amrudesh.bakingapp.Model;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.amrudesh.bakingapp.Data.Steps;
import com.application.amrudesh.bakingapp.R;
import com.application.amrudesh.bakingapp.Util.FragmentListerner;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    FragmentListerner fragmentListerner;
    Context ctx;
    List<Steps> stepsList;

    public StepsAdapter(Context ctx, List<Steps> stepsList,FragmentListerner fragmentListerner) {
        this.ctx = ctx;
        this.stepsList = stepsList;
        this.fragmentListerner = fragmentListerner;
    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list, parent, false);
        return new StepsAdapter.ViewHolder(ctx,view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.ViewHolder holder, final int position) {
            Steps steps = stepsList.get(position);
            holder.step.setText(steps.getShortdescription());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentListerner.setStep(position);
                    Log.i("Steps_Position",String.valueOf(position));
                }
            });
    }

    @Override
    public int getItemCount() {

        return stepsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.steps_text)
        TextView step;

        public ViewHolder(Context context,View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            ctx = context;
                    }

    }


}
