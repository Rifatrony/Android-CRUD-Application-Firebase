package com.example.storedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText t1, t2,t3,t4;
    Button button, loadButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("students");

        t1 = findViewById(R.id.t1);
        t2 = findViewById(R.id.t2);
        t3 = findViewById(R.id.t3);
        t4 = findViewById(R.id.t4);


        button = findViewById(R.id.Button);
        loadButton = findViewById(R.id.loadButtonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insertData();

            }
        });
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void insertData() {


        String roll = t1.getText().toString().trim();
        String name = t2.getText().toString().trim();
        String course = t3.getText().toString().trim();
        String duration = t4.getText().toString().trim();

        String key = databaseReference.push().getKey();


        if (roll.isEmpty()){
            t1.setError("Required");
            t1.requestFocus();
            return;
        }
        if (name.isEmpty()){
            t2.setError("Required");
            t2.requestFocus();
            return;
        }

        if (course.isEmpty()){
            t3.setError("Required");
            t3.requestFocus();
            return;
        }

        if (duration.isEmpty()){
            t4.setError("Required");
            t4.requestFocus();
            return;
        }


        dataholder obj = new dataholder(roll, name, course,duration);

        databaseReference.child(key).setValue(obj);



        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        Toast.makeText(getApplicationContext(), "Inserted successfully", Toast.LENGTH_LONG).show();


    }
}