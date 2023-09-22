package ru.pulka.volunteerbook;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DBHelper {


    private FirebaseFirestore db;
    private boolean isLoading = false;

    private static DBHelper instance;

    private DBHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public static DBHelper getInstance(){
        if(instance == null){
            instance = new DBHelper();
        }
        return instance;
    }

    public void addUser(String [] name,String data, String address,String form, String password, String schol_id, String activities_worked, String hours_worked) {

        if (isLoading)
            return;

        isLoading = true;

        Map<String, String> volunteers = new HashMap<>();
        volunteers.put("name", name[0]);
        volunteers.put("surname", name[1]);
        volunteers.put("fathername", name[2]);
        volunteers.put("birthdate", data);
        volunteers.put("address", address);
        volunteers.put("form", form);
        volunteers.put("hours", hours_worked);
        volunteers.put("activities", activities_worked);
        volunteers.put("password", password);
        volunteers.put("id_school", schol_id);

        // Add a new document with a generated ID
        db.collection("volunteers")
                .add(volunteers)

                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                });
    }
    public void addEvent(String name, String description, String schol_id, String hours, String volunteer, String is_going) {

        if (isLoading)
            return;

        isLoading = true;

        Map<String, String> events = new HashMap<>();
        events.put("name", name);
        events.put("description", description);
        events.put("event_id", schol_id);
        events.put("hours", hours);
        events.put("volunteers", volunteer);
        events.put("is_going", is_going);

        // Add a new document with a generated ID
        db.collection("events")
                .add(events)

                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {

                    }
                });
    }
}

