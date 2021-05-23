package com.example.hbooking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hbooking.classes.User;
import com.example.hbooking.fragment.Fragment_Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class edit_profil extends AppCompatActivity {
    TextView txtUser,txtPwd_An,txtPwd_Nv,txtEmail,txtTele;
    String  VilleSelectionne;

DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil);


       txtUser=findViewById(R.id.Username);
         txtPwd_An=findViewById(R.id.pwd_anc);
      txtPwd_Nv=findViewById(R.id.pwdNv);
        txtEmail=findViewById(R.id.Email);
         txtTele=findViewById(R.id.telephne);
        Spinner spiner=findViewById(R.id.spinner);
        txtUser.setText(User.usr.getUsername());
        txtEmail.setText(User.usr.getEmail());
        txtTele.setText(User.usr.getPhone());
        spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 VilleSelectionne = adapterView.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });









spiner.setSelection(getIndex(spiner,User.usr.getVille()));
        txtTele.setText(User.usr.getPhone());

        //reference=FirebaseDatabase.getInstance().getReference().child("users").child(User.key);
        //reference.setValue(User.usr);
    }

    public int getIndex(Spinner spiner , String s){
        for (int i=0; i<spiner.getCount();i++){
            if(spiner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }

return 0;
    }

    public void modifier(View view) {
        String EmailC = txtEmail.getText().toString();
        String pwdAn = txtPwd_An.getText().toString();
        String pwdNv=txtPwd_Nv.getText().toString();
        String phoneC = String.valueOf(txtTele.getText());
        String usernameC =txtUser.getText().toString();
        if(EmailC.isEmpty() || pwdAn.isEmpty() || pwdNv.isEmpty() || phoneC.isEmpty() || usernameC.isEmpty() ){
            Toast.makeText(edit_profil.this, " Remplir les champs ", Toast.LENGTH_SHORT).show();

        }
        else{
            if(pwdAn.equals(User.usr.getPwd())){
                User.usr.setPhone(phoneC);
                User.usr.setEmail(EmailC);
                User.usr.setVille(VilleSelectionne);
                User.usr.setPwd(pwdNv);
                User.usr.setUsername(usernameC);
                reference=FirebaseDatabase.getInstance().getReference().child("users").child(User.key);
                reference.setValue(User.usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(edit_profil.this, " User updated ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(edit_profil.this, Principale.class);

                            startActivity(intent);
                        }else{
                            Toast.makeText(edit_profil.this, "dataUserError" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });



            }else{
                Toast.makeText(edit_profil.this, " Erreur password", Toast.LENGTH_SHORT).show();
            }



        }

    }

}
