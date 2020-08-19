package com.example.cursofirebase.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursofirebase.Activity.Adapter.CardapioAdapter;
import com.example.cursofirebase.Activity.Classes.Cardapio;
import com.example.cursofirebase.Activity.DAO.ConfiguracaoFirebase;
import com.example.cursofirebase.Activity.Helper.RecyclerItemClickListener;
import com.example.cursofirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CardapioActivity extends AppCompatActivity {

    private TextView tituloTxt;
    private Button btnCadastrarNovoColaborador;
    private RecyclerView recyclerViewListaCardapio;
    private ValueEventListener valueEventListener;
    private CardapioAdapter cardapioAdapter;
    private List<Cardapio> mCardapioList  = new ArrayList<Cardapio>();
    private DatabaseReference databaseCardapio;
    Cardapio cardapio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardapio);

        //configurações iniciais
        recyclerViewListaCardapio = (RecyclerView) findViewById(R.id.idRecycleViewTodosProdutos);

        //configurar Adapter
        cardapioAdapter = new CardapioAdapter( mCardapioList, getApplicationContext() );

        //Configurar RecycleView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewListaCardapio.setLayoutManager( layoutManager );
        recyclerViewListaCardapio.setHasFixedSize( true );
        recyclerViewListaCardapio.setAdapter(cardapioAdapter);

        databaseCardapio = ConfiguracaoFirebase.getFirebase()
                .child("cardapios");


        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mCardapioList.clear();
                for (DataSnapshot data: dataSnapshot.getChildren()){
                    Cardapio cardapio = data.getValue(Cardapio.class);
                    mCardapioList.add(cardapio);
                }
                cardapioAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        //Configurar evento de clicle no recycle view
        recyclerViewListaCardapio.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                        recyclerViewListaCardapio,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Intent intent = new Intent(getApplicationContext(), CardapioActivity.class);
                                intent.putExtra("cardapio", (Parcelable) mCardapioList.get(position));
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }) {
                }
        );

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseCardapio.addValueEventListener(valueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        databaseCardapio.removeEventListener(valueEventListener);
    }

}
