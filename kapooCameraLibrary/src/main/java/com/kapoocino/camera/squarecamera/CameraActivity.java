package com.kapoocino.camera.squarecamera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.kapoocino.camera.KapooCamera;
import com.kapoocino.camera.R;

import java.io.Serializable;

public class CameraActivity extends AppCompatActivity {

    public static final String TAG = CameraActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.squarecamera__CameraFullScreenTheme);
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.squarecamera__activity_camera);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, CameraFragment.newInstance(), CameraFragment.TAG)
                    .commit();
        }
    }

    public void returnPhotoUri(Uri uri) {
        Intent data = new Intent();
        data.setData(uri);

        if (getParent() == null) {
            setResult(RESULT_OK, data);
        } else {
            getParent().setResult(RESULT_OK, data);
        }

        finish();
    }

    public void onCancel(View view) {
        getSupportFragmentManager().popBackStack();
    }

    private void handleSelectImage(Intent data) {
        String filepath = data.getStringExtra("imgPath");
        Log.d(TAG, "handleSelectImage: " + filepath);
        KapooCamera.cropCamera(this, Uri.parse(filepath));
//        returnPhotoUri(Uri.parse(filepath));
//        path = filepath;
        // System.out.println("path---->"+path);
//        LoadImageTask task = new LoadImageTask();
//        task.execute(path);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case 1:
                Uri imageUri = data.getData();
                break;

            case KapooCamera.SELECT_GALLERY_IMAGE_CODE:
                handleSelectImage(data);
                break;

            case KapooCamera.SELECT_GALLERY_VIDEO_CODE:
                break;

            case KapooCamera.CROP_IMAGE:
                setResult(RESULT_OK, data);
                finish();
                break;

            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
