package com.example.food_app_planner.archistartcode.presentation.calenderpage.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.calendermealrepo.CalenderMealRepo;

import java.util.ArrayList;
import java.util.List;

public class PlannerAdapter extends RecyclerView.Adapter<PlannerAdapter.ViewHolder>{
    private List<CalenderMeal>calenderMeals;
    private OnCalenderMealClickListener onCalenderMealClickListener;
    public PlannerAdapter(OnCalenderMealClickListener onCalenderMealClickListener){
        this.onCalenderMealClickListener=onCalenderMealClickListener;

        calenderMeals=new ArrayList<>();
    }
    void setCalenderMeals(List<CalenderMeal> calenderMeals){
        this.calenderMeals=calenderMeals;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.calender_meal_item,parent,false);
        return new PlannerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CalenderMeal calenderMeal=calenderMeals.get(position);
        holder.bind(calenderMeal);

    }

    @Override
    public int getItemCount() {

        return  calenderMeals.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView strMeal;
        private TextView strCat;
        private TextView strArea;
        private ImageView mealImg;
        private ImageView del;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            strMeal=itemView.findViewById(R.id.stringmeal);
            strCat=itemView.findViewById(R.id.strcategory);
            strArea=itemView.findViewById(R.id.areaText);
            mealImg=itemView.findViewById(R.id.mealImage);
            del=itemView.findViewById(R.id.delfromcalender);
        }
        void bind(CalenderMeal calenderMeal){
            Log.i("mealimg", calenderMeal.getStrMealThumb());
            Glide.with(itemView).load(calenderMeal.getStrMealThumb()).into(mealImg);
            strMeal.setText(calenderMeal.getStrArea());
            strCat.setText(calenderMeal.getStrCategory());
            //strArea.setText(calenderMeal.getStrArea());
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCalenderMealClickListener.delFromFire(calenderMeal.getIdMeal());
                    onCalenderMealClickListener.onClickDel(calenderMeal);

                }
            });
        }
    }
}
