package com.example.food_app_planner.archistartcode.presentation.mealbyid.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.presenter.MealByIdPresenter;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.presenter.MealByIdPresenterImp;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.List;

public class MealByIdFragment extends Fragment implements MealByIdView,OnClickMealListener {
    private String id;
    private RecyclerView recyclerView;
    private MealByIdPresenter mealByIdPresenter;
    private MealByIdAdapter mealByIdAdapter;
    private ImageView mealImage;
    private TextView mealTitle;
    private TextView mealDesc;
    private TextView mealCat;
    private YouTubePlayerView youtubePlayerView;

    public MealByIdFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_meal_by_id, container, false);
        recyclerView=v.findViewById(R.id.rvIngredients);
        mealImage=v.findViewById(R.id.ivFoodImage);
        mealTitle=v.findViewById(R.id.tvFoodTitle);
        mealCat=v.findViewById(R.id.catName);
        mealDesc=v.findViewById(R.id.tvDescription);
        youtubePlayerView= v.findViewById(R.id.youtubePlayerView);
        id = getArguments().getString("Meal_id");
        mealByIdPresenter=new MealByIdPresenterImp(getContext(),this,id);
        mealByIdAdapter=new MealByIdAdapter();
        recyclerView.setAdapter(mealByIdAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        ));

        mealByIdPresenter.getMealById();

        return v;
    }
    @Override
    public void onSuccess(MealById mealByIdList) {
        Glide.with(MealByIdFragment.this).load(mealByIdList.getStrMealThumb()).into(mealImage);
        mealTitle.setText(mealByIdList.getStrMeal());
        mealCat.setText(mealByIdList.getStrCategory());
        mealDesc.setText(mealByIdList.getStrInstructions());
        mealByIdAdapter.setLists(mealByIdList);
        setupYouTubePlayer(mealByIdList.getStrYoutube());

    }
    private void setupYouTubePlayer(String youtubeUrl) {
        if (youtubeUrl == null || youtubeUrl.isEmpty()) {
            youtubePlayerView.setVisibility(View.GONE);
            return;
        }

        String videoId = extractVideoId(youtubeUrl);

        getLifecycle().addObserver(youtubePlayerView);

        youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(videoId, 0);
            }
        });
    }
    private  String extractVideoId(String youtubeUrl) {
        if (youtubeUrl.contains("v=")) {
            return youtubeUrl.split("v=")[1].split("&")[0];
        }
        else if (youtubeUrl.contains("youtu.be/")) {
            return youtubeUrl.split("youtu.be/")[1].split("\\?")[0];
        }
        return "";
    }

    @Override
    public void onFailure(String errorMessage) {

    }

    @Override
    public void onClickMeal(String id) {
        this.id=id;

    }
}