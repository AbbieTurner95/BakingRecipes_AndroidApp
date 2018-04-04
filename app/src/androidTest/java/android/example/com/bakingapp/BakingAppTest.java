package android.example.com.bakingapp;

import android.example.com.bakingapp.RecipeDetail.RecipeItem;
import android.example.com.bakingapp.RecipeList.RecipeList;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeUp;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)       //allows user to choose order of execution of methods within test class
public class BakingAppTest {

    @Rule
    public IntentsTestRule<RecipeList> mRecipeListTestRule = new IntentsTestRule<>(RecipeList.class);

    @Test
    public void A_checkRecipeListShowsTest() {
        onView(withId(R.id.list_scroll_view))
                .perform(click());

        onView(withId(R.id.list_llayout))
                .perform(click())
                .check(matches(isDisplayed()));
    }

    @Test
    public void B_openRecipeItemTest(){
        onView(withId(R.id.list_scroll_view))
                .perform(click());

        onView(allOf(withId(R.id.recipe_list_recycler_view)))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

    @Test
    public void C_recipeItemIsLaunchedTest() {
        B_openRecipeItemTest();

        intended(hasComponent(RecipeItem.class.getName()));
    }


   @Test
    public void D_simpleExoPlayerTest() {
        B_openRecipeItemTest();

        //get detailed steps single view fragment

        onView(withId(R.id.detailed_steps_fragment_holder))
                .check(matches(isDisplayed()));

        onData(withId(R.id.video_view))
                .check(matches(isDisplayed()));

    }

   @Test
    public void E_stepRecyclerViewTest(){
       B_openRecipeItemTest();

       onView(allOf(withId(R.id.recipe_steps_recycler_view)))
               .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }

   @Test
    public void F_ingredientsRecyclerViewTest() {
        B_openRecipeItemTest();

       //get ingredients single view fragment

       onView(allOf(withId(R.id.recipe_ingredients_recycler_view)))
               .perform(swipeUp());

   }
}
