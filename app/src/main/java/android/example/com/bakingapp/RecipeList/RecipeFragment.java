package android.example.com.bakingapp.RecipeList;

import android.content.Intent;
import android.content.res.Configuration;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.RecipeDetail.RecipeItemActivity;
import android.example.com.bakingapp.data.Ingredients;
import android.example.com.bakingapp.data.Recipe;
import android.example.com.bakingapp.data.Steps;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.example.com.bakingapp.Service.RecipeApi.RecipesApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RecipeFragment extends Fragment implements RecipeAdapter.RecipeClickListener {

    private RecipesApi service;
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private GridLayoutManager layoutManager;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);

        recyclerView = rootView.findViewById(R.id.recipe_list_recycler_view);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recipeAdapter = new RecipeAdapter(getContext(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeAdapter);
        List<Recipe> recipes = new ArrayList<>();

        recipeAdapter.setRecipesList(recipes);

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://d17h27t6h515a5.cloudfront.net")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        service = restAdapter.create(RecipesApi.class);
        showRecipes();
    }

    @Override
    public void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mBundleRecyclerViewState != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    recyclerView.getLayoutManager().onRestoreInstanceState(mListState);
                }
            }, 50);
        }

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager.setSpanCount(2);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager.setSpanCount(2);
        }
        recyclerView.setLayoutManager(layoutManager);
    }


    public void showRecipes() {
        service.getRecipes(new Callback<List<Recipe>>() {
            @Override
            public void success(List<Recipe> recipeResult, Response response) {
                recipeAdapter.setRecipesList(recipeResult);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onRecipeItemClick(Recipe recipe) {
        List<Ingredients> ingredients;
        ingredients = recipe.getIngredients();

        List<Steps> steps;
        steps = recipe.getSteps();

        Intent intent = new Intent(getActivity(), RecipeItemActivity.class);
        intent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) ingredients);
        intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) steps);
        startActivity(intent);
    }
}