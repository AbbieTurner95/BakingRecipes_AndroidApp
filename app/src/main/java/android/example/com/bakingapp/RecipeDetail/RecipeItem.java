package android.example.com.bakingapp.RecipeDetail;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.example.com.bakingapp.R;
import android.example.com.bakingapp.RecipeDetail.DetailedStepsFragment;
import android.example.com.bakingapp.RecipeDetail.IngredientsFragment;
import android.example.com.bakingapp.RecipeDetail.StepsFragment;
import android.example.com.bakingapp.data.Ingredients;
import android.example.com.bakingapp.data.Steps;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbie on 16/03/2018.
 */

public class RecipeItem extends AppCompatActivity implements StepsFragment.OnStepsClickListener{
    List<Ingredients> ingredients;
    List<Steps> steps;
    Boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_item);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        if (getIntent().getExtras().get("ingredients") != null || getIntent().getExtras().get("steps") != null) {
            ingredients = this.getIntent().getParcelableArrayListExtra("ingredients");
            steps = this.getIntent().getParcelableArrayListExtra("steps");
        } else {
            Toast.makeText(getApplicationContext(), "Error getting recipe data!", Toast.LENGTH_LONG).show();
        }

        if(findViewById(R.id.linear_layout_tablet_holder) != null){

            DetailedStepsFragment detailedStepsFragment = new DetailedStepsFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("steps",steps.get(0));
            detailedStepsFragment.setArguments(bundle);

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.detailed_steps_fragment_holder, detailedStepsFragment)
                    .commit();

            mTwoPane = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        } else {

            mTwoPane = false;
        }

        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) ingredients);
        ingredientsFragment.setArguments(bundle);

        StepsFragment stepsFragment = new StepsFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) steps);
        stepsFragment.setArguments(bundle2);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_fragment_holder, ingredientsFragment)
                .add(R.id.steps_fragment_holder, stepsFragment)
                .commit();


        if (!isOnline()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_connection)
                    .setCancelable(false)
                    .setMessage("You seem to have lost your connection, please connect and try again!")
                    .setIcon(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
                    .show();
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    @Override
    public void onStepSelected(List<Steps> steps) {


    }
}