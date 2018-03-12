package android.example.com.bakingapp;

import android.example.com.bakingapp.data.Recipe;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Abbie on 16/02/2018.
 */

public class RecipeApi {
    public interface RecipesApi {
        @GET("/topher/2017/May/59121517_baking/baking.json")
        void getRecipes(Callback<Recipe.RecipeResult> callback);
    }
}
