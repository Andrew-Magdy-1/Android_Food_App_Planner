package com.example.food_app_planner.archistartcode.presentation.favouritemeals.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import com.example.food_app_planner.archistartcode.presentation.favouritemeals.presenter.FavMealPresenter;
import com.example.food_app_planner.archistartcode.presentation.favouritemeals.presenter.FavProdPresenterImp;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private static OnFavClickListener onFavClickListener;
    private List<MealById> mealByIdList;
    public FavAdapter(OnFavClickListener onFavClickListener){
        this.onFavClickListener=onFavClickListener;
        this.mealByIdList=new ArrayList<>();

    }
    public void setMealByIdList(List<MealById> mealByIdList){
        this.mealByIdList=mealByIdList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item_card,parent,false);


        return new FavAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealById mealById= mealByIdList.get(position);
        holder.bind(mealById);

    }

    @Override
    public int getItemCount() {
        return mealByIdList == null ? 0 : mealByIdList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mealImage;
        private TextView strMeal;
        private TextView strDesc;
        private ImageButton delBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage=itemView.findViewById(R.id.mealImg);
            strMeal=itemView.findViewById(R.id.mealname);
            strDesc=itemView.findViewById(R.id.descriprtion);
            delBtn=itemView.findViewById(R.id.deleteicon);


        }
        void bind(MealById mealById){
            Glide.with(itemView).load(mealById.getStrMealThumb())
                    .into(mealImage);
            strMeal.setText(mealById.getStrMeal());
            strDesc.setText(mealById.getStrCategory());
            delBtn.setOnClickListener(view ->{
                     onFavClickListener.onClickDel(mealById);
                     onFavClickListener.delFromFire(mealById.getIdMeal());}
            );


        }
    }
}
