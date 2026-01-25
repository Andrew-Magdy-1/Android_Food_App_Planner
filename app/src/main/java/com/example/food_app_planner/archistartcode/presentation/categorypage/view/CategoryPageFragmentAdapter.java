package com.example.food_app_planner.archistartcode.presentation.categorypage.view;

import android.content.Context;
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

public class CategoryPageFragmentAdapter extends RecyclerView.Adapter<CategoryPageFragmentAdapter.CategoryVh> {
    private List<Category> categoryList;
    OnClickCategoryClick onClickCategoryClick;
    Context context;

    public CategoryPageFragmentAdapter(Context cotext){
        this.categoryList=new ArrayList<>();
        this.context=cotext;
    }
    public void setOnCategoryClickListener(OnClickCategoryClick listener) {
        this.onClickCategoryClick = listener;
    }

    @NonNull
    @Override
    public CategoryVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.categpry_item_in_cat_page,parent,false);
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
        private TextView desc;


        public CategoryVh(@NonNull View itemView) {
            super(itemView);
            foodname=itemView.findViewById(R.id.mealTitle);
            img=itemView.findViewById(R.id.mealImage);
            desc=itemView.findViewById(R.id.mealDescription);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Category category = categoryList.get(position);
                    onClickCategoryClick.onClick(category.getStrCategory());

                }
            });

        }

        public void bind(Category category){
            foodname.setText(category.getStrCategory());
            desc.setText(category.getStrCategoryDescription());
            Glide.with(itemView).load(category.getStrCategoryThumb()).into(img);



        }
    }
}
