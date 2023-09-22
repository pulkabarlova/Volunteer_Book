package ru.pulka.volunteerbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;


public class Vol_Home_frag extends Fragment {
    int hours = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vol__home_frag, container, false);
        LinearLayout grid = view.findViewById(R.id.androidlin);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        TextView itog = view.findViewById(R.id.itog);
        String itog_hours = itog.toString();
        db.collection("volunteers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences mySharedPreferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (Objects.requireNonNull(document.get("id_school")).toString().equals(mySharedPreferences.getString("id_sc_v", ""))
                                        && Objects.requireNonNull(document.get("address")).toString().equals(mySharedPreferences.getString("address", ""))) {
                                    LayoutInflater inflater = LayoutInflater.from(getContext());
                                    int hss = Integer.parseInt(document.get("hours").toString());
                                    String ch = "";

                                    if (hss % 10 == 0 || hss % 10 == 5 || hss % 10 == 6 ||hss % 10 == 7 || hss % 10 == 8 || hss % 10 == 9){
                                        ch = "часов";
                                    }
                                    if (hss % 10 == 2 || hss % 10 == 3 || hss % 10 == 4){
                                        ch = "часа";
                                    }
                                    if (hss % 10 == 1){
                                        ch = "час";
                                    }

                                    itog.setText("ИТОГ: " + document.get("hours").toString() + " " + ch);
                                    if (document.get("activities") != null) {
                                        LinearLayout grid = view.findViewById(R.id.androidlin);
                                        String[] arr = Objects.requireNonNull(document.get("activities")).toString().split("#");
                                        if (arr[0].length() != 0) {
                                            for (String s : arr) {
                                                View cardView = inflater.inflate(R.layout.card4, null, false);
                                                TextView v1 = cardView.findViewById(R.id.t1);
                                                v1.setText(s.split("_")[0]);
                                                TextView v2 = cardView.findViewById(R.id.t2);
                                                String str = "";
                                                int hs = Integer.parseInt(s.split("_")[1]);
                                                if (hs % 10 == 0 || hs % 10 == 5 || hs % 10 == 6 ||hs % 10 == 7 || hs % 10 == 8 || hs % 10 == 9){
                                                    str = "часов";
                                                }
                                                if (hs % 10 == 2 || hs % 10 == 3 || hs % 10 == 4){
                                                    str = "часа";
                                                }
                                                if (hs % 10 == 1){
                                                    str = "час";
                                                }
                                                v2.setText(s.split("_")[1] + " " + str);
                                                grid.addView(cardView);
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                });
        return view;
    }
}