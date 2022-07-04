package com.example.gasolineprices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ActivitySignup extends AppCompatActivity {



        private FirebaseAuth mAuth;

        private String mail;
        private String password;

        private Button singUp;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            mAuth = FirebaseAuth.getInstance();

            singUp = findViewById(R.id.button2);

            singUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mail = ((EditText) findViewById(R.id.EmailAddress)).getText().toString();
                    password = ((EditText) findViewById(R.id.Password)).getText().toString();



                    if (
                          //  !getCode().contains(mail.split("@", 2)[0]) ||



                                    !(mail.equals("") && !password.equals(""))) {

                            addFirebase();


                    } else {
                        Toast.makeText(getApplicationContext(), "mail or password can't be empty or mail is already connected to an account", Toast.LENGTH_SHORT).show();
                    }

                }
            });



        }
    private List<String> getCode() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        // Initialize firebase user
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        DatabaseReference ref = database.getReference();
        // calling add value event listener method
        // for getting the values from database.

        List<String> newList = null;
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.


                for (DataSnapshot postSnapshot: snapshot.getChildren()) {

                    newList.add(postSnapshot.getKey());




                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return newList;
    }



        public void addFirebase() {
            mAuth.createUserWithEmailAndPassword(mail, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information

                                Intent intent = new Intent(ActivitySignup.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e("info", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(ActivitySignup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }