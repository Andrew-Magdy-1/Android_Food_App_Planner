package com.example.food_app_planner.archistartcode.data.datasource.local.calendermeal;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.database.calendermeal.CalenderMealDAO;
import com.example.food_app_planner.archistartcode.database.calendermeal.CalenderMealDataBase;
import com.example.food_app_planner.archistartcode.database.favourits.MealDAO;

import java.util.List;

public class CalenderMealDataSource {
    private CalenderMealDAO calenderMealDAO;
    private FirebaseManager firebaseManager;
    private static final String TAG = "CalenderMealDataSource";
    private boolean isCalendarSynced = false;
    public CalenderMealDataSource(Context context){
        CalenderMealDataBase calenderMealDataBase=CalenderMealDataBase.getInstanse(context);
        calenderMealDAO= calenderMealDataBase.calenderMealDAO();
        firebaseManager=FirebaseManager.getInstance();
    }
    public void inserCalenderMeal(CalenderMeal calenderMeal){
        new Thread(() -> {
            try {
                calenderMealDAO.insertMeal(calenderMeal);

                Log.d(TAG, "Meal saved locally: " + calenderMeal.getStrMeal());

                if (firebaseManager.isUserLoggedIn()) {
                    firebaseManager.syncCalenderMealToFirestore(calenderMeal);
                }
            } catch (Exception e) {
                Log.e(TAG, "Error inserting meal: " + e.getMessage(), e);
            }
        }).start();

    }
    public void delCalenderMeal(CalenderMeal calenderMeal){
        new Thread(() -> {
            try {
                calenderMealDAO.delCalenderMeal(calenderMeal);
                Log.d(TAG, "Meal deleted locally: " + calenderMeal.getStrMeal());

                if (calenderMeal.getFirestoreId() != null && !calenderMeal.getFirestoreId().isEmpty()) {
                    firebaseManager.deleteCalenderMealFromFirestore(calenderMeal.getFirestoreId());
                }
            } catch (Exception e) {
                Log.e(TAG, "Error deleting meal: " + e.getMessage(), e);
            }
        }).start();

    }
    public LiveData<List<CalenderMeal>> getCalenderMeals(long start,long end){


        if (!isCalendarSynced && firebaseManager.isUserLoggedIn()) {
            isCalendarSynced = true;
            syncFromFirestore(firebaseManager.getCurrentUserId(), start, end);
        }
        return calenderMealDAO.getCalenderMeals(start, end);
    }
    private void syncFromFirestore(String userId, long startDay, long endDay) {
        firebaseManager.fetchCalenderMealsFromFirestore(
                new FirebaseManager.FirestoreCallback<List<CalenderMeal>>() {
                    @Override
                    public void onCallback(List<CalenderMeal> firestoreMeals) {
                        if (firestoreMeals != null && !firestoreMeals.isEmpty()) {
                            new Thread(() -> {

                                for (CalenderMeal meal : firestoreMeals) {
                                    CalenderMeal existingMeal =calenderMealDAO.getMealById(meal.getIdMeal());
                                    if (existingMeal == null) {
                                        calenderMealDAO.insertMeal(meal);
                                    }
                                    //calenderMealDAO.insertMeal(meal);
                                }
                                Log.d(TAG, "Synced " + firestoreMeals.size() + " meals from Firestore");
                            }).start();
                        }
                    }
                });
    }
    public void updateMealWithFirestoreId(CalenderMeal calenderMeal, String firestoreId) {
        new Thread(() -> {
            calenderMeal.setFirestoreId(firestoreId);
            calenderMealDAO.insertMeal(calenderMeal); // Update
            Log.d(TAG, "Updated local meal with firestoreId: " + firestoreId);
        }).start();
    }
    public void clearLocalData() {
        new Thread(() -> calenderMealDAO.clearAll()).start();
    }

    public void getCalenderMealFirestore() {
        firebaseManager.fetchCalenderMealsFromFirestore(data -> {
            if (data == null || data.isEmpty()) {
                Log.d(TAG, "No meals fetched from Firestore");
                return;
            }

            new Thread(() -> {
                for (CalenderMeal meal : data) {
                    Log.i(TAG, "Fetched meal: " + meal.getStrMeal() + " id: " + meal.getIdMeal());
                    calenderMealDAO.insertMeal(meal);
                }
            }).start();
        });
    }
    public void delFromFire(String id){
        new Thread(() -> {
           firebaseManager.deleteCalenderMealFromFirestore(id);
        }).start();

    }





}
