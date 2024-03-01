package com.example.prm391x_project_2_dattmfx12040.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.prm391x_project_2_dattmfx12040.model.Animal;
import com.example.prm391x_project_2_dattmfx12040.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailAnimalAdapter extends PagerAdapter {
    private final List<Animal> listAnimal;
    private final Context mcontext;


    public DetailAnimalAdapter(List<Animal> listAnimal, Context mcontext) {
        this.listAnimal = listAnimal;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return listAnimal.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)  {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.item_detail, container, false);
        Animal item = listAnimal.get(position);
        String name  = item.getName().toLowerCase(Locale.ROOT);

        // Để ghi dữ liệu vào một tập tin SharedPreferences trong Android (Favorite)
        SharedPreferences sharedPrefFav = mcontext.getSharedPreferences("FILE_SAVED", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorFav = sharedPrefFav.edit();

        //Ghi du lieu vao tep tin, Key : PhoneNumber , Value: photoPath -> Call Listener
        SharedPreferences PhonePath = mcontext.getSharedPreferences("PHONE_TO_PATH", Context.MODE_PRIVATE);
        SharedPreferences.Editor editPhonePath = PhonePath.edit();
        //Ghi du lieu vao tep tin, Key : Name , Value: PhoneNumber
        SharedPreferences sharedPref = mcontext.getSharedPreferences("PHONE_NUMBER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        // Gan phan tu tren layout vao cac bien
        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvContent = view.findViewById(R.id.tv_content);
        ImageView ivCover = view.findViewById(R.id.iv_animal_cover);
        ImageView ivFav = view.findViewById(R.id.iv_fav);
        ImageView ivPhone = view.findViewById(R.id.iv_phone);
        TextView tvPhone = view.findViewById(R.id.tv_phonenumber);

        // Dat gia tri cho cac phan tu tren layout
        tvName.setTag(item);
        tvName.setText(item.getName());
        tvContent.setText(item.getContent());
        ivCover.setImageBitmap(item.getPhotoBg());
        if(item.isFav()) ivFav.setImageResource(R.drawable.ic_fav);
        else ivFav.setImageResource(R.drawable.ic_fav_border);
        tvPhone.setText(sharedPref.getString(name,null));




        // Bat su kien click vao trai tim
        ivFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               //Xu li click vao trai tim
                if (item.isFav()) {
                    ivFav.setImageResource(R.drawable.ic_fav_border);
                    item.setFav(false);
                    editorFav.putBoolean(name,false).apply();
                    Log.i("DetailAnimalAdapter", name + ": false");

                }
                else {
                    ivFav.setImageResource(R.drawable.ic_fav);
                    item.setFav(true);
                    editorFav.putBoolean(name,true).apply();
                    if(sharedPrefFav.getBoolean(name,false)) Log.i("DetailAnimalApdater", "putBoolean is ok");
                    else Log.i("DetailAnimalApdater", "putBoolean is NOT OK");
                    Log.i("DetailAnimalAdapter", name + ": true");
                }

            }
        });
        ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inflate the dialog_phone.xml layout
                View phoneDialogView = LayoutInflater.from(mcontext).inflate(R.layout.dialog_phone, null);

                // Create the dialog
                final AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setView(phoneDialogView);
                ImageView ivIcon =phoneDialogView.findViewById(R.id.iv_Animal);
                try{
                    ivIcon.setImageBitmap(BitmapFactory.decodeStream(mcontext.getAssets().open(item.getPath())));
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                EditText etPhone = phoneDialogView.findViewById(R.id.et_Phone);
                if(!(sharedPref.getString(name, "")).equals("")) etPhone.setText(sharedPref.getString(name,""));

                // Display the dialog
                final AlertDialog phoneDialog = builder.create();
                phoneDialog.show();

                // Set save button click listener
                Button btnSave = (Button) phoneDialogView.findViewById(R.id.btn_Save);
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Save phone number here
                        EditText editTextPhone = (EditText) phoneDialogView.findViewById(R.id.et_Phone);
                        String phoneNumber = editTextPhone.getText().toString();


                        //Kiem tra truoc do da co sdt chua, thay the trong PHONE_TO_PATH

                        if(!sharedPref.getString(name, "").equals("")){ //Da co sdt
                            editPhonePath.remove(sharedPref.getString(name, "")).apply();
                        }
                        editPhonePath.putString(phoneNumber, item.getPath()).apply();

                        //Luu danh ba moi
                        editor.putString(name, phoneNumber);
                        editor.apply();


                        phoneDialog.dismiss();

                        tvPhone.setText(sharedPref.getString(name, ""));


                    }
                });

                // Set delete button click listener
                Button btnDelete = (Button) phoneDialogView.findViewById(R.id.btn_Delete);
                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Delete phone number here
                        editPhonePath.remove(sharedPref.getString(name,"")).apply(); //Xoa trong PHONE_TO_PATH
                        editor.remove(name).apply(); // xoa trong PHONE_NUMBER

                        phoneDialog.dismiss();
                        tvPhone.setText(sharedPref.getString(name, ""));
                    }
                });


            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}


