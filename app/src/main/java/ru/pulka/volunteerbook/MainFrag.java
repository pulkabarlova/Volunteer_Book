package ru.pulka.volunteerbook;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class MainFrag extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_frag, container, false);
        LinearLayout grid = view.findViewById(R.id.androidfrag2);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("events")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences mySharedPreferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                            LinearLayout grid = view.findViewById(R.id.androidfrag2);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("event_id").toString().equals(mySharedPreferences.getString("id_sc_v", ""))
                                        && document.get("is_going").equals("True")) {
                                    LayoutInflater inflater = LayoutInflater.from(getContext());
                                    View cardView = inflater.inflate(R.layout.card1, null, false);
                                    TextView v1 = cardView.findViewById(R.id.t1);
                                    v1.setText(document.get("name").toString());
                                    TextView v2 = cardView.findViewById(R.id.t2);
                                    v2.setText(document.get("description").toString());
                                    grid.addView(cardView);
                                    cardView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Dialog dialog = new Dialog(getContext());
                                            dialog.setContentView(R.layout.previewforeventsorg);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            Button yes_end = dialog.findViewById(R.id.button_yes_delete);
                                            dialog.show();
                                            yes_end.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    //cardView.setVisibility(View.GONE);
                                                    cardView.setBackgroundColor(808080);
                                                    HashMap map = new HashMap();
                                                    map.put("is_going", "false");
                                                    SharedPreferences mySharedPreferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = mySharedPreferences.edit();
                                                    editor.putString("event_desc", document.get("name").toString() + document.get("description").toString());
                                                    editor.putString("event_name", document.get("name").toString());
                                                    editor.putString("volunt", document.get("volunteers").toString());
                                                    editor.apply();
                                                    db.collection("events").document(document.getId()).update(map);
                                                    Intent intent = new Intent(getActivity(), Enrollment.class);
                                                    startActivity(intent);
                                                }

                                            });

                                        }
                                    });
                                }
                            }
                        }
                    }
                });
        return view;
    }
}
