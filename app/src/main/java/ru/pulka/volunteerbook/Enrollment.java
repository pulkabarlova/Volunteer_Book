package ru.pulka.volunteerbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Enrollment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String[] ch = {""};
        Integer j = 0;
        setContentView(R.layout.activity_enrollment);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<String> people = new ArrayList<String>();
        String[] hours = new String[1000];
        EditText[] texts = new EditText[1000];
        SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("event_id").toString().equals(mySharedPreferences.getString("id_sc_v", "")) && (document.get("name").toString() + document.get("description")).toString().equals(mySharedPreferences.getString("event_desc", ""))) {
                                    people.addAll(Arrays.asList(document.get("volunteers").toString().split("#")));
                                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                                    editor.putString("tec_hours", document.get("hours").toString());
                                    ch[0] = document.get("hours").toString();

                                }
                            }
                        }
                    }
                });
        db.collection("volunteers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            LinearLayout grid = findViewById(R.id.andgrid);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (people.contains(document.get("address").toString())) {
                                    LayoutInflater inflater = getLayoutInflater();
                                    View cardView = inflater.inflate(R.layout.card5, null, false);
                                    TextView v2 = cardView.findViewById(R.id.t2);
                                    v2.setText(document.get("name").toString() + " " + document.get("surname"));
                                    EditText edit = cardView.findViewById(R.id.hourrs);

                                    edit.setText(ch[0]);
                                    grid.addView(cardView);
                                    int c = people.indexOf(document.get("address"));
                                    texts[c] = edit;
                                    //hours[c] = edit.getText().toString();

                                }
                            }
                        }
                    }
                });
        Button sav = findViewById(R.id.savehours);
        sav.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("volunteers")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    if (task.isSuccessful()) {
                                        SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                                        if (document.get("id_school").toString().equals(mySharedPreferences.getString("id_sc_v", ""))
                                                && people.contains(document.get("address").toString())) {
                                            //Toast toast3 = Toast.makeText(getApplicationContext(),hours[c], Toast.LENGTH_LONG);
                                            //toast3.show();

                                            Integer num = people.indexOf(document.get("address").toString());
                                            hours[num] = texts[num].getText().toString();
                                            Integer hourss = Integer.parseInt(Objects.requireNonNull(document.get("hours")).toString()) + Integer.parseInt(hours[num]);
                                            HashMap map1 = new HashMap();
                                            map1.put("hours", String.valueOf(hourss));
                                            String map2_s = document.get("activities").toString() + mySharedPreferences.getString("event_name", "") + "_" + hours[num] + "#";
                                            HashMap map2 = new HashMap();
                                            map2.put("activities", map2_s);
                                            db.collection("volunteers").document(document.getId()).update(map1);
                                            db.collection("volunteers").document(document.getId()).update(map2);
                                        }

                                    }
                                }
                            }
                        });
                Intent intent = new Intent(Enrollment.this, main_for_org.class);
                startActivity(intent);

            }
        });
    }
}