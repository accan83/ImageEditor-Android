package com.kapoocino.imageeditandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kapoocino.camera.KapooCamera;
import com.kapoocino.camera.KapooOption;

public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO_CODE = 8;
    private ImageView imgView;
    private TextView txtPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        imgView = (ImageView) findViewById(R.id.img);
        txtPath = (TextView) findViewById(R.id.txtPath);
        View takePhoto = findViewById(R.id.take_photo);
        View takeVideo = findViewById(R.id.take_video);
        View takePhotoVideo = findViewById(R.id.take_image_video);

        if (takePhoto != null) {
            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchCamera(KapooOption.IMAGE_ONLY);
                }
            });
        }

        if (takeVideo != null) {
            takeVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchCamera(KapooOption.VIDEO_ONLY);
                }
            });
        }

        if (takePhotoVideo != null) {
            takePhotoVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    launchCamera(KapooOption.IMAGE_AND_VIDEO);
                }
            });
        }
    }

    private void launchCamera(KapooOption type) {
        KapooCamera.openCamera(this, type);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PHOTO_CODE:
                    txtPath.setText(data.getStringExtra("filepath"));
                    if (data.getLongExtra("filesize", 0) > 0) {
                        txtPath.setText(txtPath.getText() + ";;" + String.valueOf(data.getLongExtra("filesize", 0)));
                    }
                    imgView.setImageBitmap(KapooCamera.bitmap);
                    break;
            }// end switch
        }
    }
}//end class
