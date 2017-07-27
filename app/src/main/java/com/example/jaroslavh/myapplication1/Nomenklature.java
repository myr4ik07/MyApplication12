package com.example.jaroslavh.myapplication1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import static android.R.drawable.alert_dark_frame;

public class Nomenklature extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    protected ArrayList<Drawable> imgArray = new ArrayList<>();
    private int currentImage = 0;
    private boolean thisPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomenklature);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void saveNomenclature(View view) {

    }

    public void previousPhoto(View view) {

        if ((imgArray.size() == 0) | (currentImage <= 0)) {
            return;
        }

        if (currentImage > imgArray.size()) {
            currentImage = imgArray.size() - 1;
        } else {
            currentImage = currentImage - 1;
        }

        ImageView mImageView = (ImageView) findViewById(R.id.imageView2);
        mImageView.setImageDrawable(imgArray.get(currentImage));
    }

    public void nextPhoto(View view) {
        ImageView mImageView = (ImageView) findViewById(R.id.imageView2);

        if (thisPhoto) {
            saveImage(mImageView.getDrawable());
            currentImage = imgArray.size() - 1;
        }

        if (currentImage + 1 >= imgArray.size()) {
            mImageView.setImageResource(alert_dark_frame);
            thisPhoto = false;
            currentImage++;
        } else {
            currentImage = currentImage + 1;
            mImageView.setImageDrawable(imgArray.get(currentImage));
        }

    }

    private void saveImage(Drawable drawable) {
        imgArray.add(drawable);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

            ImageView mImageView = (ImageView) findViewById(R.id.imageView2);

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);

            thisPhoto = true;
        }
    }
}