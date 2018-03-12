package android.example.com.bakingapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Abbie on 12/03/2018.
 */

public class Ingredients {

    @SerializedName("ingredients")
    private String ingredients;
    @SerializedName("quantity")
    private int quantity;
    @SerializedName("measure")
    private String measure;
    @SerializedName("ingredient")
    private String ingredient;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
