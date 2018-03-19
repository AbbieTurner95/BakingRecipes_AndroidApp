package android.example.com.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Abbie on 12/03/2018.
 */

public class Result implements Parcelable {

    private List<Recipe> results;

    public Result(List<Recipe> results) {
        this.results = results;
    }

    public Result() {}

    protected Result(Parcel in) {
        results = in.createTypedArrayList(Recipe.CREATOR);
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public List<Recipe> getResults() {
        return results;
    }

    public void setResults(List<Recipe> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(results);
    }
}
