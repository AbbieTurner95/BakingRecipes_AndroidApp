package android.example.com.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


/**
 * Created by Abbie on 21/03/2018.
 */

public class DetailedStepsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_fragment);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DetailedStepsFragment detailedStepsFragment = new DetailedStepsFragment();
        detailedStepsFragment.setArguments(getIntent().getBundleExtra("steps"));

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.recipe_step_detail_framelayout, detailedStepsFragment)
                .commit();


    }
}
