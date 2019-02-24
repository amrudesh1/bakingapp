package com.application.amrudesh.bakingapp.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.application.amrudesh.bakingapp.Data.Ingredients;
import com.application.amrudesh.bakingapp.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    Context ctx;
    List <Ingredients> ingredientsList;

    public IngredientsAdapter(Context ctx, List<Ingredients> ingredientsList) {
        this.ctx = ctx;
        this.ingredientsList = ingredientsList;
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_list, parent, false);
        return new IngredientsAdapter.ViewHolder(ctx,view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.ViewHolder holder, int position) {

        Ingredients ingredients = ingredientsList.get(position);
        holder.ingredient_name.setText(ingredients.getIngredient());
        holder.measurement.setText(ingredients.getMeasure());
        holder.quantity.setText(ingredients.getQuantity());

    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient_name)
        TextView ingredient_name;
        @BindView(R.id.quantity_txt)
        TextView quantity;
        @BindView(R.id.spoon)
        TextView measurement;

        public ViewHolder(Context context,View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            ctx = context;
        }
    }
}
