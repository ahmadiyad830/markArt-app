package com.Softwillow.MarkArt.SettingApp;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;

public class FileExtension {
    private ContentResolver contentResolver;
    private MimeTypeMap mimeTypeMap;
    private Context context;

    public FileExtension(Context context) {
        this.context = context;
    }

    public String fileExtension(Uri uri) {
        contentResolver = context.getContentResolver();
        mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
}
