package com.gxb.lazynetlibrary.utils.photo;

public interface PhotoReadyHandler {

    public static final int FROM_CAMERA = 1;
    public static final int FROM_ALBUM = 2;
    public static final int FROM_CROP = 3;

    public void onPhotoReady(int from, String imgPath);
}
