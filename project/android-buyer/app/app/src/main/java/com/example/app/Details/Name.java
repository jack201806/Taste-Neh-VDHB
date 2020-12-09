package com.example.app.Details;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app.R;
import com.example.app.fragments.MyFragment;

public class Name extends AppCompatActivity {
    private EditText name;
    private Button commit;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_name);
        name=findViewById(R.id.name);
        commit=findViewById(R.id.commit);


        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName=name.getText().toString().trim();
                Intent intent=new Intent(getApplicationContext(),Detail.class);
                intent.putExtra("userName",userName);
                setResult(13,intent);
                finish();
            }
        });
    }
}