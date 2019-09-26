package com.lazadazal.android;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentProviderClient;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.facebook.FacebookSdk;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

public class MainActivity extends AppCompatActivity {

    private FacebookSharePlatform facebookSharePlatform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        facebookSharePlatform = new FacebookSharePlatform();

        findViewById(R.id.button2).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookSharePlatform.shareUrl(MainActivity.this, "https://s.lazada.co.th/s.ZkuXG", "the share title", " the share description");
            }
        });

        findViewById(R.id.button3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // share url
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image_3900);
                //File localImageFile = saveBitmapToCache(MainActivity.this, bitmap);
                //Uri uri = ShareFileProvider.getUriForFile(getApplicationContext(),
                //    getString(R.string.file_provider_authories), localImageFile);

                SharePhoto photo = new SharePhoto.Builder()
                    .setBitmap(bitmap).build();
                SharePhotoContent bitmapContent = new SharePhotoContent.Builder().addPhoto(photo).build();
                facebookSharePlatform.shareContent(MainActivity.this, bitmapContent);
                //facebookSharePlatform.shareImgUri(MainActivity.this, "the share title", uri);
            }
        });

        findViewById(R.id.button4).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // share url
                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.image_3900);
                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.image_6397);
                Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.image_hori);
                SharePhoto photo = new SharePhoto.Builder().setBitmap(bitmap1).build();
                SharePhoto photo2 = new SharePhoto.Builder().setBitmap(bitmap2).build();
                SharePhoto photo3 = new SharePhoto.Builder().setBitmap(bitmap3).build();
                SharePhotoContent bitmapContent = new SharePhotoContent.Builder().addPhoto(photo)
                    .addPhoto(photo2).addPhoto(photo3).build();
                facebookSharePlatform.shareContent(MainActivity.this, bitmapContent);
                //facebookSharePlatform.shareImgUri(MainActivity.this, "the share title", uri);
            }
        });

        findViewById(R.id.button5).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // share url

                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.image_3900);
                Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.image_6397);
                Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.image_hori);
                File localImageFile = saveBitmapToCache(MainActivity.this, bitmap1);
                Uri uri = ShareFileProvider.getUriForFile(getApplicationContext(),
                    getString(R.string.file_provider_authories), localImageFile);

                SharePhoto photo = new SharePhoto.Builder().setImageUrl(uri).build();
                SharePhoto photo2 = new SharePhoto.Builder().setBitmap(bitmap2).build();
                SharePhoto photo3 = new SharePhoto.Builder().setBitmap(bitmap3).build();
                SharePhotoContent bitmapContent = new SharePhotoContent.Builder().addPhoto(photo)
                    .addPhoto(photo2).addPhoto(photo3).build();
                facebookSharePlatform.shareContent(MainActivity.this, bitmapContent);
                //facebookSharePlatform.shareImgUri(MainActivity.this, "the share title", uri);
            }
        });

    }

    public static File saveBitmapToCache(Context context, Bitmap bitmap) {
        File parentFile = new File(getDiskCachePath(context) + File.separatorChar + "share_cache");
        if (!parentFile.exists() || !parentFile.isDirectory()) {
            parentFile.mkdirs();
        }
        File bitmapFile = new File(parentFile.getAbsolutePath(), System.currentTimeMillis() + ".png");
        if (bitmapFile.exists()) {
            bitmapFile.delete();
        }

        FileOutputStream fos = null;
        try {
            bitmapFile.createNewFile();
            fos = new FileOutputStream(bitmapFile);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            return bitmapFile;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public static String getDiskCachePath(Context context) {
        return context.getCacheDir().getPath();
    }
}
