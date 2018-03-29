package android.example.com.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.example.com.bakingapp.RecipeList.RecipeAdapter;
import android.example.com.bakingapp.Service.RecipeApi;
import android.example.com.bakingapp.data.Ingredients;
import android.example.com.bakingapp.data.Recipe;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WidgetActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {
    private RecipeApi.RecipesApi service;
    private List<Recipe> recipes;
    private List<Ingredients> ingredients;

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private GridLayoutManager layoutManager;
    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private AppWidgetManager widgetManager;
    private RemoteViews views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_CANCELED);
        setContentView(R.layout.activity_widget);

        recyclerView = findViewById(R.id.recipe_list_recycler_view_widget);
        layoutManager = new GridLayoutManager(this, 2);
        recipeAdapter = new RecipeAdapter(this, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(recipeAdapter);
        recipes = new ArrayList<>();

        recipeAdapter.setRecipesList(recipes);

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://d17h27t6h515a5.cloudfront.net")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        service = restAdapter.create(RecipeApi.RecipesApi.class);
        showRecipes();

        widgetManager = AppWidgetManager.getInstance(this);
        views = new RemoteViews(this.getPackageName(), R.layout.baking_widget_provider);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
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
        views.setTextViewText(R.id.wigdet_title, recipe.getName());
        ingredients=recipe.getIngredients();
        StringBuilder ing= new StringBuilder();
        for (Ingredients ingredient:ingredients){
            ing.append("- ")
                    .append(ingredient.getIngredient())
                    .append(" ")
                    .append(ingredient.getMeasure())
                    .append(" ")
                    .append(ingredient.getQuantity())
                    .append("\n");
        }

        views.setTextViewText(R.id.baking_widget_ingredients, ing.toString());

        widgetManager.updateAppWidget(mAppWidgetId, views);
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}