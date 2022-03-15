package com.js.test;

import com.google.firebase.firestore.auth.User;
import com.tuya.smart.home.sdk.TuyaHomeSdk;
import com.tuya.smart.android.user.api.ILoginCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.function.IntToLongFunction;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword, etCountryCode;
    private Button btnLogin, btnRegister;

    private static final String TAG = "MySmartLight"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String countryCode = etCountryCode.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                TuyaHomesdk.getUserInstance().loginWithEmail(countryCode, email, password, loginCallback)
            }
        });
    }

    private ILoginCallback loginCallback = new ILoginCallback() {
        @Override
        public void onSuccess(User user) {
            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
        }

        @Override
        public void onError(String code, String error) {
            Log.d(TAG, "Login Failed with error " + error);
            Toast.makeText(MainActivity.this, "Login failed with error " + error, Toast.LENGTH_LONG).show();
        }
    };

    private void initViews(){
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etCountryCode = findViewById(R.id.etCountryCode);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

    }
}