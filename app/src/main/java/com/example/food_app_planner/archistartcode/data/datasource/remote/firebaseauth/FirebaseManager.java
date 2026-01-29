package com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth;
import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    private static FirebaseManager instance;
    private final FirebaseAuth auth;
    private final FirebaseFirestore firestore;

    private static final String TAG = "FirebaseManager";

    private FirebaseManager() {
        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }
    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }
    public boolean isUserLoggedIn() {
        return auth.getCurrentUser() != null;
    }

    public String getCurrentUserId() {
        return auth.getCurrentUser() != null
                ? auth.getCurrentUser().getUid()
                : null;
    }
    public void syncCalenderMealToFirestore(CalenderMeal meal) {
        if (!isUserLoggedIn()) return;

        String userId = getCurrentUserId();

        firestore.collection("users")
                .document(userId)
                .collection("calender_meals")
                .document(meal.getIdMeal())
                .set(meal);
        }
    public void syncFavouriteMealToFirestore(MealById meal) {
        if (!isUserLoggedIn()) return;

        String userId = getCurrentUserId();

        firestore.collection("users")
                .document(userId)
                .collection("favourite_meals")
                .document(meal.getIdMeal())
                .set(meal);
    }
    public void deleteCalenderMealFromFirestore(String firestoreId) {
        if (!isUserLoggedIn()) return;

        firestore.collection("users")
                .document(getCurrentUserId())
                .collection("calender_meals")
                .document(firestoreId)
                .delete()
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "Deleted calendar meal from Firestore"))
                .addOnFailureListener(e ->
                        Log.e(TAG, "Delete failed", e));
    }


    public void fetchCalenderMealsFromFirestore(FirestoreCallback<List<CalenderMeal>> callback) {
        firestore.collection("users")
                .document(getCurrentUserId())
                .collection("calender_meals")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<CalenderMeal> meals = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        CalenderMeal meal = doc.toObject(CalenderMeal.class);
                        meal.setFirestoreId(doc.getId());
                        if (meal.getTimestamp() == 0) {
                            meal.setTimestamp(System.currentTimeMillis());
                        }
                        meals.add(meal);
                    }
                    callback.onCallback(meals);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Fetch failed", e);
                    callback.onCallback(null);
                });
    }
    public void fetchFavouritsFromFirestore(FirestoreCallback<List<MealById>> callback) {
        firestore.collection("users")
                .document(getCurrentUserId())
                .collection("favourite_meals")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<MealById> meals = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        MealById meal = doc.toObject(MealById.class);
                        meal.setFirestoreId(doc.getId());
                        meals.add(meal);
                    }
                    callback.onCallback(meals);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Fetch failed", e);
                    callback.onCallback(null);
                });
    }
    public void deleteFavouritsFromFirestore(String firestoreId) {
        if (!isUserLoggedIn()) return;

        firestore.collection("users")
                .document(getCurrentUserId())
                .collection("favourite_meals")
                .document(firestoreId)
                .delete()
                .addOnSuccessListener(aVoid ->
                        Log.d(TAG, "Deleted calendar meal from Firestore"))
                .addOnFailureListener(e ->
                        Log.e(TAG, "Delete failed", e));
    }


    public interface FirestoreCallback<T> {
        void onCallback(T data);
    }
}
