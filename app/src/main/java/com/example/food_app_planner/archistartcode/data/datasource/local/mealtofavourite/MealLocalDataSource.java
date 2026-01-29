package com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.database.favourits.AddMealToDb;
import com.example.food_app_planner.archistartcode.database.favourits.MealDAO;

import java.util.List;

public class MealLocalDataSource {
    private MealDAO mealDAO;
    private FirebaseManager firebaseManager;
    private static final String TAG = "MealLocalDataSource";
    private boolean isSyncing = false;

    public MealLocalDataSource(Context context) {
        AddMealToDb addMealToDb = AddMealToDb.getInstance(context);
        mealDAO = addMealToDb.mealDAO();
        firebaseManager = FirebaseManager.getInstance();
    }

    public void insertMeal(MealById mealById) {
        new Thread(() -> {
            try {
                String userId = firebaseManager.getCurrentUserId();
                if (userId != null) {
                    mealById.setUserId(userId);
                } else {
                    mealById.setUserId("guest");
                }

                mealDAO.insertMeal(mealById);
                Log.d(TAG, "✅ Favorite saved locally: " + mealById.getStrMeal());

                if (firebaseManager.isUserLoggedIn()) {
                    firebaseManager.syncFavouriteMealToFirestore(mealById);
                }
            } catch (Exception e) {
                Log.e(TAG, "❌ Error inserting favorite: " + e.getMessage());
            }
        }).start();
    }

    public void deleteMeal(MealById mealById) {
        new Thread(() -> {
            try {
                mealDAO.deletMeal(mealById);
                Log.d(TAG, "✅ Favorite deleted locally: " + mealById.getStrMeal());

            } catch (Exception e) {
                Log.e(TAG, "❌ Error deleting favorite: " + e.getMessage());
            }
        }).start();
    }

    public LiveData<List<MealById>> getMeals() {
        String userId = firebaseManager.getCurrentUserId();


        if (!isSyncing && firebaseManager.isUserLoggedIn()) {
            syncFavoritesFromFirestore(userId);
        }
        return mealDAO.mealList();
    }

    private void syncFavoritesFromFirestore(String userId) {
        if (userId == null || userId.equals("guest")) {
            return;
        }
        isSyncing = true;
    }
    public void getFavoritesFirestore() {
        firebaseManager.fetchFavouritsFromFirestore(new FirebaseManager.FirestoreCallback<List<MealById>>() {
            @Override
            public void onCallback(List<MealById> data) {
                if (data == null || data.isEmpty()) {
                    Log.d(TAG, "No meals fetched from FavouritsFirestore");

                }
                new Thread(() -> {
                    for (MealById meal : data) {
                        MealById existingMeal = mealDAO.getMealById(meal.getIdMeal());
                        if (existingMeal == null) {
                            mealDAO.insertMeal(meal);
                        }
                    }
                }).start();

            }
        });
    }
    public void removeFromFire(String id){
        new Thread(() -> {

            firebaseManager.deleteFavouritsFromFirestore(id);

        }).start();


    }

    public void forceSyncFromFirestore() {
        String userId = firebaseManager.getCurrentUserId();
        if (userId != null && !userId.equals("guest")) {
            isSyncing = false; // Reset flag
            syncFavoritesFromFirestore(userId);
        }
    }
}