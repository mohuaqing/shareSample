package com.lazadazal.android;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMediaContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.facebook.share.widget.ShareDialog.Mode;

/**
 * @author: ryan.lc
 * @date: 2018/4/11
 * @version: v1.0
 * @description: facebook share,from sdk,can share link or image
 */
public class FacebookSharePlatform {

    public static final String TAG = "SHARE_Facebook";

    public static final String PACKAGE = "com.facebook.katana";

    public CallbackManager mCallbackManager;

    public FacebookSharePlatform() {
        mCallbackManager = CallbackManager.Factory.create();
    }

    /**
     * share utl to facebook
     *
     * @param context
     * @param url
     * @param subject
     * @param packageName
     */
    public void shareUrl(Context context, String url, String title, String description) {
        // if a url, can share use sdk
        Log.d(TAG, "share url with sdk=" + url);
        ShareLinkContent content = new ShareLinkContent.Builder().setContentUrl(Uri.parse(url)).build();
        shareContent((Activity)context, content);
    }

    protected void shareImgUri(Context context, String title, Uri uri) {
        Log.d(TAG, "share img with sdk");
        //use sdk to share
        if (context instanceof Activity) {
            SharePhoto photo = new SharePhoto.Builder().setImageUrl(uri).build();
            SharePhotoContent bitmapContent = new SharePhotoContent.Builder().addPhoto(photo).build();
            shareContent((Activity)context, bitmapContent);
        } else {
            Log.e(TAG, "share error,should use activity to share");
            throw new RuntimeException("facebook share should use activity to share");
        }
    }

    /**
     * 分享多图功能
     */
    public void shareImgUris(Context context, List<Uri> uriList) {
        Log.d(TAG, "share img with sdk");
        //use sdk to share
        if (context instanceof Activity) {
            ShareMediaContent.Builder bitmapContentBuilder = new ShareMediaContent.Builder();
            for (Uri imageUri : uriList) {
                // set file provider uri
                SharePhoto.Builder photoBuilder = new SharePhoto.Builder();
                photoBuilder.setImageUrl(imageUri);
                bitmapContentBuilder.addMedium(photoBuilder.build());
            }
            shareContent((Activity)context, bitmapContentBuilder.build());
        } else {
            Log.e(TAG, "share error,should use activity to share");
            throw new RuntimeException("facebook share should use activity to share");
        }
    }

    /**
     * 唤起分享dialog
     *
     * @param activity
     * @param content
     */
    public void shareContent(final Activity activity, ShareContent content) {
        ShareDialog shareDialog = new ShareDialog(activity);
        shareDialog.registerCallback(mCallbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Log.d(TAG, "share result success: " + result.getPostId());
                Toast.makeText(activity, " share result success: " + result.getPostId(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "share result cancel");
                Toast.makeText(activity, " share result cancel", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "share result error");
                Toast.makeText(activity, " share result error: " + error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        shareDialog.show(content, Mode.AUTOMATIC);
    }

}
