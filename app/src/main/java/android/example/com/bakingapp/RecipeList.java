package android.example.com.bakingapp;

import android.content.Context;
import android.example.com.bakingapp.data.Ingredients;
import android.example.com.bakingapp.data.Recipe;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class RecipeList extends AppCompatActivity {

    Ingredients ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (!isOnline()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_connection)
                    .setCancelable(false)
                    .setMessage("You seem to have lost your connection, please connect and try again!")
                    .setIcon(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
                    .show();
        }


        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ingredients", ingredients);
        recipeFragment.setArguments(bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager
                .beginTransaction()
                .replace(R.id.recipe_list_framelayout, recipeFragment)
                .commit();

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}