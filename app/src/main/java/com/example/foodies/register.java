package com.example.foodies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {

    TextView txt_login;
    EditText inp_password, inp_email, inp_user_name;
    Button btn_register;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txt_login = findViewById(R.id.txt_login);
        inp_email = findViewById(R.id.inp_email);
        inp_password = findViewById(R.id.inp_password);
        inp_user_name = findViewById(R.id.inp_username);
        btn_register = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressbar);

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
                finish();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tmp_email, tmp_password, tmp_username;

                tmp_email = String.valueOf(inp_email.getText());
                tmp_username = String.valueOf(inp_user_name.getText());
                tmp_password = String.valueOf(inp_password.getText());

                DatabaseHelper DB = new DatabaseHelper(getApplicationContext());

                if (TextUtils.isEmpty(tmp_email)) {
                    Toast.makeText(getApplicationContext(), "Enter correct email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tmp_password)) {
                    Toast.makeText(getApplicationContext(), "Enter correct password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(tmp_username)) {
                    Toast.makeText(getApplicationContext(), "Enter correct username", Toast.LENGTH_SHORT).show();
                    return;
                }
                boolean isEmailRegistered = DB.checkEmailExists(tmp_email);

                if (isEmailRegistered) {
                    Toast.makeText(getApplicationContext(), "Email already registered", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    progressBar.setVisibility(View.VISIBLE);

                    boolean isInserted = DB.insertData(tmp_username, tmp_email, tmp_password);
                    if (isInserted) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), login.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 2000); // Delay in milliseconds (e.g., 2000 = 2 seconds)

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }
        });
    }
}
