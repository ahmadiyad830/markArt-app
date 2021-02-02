package com.Softwillow.MarkArt.Firebase.Error_UI;


import android.content.Context;
import android.widget.Toast;

import com.Softwillow.MarkArt.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.StorageException;

public class FireStorageError {
    protected FireStorageError() {
    }

    public static FireStorageError getInstance() {
        return new FireStorageError();
    }
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    public void getError(Context context, Throwable e) {
        switch (StorageException.fromException(e).getErrorCode()) {
            case StorageException.ERROR_BUCKET_NOT_FOUND:
                Toast.makeText(context, context.getString(R.string.send_email_dev), Toast.LENGTH_LONG).show();
                break;
            case StorageException.ERROR_CANCELED:
                Toast.makeText(context, context.getString(R.string.error)+"\n"+context.getString(R.string.operation_failed), Toast.LENGTH_LONG).show();
                break;
            case StorageException.ERROR_QUOTA_EXCEEDED:
                Toast.makeText(context, context.getString(R.string.error)+"\n"+context.getString(R.string.fixd_soon), Toast.LENGTH_LONG).show();
                break;
            case StorageException.ERROR_RETRY_LIMIT_EXCEEDED:
                Toast.makeText(context, context.getString(R.string.multi_try), Toast.LENGTH_SHORT).show();
                break;
            case StorageException.ERROR_NOT_AUTHENTICATED:
            case StorageException.ERROR_NOT_AUTHORIZED:
                Toast.makeText(context, context.getString(R.string.not_possible), Toast.LENGTH_SHORT).show();
                break;
            case StorageException.ERROR_OBJECT_NOT_FOUND:
            case StorageException.ERROR_UNKNOWN:
            case StorageException.ERROR_PROJECT_NOT_FOUND:
            case StorageException.ERROR_INVALID_CHECKSUM:
            default:
                Toast.makeText(context, context.getString(R.string.error)+"\n"+context.getString(R.string.try_again)+"\n"+context.getString(R.string.send_email_dev), Toast.LENGTH_LONG).show();
                break;
        }
    }

}
