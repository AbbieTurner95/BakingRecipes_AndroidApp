package android.example.com.bakingapp;

import android.example.com.bakingapp.RecipeDetail.RecipeItem;
import android.example.com.bakingapp.RecipeList.RecipeList;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.AllOf.allOf;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class BakingAppTest {

    @Rule
    public IntentsTestRule<RecipeList> mRecipeListTestRule
            = new IntentsTestRule<>(RecipeList.class);

    @Test
    public void A_checkRecipeListShowsTest() {
        onView(withId(R.id.list_scroll_view))
                .perform(click());

        onView(withId(R.id.list_llayout))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void B_recipeItemIsLaunchedTest() {
        onView(withId(R.id.list_scroll_view))
                .perform(click());

        onView(allOf(withId(R.id.recipe_list_recycler_view)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(RecipeItem.class.getName()));
    }


    @Test
    public void C_simpleExoPlayerTest() {
        onView(withId(R.id.list_scroll_view))
                .perform(click());

        onView(allOf(withId(R.id.recipe_list_recycler_view)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(withId(R.id.detailed_steps_fragment_holder))
                .check(matches(isDisplayed()));

        onView(withId(R.id.video_view))
                .check(matches(isDisplayed()));

        onView(allOf(withId(R.id.video_view),
                withClassName(is(SimpleExoPlayerView.class.getName()))));

    }
}
