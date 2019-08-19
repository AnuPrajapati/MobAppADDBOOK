package com.shiva.threeinkdirectory.Model;

import java.sql.Blob;

public class CategoryModel {
    String category_name,id;
    Blob image;

    public void setId(String id) {
        this.id = id;
    }


    public String getCategory_name() {
        return category_name;
    }

    public String getId() {
        return id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
