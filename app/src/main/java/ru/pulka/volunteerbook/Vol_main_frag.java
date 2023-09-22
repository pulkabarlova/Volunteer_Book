package ru.pulka.volunteerbook;

import android.app.Dialog;
import android.content.Context;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Vol_main_frag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Vol_main_frag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Vol_main_frag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Vol_main_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static Vol_main_frag newInstance(String param1, String param2) {
        Vol_main_frag fragment = new Vol_main_frag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_vol_main_frag, container, false);

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
                                && document.get("is_going").toString().equals("True")){
                                    LayoutInflater inflater = LayoutInflater.from(getContext());
                                    View cardView = inflater.inflate(R.layout.card3, null, false);
                                    TextView v1 = cardView.findViewById(R.id.t1);
                                    v1.setText(document.get("name").toString());
                                    TextView v2 = cardView.findViewById(R.id.t2);
                                    v2.setText(document.get("description").toString());
                                    grid.addView(cardView);
                                    cardView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Dialog dialog = new Dialog(getContext());
                                            dialog.setContentView(R.layout.previewforagreement);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            Button yes_but = dialog.findViewById(R.id.button_yes_takepart);
                                            dialog.show();
                                            yes_but.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    HashMap map = new HashMap();
                                                    map.put("volunteers", document.get("volunteers") + "#" + mySharedPreferences.getString("address", ""));
                                                    db.collection("events").document(document.getId()).update(map);
                                                    dialog.hide();
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