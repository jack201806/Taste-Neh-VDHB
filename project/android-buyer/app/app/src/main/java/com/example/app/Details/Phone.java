package com.example.app.Details;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;

public class Phone extends AppCompatActivity {
    private EditText phone;
    private Button commit;
    private String userPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_phone);
        phone = findViewById(R.id.phone);
        commit = findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userPhone = phone.getText().toString();
                Intent intent=new Intent(getApplicationContext(),Detail.class);
                intent.putExtra("userPhone",userPhone);
                setResult(14,intent);
                finish();
            }
        });
    }
}