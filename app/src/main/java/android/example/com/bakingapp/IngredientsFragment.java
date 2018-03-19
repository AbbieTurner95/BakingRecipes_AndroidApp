package android.example.com.bakingapp;

import android.content.res.Configuration;
import android.example.com.bakingapp.data.Ingredients;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbie on 16/03/2018.
 */

public class IngredientsFragment extends Fragment implements IngredientsAdapter.IngredientsListener{
    private List<Ingredients> ingredients;

    private RecyclerView recyclerView;
    private IngredientsAdapter ingredientsAdapter;
    private GridLayoutManager layoutManager;

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.ingredients_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recipe_ingredients_recycler_view);
        layoutManager = new GridLayoutManager(getContext(), 2);
        ingredientsAdapter = new IngredientsAdapter(getContext(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(ingredientsAdapter);
        ingredients = new ArrayList<>();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ingredients=bundle.getParcelableArrayList("ingredients");
            ingredientsAdapter.setIngredientsList(ingredients);
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
    public void onIngredientsItemClick(Ingredients ingredients) {

    }
}