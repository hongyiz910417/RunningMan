package team33.cmu.com.runningman.entities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by d on 11/13/15.
 */
public class User {
    private static User user;
    private String name;
    private String password;
    private byte[] photo;

    private User(){}

    public User (String name, String password){
        this.name = name;
        this.password = password;
    }

    public User (String name, String password, Bitmap photo){
        this.name = name;
        this.password = password;
        this.photo = Bitmap2Bytes(photo);
    }

    private byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 50, baos);
        return baos.toByteArray();
    }
    private Bitmap Bytes2Bimap(byte[] b){
        if(b.length!=0){
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        else {
            return null;
        }
    }

    public static User getUser(){
        if(user == null){
            user = new User();
        }
        return user;
    }

    public String getName() {
        return name;
    }

    public byte[] getPhoto(){
        return this.photo;
    }

    public Bitmap getBitmapPhoto() {
        return Bytes2Bimap(photo);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return hashedPassword(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean authenticate(){


        return false;
    }

    private String hashedPassword(String password){

        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = null;
            md = MessageDigest.getInstance("MD5");

            //Add password bytes to digest
            md.update(password.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        System.out.println(generatedPassword);
        return generatedPassword;
    }

}
