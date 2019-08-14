package com.minorproject.pariksha;

import android.net.Uri;

public class UploadPhoto {

    private String name;
    private Uri mImageUri;

    public UploadPhoto(String name, double v) {
    }

    public UploadPhoto(String name, Uri mImageUri) {
        this.name = name;
        this.mImageUri = mImageUri;
    }

    public UploadPhoto() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getmImageUri() {
        return mImageUri;
    }

    public void setmImageUri(Uri mImageUri) {
        this.mImageUri = mImageUri;
    }
}
