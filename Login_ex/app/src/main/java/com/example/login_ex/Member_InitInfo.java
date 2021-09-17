package com.example.login_ex;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.ZoneId;

public class Member_InitInfo extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText nicknameEdit;
    Button initInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);
        initInfoButton = (Button) findViewById(R.id.initInfoButton);
        nicknameEdit = (EditText) findViewById(R.id.nicknameEditText);
        mAuth = FirebaseAuth.getInstance();

        initInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initInfo();
            }
        });
    }

    private void initInfo() {
        String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        String nickname = ((EditText) findViewById(R.id.nicknameEditText)).getText().toString();
        String birth = ((EditText) findViewById(R.id.dateOfBirthEditText)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.phoneNumberEditText)).getText().toString();

        if (name.length() > 1 && nickname.length() > 2 && birth.length() > 7 && phoneNumber.length() > 10) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            DatabaseReference userDB = FirebaseDatabase.getInstance().getReference();

            Member_Info memberInfo = new Member_Info(name, nickname, birth, phoneNumber);

            if (user != null) {
                userDB.child("users").child(user.getUid()).setValue(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                userProfile(name);
                                ToastMessage("정보 등록 완료");
                                Intent intent = new Intent(Member_InitInfo.this, MainActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                ToastMessage("정보 등록 실패");
                            }
                        });
            }
        }
    }

    // 프로필 이름 설정
    private void userProfile(String name){
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                    .setDisplayName(name.trim()).build();

            user.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Log.d("Testing", "User Profile Updated");
                    }
                }
            });
        }
    }
    private void ToastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
