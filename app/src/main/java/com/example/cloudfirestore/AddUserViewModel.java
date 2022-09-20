package com.example.cloudfirestore;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class AddUserViewModel extends BaseObservable {
    private String username;
    private String password;
    public ObservableField<String> messageAdd = new ObservableField<>();
    public ObservableField<Boolean> isSuccess = new ObservableField<>();
    public ObservableField<Boolean> isShowMessage = new ObservableField<>();

    private static final String TAG = "MainActivity";
    private static final String KEY_USER = "username";
    private static final String KEY_PASS = "pass";
    private static int index = 1;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Bindable
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyPropertyChanged(BR.username);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }

    public void onClickAdd(){
        User user = new User(getUsername(), getPassword());
        isShowMessage.set(true);
        if(!user.getUsername().isEmpty() && user.isValidPasswords()){
            isSuccess.set(true);
            messageAdd.set("Add User Success!");
            Map<String, Object> accountUser = new HashMap<>();
            accountUser.put(KEY_USER, user.getUsername());
            accountUser.put(KEY_PASS, user.getPassword());
            db.collection("UserAccount").add(accountUser)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.getAppContext(), "Success!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
            index++;
        }
        else{
            isSuccess.set(false );
            messageAdd.set("Passwords < 6 characters!");
        }
        db.collection("UserAccount")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                Log.e(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });

    }
}
