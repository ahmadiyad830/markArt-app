package com.Softwillow.MarkArt.Firebase.Database;



import com.Softwillow.MarkArt.Firebase.FirebaseVar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;

public interface ConditionWriteDatabase<TResult> extends OnCompleteListener<TResult> , OnFailureListener , FirebaseVar {

}
