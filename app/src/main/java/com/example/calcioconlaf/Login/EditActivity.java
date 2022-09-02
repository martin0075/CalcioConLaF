package com.example.calcioconlaf.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calcioconlaf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    private EditText inputEditTextField;
    private EditText inputEditTextFieldPw;
    String username;
    String oldPw;
    String newUsername;
    public String newPassword;
    String user;
    private ArrayList lista=new ArrayList();
    private EditUsernameThread editUsername;
    private EditPasswordThread editPassword;
    EditActivity editActivity=EditActivity.this;
    AlertDialog dialog;
    EditText newUsernameEdit;
    Button btnEditPassword;
    EditText newPasswordEdit;
    public FirebaseDatabase database=FirebaseDatabase.getInstance("https://calcioconlaf-37122-default-rtdb.europe-west1.firebasedatabase.app/");
    DatabaseReference ref = database.getReference();
    DatabaseReference usersRef = ref.child("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        TextView title=findViewById(R.id.txtTileUser);
        inputEditTextField= new EditText(this);
        inputEditTextField.setHint("Username");
        inputEditTextFieldPw= new EditText(this);
        inputEditTextFieldPw.setHint("Password");
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.addView(inputEditTextField);
        lay.addView(inputEditTextFieldPw);
        dialog = new AlertDialog.Builder(this)
                .setTitle("Inserisci username e password")
                .setView(lay)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        username = inputEditTextField.getText().toString();
                        oldPw=inputEditTextFieldPw.getText().toString();
                        if(username.equals("") || oldPw.equals("")){
                            //title.setText(newUsername);
                            Toast.makeText(editActivity, "Dati non corretti",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(EditActivity.this,EditActivity.class);
                            startActivity(intent);
                        }else{
                            String dati=username+" "+oldPw;
                            usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    lista.clear();
                                    for(DataSnapshot ds: snapshot.getChildren()) {
                                        lista.add(ds.child("Username").getValue()+" "+ds.child("Password").getValue());
                                    }
                                    if(lista.contains(dati)){
                                        title.setText(username);
                                        dialog.dismiss();
                                        Toast.makeText(editActivity,"Dati corretti",Toast.LENGTH_SHORT).show();
                                    }else{
                                        Intent intent=new Intent(EditActivity.this,EditActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(editActivity,"Dati mancanti o non corretti",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(EditActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .create();
        dialog.show();

        newUsernameEdit=findViewById(R.id.txtEditUsername);
        Button btnEditUsername=findViewById(R.id.btnEditUsername);
        btnEditUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newUsername=newUsernameEdit.getText().toString();
                String user=title.getText().toString();
                //title.setText(newUsername);
                editUsername=new EditUsernameThread(user, newUsername, editActivity, title);
                editUsername.start();

            }
        });
        newPasswordEdit=findViewById(R.id.txtEditPassword);
        btnEditPassword=findViewById(R.id.btnEditPassword);
        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassword=newPasswordEdit.getText().toString();
                String user=title.getText().toString();
                editPassword=new EditPasswordThread(user, newPassword, editActivity);
                editPassword.start();

                }
            });
    }
}