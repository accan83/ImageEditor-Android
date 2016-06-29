
package com.kapoocino.camera.picchooser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bumptech.glide.Glide;
import com.kapoocino.camera.BaseActivity;
import com.kapoocino.camera.KapooCamera;
import com.kapoocino.camera.R;

import java.util.concurrent.ExecutionException;

public class SelectVideoActivity extends BaseActivity {
    Bitmap theBitmap = null;

    @Override
    protected void onCreate(final Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_select_video);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Select Video");

        toolbar.setNavigationOnClickListener(toolbarClick);

//        checkInitImageLoader();
        setResult(RESULT_CANCELED);

        // Create new fragment and transaction
        Fragment newFragment = new VideoBucketsFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.contentVideo, newFragment);

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
        Fragment f = new VideosFragment();
        f.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contentVideo, f).addToBackStack(null).commit();
    }

    void imageSelected(final String imgPath, long imgSize) {
        returnResult(imgPath, imgSize);
    }

    private void returnResult(final String imgPath, final long imgSize) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Looper.prepare();
                try {
                    theBitmap = Glide.with(SelectVideoActivity.this).load(imgPath).asBitmap().into(-1,-1).get();
                } catch (final ExecutionException | InterruptedException ignore) {}
                return null;
            }
            @Override
            protected void onPostExecute(Void dummy) {
                if (theBitmap != null) {
                    KapooCamera.bitmap = theBitmap;
                    Intent result = new Intent();
                    result.putExtra("filepath", imgPath);
                    result.putExtra("filesize", imgSize);
                    setResult(RESULT_OK, result);
                    finish();
                };
            }
        }.execute();
    }
}
