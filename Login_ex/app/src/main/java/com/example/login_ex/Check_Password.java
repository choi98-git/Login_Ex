package com.example.login_ex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Check_Password extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView emailTextView;
    EditText passwordEditText;
    Button checkButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_password);

        emailTextView = (TextView) findViewById(R.id.emailTextView);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        checkButton = (Button) findViewById(R.id.checkButton);
        mAuth = FirebaseAuth.getInstance();

        setEmailText();
        CheckPassword();


    }
    private void setEmailText(){
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            emailTextView.setText(currentUser.getEmail());
        }
    }

    private void CheckPassword(){
        Intent intent = getIntent();
        String password = intent.getStringExtra("password");
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEditText.length() > 0) {
                    if (passwordEditText.getText().toString().equals(password)) {
                        Intent intent1 = new Intent(Check_Password.this, UpdatePassword.class);
                        startActivity(intent1);
                    } else {
                        ToastMessage("입력하신 비밀번호가 틀렸습니다!!");
                    }
                } else {
                    ToastMessage("비밀번호를 입력해 주세요!!");
                }
            }
        });
    }
    private void ToastMessage(String message){
        Toast.makeText(this, message,Toast.LENGTH_SHORT).show();
    }
}
