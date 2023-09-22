package ru.pulka.volunteerbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Enter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_enter);
        Button goin = findViewById(R.id.going_vol);
        Button goin2 = findViewById(R.id.goin_org);
        goin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.email_space)).getText().toString();
                String password = ((EditText) findViewById(R.id.password_space)).getText().toString();
                db.collection("organizators")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.get("email").toString().equals(email) && document.get("password").toString().equals(password)) {
                                            SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = mySharedPreferences.edit();
                                            editor.putString("id_sc_v", (String) document.get("id"));
                                            editor.apply();
                                            Intent intent = new Intent(Enter.this, main_for_org.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }
                        });
            }
        });
        goin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText) findViewById(R.id.email_space)).getText().toString();
                String password = ((EditText) findViewById(R.id.password_space)).getText().toString();
                db.collection("volunteers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {

                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        if (document.get("address").toString().equals(email) && document.get("password").toString().equals(password)) {
                                            SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = mySharedPreferences.edit();
                                            editor.putString("id_sc_v", (String) document.get("id_school"));
                                            editor.putString("address", (String) document.get("address"));
                                            editor.apply();
                                            Intent intent = new Intent(Enter.this, main_for_vol.class);
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }
                        });
            }
        });
    }
}