package com.example.login_ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePassword extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText updatePasswordEdit, checkUpdatePasswordEdit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        mAuth = FirebaseAuth.getInstance();

        updatePasswordEdit = (EditText) findViewById(R.id.updatePassword);
        checkUpdatePasswordEdit = (EditText) findViewById(R.id.checkUpdatePassword);
        findViewById(R.id.updatePasswordButton).setOnClickListener(onClickListener);
        findViewById(R.id.cancelButton).setOnClickListener(onClickListener);


    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.updatePasswordButton:
                    updatePassword();
                    break;

                case R.id.cancelButton:
                    mAuth.signOut();
                    Intent intent = new Intent(UpdatePassword.this, LoginActivity.class);
                    startActivity(intent);
            }
        }
    };
    private void updatePassword(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(updatePasswordEdit.length() > 0 && checkUpdatePasswordEdit.length() > 0){
            if(updatePasswordEdit.length() > 5 && checkUpdatePasswordEdit.length() > 5) {
                if (updatePasswordEdit.getText().toString().equals(checkUpdatePasswordEdit.getText().toString())) {
                    String newPassword = updatePasswordEdit.getText().toString();

                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        ToastMessage("??????????????? ??????????????? ?????????????????????!");
                                    }
                                }
                            });
                } else {
                    ToastMessage("????????? ??????????????? ???????????? ????????????!!");
                }
            }else {
                ToastMessage("??????????????? 6?????? ??????????????? ?????????!!");
            }
        }else {
            ToastMessage("????????? ??????????????? ????????? ?????????!!");
        }
    }
    private void ToastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
