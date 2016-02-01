package com.galleriafrique.controller.activity.account;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.galleriafrique.util.tools.Strings;

/**
 * Created by osifo on 1/31/16.
 */

public class SignupActivity extends AppCompatActivity {

    private EditText nameText;
    private EditText emailText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    private void initUI() {

    }

    private void validateSignupForm(String email, String password) {
//        Strings.getStringFromView()
    }
}
