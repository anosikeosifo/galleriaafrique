package com.galleriafrique.controller.activity.account;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.galleriafrique.R;
import com.galleriafrique.controller.activity.base.BaseActivity;
import com.galleriafrique.controller.activity.base.HomeActivity;
import com.galleriafrique.util.helpers.AccountManager;
import com.galleriafrique.util.helpers.ProgressDialogHelper;
import com.galleriafrique.util.repo.LoginRepo;

/**
 * Created by osifo on 1/14/16.
 */
public class LoginActivity extends BaseActivity implements LoginRepo.LoginRepoListener {
    private LoginActivity activity;
    private LoginRepo loginRepo;
    private ProgressDialogHelper progressDialogHelper;
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

    @Override
    public void authenticate() {

    }

    public void initUI(){
        loginButton = (Button)findViewById(R.id.btn_login);
    }

    public void setClickListeners() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, HomeActivity.class));
                finish();
            }
        });
    }
}
