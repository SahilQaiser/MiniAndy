package com.sq.myfb2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
private EditText username,password;
private Button login,register;
private FirebaseFirestore db;
boolean end = false;
Map<String,Object> users;
Map<String,String> registeredUsers,newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        users = new HashMap<>();
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        registeredUsers = new HashMap<>();
        newUser = new HashMap<>();
        getDataFromFirebase();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromFirebase();
                String uname = username.getText().toString();
                String pw = password.getText().toString();

                if(registeredUsers.containsKey(uname)){
                    if(registeredUsers.get(uname).equals(pw))
                    {
                        //Toast.makeText(MainActivity.this,uname+" Logged In",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this,Dashboard.class);
                        i.putExtra("USERNAME",username.getText().toString());
                        end = true;
                        startActivity(i);
                    } else
                        Toast.makeText(MainActivity.this,"Wrong Password",Toast.LENGTH_LONG).show();
                }
                else Toast.makeText(MainActivity.this,"Invalid Credentials, Kindly Register",Toast.LENGTH_LONG).show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromFirebase();
                String uname = username.getText().toString();
                if(uname.equals("")){
                    username.setHint("Enter Credentials");
                    username.setFocusable(true);
                } else {
                    String pw = password.getText().toString();
                    if(!registeredUsers.containsKey(uname))
                    {
                        newUser.put("username",uname);
                        newUser.put("password",pw);
                        db.collection("users").add(newUser);
                        end = true;
                        Intent i = new Intent(MainActivity.this, Dashboard.class);
                        i.putExtra("USERNAME", uname);
                        startActivity(i);
                    }
                    else {
                        username.setText("");
                        Toast.makeText(MainActivity.this,"Username Already Exists",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
    public void getDataFromFirebase()
    {
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                users = document.getData();
                                String uname,pw;
                                {
                                    uname = users.get("username").toString();
                                    pw = users.get("password").toString();
                                    registeredUsers.put(uname,pw);
                                    Log.d("UsersFromFirestore","Username "+uname+", Password "+pw);
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromFirebase();
        if(end)
            finish();
    }

}
