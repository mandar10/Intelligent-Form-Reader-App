package com.example.android.ocrmain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import com.yalantis.ucrop.view.CropImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.R.attr.maxHeight;
import static android.R.attr.maxWidth;

public class ImageViewerActivity extends AppCompatActivity {
    private File mImageFolder;
    private ImageView imageView;
    private EditText formname;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_viewer);
        Intent intentget = getIntent();
        String path = intentget.getStringExtra("ImagePath");
        imageView = (ImageView)findViewById(R.id.imageViewer);
        Bitmap bmp = BitmapFactory.decodeFile(path);
        Matrix matrix = new Matrix();
        formname=(EditText)findViewById(R.id.formname);
        //set image rotation value to 90 degrees in matrix.
        matrix.postRotate(90);
        matrix.postScale(2f, 2f);

        int newWidth = bmp.getWidth();
        int newHeight = bmp.getHeight();

        imageView.setImageBitmap(bmp);
        Bitmap bMapRotate = Bitmap.createBitmap(bmp, 0, 0, newWidth, newHeight, matrix, true);

        //put rotated image in ImageView.
        imageView.setImageBitmap(bMapRotate);
        File imgf = new File(path);
        imageUri = Uri.fromFile(imgf);

        ImageView crop = (ImageView)this.findViewById(R.id.vst_crop_image);
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("formname",0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("formname", formname.getText().toString() );
                editor.commit();
                createImageFolder();

                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String prepend = "IMAGE_" + timestamp + "_";
                File imageFile = null;
                try {
                    imageFile = File.createTempFile(prepend, ".jpg", mImageFolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UCrop.Options myOptions = new UCrop.Options();
                myOptions.setAspectRatioOptions(1,
                        new AspectRatio("WOW", 1, 2),
                        new AspectRatio("MUCH", 3, 4),
                        new AspectRatio("RATIO", CropImageView.DEFAULT_ASPECT_RATIO, CropImageView.DEFAULT_ASPECT_RATIO),
                        new AspectRatio("SO", 16, 9),
                        new AspectRatio("ASPECT", 1, 1));
                myOptions.setFreeStyleCropEnabled(true);
                Uri destinationUri = Uri.fromFile(imageFile);
                UCrop.of(imageUri, destinationUri)
                        .withOptions(myOptions)
                        .withMaxResultSize(maxWidth, maxHeight)
                        .start(ImageViewerActivity.this);
            }
        });

        ImageView next = (ImageView)findViewById(R.id.vst_next_image);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("formname",0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("formname", formname.getText().toString() );
                editor.commit();
                Log.e("ResultCode","NextPage");
                Intent intent = getIntent();
                String path = intent.getStringExtra("ImagePath");
                Intent intentnext = new Intent(ImageViewerActivity.this, ImageFeildSelector.class);
                intentnext.putExtra("CropedImagePath",path);
                intentnext.putExtra("CropStat","0");
                startActivity(intentnext);
            }
        });


    }



    private void createImageFolder() {
        File imageFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        mImageFolder = new File(imageFile, "camera2VideoImage");
        if(!mImageFolder.exists()) {
            mImageFolder.mkdirs();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            imageView.setImageURI(resultUri);
            Intent intentnext = new Intent(this, ImageFeildSelector.class);
            intentnext.putExtra("CropedImagePath",resultUri.getPath());
            intentnext.putExtra("CropStat","1");
            startActivity(intentnext);

        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }
}
