package com.example.hbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbooking.classes.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Connecter extends AppCompatActivity {

    TextView CreeCompte,email,ps;
    Button Conncter;
     User user =new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecter);


            email=findViewById(R.id.Email);
              ps =findViewById(R.id.pwd_anc);

        // remove actionbar
        getSupportActionBar().hide();
        //btn action
        Conncter = findViewById(R.id.Confermer);
        Conncter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String e=email.getText().toString();
                String p=ps.getText().toString();
               if(TextUtils.isEmpty(e) || TextUtils.isEmpty(p) ){
                    Toast.makeText(Connecter.this, "All Fields Required", Toast.LENGTH_SHORT).show();

                }else login(e, p);
               /* FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            for(DataSnapshot s:snapshot.getChildren())
                            user =s.getValue(User.class);
                            if(user!=null)
                                Toast.makeText(Connecter.this,user.getEmail() +" hhhh",Toast.LENGTH_SHORT).show();

                        }




                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });*/




                //Intent intent = new Intent(Connecter.this , Principale.class);
                //startActivity(intent);
            }
        });



        /***************************/
        CreeCompte = findViewById(R.id.CreeCompte);
        CreeCompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Connecter.this , Register.class);
                startActivity(intent);
            }
        });
    }

    private void login(String e, String p) {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User us=null;
                if(snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {

                        user = s.getValue(User.class);
                        if (user != null)
                            if (user.getEmail().equals(e) && user.getPwd().equals(p)) {
                                us = user;
                                User.key=s.getKey();
                                break;

                                // Intent intent = new Intent(Connecter.this , Principale.class);

                                //startActivity(intent);
                            }
                    }
                    if (us != null) {
                        User.usr = us;
                        Toast.makeText(Connecter.this, " true", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Connecter.this, Principale.class);
                        Intent in = new Intent(Connecter.this, edit_profil.class);
                        intent.putExtra("user",us);
                        in.putExtra("use","kkkk");
                        startActivity(intent);
                    } else {
                        Toast.makeText(Connecter.this, " email ou password incorrecte", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Connecter.this, " ERReur", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}