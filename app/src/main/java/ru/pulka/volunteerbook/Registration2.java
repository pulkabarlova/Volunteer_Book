package ru.pulka.volunteerbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registration2 extends AppCompatActivity {
    String schol_id = "";
    String activities_worked = "";
    String hours_worked = "";

    int c = 0;

    public boolean checker1(String[] args) {
        return args.length == 3;
    }

    public boolean checker2(String args) {
        return args.split("\\.").length == 3 && args.length() == 10;
    }

    public boolean checker3(String args) {
        return Integer.parseInt(args) <= 11 && Integer.parseInt(args) >= 1;
    }

    public boolean checker4(String args) {
        int count = 0;
        for (int i = 0; i < args.length(); i++) {
            if (Character.isDigit(args.charAt(i))) {
                count++;
            }
        }
        return args.length() > 7 && args.length() < 15 && count >= 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration2);
        Button but_per = findViewById(R.id.button_volunteer);
        but_per.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] name = ((EditText) findViewById(R.id.editTextTextPersonName)).getText().toString().split(" ");
                String data = ((EditText) findViewById(R.id.editTextTextPersoDate)).getText().toString();
                String address = ((EditText) findViewById(R.id.editTextTextPersoAdress)).getText().toString();
                String form = ((EditText) findViewById(R.id.editTextTextPersonForm)).getText().toString();
                String password = ((EditText) findViewById(R.id.password_space)).getText().toString();
                if (!checker4(password)) {
                    Toast toast4 = Toast.makeText(getApplicationContext(), "Пароль должен содержать как буквы, так и цифры и быть не более 14 и не менее 8 символов", Toast.LENGTH_LONG);
                    toast4.show();
                }
                if (!checker1(name)) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Ошибка при вводе ФИО", Toast.LENGTH_LONG);
                    toast.show();
                }
                if (!checker2(data)) {
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Ошибка при вводе даты рожденья", Toast.LENGTH_LONG);
                    toast2.show();
                }
                if (!checker3(form)) {
                    Toast toast3 = Toast.makeText(getApplicationContext(), "Ошибка при вводе класса", Toast.LENGTH_LONG);
                    toast3.show();
                }
                if (c == 0 && checker1(name) && checker2(data) && checker3(form) && checker4(password)) {
                    SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    schol_id = mySharedPreferences.getString("id_sc_v", "");
                    DBHelper.getInstance().addUser(name, data, address, form, password, schol_id, activities_worked, hours_worked);
                    c++;
                    Intent intent = new Intent(Registration2.this, main_for_vol.class);
                    startActivity(intent);
                }

            }
        });

    }
}