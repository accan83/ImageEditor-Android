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
import com.kapoocino.camera.KapooCamera;
import com.kapoocino.camera.R;

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

        Log.d("---DEBUG---", "onPictureTaken: ");
        byte[] byteArray = getIntent().getByteArrayExtra("byteArray");
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        if (cropImageView != null) {
            cropImageView.setImageBitmap(bitmap);
        }

        if (btnCrop != null) {
            btnCrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (cropImageView != null) {
                        Bitmap croppedPhoto = cropImageView.getCroppedBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        croppedPhoto.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();

                        Intent intent=new Intent();
                        intent.putExtra("overlay",byteArray);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
        }
    }
}
