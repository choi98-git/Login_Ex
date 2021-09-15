package com.example.login_ex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
       password = intent.getStringExtra("password");

        findViewById(R.id.logoutButton).setOnClickListener(onClickListener);
        findViewById(R.id.initInfoButton).setOnClickListener(onClickListener);
        findViewById(R.id.updatePasswordButton).setOnClickListener(onClickListener);
        findViewById(R.id.unregisterButton).setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.logoutButton:
                    Logout();
                    break;

                case R.id.initInfoButton:
                    GoInitInfo();
                    break;

                case R.id.updatePasswordButton:
                    UpgradePassWord();
                    break;

                case R.id.unregisterButton:
                    Unregister();
                    break;
            }
        }
    };

    private void ToastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }



    private void Logout(){
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void GoInitInfo(){
        Intent intent = new Intent(MainActivity.this, Member_InitInfo.class);
        startActivity(intent);
    }

    private void UpgradePassWord(){
        Intent intent = new Intent(MainActivity.this, Check_Password.class);
        intent.putExtra("password",password);
        startActivity(intent);
    }

    private void Unregister(){

    }

}