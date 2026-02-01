package com.example.food_app_planner.archistartcode.data.datasource.local.mealtofavourite;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.database.favourits.AddMealToDb;
import com.example.food_app_planner.archistartcode.database.favourits.MealDAO;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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

    public Completable insertMeal(MealById mealById) {
         if (firebaseManager.isUserLoggedIn()) {
             firebaseManager.syncFavouriteMealToFirestore(mealById);
         }
                return mealDAO.insertMeal(mealById);

    }

    public Completable deleteMeal(MealById mealById) {
        //new Thread(() -> {
            //try {
                return mealDAO.deletMeal(mealById);
                //Log.d(TAG, "✅ Favorite deleted locally: " + mealById.getStrMeal());

            //} catch (Exception e) {
             //   Log.e(TAG, "❌ Error deleting favorite: " + e.getMessage());
            //}
       // }).start();
    }

    public Observable<List<MealById>> getMeals() {
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
        firebaseManager.fetchFavouritsFromFirestore(data -> {
            if (data != null && !data.isEmpty()) {
                Observable.fromIterable(data)
                        .concatMapCompletable(meal ->
                                mealDAO.getMealById(meal.getIdMeal())
                                        .subscribeOn(Schedulers.io())
                                        .flatMapCompletable(existing -> Completable.complete())
                                        .onErrorResumeNext(throwable -> {
                                            Log.d(TAG, "Meal not found, inserting: " + meal.getStrMeal());
                                            return mealDAO.insertMeal(meal);
                                        })
                        )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> Log.d(TAG, "Favorites Sync Completed"),
                                e -> Log.e(TAG, "Sync error: " + e.getMessage())
                        );
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