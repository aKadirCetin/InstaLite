package com.kadircetin.instalite.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.kadircetin.instalite.R;
import com.kadircetin.instalite.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth=FirebaseAuth.getInstance();
        FirebaseUser user =auth.getCurrentUser();
        if(user != null){
            startActivity(new Intent(MainActivity.this,FeedActivity.class));
            finish();
        }

        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance());

        settings();
    }
    public void signInClicked(View view){
        String email = binding.emailText.getText().toString();
        String password= binding.passwordText.getText().toString();

        if(email.isEmpty() ||password.isEmpty()){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show();
        }else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e->{
                Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            });
        }
    }
    public void signUpClicked(View view) {
        String email = binding.emailText.getText().toString();
        String password= binding.passwordText.getText().toString();

        if(email.isEmpty() ||password.isEmpty()){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show();
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {
                Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                startActivity(intent);
                finish();
            }).addOnFailureListener(e->{
                Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            });
        }
    }
    public void settings(){
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}