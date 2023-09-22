package ru.pulka.volunteerbook;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFrag extends Fragment {
    Dialog dialog;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home_frag.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFrag newInstance(String param1, String param2) {
        HomeFrag fragment = new HomeFrag();
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
        View view = inflater.inflate(R.layout.fragment_home_frag, container, false);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("volunteers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences mySharedPreferences = getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
                            LinearLayout grid = view.findViewById(R.id.androidfrag2);
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document.get("id_school").toString().equals(mySharedPreferences.getString("id_sc_v", ""))){
                                    LayoutInflater inflater = LayoutInflater.from(getContext());
                                    View cardView = inflater.inflate(R.layout.card2, null, false);
                                    TextView v1 = cardView.findViewById(R.id.t2);
                                    v1.setText(document.get("name").toString() + " " + document.get("surname").toString());
                                    grid.addView(cardView);
                                    cardView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog = new Dialog(getContext());
                                            dialog.setContentView(R.layout.previewdialogue);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                            TextView text_fio = (TextView) dialog.findViewById(R.id.textView4);
                                            text_fio.setText(text_fio.getText().toString() + document.get("name").toString() + " "
                                                    + document.get("surname") + " " + document.get("fathername").toString());
                                            TextView text_form = (TextView) dialog.findViewById(R.id.textView5);
                                            text_form.setText(text_form.getText().toString() + document.get("form").toString());
                                            TextView text_hours = (TextView) dialog.findViewById(R.id.textView6);
                                            text_hours.setText(text_hours.getText().toString() + document.get("hours").toString());
                                            dialog.show();
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