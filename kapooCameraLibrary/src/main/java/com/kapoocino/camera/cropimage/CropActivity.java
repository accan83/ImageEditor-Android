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
        String filepath = getIntent().getStringExtra("filepath");
        if (cropImageView != null) {
            InputStream image_stream;
            try {
                image_stream = getContentResolver().openInputStream(Uri.parse("file://" + filepath));
                Bitmap bitmap= BitmapFactory.decodeStream(image_stream);
                cropImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
