package com.example.cursofirebase.Activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cursofirebase.Activity.Classes.Usuario;
import com.example.cursofirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private DatabaseReference referenciaFirebase;
    private TextView tipoUsuario;
    private Usuario usuario;
    private String tipoUsuarioEmail;
    private Menu menu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tipoUsuario = (TextView) findViewById(R.id.idtxtTipoUsuario);
        autenticacao = FirebaseAuth.getInstance();

        referenciaFirebase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        menu.clear();

        this.menu1 = menu;

        //RECEBENDO O EMAIL DO USUARIO LOGADO
        String email = autenticacao.getCurrentUser().getEmail().toString();



        referenciaFirebase.child("usuarios").orderByChild("email").equalTo(email.toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapShot : dataSnapshot.getChildren()){//PERCORRENDO TODOS OS RESULTADOS DO dataSnapshot
                    tipoUsuarioEmail = postSnapShot.child("tipoUsuario").getValue().toString();

                    tipoUsuario.setText(tipoUsuarioEmail);

                    menu1.clear();

                    if(tipoUsuarioEmail.equals("Administrador")){
                        getMenuInflater().inflate(R.menu.menu_admin, menu1);
                    } else if (tipoUsuarioEmail.equals("Atendente")){
                        getMenuInflater().inflate(R.menu.menu_atend, menu1);
                    } else if (tipoUsuarioEmail.equals("Comum")){
                        getMenuInflater().inflate(R.menu.menu_comum, menu1);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if (id == R.id.action_add_usuario){
            abrirTelaCadastroUsuario();
        } else if (id == R.id.action_sair_admin){
            deslogarUsuario();
        }else if (id == R.id.action_sair_atend){
            deslogarUsuario();
        }else if (id == R.id.action_sair_comum){
            deslogarUsuario();
        }else if (id == R.id.action_cardarpio_comun){
            verCardapio();
        }else if (id == R.id.action_cardarpio){
            verCardapio();
        }


        return super.onOptionsItemSelected(item);
    }

    private void verCardapio() {
        Intent intent = new Intent(PrincipalActivity.this, CardapioActivity.class);
        startActivity(intent);
    }

    private void abrirTelaCadastroUsuario(){
        Intent intent = new Intent(PrincipalActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void deslogarUsuario(){
        autenticacao.signOut();

        Intent intent = new Intent(PrincipalActivity.this, MainActivity.class);
        startActivity(intent);
        finish();

    }


}
