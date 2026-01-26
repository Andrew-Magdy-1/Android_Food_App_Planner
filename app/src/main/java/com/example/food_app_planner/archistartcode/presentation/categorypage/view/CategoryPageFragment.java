package com.example.food_app_planner.archistartcode.presentation.categorypage.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view.OnClickCategoryClick;
import com.example.food_app_planner.archistartcode.presentation.filterbycategorypage.view.SpecificCategoryPageFragment;
import com.example.food_app_planner.archistartcode.presentation.homepage.view.CategoryAdapter;
import com.example.food_app_planner.archistartcode.presentation.homepage.view.HomePageView;

import java.util.List;


public class CategoryPageFragment extends Fragment implements OnClickCategoryClick {
    CategoryPageFragmentAdapter categoryAdapter;
    RecyclerView recyclerView;
    List<Category> categoryList;
    public CategoryPageFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryList = (List<Category>) getArguments().getSerializable("categories");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.fragment_category_page, container, false);
        recyclerView=v.findViewById(R.id.categoryRecyclerView);
        categoryAdapter=new CategoryPageFragmentAdapter(getContext());
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.setOnCategoryClickListener(this);
        categoryAdapter.setCategoryList(categoryList);


        return v;
    }


    @Override
    public void onClick(String catName) {
        Bundle bundle=new Bundle();
        bundle.putString("CATEGORY_NAME",catName);
        NavHostFragment.findNavController(CategoryPageFragment.this)
                .navigate(R.id.action_categoryPageFragment_to_specificCategoryPageFragment, bundle);

    }

}