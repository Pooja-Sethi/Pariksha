package com.minorproject.pariksha;

import android.net.Uri;

public class UploadPhoto {

    private String name;
    private Uri mImageUri;

    public UploadPhoto() {
    }

    public UploadPhoto(String name, Uri mImageUri) {
        this.name = name;
        this.mImageUri = mImageUri;
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
