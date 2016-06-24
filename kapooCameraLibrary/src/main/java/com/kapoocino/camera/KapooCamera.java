package com.kapoocino.camera;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.kapoocino.camera.cropimage.CropActivity;
import com.kapoocino.camera.picchooser.SelectPictureActivity;
import com.kapoocino.camera.picchooser.SelectVideoActivity;
import com.kapoocino.camera.squarecamera.CameraActivity;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
        try {
            InputStream image_stream = activity.getContentResolver().openInputStream(Uri.parse("file://" + uri.getPath()));
            Bitmap bitmap= BitmapFactory.decodeStream(image_stream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            cropCamera(activity, byteArray);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void cropCamera(Activity activity, byte[] bitmapByteArray) {
        Log.d("---DEBUG---", "onPictureTaken: ");
        Intent i = new Intent(activity, CropActivity.class);
        i.putExtra("byteArray", bitmapByteArray);
        activity.startActivityForResult(i,
                CROP_IMAGE);
    }
}