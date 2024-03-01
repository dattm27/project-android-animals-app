package com.example.prm391x_project_2_dattmfx12040.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.prm391x_project_2_dattmfx12040.model.Animal;
import com.example.prm391x_project_2_dattmfx12040.adapter.DetailAnimalAdapter;
import com.example.prm391x_project_2_dattmfx12040.activity.MainActivity;
import com.example.prm391x_project_2_dattmfx12040.R;

import java.util.ArrayList;


public class DetailFragment extends Fragment {



    private Context mContext;
    private ArrayList<Animal> listAnimal;
    private String name;
    private Animal currentAnimal;




    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        initViews(rootView);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    private void initViews(View v) {
        //Dat icon back tren action bar
        ImageView ivBack = v.findViewById(R.id.iv_menu);
        ivBack.setImageResource(R.drawable.ic_back);
        ivBack.setOnClickListener(v1 -> goToListAnimals());

        ViewPager vp = v.findViewById(R.id.vp_animal);
        DetailAnimalAdapter adapter = new DetailAnimalAdapter(listAnimal, mContext);
        vp.setAdapter(adapter);
        vp.setCurrentItem(listAnimal.indexOf(currentAnimal),true);


    }

    private void goToListAnimals() {
        Log.i("DetailFragment","Back to ListAnimal");
        ((MainActivity) getActivity()).backToMenu(listAnimal);

    }

    public void setData(ArrayList<Animal> listAnimals, Animal animal) {
        this.listAnimal = listAnimals;
        this.currentAnimal = animal;
    }

    public ArrayList<Animal> getListAnimal() {
        return listAnimal;
    }
}