package com.example.admin.w4d3_contentprovider;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Admin on 8/23/2017.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    ArrayList<foodEntry> foodList;
    Context context;

    public FoodAdapter(ArrayList<foodEntry> foodList) {
        this.foodList = foodList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.food_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FoodAdapter.ViewHolder holder, int position) {
        final foodEntry entry = foodList.get(position);
        holder.tvName.setText(entry.getName());
        holder.tvCalories.setText(entry.getCalories());
        holder.tvFat.setText(entry.getFat());
        holder.tvProtein.setText(entry.getFat());
        holder.tvSodium.setText(entry.getSodium());
    }

    @Override
    public int getItemCount() {
        if(foodList == null){
            return 0;
        }
        else{return foodList.size();}
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvCalories, tvFat, tvProtein, tvSodium;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCalories = (TextView) itemView.findViewById(R.id.tvCalories);
            tvFat = itemView.findViewById(R.id.tvFat);
            tvProtein = itemView.findViewById(R.id.tvProtein);
            tvSodium = itemView.findViewById(R.id.tvSodium);
        }
    }
}
