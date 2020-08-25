package com.example.cursofirebase.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.cursofirebase.R;

public class NovoItemCardapioActivity extends AppCompatActivity {

    private BootstrapEditText nome, descricao, serve, preco;
    private ImageView fotoPrato;
    Button btnCadastrar, btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_item_cardapio);

        nome = (BootstrapEditText) findViewById(R.id.idNomePrato);
        descricao = (BootstrapEditText) findViewById(R.id.idDescricao);
        serve = (BootstrapEditText) findViewById(R.id.idserve);
        preco = (BootstrapEditText) findViewById(R.id.idpreco);
        fotoPrato = (ImageView) findViewById(R.id.idAdicionarImagem);

        btnCadastrar = (Button) findViewById(R.id.idbtnCadastrar);
        btnCancelar = (Button) findViewById(R.id.idbtnCancelar);



    }
}