package com.application.amrudesh.bakingapp.Model;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.amrudesh.bakingapp.Data.Recipe;
import com.application.amrudesh.bakingapp.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private Context context;
    private List<Recipe> recipeList;

    public RecipeAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }


    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mainrecipe_list, parent, false);
        return new ViewHolder(context,view);
    }

    @Override
    public void onBindViewHolder(RecipeAdapter.ViewHolder holder, int position) {


        Recipe recipe = recipeList.get(position);
        String image_url =recipe.getImage();
        holder.recipeName.setText(recipe.getName());

        if (!image_url.isEmpty()) {
            Picasso.get()
                    .load(image_url)
                    .memoryPolicy(MemoryPolicy.NO_CACHE)  // Memory cache has to disabled else it will take some time to change the image
                    .placeholder(R.drawable.no_image_found)
                    .into(holder.recipeImage);
        }
        else
        {
            holder.recipeImage.setImageResource(R.drawable.no_image_found);
        }

    }

    @Override
    public int getItemCount() {

        return recipeList.size() ;
    }
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.recipe_name)
        TextView recipeName;

        @BindView(R.id.recipe_image)
        ImageView recipeImage;

        public ViewHolder(Context ctx,View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            context = ctx;
        }
    }
}
