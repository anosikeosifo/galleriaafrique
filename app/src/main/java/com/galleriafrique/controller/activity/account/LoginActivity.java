package com.galleriafrique.controller.activity.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.BaseActivity;
import com.galleriafrique.controller.activity.base.HomeActivity;
import com.galleriafrique.model.user.User;
import com.galleriafrique.util.CommonUtils;
import com.galleriafrique.util.helpers.AccountManager;
import com.galleriafrique.util.helpers.ProgressDialogHelper;
import com.galleriafrique.util.repo.LoginRepo;
import com.galleriafrique.util.tools.Strings;
import com.galleriafrique.util.tools.Validation;

/**
 * Created by osifo on 1/14/16.
 */
public class LoginActivity extends BaseActivity implements LoginRepo.LoginRepoListener {
    private LoginActivity activity;
    private LoginRepo loginRepo;
    private ProgressDialogHelper progressDialogHelper;
    private EditText emailText;
    private EditText passwordText;
    private Button loginButton;

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
    public void setToolbarTitle(String title) {
        super.setToolbarTitle(title);
    }

    @Override
    public void retryAction(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative) {
        super.retryAction(positive, negative);
    }

    public boolean validateLogin() {
        if(!Validation.isEmail(emailText.getText().toString())) {
            CommonUtils.toast(activity, "Enter a valid email address");
        } else if(Validation.isStringEmpty(passwordText.getText().toString())) {
            CommonUtils.toast(activity, "Youn need to enter a password");
        } else {
            return true;
        }

        return false;
    }

    public void initUI(){
        emailText = (EditText)findViewById(R.id.input_email);
        passwordText = (EditText)findViewById(R.id.input_password);
        loginButton = (Button)findViewById(R.id.btn_login);
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
}
