package com.example.food_app_planner.archistartcode.presentation.countrypage.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.presentation.specificarea.view.OnClickAreaListener;

import java.util.ArrayList;
import java.util.List;

public class CountryPageAdapter extends RecyclerView.Adapter<CountryPageAdapter.CountryVm>{
    private List<Country> countryList;
    private OnClickAreaListener onClickAreaListener;
    private Context context;

    public CountryPageAdapter(Context context){
        countryList=new ArrayList<>();
        this.context=context;

    }
    public void setOnClickAreaListener(OnClickAreaListener listener) {
        this.onClickAreaListener = listener;
    }


    @NonNull
    @Override
    public CountryVm onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item,parent,false);

        return new CountryPageAdapter.CountryVm(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CountryVm holder, int position) {
        Country country=countryList.get(position);
        holder.bind(country);

    }

    @Override
    public int getItemCount() {
        return countryList !=null? countryList.size():0;
    }
    public void setCountryList(List<Country> countryList){
        this.countryList=countryList;
        notifyDataSetChanged();
    }


    class CountryVm extends RecyclerView.ViewHolder{
        private TextView areaname;
        private ImageView imageView;
        public CountryVm(@NonNull View itemView) {
            super(itemView);
            areaname=itemView.findViewById(R.id.mealname);
            imageView=itemView.findViewById(R.id.areaflag);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    onClickAreaListener.getAreaName(countryList.get(position).getStrArea());


                }
            });

        }
        public void bind(Country country){
            String url = "https://www.themealdb.com/images/icons/flags/big/64/" + country.getStrArea() + ".png";
            Glide.with(itemView).load(url).into(imageView);
            areaname.setText(country.getStrArea());
        }
    }
}
