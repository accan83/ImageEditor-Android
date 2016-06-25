package com.kapoocino.camera.cropimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.isseiaoki.simplecropview.CropImageView;
import com.kapoocino.camera.BaseActivity;
import com.kapoocino.camera.KapooCamera;
import com.kapoocino.camera.R;
import com.kapoocino.camera.squarecamera.ImageUtility;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        final CropImageView cropImageView = (CropImageView)findViewById(R.id.cropImageView);
        Button btnCrop = (Button) findViewById(R.id.btnCrop);

        if (cropImageView != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            KapooCamera.bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

            cropImageView.setImageBitmap(bitmap);
        }

        if (btnCrop != null) {
            btnCrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cropImageView != null) {
                        KapooCamera.bitmap = cropImageView.getCroppedBitmap();

                        Intent intent=new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
        BaseActivity.getLoadingDialog().dismiss();
    }
}
