package ru.pulka.volunteerbook;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Registration2_org extends AppCompatActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2_org);
        Button but_sav = findViewById(R.id.button_saveorg);
        EditText name = findViewById(R.id.editTextTextPersonName);
        EditText birth = findViewById(R.id.editTextTextPersoDate);
        EditText form = findViewById(R.id.editTextTextPersonForm);
        EditText email = findViewById(R.id.editTextTextPersoAdress);
//        dbHelper = new DBHelper();
//        dbHelper.addUser();

        but_sav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration2_org.this, main_for_org.class);
                startActivity(intent);
                String name_txt = name.getText().toString();
                String email_txt = email.getText().toString();
                String form_txt = form.getText().toString();
                String birth_txt = birth.getText().toString();

                ContentValues contentValues = new ContentValues();
            }
        });

    }
}