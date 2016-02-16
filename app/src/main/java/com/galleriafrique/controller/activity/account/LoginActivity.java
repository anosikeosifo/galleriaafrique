package com.galleriafrique.controller.activity.account;

import android.accounts.Account;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.BaseActivity;
import com.galleriafrique.controller.activity.base.HomeActivity;
import com.galleriafrique.model.user.User;
import com.galleriafrique.model.user.UserResponse;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.helpers.AccountManager;
import com.galleriafrique.util.helpers.ProgressDialogHelper;
import com.galleriafrique.util.repo.LoginRepo;
import com.galleriafrique.util.tools.Strings;
import com.galleriafrique.util.tools.Validation;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by osifo on 1/14/16.
 */
public class LoginActivity extends FragmentActivity implements LoginRepo.LoginRepoListener {
    private LoginActivity activity;
    private LoginRepo loginRepo;
    private ProgressDialogHelper progressDialogHelper;
    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;
    private com.facebook.login.widget.LoginButton fbLoginButton;
//    private TwitterLoginButton twitterLoginButton;
    private CallbackManager callbackManager;
    private GoogleApiClient googleApiClient;
    private SignInButton googleSigninBtn;
    private static final int GOOGLE_SIGN_IN = 9001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(AccountManager.isUserLoggedIn()) {
            startActivity(new Intent(this, HomeActivity.class));
            finish(); // closes the current activity
        } else {
            setContentView(R.layout.activity_login);
            activity = this;
            loginRepo = new LoginRepo(this);
            progressDialogHelper = new ProgressDialogHelper(this);

            initUI();
            setClickListeners();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CommonUtils.log("activity result data: " + data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == GOOGLE_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignIn(result);
        }
    }

    public void retryAction(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.retry_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes),
                        positive)
                .setNegativeButton(getString(R.string.no), negative);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public boolean validateLogin() {
        if(!Validation.isEmail(emailText.getText().toString())) {
            CommonUtils.toast(activity, "Enter a valid email address");
        } else if(Validation.isStringEmpty(passwordText.getText().toString())) {
            CommonUtils.toast(activity, "You need to enter a password");
        } else {
            return true;
        }

        return false;
    }

    public void initUI(){
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this , new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {
                        CommonUtils.toast(activity, "Sorry, Connection to google plus failed");
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        googleSigninBtn = (SignInButton) findViewById(R.id.sign_in_button);
        googleSigninBtn.setSize(SignInButton.SIZE_WIDE);
        googleSigninBtn.setScopes(gso.getScopeArray());
        emailText = (EditText)findViewById(R.id.input_email);
        passwordText = (EditText)findViewById(R.id.input_password);
        loginButton = (Button)findViewById(R.id.btn_login);
        fbLoginButton = (com.facebook.login.widget.LoginButton)findViewById(R.id.fb_login_button);
        fbLoginButton.setReadPermissions("user_friends");

    }

    private void handleGoogleSignIn (GoogleSignInResult result) {
        CommonUtils.log("OAUTH_LOGIN:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            CommonUtils.toast(activity, acct.getDisplayName() + acct.getEmail() + acct.getPhotoUrl().toString());
            loginRepo.loginWithGoogle(acct.getEmail(), acct.getDisplayName(), acct.getId(), acct.getIdToken(), acct.getPhotoUrl().toString());
//            updateUI(true);
        } else {
            CommonUtils.toast(activity, "Google signin not successful");
            // Signed out, show unauthenticated UI.
//            updateUI(false);
        }
    }

    public void setClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateLogin()) {
                    progressDialogHelper.showProgress("Welcome back...");
                    loginRepo.login(Strings.getStringFromView(emailText), Strings.getStringFromView(passwordText));
                }
            }
        });

        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                //if user is logged in successfully, check if the user(via email) exists on the system,
                //if yes, then fetch his details and log him in
                //if no sign him up with that email, give him a default password and render a view for entering password
                CommonUtils.log("login result: " + loginResult.getAccessToken());

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    CommonUtils.log(object.getString("picture"));
                                    loginRepo.loginWithFacebook(object.getString("email"), object.getString("name"), loginResult.getAccessToken().getUserId(), loginResult.getAccessToken().getToken(), "https://graph.facebook.com/" + object.getString("id") + "/picture");

                                    //next, save the user's info and friend's email addresses, so as to connecting him with other friends using the file
                                    CommonUtils.toast(activity, "user name: " + object.getString("email"));
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,picture,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                CommonUtils.toast(activity, "user log-in cancelled");
            }

            @Override
            public void onError(FacebookException error) {
                CommonUtils.toast(activity, "user log-in failed");
            }
        });

        googleSigninBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN);
            }
        });
    }

    @Override
    public void requestFailed() {
        progressDialogHelper.dismissProgress();
        CommonUtils.toast(activity, "Login failed, Please try again");
    }

    @Override
    public void showErrorMessage(String message) {
        progressDialogHelper.dismissProgress();
        CommonUtils.toast(activity, "Login failed. " + message );
    }

    @Override
    public void retryLogin(String email, String password) {
        loginRepo.login(email, password);
    }

    @Override
    public void loginSuccessful(User user) {
        AccountManager.saveUser(user);
        progressDialogHelper.dismissProgress();
        startActivity(new Intent(activity, HomeActivity.class));
        finish();
    }

    @Override
    public void retryGetInfo(String email) {

    }

    @Override
    public void facebookLoginSuccessful(UserResponse response) {

    }

    @Override
    public void retryFacebookLogin(String email, String uid, String name, String token, String photoUrl) {

    }

    @Override
    public void retryGoogleLogin(String email, String uid, String name, String token, String photoUrl) {

    }
}
