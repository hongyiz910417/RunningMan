package team33.cmu.com.runningman.ui.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import team33.cmu.com.runningman.R;
import team33.cmu.com.runningman.dbLayout.RegisterDBManager;
import team33.cmu.com.runningman.entities.User;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * Team33
 * RunningMan
 * Date: 12/10/15
 **/
public class RegisterActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final int UPLOAD_REQUEST = 100;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private ImageView imageView;
    private Bitmap mPhoto = null;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = (EditText) findViewById(R.id.email_sign_up);
        passwordEditText = (EditText) findViewById(R.id.password_sign_up);
        passwordConfirmEditText = (EditText) findViewById(R.id.password_sign_up_confirm);

        imageView = (ImageView) findViewById(R.id.imageView);

        if (savedInstanceState != null)
            bitmap = savedInstanceState.getParcelable("bitmap");
        if (bitmap == null){
            setImage(imageView,"image2.png");

        }
        else{
            imageView.setImageBitmap(bitmap);
            mPhoto=bitmap;
        }

        Button signUpButton = (Button)findViewById(R.id.register_button);
        signUpButton.setOnClickListener(signUpButtonClicked);

        Button takePhotoButton = (Button) findViewById(R.id.take_photo_button);
        takePhotoButton.setOnClickListener(takePhotoButtonClicked);

        Button uploadPhotoButton = (Button) findViewById(R.id.upload_photo_button);
        uploadPhotoButton.setOnClickListener(uploadPhotoButtonClicked);
    }

    View.OnClickListener takePhotoButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
    };

    View.OnClickListener uploadPhotoButtonClicked = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, UPLOAD_REQUEST);
        }
    };

    private void saveUser(User user){
        RegisterDBManager m = new RegisterDBManager();
        try {
            m.insertUser(user.getName(),user.getPassword(), user.getPhoto());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void setImage(ImageView image, String url){
        try {
            // get input stream
            InputStream ims = this.getAssets().open(url);
            // load image as Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // set image to ImageView
            image.setImageDrawable(d);
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }


    }

    View.OnClickListener signUpButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (emailEditText.getText().length() != 0 && passwordEditText.getText().length()>=4 &&
                    passwordConfirmEditText.getText().length() == passwordEditText.getText().length() &&
                    passwordConfirmEditText.getText().toString().equals(passwordEditText.getText().toString())) {
                AsyncTask<Object, Object, Object> saveAccountTask =
                        new AsyncTask<Object, Object, Object>() {

                            String username = emailEditText.getText().toString();
                            String password = passwordEditText.getText().toString();
                            Bitmap photo = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                            User user = new User(username, password,Bitmap.createScaledBitmap(photo, 50, 50, false));
                            @Override
                            protected Object doInBackground(Object... params) {
                                saveUser(user); // save contact to the database
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
                                        finish();
                                    }
                                });
                                builder.show(); // display the Dialog
                            } // end method onPostExecute
                        }; // end AsyncTask

                // save the contact to the database using a separate thread
                saveAccountTask.execute((Object[]) null);
            } // end if
            else if (emailEditText.getText().length() == 0){
                // create a new AlertDialog Builder
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(RegisterActivity.this);

                // set dialog title & message, and provide Button to dismiss
                builder.setTitle(R.string.error_field_required);
                builder.setMessage(R.string.error_invalid_email);
                builder.setPositiveButton(R.string.errorButton, null);

                builder.show(); // display the Dialog
            }
            else if (passwordEditText.getText().length()<4){
                // create a new AlertDialog Builder
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(RegisterActivity.this);

                // set dialog title & message, and provide Button to dismiss
                builder.setTitle(R.string.error_field_required);
                builder.setMessage("Password too short!");
                builder.setPositiveButton(R.string.errorButton, null);

                builder.show(); // display the Dialog
            }
            else{
                // create a new AlertDialog Builder
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(RegisterActivity.this);

                // set dialog title & message, and provide Button to dismiss
                builder.setTitle(R.string.error_field_required);
                builder.setMessage("Passwords entered differ!");
                builder.setPositiveButton(R.string.errorButton, null);

                builder.show(); // display the Dialog
            }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream stream = null;
        if (requestCode == UPLOAD_REQUEST && resultCode == Activity.RESULT_OK)
            try {
                // recyle unused bitmaps
                if (bitmap != null) {
                    bitmap.recycle();
                }
                try {
                    stream = getContentResolver().openInputStream(data.getData());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                bitmap = BitmapFactory.decodeStream(stream,new Rect(),options);
                mPhoto = Bitmap.createScaledBitmap(bitmap, 50, 50, true);
                System.out.println(mPhoto.getByteCount());
                imageView.setImageBitmap(mPhoto);

            } finally {
                if (stream != null)
                    try {
                        stream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            mPhoto = Bitmap.createScaledBitmap(photo, 50, 50, true);
            System.out.println(mPhoto.getByteCount());
            imageView.setImageBitmap(mPhoto);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle toSave) {
        super.onSaveInstanceState(toSave);
        toSave.putParcelable("bitmap", mPhoto);
    }

}
