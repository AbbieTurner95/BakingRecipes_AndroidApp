package android.example.com.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;

import android.example.com.bakingapp.data.Steps;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbie on 16/03/2018.
 */

public class StepsFragment extends Fragment implements StepsAdapter.StepsListener{
    private List<Steps> steps;

    private RecyclerView recyclerView;
    private StepsAdapter stepsAdapter;
    private GridLayoutManager layoutManager;

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;

    OnStepsClickListener mCallback;

    public interface OnStepsClickListener {
        void onStepSelected(List<Steps> steps);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.steps_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recipe_steps_recycler_view);
        layoutManager = new GridLayoutManager(getContext(), 1);
        stepsAdapter = new StepsAdapter(getContext(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(stepsAdapter);
        steps = new ArrayList<>();

        recyclerView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onStepSelected(steps);
            }
        });

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            steps = bundle.getParcelableArrayList("steps");
            stepsAdapter.setStepsList(steps);
        }

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        layoutManager.setSpanCount(2);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
    }


    @Override
    public void onStepsItemClick(Steps steps) {
        FragmentManager fragmentManager = this.getFragmentManager();

        DetailedStepsFragment detailedStepsFragment = new DetailedStepsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("step",steps);
        detailedStepsFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .replace(R.id.detailed_steps_fragment_holder, detailedStepsFragment)
                .commit();
    }
}