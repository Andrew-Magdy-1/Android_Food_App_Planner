//package com.example.food_app_planner.archistartcode.presentation.homepage.view;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.example.food_app_planner.R;
//import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
//import com.example.food_app_planner.archistartcode.data.datasource.models.randommeal.RandomMeal;
//import com.example.food_app_planner.archistartcode.presentation.homepage.presenter.HomePagePresenter;
//import com.example.food_app_planner.archistartcode.presentation.homepage.presenter.HomePagePresenterImp;
//
//import java.util.List;
//
//
//public class RandomMealFragment extends Fragment  {
//    ImageView imageView;
//    TextView title;
//    TextView description;
//    TextView area;
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view=inflater.inflate(R.layout.fragment_random_meal, container, false);
//        imageView=view.findViewById(R.id.card_image);
//        title=view.findViewById(R.id.card_title);
//        area=view.findViewById(R.id.area);
//        description=view.findViewById(R.id.card_description);
//        // Inflate the layout for this fragment
//        return view;
//    }
//    public void showRandomMeal(RandomMeal randomMealList){
//        title.setText(randomMealList.getStrCategory());
//        description.setText(randomMealList.getStrMeal());
//        area.setText(randomMealList.getStrArea());
//        Glide.with(this)
//                .load(randomMealList.getStrMealThumb())
//                .into(imageView);
//    }
//
//
//}