package com.shiva.threeinkdirectory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.shiva.threeinkdirectory.Model.DirectoryModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Directory_List_Activity extends AppCompatActivity {
    ListView listView;
    AQuery aQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        listView = findViewById(R.id.listview);
        aQuery = new AQuery(this);
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");


        fetchData(id);

    }

    private void fetchData(String id) {
        HashMap<String,Object> param= new HashMap<>();
        param.put("category_id",id);
        aQuery.ajax(DataHolder.MainUrl + DataHolder.GetAdsUrl,param, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("response", "Response" + object);
              ArrayList<DirectoryModel> list=JsonParser.getDirectory(object);
                listView.setAdapter(new Directory_List_Adapter(Directory_List_Activity.this, list));
            }
        });
    }
}
