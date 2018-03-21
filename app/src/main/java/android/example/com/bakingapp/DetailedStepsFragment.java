package android.example.com.bakingapp;

import android.example.com.bakingapp.data.Steps;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbie on 20/03/2018.
 */

public class DetailedStepsFragment extends Fragment{
    Steps step;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.detailed_step_fragment, container, false);

        TextView description = rootView.findViewById(R.id.detailed_textview);
        TextView stepidTextview = rootView.findViewById(R.id.step_id_textview);


        if (this.getArguments() != null) {
            step = this.getArguments().getParcelable("steps");
            description.setText(step.getDescription());
        }

        stepidTextview.setText(String.valueOf("Step " + step.getSteps_id()));

        return rootView;
    }

}