package com.example.cursofirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.cursofirebase.Activity.DAO.ConfiguracaoFirebase;
import com.example.cursofirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarSenha extends AppCompatActivity {
    EditText emailnovo;
    Button btnrecuperar;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

            emailnovo=findViewById(R.id.idEmailRecuperar);
            btnrecuperar=findViewById(R.id.btnrecuperarsenha);
            btnrecuperar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String email = emailnovo.getText().toString().trim();
                    resetsenha(email);

                }
            });
        }

        private void resetsenha(String email) {

            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(RecuperarSenha.this, new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()){
                        alert("Um e-mail foi emcaminhado para alterar sua senha");
                        finish();
                    }else
                        alert("E-mail não foi encontrado");
                }
            });
        }

        private void alert(String s) {

            Toast.makeText(RecuperarSenha.this,s, Toast.LENGTH_SHORT).show();
        }

        protected void onStart() {
            super.onStart();
            firebaseAuth = ConfiguracaoFirebase.getAutenticacao();
        }
}