package com.example.prm391x_project_2_dattmfx12040.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391x_project_2_dattmfx12040.model.Animal;
import com.example.prm391x_project_2_dattmfx12040.adapter.AnimalAdapter;
import com.example.prm391x_project_2_dattmfx12040.R;

import java.util.ArrayList;


public class MenuFragment extends Fragment {

    public Context mContext;

    private RecyclerView rvAnimal;

    private ArrayList<Animal> listAnimals;

    private DrawerLayout mDrawer;

    private  ArrayList listSea = new ArrayList<Animal>();
    private  ArrayList listMamal = new ArrayList<Animal>();
    private  ArrayList listBird = new ArrayList<Animal>();



    public MenuFragment() {
    }
    public MenuFragment(ArrayList<Animal> listSea, ArrayList<Animal> listMammal, ArrayList<Animal> listBird){
        this.listSea = listSea;
        this.listBird = listBird;
        this.listMamal = listMammal;
    }


    public void setListAnimals(ArrayList<Animal> listAnimals) {
        this.listAnimals = listAnimals;
    }

    @Nullable

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,

                             @Nullable ViewGroup container,

                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        initView(v);

        return v;

    }



    @Override

    public void onAttach(@NonNull Context context) {

        super.onAttach(context);

        mContext = context;

    }

    private void initView(View v) {

        mDrawer = v.findViewById(R.id.drawer);

        rvAnimal = v.findViewById(R.id.rv_animals);

        //Xử lý mở menu trái

        v.findViewById(R.id.iv_menu).setOnClickListener(v12 -> mDrawer.openDrawer(GravityCompat.START));



        //Hiển thị ảnh động vật biển

        v.findViewById(R.id.iv_sea).setOnClickListener(v1 -> {

            // v.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

            showAnimals("sea");

        });



        //Hiển thị ảnh động vật có vú

        v.findViewById(R.id.iv_mammal).setOnClickListener(v1 -> {

            //v.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

            showAnimals("mammal");

        });



        //Hiển thị ảnh chim muông

        v.findViewById(R.id.iv_bird).setOnClickListener(v1 -> {

            //v.startAnimation(AnimationUtils.loadAnimation(mContext, androidx.appcompat.R.anim.abc_fade_in));

            showAnimals("bird");

        });



        //Hiển thị danh sách ảnh lên RecyclerView

        if (listAnimals != null) {

            AnimalAdapter animalAdapter = new AnimalAdapter(listAnimals, mContext);

            rvAnimal.setAdapter(animalAdapter);

        }

    }



    public void showAnimals(String animalType) {

        ArrayList listAnimals = new ArrayList<Animal>();

        //Học sinh tự xử lý để xây dựng được danh sách Animal tương ứng dựa vào animalType
        //Hiển thị danh sách con vật theo phân loại tương ứng
        if(animalType=="sea"){
            listAnimals = listSea;
        }
        else if (animalType == "mammal") {
            listAnimals = listMamal;
        }
        else if (animalType == "bird"){
            listAnimals = listBird;
        }
        //Hiển thị danh sách ảnh lên RecyclerView
        Log.i("MenuFragment","listAnimals size = " + listAnimals.size());

        AnimalAdapter animalAdapter = new AnimalAdapter(listAnimals, mContext);
        rvAnimal.setAdapter(animalAdapter);

        mDrawer.closeDrawers();

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Log.i("Menu", "Back Button Pressed");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                System.exit(0);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()
    }

}

