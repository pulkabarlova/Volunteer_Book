package ru.pulka.volunteerbook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    String sss = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Button volunteer_button = findViewById(R.id.button_volunteer);
        Button button_enter = findViewById(R.id.button_enter);
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView2);
//        adapterItems = new ArrayAdapter<String>(this, R.layout.drop_down, items);
//        autoCompleteTextView.setAdapter(adapterItems);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        volunteer_button.setOnClickListener(new Button.OnClickListener() {
            public String s_f_i = "";

            @Override
            public void onClick(View v) {
                String school_auto = autoCompleteTextView.getText().toString();
                if (!school_auto.equals("Школа")  && !school_auto.equals("") && !school_auto.equals("школа")) {
                    db.collection("schools")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        ArrayList<String> items = new ArrayList<>();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            if (String.valueOf(document.get("school")).equals(school_auto)) {
                                                s_f_i = document.get("id").toString();
                                                SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor = mySharedPreferences.edit();
                                                editor.putString("id_sc_v", s_f_i);
                                                editor.apply();
                                                Toast toast1 = Toast.makeText(getApplicationContext(), mySharedPreferences.getString("id_sc_v", ""), Toast.LENGTH_LONG);
                                                toast1.show();
                                                sss = s_f_i;
                                            }
                                        }
                                    } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                                    }
                                }
                            });
                    Intent intent = new Intent(MainActivity.this, Registration2.class);
                    startActivity(intent);

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Выберите школу", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
        button_enter.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Enter.class);
                startActivity(intent);
            }
        });
        
        db.collection("schools")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Loaded", Toast.LENGTH_LONG).show();
                            ArrayList<String> items = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                items.add(String.valueOf(document.get("school")));
                            }

                            adapterItems = new ArrayAdapter<String>(getApplicationContext(), R.layout.drop_down, items);
                            autoCompleteTextView.setAdapter(adapterItems);
                        } else {
//                            Log.w(TAG, "Error getting documents.", task.getException());
                            throw new NullPointerException();
                        }
                    }
                });
    }
}