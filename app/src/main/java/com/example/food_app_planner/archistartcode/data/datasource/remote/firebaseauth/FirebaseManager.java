package com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth;


import android.content.Context;
import android.util.Log;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseManager {
    private static FirebaseManager instance;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;

    private static final String TAG = "FirebaseManager";

    private FirebaseManager() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
    }

    public static synchronized FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    // ========== Authentication Methods ==========
    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }

    public String getCurrentUserId() {
        FirebaseUser user = getCurrentUser();
        return user != null ? user.getUid() : null;
    }

    public boolean isUserLoggedIn() {
        return getCurrentUser() != null;
    }

    // ========== Firestore Collections ==========
    public CollectionReference getCalenderMealsCollection() {
        String userId = getCurrentUserId();
        if (userId == null) {
            Log.e(TAG, "User not logged in");
            return null;
        }
        return firestore.collection("users")
                .document(userId)
                .collection("calender_meals");
    }

    public CollectionReference getFavoriteMealsCollection() {
        String userId = getCurrentUserId();
        if (userId == null) {
            Log.e(TAG, "User not logged in");
            return null;
        }
        return firestore.collection("users")
                .document(userId)
                .collection("favorite_meals");
    }

    // ========== User Management ==========
    public void createUserDocument(String userId, String email, String name) {
        Map<String, Object> user = new HashMap<>();
        user.put("email", email);
        user.put("name", name);
        user.put("createdAt", FieldValue.serverTimestamp());
        user.put("lastLogin", FieldValue.serverTimestamp());

        firestore.collection("users")
                .document(userId)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "User document created for: " + userId);

                    // Create initial empty collections
                    createInitialCollections(userId);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error creating user document", e);
                });
    }

    private void createInitialCollections(String userId) {
        // Create empty document in calender_meals collection
        Map<String, Object> initialData = new HashMap<>();
        initialData.put("initialized", true);
        initialData.put("createdAt", FieldValue.serverTimestamp());

        firestore.collection("users")
                .document(userId)
                .collection("calender_meals")
                .document("initial")
                .set(initialData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Calender meals collection initialized");
                });

        // Create empty document in favorite_meals collection
        firestore.collection("users")
                .document(userId)
                .collection("favorite_meals")
                .document("initial")
                .set(initialData)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Favorite meals collection initialized");
                });
    }

    // ========== Sync Methods ==========
    public void syncCalenderMealToFirestore(com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal calenderMeal) {
        String userId = getCurrentUserId();
        if (userId == null) {
            Log.e(TAG, "Cannot sync: User not logged in");
            return;
        }

        // Set userId to the meal
        calenderMeal.setUserId(userId);

        // Prepare data for Firestore
        Map<String, Object> mealData = new HashMap<>();
        mealData.put("idMeal", calenderMeal.getIdMeal());
        mealData.put("strMeal", calenderMeal.getStrMeal());
        mealData.put("strCategory", calenderMeal.getStrCategory());
        mealData.put("strArea", calenderMeal.getStrArea());
        mealData.put("strMealThumb", calenderMeal.getStrMealThumb());
        mealData.put("timestamp", calenderMeal.getTimestamp());
        mealData.put("userId", userId);
        mealData.put("createdAt", FieldValue.serverTimestamp());
        mealData.put("syncedAt", FieldValue.serverTimestamp());

        CollectionReference calenderMealsRef = getCalenderMealsCollection();
        if (calenderMealsRef == null) return;

        // Check if already exists in Firestore
        if (calenderMeal.getFirestoreId() != null && !calenderMeal.getFirestoreId().isEmpty()) {
            // Update existing document
            calenderMealsRef.document(calenderMeal.getFirestoreId())
                    .update(mealData)
                    .addOnSuccessListener(aVoid -> {
                        Log.d(TAG, "Meal updated in Firestore: " + calenderMeal.getStrMeal());
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error updating meal in Firestore", e);
                    });
        } else {
            // Add new document
            calenderMealsRef.add(mealData)
                    .addOnSuccessListener(documentReference -> {
                        String firestoreId = documentReference.getId();
                        calenderMeal.setFirestoreId(firestoreId);
                        Log.d(TAG, "Meal added to Firestore with ID: " + firestoreId);

                        // Update local database with firestoreId
                        updateLocalMealWithFirestoreId(calenderMeal, firestoreId);
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Error adding meal to Firestore", e);
                    });
        }
    }

    private void updateLocalMealWithFirestoreId(com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal calenderMeal, String firestoreId) {
        // You'll need to implement this method to update the local Room database
        // This should be done in your repository or data source
    }

    // ========== Fetch Methods ==========
    public void fetchCalenderMealsFromFirestore(String userId, long startDate, long endDate,
                                                FirestoreCallback<List<com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal>> callback) {
        CollectionReference calenderMealsRef = getCalenderMealsCollection();
        if (calenderMealsRef == null) {
            callback.onCallback(new java.util.ArrayList<>());
            return;
        }

        Query query = calenderMealsRef
                .whereEqualTo("userId", userId)
                .whereGreaterThanOrEqualTo("timestamp", startDate)
                .whereLessThanOrEqualTo("timestamp", endDate)
                .orderBy("timestamp", Query.Direction.ASCENDING);

        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<CalenderMeal> meals = new java.util.ArrayList<>();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {CalenderMeal meal =new CalenderMeal();

                        meal.setFirestoreId(document.getId());
                        meal.setUserId(document.getString("userId"));
                        meal.setIdMeal(document.getString("idMeal"));
                        meal.setStrMeal(document.getString("strMeal"));
                        meal.setStrCategory(document.getString("strCategory"));
                        meal.setStrArea(document.getString("strArea"));
                        meal.setStrMealThumb(document.getString("strMealThumb"));

                        Long timestamp = document.getLong("timestamp");
                        if (timestamp != null) {
                            meal.setTimestamp(timestamp);
                        }

                        meals.add(meal);
                    }

                    callback.onCallback(meals);
                    Log.d(TAG, "Fetched " + meals.size() + " meals from Firestore");
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching meals from Firestore", e);
                    callback.onCallback(new java.util.ArrayList<>());
                });
    }

    // ========== Delete Methods ==========
    public void deleteCalenderMealFromFirestore(String firestoreId) {
        if (firestoreId == null || firestoreId.isEmpty()) {
            Log.e(TAG, "Cannot delete: firestoreId is null or empty");
            return;
        }

        CollectionReference calenderMealsRef = getCalenderMealsCollection();
        if (calenderMealsRef == null) return;

        calenderMealsRef.document(firestoreId)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Meal deleted from Firestore: " + firestoreId);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error deleting meal from Firestore", e);
                });
    }

    // Callback interface
    public interface FirestoreCallback<T> {
        void onCallback(T data);
    }
}