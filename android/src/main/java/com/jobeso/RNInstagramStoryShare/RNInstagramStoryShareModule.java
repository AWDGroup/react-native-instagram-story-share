package com.jobeso.RNInstagramStoryShare;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

public class RNInstagramStoryShareModule extends ReactContextBaseJavaModule {

    ReactApplicationContext reactContext;

    public static final String NOT_INSTALLED = "Not installed";
    public static final String INTERNAL_ERROR = "Data conversion failed";
    public static final String NO_BASE64_IMAGE = "No base64 image";
    public static final String INVALID_PARAMETER = "Invalid parameter";
    private static final String MEDIA_TYPE_JPEG = "image/*";

    public RNInstagramStoryShareModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNInstagramStoryShare";
    }


    @ReactMethod
    public void isInstagramInstalledOnDevice(Promise promise) {
        try {
            Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Activity activity = getCurrentActivity();
            if (activity.getPackageManager().resolveActivity(intent, 0) != null)
            {
                promise.resolve("OK");
            }
            else
            {
                promise.reject("ERROR", new Exception(NOT_INSTALLED));
            }
        } catch (Exception e) {
            promise.reject("ERROR", e);
        }

    }


    private static boolean isPackageInstalled(Context c, String targetPackage) {
        PackageManager pm = c.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    @ReactMethod
    public void share(ReadableMap options, Promise promise) {
        try {
            // Define image asset URI and attribution link URL
            Uri backgroundAssetUri = Uri.parse(options.getString("backgroundImage"));
            Uri stickerImageUri = Uri.parse(options.getString("stickerImage"));
            String attributionLinkUrl = options.getString("deeplinkingUrl");


            // Instantiate implicit intent with ADD_TO_STORY action,
            // background asset, and attribution link
            Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(backgroundAssetUri, MEDIA_TYPE_JPEG);
            intent.putExtra("interactive_asset_uri", stickerImageUri);
            intent.putExtra("content_url", attributionLinkUrl);

            // Instantiate activity and verify it will resolve implicit intent
            Activity activity = getCurrentActivity();
            activity.grantUriPermission(
                    "com.instagram.android", stickerImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (activity.getPackageManager().resolveActivity(intent, 0) != null)
            {
                activity.startActivityForResult(intent, 0);
                promise.resolve("OK");
            }
            else
            {
                promise.reject("ERROR", new Exception(NOT_INSTALLED));
            }


        } catch (ActivityNotFoundException ex) {
            promise.reject("ERROR", ex);
        }
    }

}
