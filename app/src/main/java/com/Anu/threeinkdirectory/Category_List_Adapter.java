package com.shiva.threeinkdirectory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.shiva.threeinkdirectory.Model.CategoryModel;

import java.util.ArrayList;

public class Category_List_Adapter extends ArrayAdapter<CategoryModel> {
    Context context;
    public Category_List_Adapter(Context context, ArrayList<CategoryModel> list) {
        super(context, 0,list);
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.gridcategoryitem,null);
        TextView categoryname=view.findViewById(R.id.textview);
        ImageView imageView=view.findViewById(R.id.image);
        final CategoryModel model=getItem(position);
        categoryname.setText(model.getCategory_name());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, Directory_List_Activity.class);
                intent.putExtra("id",model.getId());
                context.startActivity(intent);
            }
        });

        return  view;
    }
}
