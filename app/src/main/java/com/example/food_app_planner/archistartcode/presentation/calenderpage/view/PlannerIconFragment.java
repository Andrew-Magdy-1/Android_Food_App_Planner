package com.example.food_app_planner.archistartcode.presentation.calenderpage.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.presentation.calenderpage.presenter.CalenderMealPresenter;
import com.example.food_app_planner.archistartcode.presentation.calenderpage.presenter.CalenderMealPresenterImp;

import java.util.Calendar;
import java.util.List;

public class PlannerIconFragment extends Fragment implements PlannerView,OnCalenderMealClickListener{
    private RecyclerView recyclerView;
    private CalendarView calendarView;
    private PlannerAdapter plannerAdapter;
    private CalenderMealPresenter calenderMealPresenter;
    public PlannerIconFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_planner_icon, container, false);
        recyclerView=v.findViewById(R.id.calendermealsrc);
        calendarView=v.findViewById(R.id.calender);
        plannerAdapter=new PlannerAdapter(this);
        recyclerView.setAdapter(plannerAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false)
        );
        calenderMealPresenter=new CalenderMealPresenterImp(getContext());
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        long minDate = today.getTimeInMillis();
        Calendar endOf2050 = Calendar.getInstance();
        endOf2050.set(2050, Calendar.DECEMBER, 31, 23, 59, 59);
        long maxDate = endOf2050.getTimeInMillis();
        calendarView.setMinDate(minDate);
        calendarView.setMaxDate(maxDate);
        calendarView.setDate(minDate);
//        calenderMealPresenter.getFromFireStore();

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth, 0, 0, 0);
                long startDay = cal.getTimeInMillis();

                cal.set(year, month, dayOfMonth, 23, 59, 59);
                long endDay = cal.getTimeInMillis();
                calenderMealPresenter.getCalenderMealPresenetr(startDay,endDay).observe(getViewLifecycleOwner(), new Observer<List<CalenderMeal>>() {
                    @Override
                    public void onChanged(List<CalenderMeal> calenderMeals) {
                        plannerAdapter.setCalenderMeals(calenderMeals);
                    }
                });
            }
        });
        return v;
    }

//    @Override
//    public void onDelMeal(CalenderMeal calenderMeal) {
//        calenderMealPresenter.delCalenderMeal(calenderMeal);
//
//    }

    @Override
    public void onSuccess(LiveData<List<CalenderMeal>> calenderMeals) {

    }

    @Override
    public void onClickDel(CalenderMeal calenderMeal) {
        calenderMealPresenter.delCalenderMeal(calenderMeal);

    }

    @Override
    public void delFromFire(String id) {
        calenderMealPresenter.getFromFireStore(id);

    }
}