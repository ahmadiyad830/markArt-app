package com.Softwillow.MarkArt.Firebase.Error_UI;


import android.content.Context;

import com.Softwillow.MarkArt.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FireAuthCatchError {

    private Context context;

    /**
     * {@link  FireAuthCatchError}
     * FireAuthCatchError
     */
    private FireAuthCatchError() {
    }

    public static FireAuthCatchError getInstance() {

        return new FireAuthCatchError();
    }

    public String getError(Exception errorCode) {
        String errorValue = null;
        String message = errorCode.getMessage();
        if ("ERROR_INVALID_CUSTOM_TOKEN".equals(message) || "ERROR_OPERATION_NOT_ALLOWED".equals(message) || "ERROR_MISSING_EMAIL".equals(message) || "ERROR_CUSTOM_TOKEN_MISMATCH".equals(message) || "ERROR_CREDENTIAL_ALREADY_IN_USE".equals(message) || "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL".equals(message)) {
            errorValue = String.valueOf(R.string.error);
            return errorValue;
        } else if ("ERROR_INVALID_CREDENTIAL".equals(message)) {
            errorValue = String.valueOf(R.string.account_expired);
            return errorValue;
        } else if ("ERROR_INVALID_EMAIL".equals(message)) {
            errorValue = String.valueOf(R.string.formatted_email);
            return errorValue;
        } else if ("ERROR_WRONG_PASSWORD".equals(message)) {
            errorValue = String.valueOf(R.string.password_invalid);/*"or the user does not have a password."*/
            return errorValue;
        } else if ("ERROR_USER_MISMATCH".equals(message)) {//                if this error happend we shuld be do
//                some think like logout user or asq user email
            errorValue = String.valueOf(R.string.error_credentils);
            return errorValue;
        } else if ("ERROR_REQUIRES_RECENT_LOGIN".equals(message)) {
            errorValue = String.valueOf(R.string.operation_delicate);
            /*"This operation is sensitive and requires recent authentication."+*/
            return errorValue;
        } else if ("ERROR_EMAIL_ALREADY_IN_USE".equals(message)) {
            errorValue = String.valueOf(R.string.error_email_used);
            return errorValue;
        } else if ("ERROR_USER_DISABLED".equals(message)) {
            errorValue = String.valueOf(R.string.error_email_disable);
            return errorValue;
        } else if ("ERROR_USER_TOKEN_EXPIRED".equals(message) || "ERROR_INVALID_USER_TOKEN".equals(message)) {
            errorValue = String.valueOf(R.string.error_reglongtime);
            return errorValue;
        } else if ("ERROR_USER_NOT_FOUND".equals(message)) {
            errorValue = String.valueOf(R.string.error_user_delete);
            return errorValue;
            /*"This operation is not allowed. You must enable this service in the console."*/
        } else if ("ERROR_WEAK_PASSWORD".equals(message)) {
            errorValue = String.valueOf(R.string.error_weak_password);
            return errorValue;
            /*"An email address must be provided."*/
        }
        return errorValue;
    }

    private ArrayList<String> getArrayError() {
        ArrayList<String> errorsList = new ArrayList<>();
//        just error
        errorsList.add("ERROR_INVALID_CUSTOM_TOKEN");
        errorsList.add("ERROR_OPERATION_NOT_ALLOWED");
        errorsList.add("ERROR_MISSING_EMAIL");
        errorsList.add("ERROR_CUSTOM_TOKEN_MISMATCH");
        errorsList.add("ERROR_CREDENTIAL_ALREADY_IN_USE");
        errorsList.add("ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL");

        errorsList.add("ERROR_INVALID_CREDENTIAL");
        errorsList.add("ERROR_INVALID_EMAIL");
        errorsList.add("ERROR_WRONG_PASSWORD");
        errorsList.add("ERROR_USER_MISMATCH");
        errorsList.add("ERROR_REQUIRES_RECENT_LOGIN");
        errorsList.add("ERROR_EMAIL_ALREADY_IN_USE");
        errorsList.add("ERROR_USER_DISABLED");
//        error long time
        errorsList.add("ERROR_USER_TOKEN_EXPIRED");
        errorsList.add("ERROR_INVALID_USER_TOKEN");

        errorsList.add("ERROR_USER_NOT_FOUND");
        errorsList.add("ERROR_WEAK_PASSWORD");
        return errorsList;
    }

}












































