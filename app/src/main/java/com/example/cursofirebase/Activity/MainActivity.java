package com.example.cursofirebase.Activity;

import android.Manifest;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.cursofirebase.Activity.Classes.Usuario;
import com.example.cursofirebase.Activity.DAO.ConfiguracaoFirebase;
import com.example.cursofirebase.Activity.Helper.Preferencias;
import com.example.cursofirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private BootstrapEditText email, senha;
    private TextView abreCadastro;
    private BootstrapButton btnLogar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (BootstrapEditText) findViewById(R.id.ideditEmail);
        abreCadastro = (TextView) findViewById(R.id.txtAbreCadastro);
        senha = (BootstrapEditText) findViewById(R.id.ideditSenha);
        btnLogar = (BootstrapButton) findViewById(R.id.idbtnLogin);

        permission();

        if ( usuarioLogado() ){
            Intent intentMinhaconta = new Intent(MainActivity.this, PrincipalActivity.class);
            abrirNovaActivity(intentMinhaconta);
        } else{
            btnLogar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!email.getText().toString().equals("") && !senha.getText().toString().equals("")) {
                        usuario.setEmail(email.getText().toString());
                        usuario.setSenha(senha.getText().toString());

                        validarLogin();
                    }else{
                        Toast.makeText(getApplicationContext(), "Preencha os campos de E-mail e senha!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        abreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CadastroUsuarioComumActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuario.getEmail().toString(), usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful() ){

                    abrirTelaPrincipal();
                    Preferencias preferencias = new Preferencias(MainActivity.this);
                    preferencias.salvarUsuarioPreferencias(usuario.getEmail(), usuario.getSenha());
                    Toast.makeText(getApplicationContext(), "login efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário ou senha invalidos! Tente novamente!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(MainActivity.this, PrincipalActivity.class);
        finish();
        startActivity(intent);
    }

    public Boolean usuarioLogado(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
         if (user != null){
             return true;
         } else {
             return false;
         }
    }

    public void abrirNovaActivity(Intent intent){
        startActivity(intent);
    }

    public void permission(){
        int PERMISSION_ALL = 1;
        String [] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this, PERMISSION, PERMISSION_ALL);
    }

}
