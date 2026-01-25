package com.example.food_app_planner.archistartcode.presentation.homepage.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
import com.example.food_app_planner.archistartcode.presentation.categorypage.view.CategoryPageFragment;
import com.example.food_app_planner.archistartcode.presentation.categorypage.view.CategoryPageFragmentAdapter;
import com.example.food_app_planner.archistartcode.presentation.homepage.presenter.HomePagePresenter;
import com.example.food_app_planner.archistartcode.presentation.homepage.presenter.HomePagePresenterImp;

import java.io.Serializable;
import java.util.List;

public class HomePageFragment extends Fragment implements HomePageView {
    private RecyclerView categoryRc;
    private ImageView cardImage;
    private TextView cardTitle, cardDescription, area;
    private CategoryAdapter categoryAdapter;
    private TextView gotoCat;
    private RandomMeal randomMeal;
    private List<Category> categoryList;
    HomePagePresenter homePagePresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_page, container, false);


        cardImage = view.findViewById(R.id.card_image);
        cardTitle = view.findViewById(R.id.card_title);
        cardDescription = view.findViewById(R.id.card_description);
        area = view.findViewById(R.id.area);
        gotoCat=view.findViewById(R.id.seeAll);
        gotoCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                CategoryPageFragment categoryFragment = new CategoryPageFragment();
                bundle.putSerializable("categories", (Serializable) categoryList);

                NavHostFragment.findNavController(HomePageFragment.this)
                        .navigate(R.id.categoryPageFragment,bundle);
            }
        });
//        gotoCat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                CategoryPageFragment categoryFragment = new CategoryPageFragment();
//                bundle.putSerializable("categories", (Serializable) categoryList);
//                categoryFragment.setArguments(bundle);
//                requireActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                        .replace(R.id.homeFragmentContainer,categoryFragment)
//                                                .addToBackStack(null)
//                                                        .commit();
//
//            }
//        });
        homePagePresenter = new HomePagePresenterImp(getContext(), this);
        homePagePresenter.getCategories();
        homePagePresenter.getRandomMeal();

        categoryRc = view.findViewById(R.id.categoryRc);
        setupCategoryRecycler();

        return view;
    }
    private void setupCategoryRecycler() {
        categoryRc.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false)
        );


        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter();
        }
        categoryRc.setAdapter(categoryAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(randomMeal!=null){
            //showRandomMeal(randomMeal);
        }
    }

    @Override
    public void onSuccessRandoms(List<RandomMeal> randomMealList) {
        cardTitle.setText(randomMealList.get(0).getStrCategory());
        cardDescription.setText(randomMealList.get(0).getStrArea());
        area.setText(randomMealList.get(0).getStrArea());
        Glide.with(this)
                .load(randomMealList.get(0).getStrMealThumb())
                .into(cardImage);

    }

    @Override
    public void onSuccessCategories(List<Category> categoryList) {
        categoryAdapter.setCategoryList(categoryList);
        this.categoryList=categoryList;


    }

    @Override
    public void onFailureRandoms(String errorMessage) {

    }

    @Override
    public void onFailureCategories(String errorMessage) {

    }



}