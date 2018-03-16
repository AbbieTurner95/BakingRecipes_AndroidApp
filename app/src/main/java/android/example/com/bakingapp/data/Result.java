package android.example.com.bakingapp.data;

import java.util.List;

/**
 * Created by Abbie on 12/03/2018.
 */

public class Result {

    private List<Recipe> results;

    public Result(List<Recipe> results) {
        this.results = results;
    }

    public Result() {}

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }
}
