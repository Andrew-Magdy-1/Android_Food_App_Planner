package com.example.food_app_planner.archistartcode.presentation.specificarea.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;

import java.util.ArrayList;
import java.util.List;

public class SpecificAreaAdapter extends RecyclerView.Adapter<SpecificAreaAdapter.AreaVh> {
    private List<AreaMeals> areaMealsList;
    public SpecificAreaAdapter(){
        this.areaMealsList=new ArrayList<>();
    }
    @NonNull
    @Override
    public AreaVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.area_meals_item,parent,false);
        return new SpecificAreaAdapter.AreaVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaVh holder, int position) {
        AreaMeals areaMeals=areaMealsList.get(position);
        holder.bind(areaMeals);


    }

    @Override
    public int getItemCount() {
        return areaMealsList  != null ? areaMealsList.size() : 0;
    }
    public void setAreaMealsList(List<AreaMeals>areaMealsList){
        this.areaMealsList=areaMealsList;
        notifyDataSetChanged();
    }

    class AreaVh extends RecyclerView.ViewHolder{
        private TextView title;
        private ImageView img;

        public AreaVh(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.areamealImage);
            title=itemView.findViewById(R.id.areamealTitle);
        }
        public void bind(AreaMeals areaMeals){
            Glide.with(itemView).load(areaMeals.getStrMealThumb()).into(img);
            title.setText(areaMeals.getStrMeal());

        }
    }
}
