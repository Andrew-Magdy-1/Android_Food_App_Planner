package com.example.food_app_planner.archistartcode.presentation.search.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.food_app_planner.R;
import com.example.food_app_planner.archistartcode.data.datasource.models.category.Category;
import com.example.food_app_planner.archistartcode.data.datasource.models.countries.Country;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbyarea.AreaMeals;
import com.example.food_app_planner.archistartcode.data.datasource.models.filterbycategoryname.CategoryDetails;
import com.example.food_app_planner.archistartcode.data.datasource.models.ingredient.Ingredient;
import com.example.food_app_planner.archistartcode.presentation.search.presenter.SearchPresenter;
import com.example.food_app_planner.archistartcode.presentation.search.presenter.SearchPresenterImp;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;


public class SearchIconFragment extends Fragment implements SearchView  {

    private TextInputEditText etSearch;
    private MaterialButton btnFilterCategory, btnFilterIngredient, btnFilterCountry;
    private RecyclerView rvSearchResults;
    private ProgressBar progressBar;
    private FrameLayout noInternetContainer;

    private SearchMealAdapter searchMealAdapter;
    private List<CategoryDetails> allMealsList = new ArrayList<>();
    private List<CategoryDetails> filteredMealsList = new ArrayList<>();

    private SearchPresenter searchPresenter;
    private ProgressDialog loadingDialog;
    private String selectedFilterType = null;
    private String selectedFilterValue = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_icon, container, false);
        initViews(view);
        setupRecyclerView();
        setupClickListeners();
        searchPresenter = new SearchPresenterImp(getContext(), this);
        return view;
    }
    private void initViews(View view) {
        etSearch = view.findViewById(R.id.et_search);
        btnFilterCategory = view.findViewById(R.id.btn_filter_category);
        btnFilterIngredient = view.findViewById(R.id.btn_filter_ingredient);
        btnFilterCountry = view.findViewById(R.id.btn_filter_country);
        rvSearchResults = view.findViewById(R.id.rv_search_results);
        progressBar = view.findViewById(R.id.progress_bar_search);
        //noInternetContainer = view.findViewById(R.id.no_internet_search_container);
    }

    private void setupRecyclerView() {
        searchMealAdapter = new SearchMealAdapter(getContext(), filteredMealsList);
        rvSearchResults.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvSearchResults.setAdapter(searchMealAdapter);
    }

    private void setupClickListeners() {

        btnFilterCategory.setOnClickListener(v -> {
            selectButton(btnFilterCategory, btnFilterIngredient, btnFilterCountry);
            showLoadingDialog("Loading Categories...");
            searchPresenter.getAllCategories();
        });


        btnFilterIngredient.setOnClickListener(v -> {
            selectButton(btnFilterIngredient, btnFilterCategory, btnFilterCountry);
            showLoadingDialog("Loading Ingredients...");
            searchPresenter.getAllIngredients();
        });


        btnFilterCountry.setOnClickListener(v -> {
            selectButton(btnFilterCountry, btnFilterIngredient, btnFilterCategory);
            showLoadingDialog("Loading Countries...");
            searchPresenter.getAllCountries();
        });
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                performSearch();
                return true;
            }
            return false;
        });
    }

    @Override
    public void onCategoriesSuccess(List<Category> categoryList) {
        dismissLoadingDialog();
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categoryList) {
            categoryNames.add(category.getStrCategory());
        }
        showSelectionDialog("Select Category", categoryNames, selectedValue -> {
            selectedFilterType = "category";
            selectedFilterValue = selectedValue;
            btnFilterCategory.setText(selectedValue);
            resetOtherButtons(btnFilterCategory);
            loadFilteredMeals();
        });
    }

    @Override
    public void onCountriesSuccess(List<Country> countryList) {
        dismissLoadingDialog();
        List<String> countryNames = new ArrayList<>();
        for (Country country : countryList) {
            countryNames.add(country.getStrArea());
        }
        showSelectionDialog("Select Country", countryNames, selectedValue -> {
            selectedFilterType = "country";
            selectedFilterValue = selectedValue;
            btnFilterCountry.setText(selectedValue);
            resetOtherButtons(btnFilterCountry);
            loadFilteredMeals();
        });
    }

    @Override
    public void onIngredientsSuccess(List<Ingredient> ingredientList) {
        dismissLoadingDialog();
        List<String> ingredientNames = new ArrayList<>();
        for (Ingredient ingredient : ingredientList) {
            ingredientNames.add(ingredient.getStrIngredient());
        }
        showSelectionDialog("Select Ingredient", ingredientNames, selectedValue -> {
            selectedFilterType = "ingredient";
            selectedFilterValue = selectedValue;
            btnFilterIngredient.setText(selectedValue);
            resetOtherButtons(btnFilterIngredient);
            loadFilteredMeals();
        });
    }

    @Override
    public void onMealsSuccess(List<CategoryDetails> mealList) {
        progressBar.setVisibility(View.GONE);
        rvSearchResults.setVisibility(View.VISIBLE);

        allMealsList.clear();
        allMealsList.addAll(mealList);

        filteredMealsList.clear();
        filteredMealsList.addAll(mealList);
        searchMealAdapter.notifyDataSetChanged();

        if (mealList.isEmpty()) {
            Toast.makeText(getContext(), "No meals found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onAreaMealsSuccess(List<AreaMeals> areaMeals) {
        progressBar.setVisibility(View.GONE);
        rvSearchResults.setVisibility(View.VISIBLE);

        allMealsList.clear();
        for (AreaMeals areaMeal : areaMeals) {
            CategoryDetails meal = new CategoryDetails();
            meal.setIdMeal(areaMeal.getIdMeal());
            meal.setStrMeal(areaMeal.getStrMeal());
            meal.setStrMealThumb(areaMeal.getStrMealThumb());
            allMealsList.add(meal);
        }

        filteredMealsList.clear();
        filteredMealsList.addAll(allMealsList);
        searchMealAdapter.notifyDataSetChanged();

        if (areaMeals.isEmpty()) {
            Toast.makeText(getContext(), "No meals found", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onFailure(String errorMessage) {
        dismissLoadingDialog();
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
    private void showSelectionDialog(String title, List<String> items, OnItemSelectedListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        String[] itemsArray = items.toArray(new String[0]);
        builder.setItems(itemsArray, (dialog, which) -> {
            listener.onItemSelected(itemsArray[which]);
        });

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private void loadFilteredMeals() {
        progressBar.setVisibility(View.VISIBLE);
        rvSearchResults.setVisibility(View.GONE);
        etSearch.setText("");

        if (selectedFilterType != null && selectedFilterValue != null) {
            switch (selectedFilterType) {
                case "category":
                    searchPresenter.filterByCategory(selectedFilterValue);
                    break;
                case "ingredient":
                    searchPresenter.filterByIngredient(selectedFilterValue);
                    break;
                case "country":
                    searchPresenter.filterByCountry(selectedFilterValue);
                    break;
            }
        }
    }

    private void performSearch() {
        String searchQuery = etSearch.getText().toString().trim();

        if (searchQuery.isEmpty()) {
            filteredMealsList.clear();
            filteredMealsList.addAll(allMealsList);
            searchMealAdapter.notifyDataSetChanged();
            return;
        }
        List<CategoryDetails> searchResults = new ArrayList<>();
        for (CategoryDetails meal : allMealsList) {
            if (meal.getStrMeal().toLowerCase().contains(searchQuery.toLowerCase())) {
                searchResults.add(meal);
            }
        }

        filteredMealsList.clear();
        filteredMealsList.addAll(searchResults);
        searchMealAdapter.notifyDataSetChanged();

        if (searchResults.isEmpty()) {
            Toast.makeText(getContext(), "No results found for: " + searchQuery, Toast.LENGTH_SHORT).show();
        }

        // إخفاء الكيبورد
        hideKeyboard();
    }

    private void resetOtherButtons(MaterialButton selectedButton) {
        if (selectedButton != btnFilterCategory) {
            btnFilterCategory.setText("Category");
        }
        if (selectedButton != btnFilterIngredient) {
            btnFilterIngredient.setText("Ingredient");
        }
        if (selectedButton != btnFilterCountry) {
            btnFilterCountry.setText("Country");
        }
    }

    private void showLoadingDialog(String message) {
        if (loadingDialog == null) {
            loadingDialog = new ProgressDialog(getContext());
            loadingDialog.setCancelable(false);
        }
        loadingDialog.setMessage(message);
        loadingDialog.show();
    }

    private void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getView() != null) {
            imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        }
    }

    interface OnItemSelectedListener {
        void onItemSelected(String value);
    }
    private void selectButton(MaterialButton selected, MaterialButton... others) {
        selected.setChecked(true);
        for (MaterialButton btn : others) {
            btn.setChecked(false);
        }
    }

}