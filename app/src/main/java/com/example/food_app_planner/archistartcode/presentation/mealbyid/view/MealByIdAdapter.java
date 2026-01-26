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

    // ✅ احذف mealByIdList - مش محتاجينها
    private List<String> ingredientsList = new ArrayList<>();
    private List<String> measureList = new ArrayList<>();

    public MealByIdAdapter() {
    }

    // ✅ عدّل الـ method
    public void setLists(MealById mealById) {
        this.ingredientsList = mealById.getAllIngredients();
        this.measureList = mealById.getAllMeasures();

        Log.d("MealByIdAdapter", "setLists called");
        Log.d("MealByIdAdapter", "Ingredients: " + ingredientsList.size());
        Log.d("MealByIdAdapter", "Measures: " + measureList.size());

        if (ingredientsList.size() > 0) {
            Log.d("MealByIdAdapter", "First ingredient: " + ingredientsList.get(0));
        }

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

        Log.d("MealByIdAdapter", "onBindViewHolder - Position: " + position);
        Log.d("MealByIdAdapter", "Binding: " + ingredient + " - " + measure);

        holder.bind(ingredient, measure);
    }

    @Override
    public int getItemCount() {
        // ✅ هنا المشكلة - كان بيرجع mealByIdList.size() اللي كانت فاضية!
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
            // ✅ أضف .png في آخر الـ URL
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