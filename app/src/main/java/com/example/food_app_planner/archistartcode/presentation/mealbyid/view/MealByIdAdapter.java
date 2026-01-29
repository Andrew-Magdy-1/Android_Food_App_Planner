package com.example.food_app_planner.archistartcode.presentation.mealbyid.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.filtermealbyid.MealById;
import java.util.ArrayList;
import java.util.List;

public class MealByIdAdapter extends RecyclerView.Adapter<MealByIdAdapter.MealVh> {

    private List<String> ingredientsList = new ArrayList<>();
    private List<String> measureList = new ArrayList<>();
    private MealOnClickLIistener mealOnClickLIistener;
    public MealByIdAdapter( MealOnClickLIistener mealOnClickLIistener) {
        this.mealOnClickLIistener=mealOnClickLIistener;
    }


    public void setLists(MealById mealById) {
        this.ingredientsList = mealById.getAllIngredients();
        this.measureList = mealById.getAllMeasures();


        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_ingredient, parent, false);
        Log.d("MealByIdAdapter", "onCreateViewHolder called");
        return new MealVh(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MealVh holder, int position) {
        String ingredient = ingredientsList.get(position);
        String measure = measureList.get(position);

        holder.bind(ingredient, measure);
    }

    @Override
    public int getItemCount() {
        int count = ingredientsList.size();
        Log.d("MealByIdAdapter", "getItemCount: " + count);
        return count;
    }

    class MealVh extends RecyclerView.ViewHolder {
        private ImageView ingimg;
        private TextView ingName;
        private TextView ingInst;

        public MealVh(@NonNull View itemView) {
            super(itemView);
            ingimg = itemView.findViewById(R.id.imeIngredient);
            ingName = itemView.findViewById(R.id.ingName);
            ingInst = itemView.findViewById(R.id.ingInst);
        }

        void bind(String ingredient, String measure) {
            String url = "https://www.themealdb.com/images/ingredients/" + ingredient + ".png";
            Log.d("MealByIdAdapter", "Loading image: " + url);
            Glide.with(itemView.getContext())
                    .load(url)
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .into(ingimg);
            ingName.setText(ingredient);
            ingInst.setText(measure);
        }
    }
}