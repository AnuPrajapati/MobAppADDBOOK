package com.shiva.threeinkdirectory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.shiva.threeinkdirectory.Model.DirectoryModel;

import java.util.ArrayList;

public class Directory_List_Adapter extends ArrayAdapter<DirectoryModel> {
    Context context;
    public Directory_List_Adapter(Context context, ArrayList<DirectoryModel> list) {
        super(context, 0,list);
        this.context=context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.listviewitemforcategory,null);
        TextView name=view.findViewById(R.id.name),
                add=view.findViewById(R.id.add),
                phone=view.findViewById(R.id.phone);

        ImageView
                pic=view.findViewById(R.id.pic);
        RatingBar ratingbar=view.findViewById(R.id.ratingBar);

        final DirectoryModel model=getItem(position);
        name.setText(model.getName());
        add.setText(model.getAddress());
        phone.setText(model.getPhone());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail_Directory_Activity.class);
                intent.putExtra("model",model);

                context.startActivity(intent);

            }
        });
        return  view;
    }
}
