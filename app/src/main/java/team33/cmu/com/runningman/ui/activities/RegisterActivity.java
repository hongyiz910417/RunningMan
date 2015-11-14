package team33.cmu.com.runningman.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import team33.cmu.com.runningman.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = (EditText) findViewById(R.id.email_sign_up);
        passwordEditText = (EditText) findViewById(R.id.password_sign_up);
        passwordConfirmEditText = (EditText) findViewById(R.id.password_sign_up_confirm);

        Button signUpButton = (Button)findViewById(R.id.register_button);
        signUpButton.setOnClickListener(signUpButtonClicked);

        Button takePhotoButton = (Button) findViewById(R.id.take_photo_button);
        takePhotoButton.setOnClickListener(takePhotoButtonClicked);
    }

    View.OnClickListener takePhotoButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(RegisterActivity.this, TakePhotoActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener signUpButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (emailEditText.getText().length() != 0) {
                AsyncTask<Object, Object, Object> saveAccountTask =
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... params) {
                                //saveContact(); // save contact to the database
                                return null;
                            } // end method doInBackground

                            @Override
                            protected void onPostExecute(Object result) {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(RegisterActivity.this);

                                // set dialog title & message, and provide Button to dismiss
                                builder.setTitle(R.string.success_register);
                                builder.setMessage("Successful register user.");
                                builder.setPositiveButton(R.string.successButton, new DialogInterface.OnClickListener() { // define the 'Cancel' button
                                    public void onClick(DialogInterface dialog, int which) {
                                        //Either of the following two lines should work.
                                        dialog.cancel();
                                    }
                                });
                                builder.show(); // display the Dialog

                                finish(); // return to the previous Activity
                            } // end method onPostExecute
                        }; // end AsyncTask

                // save the contact to the database using a separate thread
                saveAccountTask.execute((Object[]) null);
            } // end if
            else {
                // create a new AlertDialog Builder
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(RegisterActivity.this);

                // set dialog title & message, and provide Button to dismiss
                builder.setTitle(R.string.error_field_required);
                builder.setMessage(R.string.error_invalid_email);
                builder.setPositiveButton(R.string.errorButton, null);

                builder.show(); // display the Dialog
            } // end else
        } // end method onClick

    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
