
package com.kapoocino.camera.picchooser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.kapoocino.camera.BaseActivity;

public class SelectPictureActivity extends BaseActivity {
    @Override
    protected void onCreate(final Bundle b) {
        super.onCreate(b);
//        checkInitImageLoader();
        setResult(RESULT_CANCELED);

        // Create new fragment and transaction
        Fragment newFragment = new BucketsFragment();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();

        // Replace whatever is in the fragment_container view with this
        // fragment,
        // and add the transaction to the back stack
        transaction.replace(android.R.id.content, newFragment);

        // Commit the transaction
        transaction.commit();
    }

    void showBucket(final int bucketId) {
        Bundle b = new Bundle();
        b.putInt("bucket", bucketId);
        Fragment f = new ImagesFragment();
        f.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, f).addToBackStack(null).commit();
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
