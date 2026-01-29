package com.example.food_app_planner.archistartcode.data.datasource.local.calendermeal;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.database.calendermeal.CalenderMealDAO;
import com.example.food_app_planner.archistartcode.database.calendermeal.CalenderMealDataBase;

import java.util.List;

public class CalenderMealDataSource {
    private CalenderMealDAO calenderMealDAO;
    private FirebaseManager firebaseManager;
    private static final String TAG = "CalenderMealDataSource";

    public CalenderMealDataSource(Context context){
        CalenderMealDataBase calenderMealDataBase=CalenderMealDataBase.getInstanse(context);
        calenderMealDAO= calenderMealDataBase.calenderMealDAO();
        firebaseManager=FirebaseManager.getInstance();
    }
    public void inserCalenderMeal(CalenderMeal calenderMeal){
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                calenderMealDAO.insertMeal(calenderMeal);
//            }
//        }).start();
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
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                calenderMealDAO.delCalenderMeal(calenderMeal);
//            }
//        }).start();
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
        LiveData<List<CalenderMeal>> local= calenderMealDAO.getCalenderMeals(start,end);
        if (firebaseManager.isUserLoggedIn()) {
            String userId = firebaseManager.getCurrentUserId();
            syncFromFirestore(userId, start, end);
        }

        return local;
      // return calenderMealDAO.getCalenderMeals(start,end);
    }
    private void syncFromFirestore(String userId, long startDay, long endDay) {
        firebaseManager.fetchCalenderMealsFromFirestore(userId, startDay, endDay,
                new FirebaseManager.FirestoreCallback<List<CalenderMeal>>() {
                    @Override
                    public void onCallback(List<CalenderMeal> firestoreMeals) {
                        if (firestoreMeals != null && !firestoreMeals.isEmpty()) {
                            new Thread(() -> {
                                // Save Firestore data to local database
                                for (CalenderMeal meal : firestoreMeals) {
                                    calenderMealDAO.insertMeal(meal);
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
}
