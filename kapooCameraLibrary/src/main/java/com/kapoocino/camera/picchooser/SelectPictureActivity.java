
package com.kapoocino.camera.picchooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.kapoocino.camera.BaseActivity;
import com.kapoocino.camera.R;

public class SelectPictureActivity extends BaseActivity {
    @Override
    protected void onCreate(final Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_select_picture);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Select Image");

        toolbar.setNavigationOnClickListener(toolbarClick);

//        checkInitImageLoader();
        setResult(RESULT_CANCELED);

        // Create new fragment and transaction
        Fragment newFragment = new BucketsFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.contentPicture, newFragment);

        // Commit the transaction
        transaction.commit();
    }

    private View.OnClickListener toolbarClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_right_out);
    }

    void showBucket(final int bucketId) {
        Bundle b = new Bundle();
        b.putInt("bucket", bucketId);
        Fragment f = new ImagesFragment();
        f.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentPicture, f).addToBackStack(null).commit();
    }

    void imageSelected(final String imgPath) {
        returnResult(imgPath);
    }

    private void returnResult(final String imgPath) {
        BaseActivity.newLoadingDialog(this, "Please Wait...",
                false);
        BaseActivity.getLoadingDialog().show();
        Intent result = new Intent();
        result.putExtra("imgPath", imgPath);
        setResult(RESULT_OK, result);
        finish();
    }
}
