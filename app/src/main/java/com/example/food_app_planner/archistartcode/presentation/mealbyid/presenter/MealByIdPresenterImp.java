package com.example.food_app_planner.archistartcode.presentation.mealbyid.presenter;

import android.content.Context;
import android.widget.Toast;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.filterbyidremote.MealByIdNetworkResponse;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.calendermealrepo.CalenderMealRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.filtermealbyidrepo.MealByIdRepo;
import com.example.food_app_planner.archistartcode.data.datasource.repositores.firbaserepo.FirebaseRepo;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.view.MealByIdView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class MealByIdPresenterImp implements MealByIdPresenter{
    private MealByIdView mealByIdView;
    private MealByIdRepo mealByIdRepo;
    private FirebaseRepo firebaseRepo;
    private String id;
    private CalenderMealRepo calenderMealRepo;
    private Context context;
    public MealByIdPresenterImp(Context context,MealByIdView mealByIdView,String id){
        mealByIdRepo=new MealByIdRepo(context);
        this.context=context;
        this.mealByIdView=mealByIdView;
        this.id=id;
        calenderMealRepo=new CalenderMealRepo(context);
        firebaseRepo=new FirebaseRepo();
    }
    @Override
    public void getMealById() {
        mealByIdRepo.getMealByIdFromRepo(id, new MealByIdNetworkResponse() {
            @Override
            public void onSuccess(List<MealById> mealByIdList) {
                mealByIdView.onSuccess(mealByIdList.get(0));
            }

            @Override
            public void onFailure(String errorMesage) {

            }
        });


    }

    @Override
    public void insertProductToFav(MealById meal) {
        if(firebaseRepo.isGuestUser()){
            mealByIdView.showRegisterRequiredDialog();

        }else{
            mealByIdRepo.insertMealToFav(meal);
            Toast.makeText(context, "Added to favorites!", Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    public void insertToCalender(CalenderMeal calenderMeal) {
        if(firebaseRepo.isGuestUser()){
            mealByIdView.showRegisterRequiredDialog();

        }else{
            calenderMealRepo.insertCalenderMeal(calenderMeal);
            Toast.makeText(context, "Added to plan!", Toast.LENGTH_SHORT).show();
        }


    }

}
