package ru.pulka.volunteerbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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

public class Whotook extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whotookpart);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ArrayList<String> people = new ArrayList<String>();
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("event_id").toString().equals(mySharedPreferences.getString("id_sc_v", "")) && (document.get("name").toString() + document.get("description")).toString().equals(mySharedPreferences.getString("volunt", ""))) {
                                    people.addAll(Arrays.asList(document.get("volunteers").toString().split("#")));
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
                            SharedPreferences mySharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                            LinearLayout grid = findViewById(R.id.andgrid);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (people.contains(document.get("address").toString())){
                                    LayoutInflater inflater = getLayoutInflater();
                                    View cardView = inflater.inflate(R.layout.card5, null, false);
                                    TextView v2 = cardView.findViewById(R.id.t2);
                                    v2.setText(document.get("name").toString() + document.get("surname").toString());
                                    grid.addView(cardView);
                                    }
                            }
                            }
                        }
                });
    }
}