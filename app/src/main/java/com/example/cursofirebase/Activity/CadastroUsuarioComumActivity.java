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

public class CadastroUsuarioComumActivity extends AppCompatActivity {


    private EditText nome, email, senha, confSenha, cpf, rua, numero;
    private RadioButton sexoFeminino, sexoMasculino;
    private Button btnCadastrar, btnCancelar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario_comum);

        nome =  findViewById(R.id.idNome);
        cpf =  findViewById(R.id.idCPF);
        rua =  findViewById(R.id.idRua);
        numero =  findViewById(R.id.idNumero);
        email =  findViewById(R.id.idEmail);
        senha =  findViewById(R.id.idSenha);
        confSenha =  findViewById(R.id.idComSenha);
        sexoFeminino = (RadioButton) findViewById(R.id.idrbFem);
        sexoMasculino = (RadioButton) findViewById(R.id.idrbMas);
        btnCadastrar =  findViewById(R.id.idbtnCadastrarUsuarioC);
        btnCancelar =  findViewById(R.id.idbtnCancelarUsuarioC);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (senha.getText().toString().equals(confSenha.getText().toString())){ //Vallidando Senha iguais
                    usuario = new Usuario();
                    usuario.setNome(nome.getText().toString());
                    usuario.setCpf(cpf.getText().toString());
                    usuario.setRua(rua.getText().toString());
                    usuario.setNumero(numero.getText().toString());
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    usuario.setTipoUsuario("Comum");

                    if (sexoFeminino.isChecked()){
                        usuario.setSexo("Feminino");
                    } else if (sexoMasculino.isChecked()){
                        usuario.setSexo("Masculino");
                    } else {
                        Toast.makeText(CadastroUsuarioComumActivity.this, "Marque um dos sexos", Toast.LENGTH_SHORT).show();
                    }
                    CadastrarUsuario();

                }else {
                    Toast.makeText(CadastroUsuarioComumActivity.this, "As senhas são diferentes!", Toast.LENGTH_SHORT).show();
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
        ).addOnCompleteListener(CadastroUsuarioComumActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    insereUsuario(usuario);

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
                    Toast.makeText(CadastroUsuarioComumActivity.this, "Erro: " + erroExcecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean insereUsuario(Usuario usuario){
        try {
            DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("usuarios");
            String key = referenciaFirebase.push().getKey();// pegando a chave que gera um novo usuario
            usuario.setKeyUsuario(key);
            referenciaFirebase.child(key).setValue(usuario);
            Toast.makeText(CadastroUsuarioComumActivity.this, "Usuário Cadastrado com Sucesso!!!", Toast.LENGTH_SHORT).show();
            abrirLoginUsuario();
            return true;
        } catch (Exception e){
            Toast.makeText(CadastroUsuarioComumActivity.this, "Erro ao gravar o usuário!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }

    }

    private void abrirLoginUsuario(){
          Intent intent = new Intent(CadastroUsuarioComumActivity.this, MainActivity.class);
          startActivity(intent);
          finish();
    }

}
