package com.example.prm391x_project_2_dattmfx12040.model;



import android.graphics.Bitmap;



public class Animal {

    private final Bitmap photo; //ảnh động vật

    private final Bitmap photoBg; //ảnh background

    private final String path; //đường dẫn đến ảnh

    private final String name; //tên động vật

    private final String content; //nội dung mô tả động vật

    private boolean isFav; //động vật yêu thích



    public void setFav(boolean fav) {
        isFav = fav;
    }

    public boolean isFav() {
        return isFav;
    }

    public Animal(String path, Bitmap photo, Bitmap photoBg, String name, String content, boolean isFav) {
        this.path = path;
        this.photo = photo;
        this.photoBg = photoBg;
        this.name = name;
        this.isFav = isFav;
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public Bitmap getPhotoBg() {
        return photoBg;
    }

    public String getContent() {
        return content;
    }

    public String getName() {
        return name;
    }
}