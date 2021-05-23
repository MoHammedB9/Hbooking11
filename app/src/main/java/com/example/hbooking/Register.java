package com.example.hbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.hbooking.classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    //variable
    EditText Email,pwd,phone,username;
    Button btnValider;
    ProgressDialog progressDialog;
    Spinner spinner;
    String VilleSelectionne;

    //database
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // remove actionbar
        getSupportActionBar().hide();
        // get all xml elemnt
        fauth =FirebaseAuth.getInstance();
        Email = findViewById(R.id.Email);   pwd = findViewById(R.id.pwd_anc);   phone = findViewById(R.id.telephne);    username = findViewById(R.id.Username);
        btnValider = findViewById(R.id.Valider);
        spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                VilleSelectionne = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Register Compte");
        progressDialog.setMessage("Registartion en cours ...");




        //test if userCurrent  is  arrendy login ***********************************************
        /*
        if(fauth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),Principale.class));
            finish();

        }*/
        //***************************************************************************

        //***************************action Listener*********************************************
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootNode = FirebaseDatabase.getInstance(); //get root of my database
                reference = rootNode.getReference("users"); // get refrence ---> documment

                //get values
                String EmailC = Email.getText().toString();
                String pwdC = pwd.getText().toString();
                String phoneC = String.valueOf(phone.getText());
                String usernameC =username.getText().toString();



                if(EmailC.isEmpty() || pwdC.isEmpty() || phoneC.isEmpty() || usernameC.isEmpty() ){
                    if (!Patterns.EMAIL_ADDRESS.matcher(EmailC).matches()) {Email.setError("Email non valide");}
                    if (pwdC.isEmpty()) {pwd.setError("Entre Motdepasse");}
                    if (phoneC.isEmpty()) {phone.setError("Entre numero");}
                    if (usernameC.isEmpty()) {username.setError("Entre Username");}
                }
                else{

                    //authentification
                    progressDialog.show();
                    fauth.createUserWithEmailAndPassword(EmailC,pwdC).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    //if create in authentification than created in database
                                    User user = new User(EmailC,pwdC,phoneC,usernameC,VilleSelectionne);
                                    reference.child(fauth.getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                progressDialog.dismiss();
                                                startActivity(new Intent(getApplicationContext(),Principale.class));
                                                finish();
                                            }else{
                                                Toast.makeText(Register.this, "dataUserError" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    Toast.makeText(Register.this, "User created", Toast.LENGTH_SHORT).show();

                                }else{
                                    progressDialog.dismiss();
                                    Toast.makeText(Register.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        }
                    });







                  //

                   // startActivity(new Intent(getApplicationContext(),MainActivity.class));

                }

            }
        });

        //***************************end action Listener*********************************************
    }
}