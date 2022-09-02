package com.example.calcioconlaf.Login;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.calcioconlaf.Login.EditActivity;
import com.example.calcioconlaf.Login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditPasswordThread extends Thread{
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference usersRef = ref.child("Users");
    String username;
    String newPassword;
    EditActivity editActivity;


    public EditPasswordThread(String username, String newPassword, EditActivity editActivity) {
        this.username = username;
        this.newPassword=newPassword;
        this.editActivity=editActivity;

    }

    @Override
    public void run() {
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {
                    if(username.equals(ds.child("Username").getValue().toString())){
                        usersRef.child(ds.getKey()).child("Password").setValue(newPassword);
                        editActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent=new Intent(editActivity, LoginActivity.class);
                                editActivity.startActivity(intent);
                                Toast.makeText(editActivity, "Password modificata correttamente",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
