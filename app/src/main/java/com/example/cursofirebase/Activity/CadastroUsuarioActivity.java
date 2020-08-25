package com.example.cursofirebase.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cursofirebase.Activity.Classes.Usuario;
import com.example.cursofirebase.Activity.DAO.ConfiguracaoFirebase;
import com.example.cursofirebase.Activity.Helper.Preferencias;
import com.example.cursofirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private EditText nome, email, senha, confSenha;
    private RadioButton permissaoAdm, permissaoAtend;
    private Button btnCadastrar, btnCancelar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome =  findViewById(R.id.ideditNome);
        email =  findViewById(R.id.ideditEmail);
        senha =  findViewById(R.id.ideditSenha);
        confSenha =  findViewById(R.id.ideditComSenha);
        permissaoAdm = (RadioButton) findViewById(R.id.idrbAdmin);
        permissaoAtend = (RadioButton) findViewById(R.id.idrbAtend);
        btnCadastrar = (Button) findViewById(R.id.idbtnCadastrar);
        btnCancelar = (Button) findViewById(R.id.idbtnCancelar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (senha.getText().toString().equals(confSenha.getText().toString())){ //Vallidando Senha iguais
                    usuario = new Usuario();
                    usuario.setNome(nome.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());


                    if (permissaoAdm.isChecked()){
                        usuario.setTipoUsuario("Administrador");
                    } else if (permissaoAtend.isChecked()){
                        usuario.setTipoUsuario("Atendente");
                    } else {
                        Toast.makeText(CadastroUsuarioActivity.this, "Marque uma das permissãoes", Toast.LENGTH_SHORT).show();
                    }
                    CadastrarUsuario();

                }else {
                    Toast.makeText(CadastroUsuarioActivity.this, "As senhas são diferentes!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void CadastrarUsuario(){

        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
            usuario.getEmail(),
            usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    insereUsuario(usuario);
                    finish();

                    //DESLOGAR AO ADICIONAR USUARIO
                    autenticacao.signOut();

                    //ABRE A TELA PRINCIPAL APÓS A RE-AUTENTICAÇÃAO
                    abreTelaPrincipal();

                } else {
                    String erroExcecao = "";

                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e){
                        erroExcecao = "Digite uma senha mais forte, contendo 8 caracteres e que contenha letras e números ";
                    } catch (FirebaseAuthInvalidCredentialsException e){
                        erroExcecao = "O e-mail digitado é invalido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e){
                        erroExcecao = "Esse e-mail já está cadastrado!";
                    } catch (Exception e) {
                        erroExcecao = "Erro ao efetuar cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean insereUsuario(Usuario usuario){
        try {
            DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios");
            referenciaFirebase.push().setValue(usuario);
            Toast.makeText(CadastroUsuarioActivity.this, "Usuário Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();

            return true;
        } catch (Exception e){
            Toast.makeText(CadastroUsuarioActivity.this, "Erro ao gravar o usuário!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }

    }

    private void abreTelaPrincipal(){
        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
        autenticacao.signInWithEmailAndPassword(preferencias.getEMAIL_USUARIO_LOGADO(), preferencias.getSENHA_USUARIO_LOGADO()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(CadastroUsuarioActivity.this, PrincipalActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(CadastroUsuarioActivity.this, "Falha!", Toast.LENGTH_SHORT).show();
                    autenticacao.signOut();
                    Intent intent = new Intent(CadastroUsuarioActivity.this, PrincipalActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }
}
