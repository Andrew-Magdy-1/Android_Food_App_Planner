package com.example.food_app_planner.archistartcode.data.datasource.models.calender;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "calender_meal",
        indices = {@Index(value = {"firestore_id"}, unique = true)}
)
public class CalenderMeal {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "firestore_id")
    private String firestoreId;
    @ColumnInfo(name = "userId")
    private String userId;
    @ColumnInfo(name="idMeal")
    private String idMeal;
    @ColumnInfo(name="strMeal")
    private String strMeal;
    @ColumnInfo(name="strCategory")
    private String strCategory;
    @ColumnInfo(name="strArea")
    private String strArea;
    @ColumnInfo(name="strMealThumb")
    private String strMealThumb;
    @ColumnInfo(name="time")
    private long timestamp;


    public String getFirestoreId() {
        return firestoreId;
    }
    public CalenderMeal() {

    }


    public void setFirestoreId(String firestoreId) {
        this.firestoreId = firestoreId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @NonNull
    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(@NonNull String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strMealThumb) {
        this.strMealThumb = strMealThumb;
    }
}
