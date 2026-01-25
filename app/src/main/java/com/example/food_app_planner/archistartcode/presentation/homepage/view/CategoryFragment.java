//package com.example.food_app_planner.archistartcode.presentation.homepage.view;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.food_app_planner.R;
//import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
//
//import java.util.List;
//
//
//public class CategoryFragment extends Fragment {
//    private RecyclerView recyclerView;
//    private CategoryAdapter categoryAdapter;
//
//
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View v=inflater.inflate(R.layout.fragment_category_frament, container, false);
//
//
//
//        return v;
//    }
//    public void setCategoryList(List<Category> categories) {
//        if (categoryAdapter != null) {
//            categoryAdapter.setCategoryList(categories);
//        }
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        recyclerView=view.findViewById(R.id.categoryRc);
//        categoryAdapter=new CategoryAdapter();
//        recyclerView.setLayoutManager(
//                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
//        );
//        recyclerView.setAdapter(categoryAdapter);
//
//    }
//}