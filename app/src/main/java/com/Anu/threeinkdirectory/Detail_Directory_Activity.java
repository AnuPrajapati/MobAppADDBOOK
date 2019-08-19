package com.shiva.threeinkdirectory;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.shiva.threeinkdirectory.Model.CommentModel;
import com.shiva.threeinkdirectory.Model.DirectoryModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Detail_Directory_Activity extends AppCompatActivity implements View.OnClickListener {
    Button commentbtn;
    EditText commentbox;
    LinearLayout container;
    AQuery aQuery;
    TextView tagline, phone, address, detail, location, serviceDays, serviceTime, email, website, facebook, call, direction, name;
    DirectoryModel model;

    ArrayList<CommentModel> commentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        model = (DirectoryModel) getIntent().getSerializableExtra("model");
        commentbtn = findViewById(R.id.commentbtn);
        commentbox = findViewById(R.id.commentbox);
        container = findViewById(R.id.cotainer);
        name = findViewById(R.id.nameof);
        address = findViewById(R.id.address);
        phone = findViewById(R.id.phonee);
        detail = findViewById(R.id.detail);
        tagline = findViewById(R.id.tagline);
        serviceDays = findViewById(R.id.service_day);
        serviceTime = findViewById(R.id.service_time);
        website = findViewById(R.id.website);
        email = findViewById(R.id.emaill);
        facebook = findViewById(R.id.facebook);
        call = findViewById(R.id.call);
        commentbtn.setOnClickListener(this);
        call.setOnClickListener(this);
        direction = findViewById(R.id.direction);
        direction.setOnClickListener(this);
        name.setText(model.getName());
        address.setText(model.getAddress());
        phone.setText(model.getPhone());
        detail.setText(model.getDetail());
        tagline.setText(model.getTagline());
        serviceDays.setText(model.getServiceDays());
        serviceTime.setText(model.getServiceTime());
        website.setText(model.getWebsite());
        email.setText(model.getEmail());
        facebook.setText(model.getFacebook());
        Button ratebutton = findViewById(R.id.ratebutton);
        ratebutton.setOnClickListener(this);

        fetchComments();
    }

    public void showMap(View view) {
        Uri gmmIntentUri = Uri.parse("geo:" + model.getLatitude() + "," + model.getLongitude());
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    public void showcustomDialogForRating() {
        final Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.rating_layout, null);
        dialog.setCancelable(false);
        dialog.setTitle("Rating");
        dialog.setContentView(view);
        dialog.show();


        Button getRating = view.findViewById(R.id.ok);
        final RatingBar ratingBar = view.findViewById(R.id.rating);
//
        getRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rating = "Rating is :" + ratingBar.getRating();
                fetchData(rating);
                dialog.dismiss();
                Toast.makeText(Detail_Directory_Activity.this, rating, Toast.LENGTH_LONG).show();
            }
        });

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Detail_Directory_Activity.this, "You cancel", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });


    }

    public void fetchData(String rating) {
        aQuery = new AQuery(Detail_Directory_Activity.this);
        HashMap<String, Object> param = new HashMap<>();
        param.put("id", model.getId());
        param.put("rating", rating);
        aQuery.ajax(DataHolder.MainUrl + DataHolder.GetRateUrl, param, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("rsponse", "response" + object);
            }
        });

    }

    public void fetchComments() {
        aQuery = new AQuery(this);
        commentList = new ArrayList<>();
        Toast.makeText(this, "helooo i am in comment fetch", Toast.LENGTH_SHORT).show();
        HashMap<String, Object> param = new HashMap<>();
        param.put("directory_id", model.getId());

        aQuery.ajax(DataHolder.MainUrl + DataHolder.GetCommentUrl, param, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("resp", "resp" + url + object);
                try {
                    commentList = JsonParser.getComments(object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                populateComments();
            }
        });


    }

    public void addComment(String comment) {
        aQuery = new AQuery(this);

        Toast.makeText(this, "helooo i am in comment fetch", Toast.LENGTH_SHORT).show();
        HashMap<String, Object> param = new HashMap<>();
        param.put("comment", comment);
        param.put("comment_by", model.getUid());
        param.put("directory_id", model.getId());

        aQuery.ajax(DataHolder.MainUrl + DataHolder.AddCommentUrl, param, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("resp", "resp add" + url + object);
                fetchComments();
            }
        });


    }

    public void populateComments() {

        for (CommentModel model : commentList
        ) {
            View view = LayoutInflater.from(this).inflate(R.layout.comment_item_layout, null);
            TextView comment = view.findViewById(R.id.comment);
            TextView name = view.findViewById(R.id.name);

            ImageView img = view.findViewById(R.id.userimage);
            comment.setText(model.getComment());
            name.setText(model.getName());


            container.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commentbtn:
                TextView usercmt = findViewById(R.id.commentbox);

                String comment = usercmt.getText().toString().trim();
                if (comment != null) {
                    addComment(comment);
                } else {

                }

                break;
            case R.id.call:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + model.getPhone()));// Initiates the Intent
                startActivity(intent);
                break;
            case R.id.direction:
                showMap(v);
                break;
            case R.id.ratebutton:
                showcustomDialogForRating();
                break;
        }

    }
}


