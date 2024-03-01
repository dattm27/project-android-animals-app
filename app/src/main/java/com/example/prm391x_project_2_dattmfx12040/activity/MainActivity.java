package com.example.prm391x_project_2_dattmfx12040.activity;



import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.example.prm391x_project_2_dattmfx12040.R;
import com.example.prm391x_project_2_dattmfx12040.fragment.DetailFragment;
import com.example.prm391x_project_2_dattmfx12040.fragment.MenuFragment;
import com.example.prm391x_project_2_dattmfx12040.model.Animal;

public class MainActivity extends AppCompatActivity {

    private String topicName;
    private  ArrayList listSea = new ArrayList<Animal>();
    private  ArrayList listMammal = new ArrayList<Animal>();
    private  ArrayList listBird = new ArrayList<Animal>();
    private static final int REQUEST_PHONE_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các view
        initViews();

    }


    public void initViews() {

        com.example.prm391x_project_2_dattmfx12040.fragment.MenuFragment menuFragment = new MenuFragment(listSea, listMammal, listBird);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.ln_main, menuFragment, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        //Tao list cac loai vat
        requestPermissions();


        try {
            createAnimalsList();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Yêu cầu quyền truy cập
    private void requestPermissions() {
        String[] permissions = new String[]{android.Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_PHONE_PERMISSIONS);
    }


    // Callback khi người dùng đồng ý hoặc từ chối quyền truy cập
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PHONE_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Nếu đã được cấp quyền, tiến hành xử lý

            } else {
                // Nếu người dùng từ chối quyền truy cập, hiển thị thông báo và tắt ứng dụng
                Toast.makeText(this, "Please allow this permission to use features of the app.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    //Tao Toast hinh anh con vat khi co cuoc goi toi
    public void makeEmojiToast (String incomingNumber) throws Exception{
        //Inflate layout
        View layout = LayoutInflater.from(this).inflate(R.layout.item_toast, (ViewGroup) null);

        //Anh xa view
        ImageView ivToast = layout.findViewById(R.id.ivIconToast);

        //Goi Preferences
        SharedPreferences sharedPref = this.getSharedPreferences("PHONE_TO_PATH", Context.MODE_PRIVATE);
        String photoPath = sharedPref.getString(incomingNumber,"");

        // Neu SDT ton tai
        Log.i("Make Emoji Toast", photoPath);
        if (!photoPath.equals("")){
            // Gan anh vao Toast
            ivToast.setImageBitmap(BitmapFactory.decodeStream(this.getAssets().open(photoPath)));

            //Tao va hien Toast
            Toast toast = new Toast(this);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();

        }

    }

    public void showDetail(ArrayList<Animal> listAnimals, Animal animal) {

        //Học sinh tự xây dựng màn hình DetailFragment dựa vào 2 dữ liệu đầu vào của phương thức
        DetailFragment frg = new DetailFragment() ;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        frg.setData(listAnimals, animal);
        fragmentTransaction.replace(R.id.ln_main, frg);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public void backToMenu(ArrayList<Animal> listAnimals){
        com.example.prm391x_project_2_dattmfx12040.fragment.MenuFragment menuFragment = new MenuFragment(listSea, listMammal, listBird);
        menuFragment.setListAnimals( listAnimals);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.ln_main, menuFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();


    }
    private void createAnimalsList() throws Exception {

        // Tao list Bird
        // Lấy danh sách ảnh trong thư mục …main/assets/photo/

       for (int i = 1 ; i<= 3 ; i++){

           String folder= "" ;//file ảnh icon
           String[] listItem = null;
           switch (i) {
               case 1:
                   listItem = getAssets().list("animal/sea");
                   folder = "animal/sea/";
                   break;
               case 2:
                   listItem = getAssets().list("animal/mammal");
                   folder= "animal/mammal/";
                   break;
               case 3:
                   listItem = getAssets().list("animal/bird");
                   folder = "animal/bird/";
                   break;
           }

           Log.i("Create List", "Type" + folder + "size: " + listItem.length );
           if (listItem.length == 0 ) continue;
           for (String fileName : listItem) {
               String name = fileName.substring(3, fileName.indexOf("."));
               String path = folder +  fileName; //file ảnh icon
               String pathBg = "bg_animal/bg_" + name + ".jpeg"; // file ảnh background
               InputStream in = this.getAssets().open("description/" + name+ ".txt"); // file  mô tả
               BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
               String desc = br.readLine();
               boolean isLove = this.getSharedPreferences("FILE_SAVED", Context.MODE_PRIVATE).getBoolean(name, false);
                Log.i("Main Activity", name + ": " + isLove);
               //Tạo Animal và Add vào List
               Animal newAnimal = new Animal(path, (Bitmap) BitmapFactory.decodeStream( this.getAssets().open(path)),
                       (Bitmap) BitmapFactory.decodeStream( this.getAssets().open(pathBg)),
                       name.substring(0, 1).toUpperCase() + name.substring(1), desc, isLove );

               switch (i) {
                   case 1:
                       listSea.add(newAnimal);
                       break;
                   case 2:
                       listMammal.add(newAnimal);
                       break;
                   case 3:
                       listBird.add(newAnimal);
                       break;
               }
           }
       }

    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.ln_main);
        if (currentFragment instanceof MenuFragment) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            // Terminate the app
            finish();
            System.exit(0);

        } else if  (currentFragment instanceof DetailFragment)  {
            backToMenu(((DetailFragment) currentFragment).getListAnimal());
        } else {
            super.onBackPressed();
        }
    }

}