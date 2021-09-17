package com.example.login_ex;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdateUserInfo extends AppCompatActivity {

    AlertDialog.Builder dlg;
    DialogInterface.OnClickListener yes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        findViewById(R.id.changePassword).setOnClickListener(onClickListener);
        findViewById(R.id.unregister).setOnClickListener(onClickListener);

        yes = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(UpdateUserInfo.this,"정상적으로 회원이 탈퇴되었습니다", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(UpdateUserInfo.this, LoginActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
            }
        };


    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.changePassword:
                    Intent intent = new Intent(UpdateUserInfo.this, UpdatePassword.class);
                    startActivity(intent);
                    break;

                case R.id.unregister:
                    dlg = new AlertDialog.Builder(UpdateUserInfo.this);
                    dlg.setTitle("회원탈퇴");
                    dlg.setMessage("정말로 회원을 탈퇴하시겠습니까?");
                    dlg.setPositiveButton("예", yes);
                    dlg.setNegativeButton("아니요", null);
                    dlg.show();
                    break;

            }
        }
    };
}
