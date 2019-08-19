package com.shiva.threeinkdirectory;

import android.util.Log;

import com.shiva.threeinkdirectory.Model.CategoryModel;
import com.shiva.threeinkdirectory.Model.CommentModel;
import com.shiva.threeinkdirectory.Model.DirectoryModel;
import com.shiva.threeinkdirectory.Model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {


    public static ArrayList<UserModel> getUserInfo(JSONObject object) throws JSONException {
        ArrayList<UserModel> list = new ArrayList<>();


        JSONArray array = object.getJSONArray("result");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            UserModel model = new UserModel();
            model.setId(obj.getString("id"));
            model.setName(obj.getString("name"));
            model.setEmail(obj.getString("email"));
            model.setImage(obj.getString("image"));

            list.add(model);

        }
        return list;

    }

    public static ArrayList<CategoryModel> getCategory(JSONObject object) {
        JSONArray array = null;
        try {
            array = object.getJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<CategoryModel> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                CategoryModel model = new CategoryModel();
                model.setCategory_name(obj.getString("name"));
                model.setId(obj.getString("id"));
//                        model.setImage(obj.get("image"));
                list.add(model);
            } catch (JSONException x) {
                x.printStackTrace();
            }
        }
        return list;
    }
    public static ArrayList<DirectoryModel> getDirectory(JSONObject object){
        JSONArray array = null;
        try {
            array = object.getJSONArray("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<DirectoryModel> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                DirectoryModel model = new DirectoryModel();
                model.setId(obj.getString("id"));
                model.setName(obj.getString("name"));
                model.setFirstName(obj.getString("first_name"));
                model.setEmail(obj.getString("email"));
                model.setLastName(obj.getString("last_name"));
                model.setMiddleName(obj.getString("middle_name"));
                model.setTagline(obj.getString("tagline"));
                model.setDetail(obj.getString("detail"));
                model.setAddress(obj.getString("address"));
                model.setPhone(obj.getString("phone"));
                model.setLocation(obj.getString("location"));
                model.setServiceDays(obj.getString("service_days"));
                model.setServiceTime(obj.getString("service_time"));
                model.setWebsite(obj.getString("website"));
                model.setFacebook(obj.getString("facebook"));
                model.setTotalRating(obj.getString("total_rating"));
                model.setCreatedAt(obj.getString("created_at"));
                model.setUid(obj.getString("uid"));
                model.setLatitude(obj.getString("latitude"));
                model.setLongitude(obj.getString("longitude"));
                model.setCategoryId(obj.getString("category_id"));

//                        model.setImage(obj.get("image"));
                list.add(model);
            } catch (JSONException x) {
                x.printStackTrace();
            }
        }
        return list;
    }
    public static ArrayList<CommentModel> getComments(JSONObject object) throws JSONException {
        ArrayList<CommentModel> list = new ArrayList<>();


        JSONArray array = object.getJSONArray("result");
        for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            CommentModel model = new CommentModel();
            model.setId(obj.getString("id"));
            model.setComment(obj.getString("comment"));
            model.setComment_by(obj.getString("comment_by"));
            model.setCreated_at(obj.getString("created_at"));
            model.setEmail(obj.getString("email"));
            model.setName(obj.getString("name"));
            model.setDirectory_id(obj.getString("directory_id"));
            list.add(model);

        }
        return list;

    }

}