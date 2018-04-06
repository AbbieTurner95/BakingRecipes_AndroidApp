package android.example.com.bakingapp;

import android.example.com.bakingapp.RecipeDetail.RecipeItem;
import android.example.com.bakingapp.RecipeList.RecipeList;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.core.AllOf.allOf;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)       //allows user to choose order of execution of methods within test class
public class RecipeOpensTest {

    @Rule
    public IntentsTestRule<RecipeList> mRecipeListTestRule = new IntentsTestRule<>(RecipeList.class);

    @Test
    public void A_checkRecipeListShowsTest() {
        onView(withId(R.id.list_scroll_view))
                .perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.list_llayout))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void B_openRecipeItemTest(){
        onView(withId(R.id.list_scroll_view))
                .perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(allOf(withId(R.id.recipe_list_recycler_view)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void C_recipeItemIsLaunchedTest() {
        B_openRecipeItemTest();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        intended(hasComponent(RecipeItem.class.getName()));
    }
}