package com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.view.OnClickMealListener;

import java.util.ArrayList;
import java.util.List;

public class SpecificCategoryAdapter extends RecyclerView.Adapter<SpecificCategoryAdapter.SpecificCategoryVh> {
    private List<CategoryDetails> categoryDetailsList;
    private OnClickMealListener onClickMealListener;
    public SpecificCategoryAdapter(){
        categoryDetailsList=new ArrayList<>();
    }
    public void setOnClickMealListener(OnClickMealListener onClickMealListener){
        this.onClickMealListener=onClickMealListener;
    }

    @NonNull
    @Override
    public SpecificCategoryVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.specific_category_meal_item,parent,false);
        return new SpecificCategoryAdapter.SpecificCategoryVh(v);

    }

    @Override
    public void onBindViewHolder(@NonNull SpecificCategoryVh holder, int position) {
        CategoryDetails categoryDetails=categoryDetailsList.get(position);

        holder.bind(categoryDetails);


    }

    @Override
    public int getItemCount() {
        return   categoryDetailsList != null ? categoryDetailsList.size() : 0;
    }
    public void setCategoryDetailsListList(List<CategoryDetails> categoryList){
        this.categoryDetailsList=categoryList;
        notifyDataSetChanged();
    }

    class SpecificCategoryVh extends RecyclerView.ViewHolder{
        TextView title;
        ImageView img;

        public SpecificCategoryVh(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.mealImage);
            title=itemView.findViewById(R.id.mealTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    CategoryDetails category = categoryDetailsList.get(position);
                    onClickMealListener.onClickMeal(category.getIdMeal());
                }
            });

        }
        public void bind(CategoryDetails categoryDetails){
            title.setText(categoryDetails.getStrMeal());
            Glide.with(itemView).load(categoryDetails.getStrMealThumb()).into(img);
        }
    }
}
