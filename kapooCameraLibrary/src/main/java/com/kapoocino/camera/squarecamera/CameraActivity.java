package com.kapoocino.camera.squarecamera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kapoocino.camera.KapooCamera;
import com.kapoocino.camera.R;

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

    private void handleSelectImage(Intent data) {
        String filepath = data.getStringExtra("imgPath");
        Log.d(TAG, "handleSelectImage: " + filepath);
        KapooCamera.cropImage(this, Uri.parse(filepath));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        switch (requestCode) {
            case KapooCamera.SELECT_GALLERY_IMAGE_CODE:
                handleSelectImage(data);
                break;

            case KapooCamera.SELECT_GALLERY_VIDEO_CODE:
                setResult(RESULT_OK, data);
                finish();
                break;

            case KapooCamera.CROP_IMAGE:
                KapooCamera.editImage(this);
                break;
            case KapooCamera.ACTION_REQUEST_EDIT_IMAGE:
                setResult(RESULT_OK, data);
                finish();
                break;


            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
