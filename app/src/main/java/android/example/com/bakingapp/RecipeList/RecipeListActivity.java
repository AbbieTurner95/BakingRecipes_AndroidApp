package android.example.com.bakingapp.RecipeList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.example.com.bakingapp.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RecipeListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        if (!isOnline()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_connection)
                    .setCancelable(false)
                    .setMessage("You seem to have lost your connection, please connect and try again!")
                    .setIcon(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
                    .setPositiveButton("Go to Connection Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);

                            dialog.cancel();
                        }
                    }).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}