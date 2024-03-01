package com.example.prm391x_project_2_dattmfx12040.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391x_project_2_dattmfx12040.model.Animal;
import com.example.prm391x_project_2_dattmfx12040.activity.MainActivity;
import com.example.prm391x_project_2_dattmfx12040.R;

import java.io.IOException;
import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder> {
    private final ArrayList<Animal> listAnimals; //danh sach cac con vat
    private Context mContext;


    public AnimalAdapter(ArrayList<Animal> listAnimals, Context mContext) {
        this.listAnimals = listAnimals;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AnimalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_animal,parent,false);
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(50, 0, 0, 200); // left, top, right, bottom
        view.setLayoutParams(layoutParams);
        return new AnimalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalHolder holder, int position) {
        Animal item = listAnimals.get(position);
        holder.ivIcon.setTag(item);
        Log.i("AnimalAdapter", item.getName());
        //holder.ivIcon.setTag(item);
        try {
            holder.ivIcon.setImageBitmap(BitmapFactory.decodeStream(mContext.getAssets().open(item.getPath())));
//            holder.ivIcon.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.alpha));
            if (item.isFav()) holder.ivFav.setVisibility(View.VISIBLE);
            else  holder.ivFav.setVisibility(View.INVISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() { //tra ve so luong animals
        return listAnimals.size();
    }

    public class AnimalHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        ImageView ivFav;
        public AnimalHolder(View itemView){
            super(itemView);
            ivIcon = itemView.findViewById(R.id.iv_animal);
            ivFav = itemView.findViewById(R.id.iv_fav);
            itemView.setOnClickListener(v -> {
                ((MainActivity)mContext).showDetail(listAnimals,(Animal)ivIcon.getTag()) ;
            });
        }
    }
}
