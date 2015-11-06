package team33.cmu.com.runningman;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        first_time_check();
        setContentView(R.layout.activity_main);
    }
    private void first_time_check(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);

        String user_id = prefs.getString( "user_id", null ); // First arg is name and second is if not found.
        System.out.println(user_id);
        String first_time_cookie = prefs.getString( "first_time_cookie" , null );

        if ( user_id == null ) {
            // User id is null so they must have logged out.
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }
        else {
            // Make a remote call to the database to increment downloads number
        }
        return;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
