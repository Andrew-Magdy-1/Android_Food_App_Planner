package com.example.food_app_planner.archistartcode.presentation.mealbyid.view;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.calender.CalenderMeal;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.data.datasource.remote.firebaseauth.FirebaseManager;
import com.example.food_app_planner.archistartcode.presentation.auth.view.AuthActivity;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.presenter.MealByIdPresenter;
import com.example.food_app_planner.archistartcode.presentation.mealbyid.presenter.MealByIdPresenterImp;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.util.Calendar;

public class MealByIdFragment extends Fragment implements MealByIdView,OnClickMealListener,MealOnClickLIistener {
    private String id;
    private RecyclerView recyclerView;
    private MealByIdPresenter mealByIdPresenter;
    private MealByIdAdapter mealByIdAdapter;
    private ImageView mealImage;
    private TextView mealTitle;
    private TextView mealDesc;
    private TextView mealCat;
    private YouTubePlayerView youtubePlayerView;
    private FloatingActionButton favBtn;
    private MealOnClickLIistener mealOnClickLIistener;
    private Button addToPlan;

    public MealByIdFragment() {

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
        addToPlan=v.findViewById(R.id.btnAddToPlan);
        favBtn=v.findViewById(R.id.fabFavorite);
        id = getArguments().getString("Meal_id");
        mealByIdPresenter=new MealByIdPresenterImp(getContext(),this,id);
        mealByIdAdapter=new MealByIdAdapter(this);
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
        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mealOnClickLIistener != null) {
                    String userId = FirebaseManager.getInstance().getCurrentUserId();
                    if (userId != null) {
                        mealByIdList.setUserId(userId);
                    }
                   mealByIdPresenter.insertProductToFav(mealByIdList);


                }

            }
        });
        addToPlan.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    requireContext(),
                    (view, year, month, dayOfMonth) -> {

                        Calendar selectedCal = Calendar.getInstance();
                        selectedCal.set(year, month, dayOfMonth, 0, 0, 0);

                        CalenderMeal calenderMeal = new CalenderMeal();
                        calenderMeal.setIdMeal(mealByIdList.getIdMeal());
                        calenderMeal.setStrMeal(mealByIdList.getStrMeal());
                        calenderMeal.setStrMealThumb(mealByIdList.getStrMealThumb());
                        calenderMeal.setStrArea(mealByIdList.getStrArea());
                        calenderMeal.setStrCategory(mealByIdList.getStrCategory());
                        calenderMeal.setTimestamp(selectedCal.getTimeInMillis());
                        mealByIdPresenter.insertToCalender(calenderMeal);
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            );

            datePickerDialog.show();
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MealOnClickLIistener) {
            mealOnClickLIistener = (MealOnClickLIistener) context;
        } else {
            mealOnClickLIistener = this;
        }
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
    public void showRegisterRequiredDialog() {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Login")
                .setMessage("Please login to use this feature 🥹")
                .setPositiveButton("Login", (dialog, which) -> performLogin())
                .setNegativeButton("Cancel", null)
                .setBackground(requireContext().getResources().getDrawable(R.drawable.dialog_background, requireContext().getTheme()))
                .show();

    }
    private void performLogin() {
        try {
           FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(requireContext(), AuthActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            requireActivity().finish();
            Toast.makeText(requireContext(), "Redirecting to Login...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("MealByIdFragment", "Navigation failed: " + e.getMessage());
        }
    }

    @Override
    public void onClickMeal(String id) {
        this.id=id;

    }


    @Override
    public void addMealToFav(MealById meal) {
       // mealByIdPresenter.insertProductToFav(meal);
        //Toast.makeText(getContext(), "Added to favorites!", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void addMealToPlan(CalenderMeal calenderMeal) {
       // mealByIdPresenter.insertToCalender(calenderMeal);
        //Toast.makeText(getContext(), "Added to plan!", Toast.LENGTH_SHORT).show();
    }
}