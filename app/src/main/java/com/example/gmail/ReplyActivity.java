package com.example.gmail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ReplyActivity extends AppCompatActivity {

    EditText edtDestination, edtSubject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        findViewById(R.id.edt_from).setEnabled(false);

        edtDestination = findViewById(R.id.edt_destination);
        edtSubject = findViewById(R.id.edt_subject);

        Intent intentFromMain = getIntent();

        Bundle dataBundle = intentFromMain.getExtras();
        edtDestination.setText(dataBundle.getString("email"));
        edtSubject.setText("Re:" + dataBundle.getString("subject"));
    }
}