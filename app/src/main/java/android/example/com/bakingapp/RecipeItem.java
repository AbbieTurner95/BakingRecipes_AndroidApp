package android.example.com.bakingapp;

import android.content.Context;
import android.example.com.bakingapp.data.Recipe;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Abbie on 16/03/2018.
 */

public class RecipeItem extends AppCompatActivity {

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (!isOnline()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_connection)
                    .setCancelable(false)
                    .setMessage("You seem to have lost your connection, please connect and try again!")
                    .setIcon(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
                    .show();
        }


        IngredientsFragment ingredientsFragment = new IngredientsFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.ingredients_fragment_holder, ingredientsFragment)
                .commit();

    }


    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
