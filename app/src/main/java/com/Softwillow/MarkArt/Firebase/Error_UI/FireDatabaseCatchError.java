package com.Softwillow.MarkArt.Firebase.Error_UI;

import android.content.Context;
import android.widget.Toast;
import com.Softwillow.MarkArt.Firebase.FireAuthUserInfo;
import com.Softwillow.MarkArt.Firebase.FirebaseVar;
import com.Softwillow.MarkArt.R;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;

public class FireDatabaseCatchError {
    /*getError make to give user error message understand
               setError make to give me real error
               method setError write error on database
               * */
    private FireDatabaseCatchError() {

    }

    public static FireDatabaseCatchError getInstance() {
        return new FireDatabaseCatchError();
    }

    public void setError(String nameError, String error) {

        FirebaseVar.TABLE_ERROR.child(FireAuthUserInfo.getUID()).child(nameError).setValue(error);
    }
//    use code message or message as prammetar

    public void getError(Context context, DatabaseError codeError) throws DatabaseException {
        int code = codeError.getCode();
        if (code == DatabaseError.NETWORK_ERROR || code == DatabaseError.DISCONNECTED || code == DatabaseError.USER_CODE_EXCEPTION) {
            Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_LONG).show();
        } else if (code == DatabaseError.MAX_RETRIES) {
            Toast.makeText(context, context.getString(R.string.waite), Toast.LENGTH_LONG).show();
        } else if (code == DatabaseError.WRITE_CANCELED || code == DatabaseError.OPERATION_FAILED) {
            Toast.makeText(context, R.string.operation_failed + "\n" + R.string.try_again, Toast.LENGTH_LONG).show();
        } else if (code == DatabaseError.UNKNOWN_ERROR || code == DatabaseError.OVERRIDDEN_BY_SET || code == DatabaseError.DATA_STALE || code == DatabaseError.PERMISSION_DENIED) {
            Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_LONG).show();
        } else if (code == DatabaseError.INVALID_TOKEN || code == DatabaseError.EXPIRED_TOKEN) {
            Toast.makeText(context, context.getString(R.string.accont_disable), Toast.LENGTH_LONG).show();
        }
    }


}
