package com.example.food_app_planner.archistartcode.database.calendermeal;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;


@Database(entities = CalenderMeal.class,version = 1)
public abstract class CalenderMealDataBase extends RoomDatabase {
    public abstract CalenderMealDAO calenderMealDAO();
    public static CalenderMealDataBase instanse=null;
    public static CalenderMealDataBase getInstanse(Context context){
        if(instanse==null) {
            instanse= Room.databaseBuilder(context.getApplicationContext(),
             CalenderMealDataBase.class,
                    "calender_meal_db"
            ).build();

        }
        return instanse;
    }
}
