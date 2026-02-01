package com.example.food_app_planner.archistartcode.presentation.search.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.view.OnClickMealListener;

import java.util.List;

public class SearchMealAdapter extends RecyclerView.Adapter<SearchMealAdapter.MealViewHolder> {

    private Context context;
    private List<CategoryDetails> mealList;
    private OnClickMealListener onClickMealListener;

    public SearchMealAdapter(Context context, List<CategoryDetails> mealList) {
        this.context = context;
        this.mealList = mealList;
    }

//    public void setOnMealClickListener(OnMealClickListener onMealClickListener) {
//        this.onMealClickListener = onMealClickListener;
//    }
    public void setOnMealClickListener(OnClickMealListener onClickMealListener) {
        this.onClickMealListener = onClickMealListener;
    }


    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_meal_vertical, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        CategoryDetails meal = mealList.get(position);
        holder.tvMealName.setText(meal.getStrMeal());

        Glide.with(context)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.ivMealImage);


        holder.itemView.setOnClickListener(v -> {
            if (onClickMealListener != null) {
                onClickMealListener.onClickMeal(meal.getIdMeal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMealImage;
        TextView tvMealName;
        CardView cardView;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMealImage = itemView.findViewById(R.id.iv_meal_image);
            tvMealName = itemView.findViewById(R.id.tv_meal_name);
            cardView = itemView.findViewById(R.id.card_meal);

        }
    }

    public interface OnMealClickListener {
        void onMealClick(String mealId);
    }
}
