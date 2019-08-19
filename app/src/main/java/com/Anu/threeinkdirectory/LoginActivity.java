package com.shiva.threeinkdirectory;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.Auth;


import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.shiva.threeinkdirectory.view.CircularImageView;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;

import com.google.android.gms.common.api.GoogleApiClient;


import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    CallbackManager callbackManager;
    AQuery aQuery;
    LoginButton loginButton;
    private SignInButton googleSignInButton;
    private static final String EMAIL = "email";
    boolean loggedIn = false;
    private ProgressDialog mProgress;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE = 101;
    Button memberlogin;
    TextView register;
    CircularImageView imageView;

    private GoogleSignInClient googleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_new);
        register = findViewById(R.id.create);
        sharedPreferences = getSharedPreferences("Userinfo", 0);

        loggedIn = sharedPreferences.getBoolean("isLoginSuccessful", false);
        if (loggedIn) {
            finish();
            Intent intent = new Intent(LoginActivity.this, Category_List_Activity.class);
            startActivity(intent);
        }
        memberlogin = findViewById(R.id.memberlogin);
        memberlogin.setOnClickListener(this);
        mProgress = new ProgressDialog(this);
        String titleId = "Signing in...";
        mProgress.setTitle(titleId);
        mProgress.setMessage("Please Wait...");
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
            }
        });

        //imageView = findViewById(R.id.circleimage);

        //imageView.setImageResource(R.mipmap.ic_launcher);


        loginButton = findViewById(R.id.fb);
        loginButton.setReadPermissions(Arrays.asList(EMAIL, "public_profile"));
        callbackManager = CallbackManager.Factory.create();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // Retrieving access token using the LoginResult
                Log.i("Response", "error");
                AccessToken accessToken = loginResult.getAccessToken();
                useLoginInformation(accessToken);
            }

            @Override
            public void onCancel() {
                Log.i("Response", "error");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("Response", "error");
            }
        });
        googleSignInButton = findViewById(R.id.google_button);
        googleSignInButton.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        Log.i("hi", "hi" + "This is Oncr8");

    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }


    private void useLoginInformation(AccessToken accessToken) {

        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.i("response", response.toString());
                try {
                    String name = object.getString("name");
                    String email = object.getString("email");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");

                    Toast.makeText(LoginActivity.this, email, Toast.LENGTH_SHORT).show();


                    putData(name, email, image);
                    finish();
                    updateUI(true);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.google_button:
                mProgress.show();
                signIn();
                break;

            case R.id.memberlogin:
                mProgress.show();
                FormemberLogin();

                break;
        }
    }

    private void FormemberLogin() {
        EditText username = findViewById(R.id.username);
        boolean result = EmailValidator.getInstance().isValid(username.getText().toString());
        if (result) {
            mProgress.dismiss();
            Toast.makeText(this, "valid" + result + username.getText().toString(), Toast.LENGTH_SHORT).show();
        } else {
            mProgress.dismiss();
            Toast.makeText(this, "valid" + result + username.getText().toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void signIn() {

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);

        startActivityForResult(intent, REQ_CODE);
        Log.i("hi", "hi i am in signin" + REQ_CODE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            mProgress.dismiss();
            Log.i("hi", "hi success");
            GoogleSignInAccount acc = result.getSignInAccount();
            String ename = acc.getDisplayName();
            String eemail = acc.getEmail();
            String urlimg = acc.getPhotoUrl() != null ? acc.getPhotoUrl().toString() : null;
            String image;
            if (urlimg == null) {

                image = "http://www.clker.com/cliparts/d/L/P/X/z/i/no-image-icon-hi.png";

            } else {
                image = acc.getPhotoUrl().toString();
            }
            putData(ename, eemail, urlimg);

            updateUI(true);
        } else {
            updateUI(false);
        }
    }

    private void putData(String ename, String eemail, String url) {
        //we need editor to edit created shared preference file
        editor = sharedPreferences.edit();
        editor.putString("name", ename);
        editor.putString("email", eemail);
        editor.putBoolean("isLoginSuccessful", true);
        editor.apply();

    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private void updateUI(boolean isLogin) {
        if (isLogin) {
            Register();
            Intent intent = new Intent(LoginActivity.this, Category_List_Activity.class);
            startActivity(intent);
        } else {

        }
    }

    private void Register() {
        aQuery = new AQuery(this);
        HashMap<String, Object> param = new HashMap<>();
        String name = sharedPreferences.getString("name", "default_value");
        Log.i("msg", "mag" + name);
        param.put("fullname", sharedPreferences.getString("name", "default_value"));
        param.put("email", sharedPreferences.getString("email", "default_value"));
        param.put("password", "");
        aQuery.ajax(DataHolder.MainUrl + DataHolder.RegisterUrl, param, JSONObject.class, new AjaxCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject object, AjaxStatus status) {
                super.callback(url, object, status);
                Log.i("msg", "msg" + object);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("hi", "hi" + REQ_CODE);
        if (requestCode == REQ_CODE) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleResult(result);

        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }
}
