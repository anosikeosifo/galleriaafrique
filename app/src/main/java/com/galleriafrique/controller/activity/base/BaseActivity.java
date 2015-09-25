package com.galleriafrique.controller.activity.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.galleriafrique.R;

/**
 * Created by osifo on 8/23/15.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
