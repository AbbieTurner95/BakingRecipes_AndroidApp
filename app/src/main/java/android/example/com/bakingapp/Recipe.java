package android.example.com.bakingapp;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Abbie on 12/03/2018.
 */

public class Recipe {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("servings")
    private int servings;
    @SerializedName("image")
    private String image;


    public static class RecipeResult {
        private List<Recipe> results;
        public List<Recipe> getResults() {
            return results;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

