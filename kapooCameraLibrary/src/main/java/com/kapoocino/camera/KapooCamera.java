package com.kapoocino.camera;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;

import com.kapoocino.camera.cropimage.CropActivity;
import com.kapoocino.camera.picchooser.SelectPictureActivity;
import com.kapoocino.camera.picchooser.SelectVideoActivity;
import com.kapoocino.camera.squarecamera.CameraActivity;

/**
 * Created by accan on 21/06/16.
 */
public class KapooCamera{
    public static final int CROP_IMAGE = 5;
    public static final int SELECT_GALLERY_VIDEO_CODE = 6;
    public static final int SELECT_GALLERY_IMAGE_CODE = 7;
    public static final int TAKE_PHOTO_CODE = 8;
    public static final int ACTION_REQUEST_EDITIMAGE = 9;

    public static void openCamera(Activity activity, KapooOption option) {
        Intent startCustomCameraIntent = new Intent(activity, CameraActivity.class);
        startCustomCameraIntent.putExtra("cameraType", option);
        activity.startActivityForResult(startCustomCameraIntent, TAKE_PHOTO_CODE);
    }

    public static void openPhotoGallery(Activity activity) {
        activity.startActivityForResult(new Intent(
                        activity, SelectPictureActivity.class),
                SELECT_GALLERY_IMAGE_CODE);
    }

    public static void openVideoGallery(Activity activity) {
        activity.startActivityForResult(new Intent(
                        activity, SelectVideoActivity.class),
                SELECT_GALLERY_VIDEO_CODE);
    }

    public static void cropCamera(Activity activity, Uri uri) {
        Intent i = new Intent(activity, CropActivity.class);
        i.putExtra("filepath", uri.getPath());
        activity.startActivityForResult(i,
                CROP_IMAGE);
    }
}