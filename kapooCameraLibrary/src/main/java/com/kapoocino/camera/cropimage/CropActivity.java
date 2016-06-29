package com.kapoocino.camera.cropimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.isseiaoki.simplecropview.CropImageView;
import com.kapoocino.camera.BaseActivity;
import com.kapoocino.camera.KapooCamera;
import com.kapoocino.camera.R;

import java.io.ByteArrayOutputStream;

public class CropActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Crop Image");

        toolbar.setNavigationOnClickListener(toolbarClick);

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
}
