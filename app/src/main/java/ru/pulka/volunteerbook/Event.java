package ru.pulka.volunteerbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Event extends AppCompatActivity {
    String volunteer = "";
    String is_going = "True";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Button create = findViewById(R.id.create_event);
        create.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)findViewById(R.id.eventnameinput)).getText().toString();
                String description = ((EditText)findViewById(R.id.eventdescription)).getText().toString();
                String hours = ((EditText)findViewById(R.id.eventhours)).getText().toString();
                SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                DBHelper.getInstance().addEvent(name, description, mySharedPreferences.getString("id_sc_v", ""), hours, volunteer, is_going);
                Intent intent = new Intent(Event.this, main_for_org.class);
                startActivity(intent);

            }
        });
    }
}
