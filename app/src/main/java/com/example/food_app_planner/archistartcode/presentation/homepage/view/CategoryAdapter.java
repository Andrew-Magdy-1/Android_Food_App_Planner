package com.example.food_app_planner.archistartcode.presentation.homepage.view;

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
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view.OnClickCategoryClick;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryVh> {
    private List<Category> categoryList;


    public CategoryAdapter(){
        this.categoryList=new ArrayList<>();

    }

    @NonNull
    @Override
    public CategoryVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item_home,parent,false);
        return new CategoryVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryVh holder, int position) {
        Category category=categoryList.get(position);
        holder.bind(category);

    }

    @Override
    public int getItemCount() {
        return  categoryList != null ? categoryList.size() : 0;
    }


    public void setCategoryList(List<Category> categoryList){
        this.categoryList=categoryList;
        notifyDataSetChanged();
    }
    class CategoryVh extends RecyclerView.ViewHolder{

        private TextView foodname;
        private ImageView img;


        public CategoryVh(@NonNull View itemView) {
            super(itemView);
            foodname=itemView.findViewById(R.id.foodName);
            img=itemView.findViewById(R.id.foodImage);
        }

        public void bind(Category category){
            foodname.setText(category.getStrCategory());
            Glide.with(itemView).load(category.getStrCategoryThumb()).into(img);


        }
    }




}
