package com.example.galaxyplayer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText nameEditText;
    private Button enterButton;


    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";
    public static final String IS_LOGIN = "isLogin";

    private String userName;
    private Boolean isLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameEditText = findViewById(R.id.nameEditText);
        enterButton = findViewById(R.id.EnterButton);

        loadData();

        if (isLogin) {

            Intent mainActivity = new Intent(this, MainActivity.class);
            mainActivity.putExtra(NAME , userName);
            startActivity(mainActivity);
        }

        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (nameEditText.getText() == null) {

                    Toast.makeText(LoginActivity.this, "Please Enter your name", Toast.LENGTH_LONG).show();

                } else {

                    isLogin = true;

                    saveData();

                    loadData();

                    Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);

                    mainActivity.putExtra(NAME, userName);

                    startActivity(mainActivity);
                }


            }
        });


    }

    public void saveData() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NAME, nameEditText.getText().toString());
        editor.putBoolean(IS_LOGIN, isLogin);

        editor.apply();

    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        userName = sharedPreferences.getString(NAME, "My Friend");
        isLogin = sharedPreferences.getBoolean(IS_LOGIN, false);

    }
}
