package com.example.food_app_planner.archistartcode.presentation.favouritemeals.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.presentation.favouritemeals.presenter.FavMealPresenter;
import com.example.food_app_planner.archistartcode.presentation.favouritemeals.presenter.FavProdPresenterImp;

import java.util.List;


public class FavouriteIconFragment extends Fragment implements OnFavClickListener,FavView{

    RecyclerView recyclerView;
    FavAdapter favAdapter;
    FavMealPresenter favMealPresenter;
    FirebaseManager firebaseManager;



    public FavouriteIconFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseManager=FirebaseManager.getInstance();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_favourite_icon, container, false);
        recyclerView=v.findViewById(R.id.favRecycler);
        favAdapter=new FavAdapter(this);
        recyclerView.setAdapter(favAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        favMealPresenter=new FavProdPresenterImp(getContext(),this);
        favMealPresenter.getFavouritsFromFav();

        favMealPresenter.getFavMeals().observe(getViewLifecycleOwner(), new Observer<List<MealById>>() {
            @Override
            public void onChanged(List<MealById> mealByIdList) {
                Log.d("FavFragment", "Data received: " + mealByIdList.size() + " items");
                favAdapter.setMealByIdList(mealByIdList);


            }
        });
        return v;
    }

    @Override
    public void onClickDel(MealById mealById) {
        //firebaseManager.deleteFavoriteMealFromFirestore(mealById.getIdMeal());
        favMealPresenter.deleteMealFromFav(mealById);

    }

    @Override
    public void delFromFire(String id) {
        favMealPresenter.deleteFromFire(id);
    }

    @Override
    public void OnProductDeleted(MealById mealById) {
        Toast.makeText(requireContext(), "Meal deleted", Toast.LENGTH_SHORT).show();

    }
}