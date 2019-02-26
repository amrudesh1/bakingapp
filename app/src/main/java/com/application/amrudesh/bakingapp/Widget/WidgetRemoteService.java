package com.application.amrudesh.bakingapp.Widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.application.amrudesh.bakingapp.Data.Ingredients;
import com.application.amrudesh.bakingapp.R;

import java.util.ArrayList;

public class WidgetRemoteService extends RemoteViewsService {
   private static ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();

    public static void setIngredientsArrayList(ArrayList<Ingredients> data){
        WidgetRemoteService.ingredientsArrayList = data;
    }
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new ListProvider(WidgetRemoteService.this, intent));
    }

    class ListProvider implements RemoteViewsFactory {

        Context mContext;
        int appWidgetId;



        public ListProvider(Context context, Intent intent) {
            this.mContext = context;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 1);
        }


        @Override
        public void onCreate() {

        }


        @Override
        public void onDataSetChanged() {


            Log.i("REMOTE_CLASS",String.valueOf(ingredientsArrayList.size()));
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return ingredientsArrayList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);

            views.setTextViewText(R.id.widget_recipe_name, ingredientsArrayList.get(position).getIngredient());
            views.setTextViewText(R.id.widget_recipe_measure, ingredientsArrayList.get(position).getQuantity()
                    + " " + ingredientsArrayList.get(position).getMeasure());


            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}