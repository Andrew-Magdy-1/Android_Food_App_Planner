package com.example.food_app_planner.archistartcode.database.favourits;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;

@Database(entities = {MealById.class},version = 1)
public abstract class AddMealToDb extends RoomDatabase {
    public abstract MealDAO mealDAO();
    public static AddMealToDb instance=null;

    public static AddMealToDb getInstance(Context context){
        if(instance ==null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AddMealToDb.class,
                    "mealdb"
                    ).build();
        }
        return instance;

    }

}