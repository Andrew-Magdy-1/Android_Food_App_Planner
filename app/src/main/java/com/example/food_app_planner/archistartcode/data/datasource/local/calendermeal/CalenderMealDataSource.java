package com.example.food_app_planner.archistartcode.data.datasource.local.calendermeal;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.database.calendermeal.CalenderMealDAO;
import com.example.food_app_planner.archistartcode.database.calendermeal.CalenderMealDataBase;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

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
    public Completable inserCalenderMeal(CalenderMeal calenderMeal){
                if (firebaseManager.isUserLoggedIn()) {
                    firebaseManager.syncCalenderMealToFirestore(calenderMeal);
                }
                return calenderMealDAO.insertMeal(calenderMeal);


    }
    public Completable delCalenderMeal(CalenderMeal calenderMeal){
        return calenderMealDAO.delCalenderMeal(calenderMeal)
                .andThen(Completable.fromAction(() -> {
                    if (calenderMeal.getFirestoreId() != null && !calenderMeal.getFirestoreId().isEmpty()) {
                        firebaseManager.deleteCalenderMealFromFirestore(calenderMeal.getFirestoreId());
                    }
                }));

    }
    public Observable<List<CalenderMeal>> getCalenderMeals(long start, long end){


        if (!isCalendarSynced && firebaseManager.isUserLoggedIn()) {
            isCalendarSynced = true;
            syncFromFirestore(firebaseManager.getCurrentUserId(), start, end);
        }
        return calenderMealDAO.getCalenderMeals(start, end);
    }
    private void syncFromFirestore(String userId, long startDay, long endDay) {
        firebaseManager.fetchCalenderMealsFromFirestore(firestoreMeals -> {
            if (firestoreMeals != null && !firestoreMeals.isEmpty()) {

                Observable.fromIterable(firestoreMeals)
                        .flatMapCompletable(meal -> {
                            return calenderMealDAO.insertMeal(meal)
                                    .subscribeOn(Schedulers.io());
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()) // اختياري لو حابب تعمل Log في المين
                        .subscribe(
                                () -> Log.d(TAG, "✅ Data synced from Firestore to Room successfully"),
                                throwable -> Log.e(TAG, "❌ Error syncing from Firestore: " + throwable.getMessage())
                        );
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
