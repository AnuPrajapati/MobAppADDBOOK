package com.shiva.threeinkdirectory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.shiva.threeinkdirectory.Model.CategoryModel;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Category_List_Activity extends AppCompatActivity {
    GridView gridView;
    AQuery aQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list_layout);
        gridView = findViewById(R.id.gridview);
        aQuery = new AQuery(this);
        fetchData();
    }

    private void fetchData() {
        aQuery.ajax(DataHolder.MainUrl+DataHolder.CategoryUrl, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("response",url+"respone:"+ object);
              ArrayList<CategoryModel> list=JsonParser.getCategory(object);
                gridView.setAdapter(new Category_List_Adapter(Category_List_Activity.this, list));
            }
        });
    }


}
