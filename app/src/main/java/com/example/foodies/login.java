package com.example.foodies;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class login extends AppCompatActivity {

    TextView txt_register;
    EditText inp_password, inp_email;
    Button btn_login;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        txt_register = findViewById(R.id.txt_register);
        inp_email = findViewById(R.id.inp_email);
        inp_password = findViewById(R.id.inp_password);
        btn_login = findViewById(R.id.btn_login);
        progressBar = findViewById(R.id.progressbar);
        SharedPreferences sp = getSharedPreferences("login_user_pref", MODE_PRIVATE);

        DatabaseHelper DB = new DatabaseHelper(getApplicationContext());

        boolean has_loggedin = sp.getBoolean("hasloggedin", false);
        if (has_loggedin) {
            Intent intent = new Intent(getApplicationContext(), profileActivity.class);
            startActivity(intent);
            finish();
            return;
        }



        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp_email, tmp_password, tmp_username;

                tmp_email = String.valueOf(inp_email.getText());
                tmp_password = String.valueOf(inp_password.getText());


                if (TextUtils.isEmpty(tmp_email)) {
                    Toast.makeText(login.this, "Enter correct email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(tmp_password)) {
                    Toast.makeText(login.this, "Enter correct password", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                boolean isValidLogin = DB.checkLoginCredentials(tmp_email, tmp_password);

                if (isValidLogin) {

                    SharedPreferences.Editor editor = sp.edit();
                    editor.putBoolean("hasloggedin", true);
                    editor.putString("user_email", tmp_email);
                    editor.putString("user_password", tmp_password);
                    editor.apply();
                    editor.commit();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), profileActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }, 2000);

                } else {
                    Toast.makeText(login.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }


            }


        });


    }
}