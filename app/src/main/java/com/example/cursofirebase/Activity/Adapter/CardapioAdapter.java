package com.example.cursofirebase.Activity.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cursofirebase.Activity.Classes.Cardapio;
import com.example.cursofirebase.R;
import com.squareup.picasso.Target;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;


import java.util.List;

public class CardapioAdapter extends RecyclerView.Adapter<CardapioAdapter.MyViewHolder> {

    private List<Cardapio> cardapio;
    private Context context;

    public CardapioAdapter(List<Cardapio>  listaCardapio, Context c) {
        this.cardapio = listaCardapio;
        this.context = c;
    }

    @androidx.annotation.NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_todos_produtos, parent, false);

        return new MyViewHolder(itemLista);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) { //cardapio são todos os objetos
        Cardapio itemCardapio = cardapio.get( position );             // itemcardapio é um item do objeto cardapio


        Glide.with(context).load(itemCardapio.getUrlImagem()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, com.bumptech.glide.request.target.Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, com.bumptech.glide.request.target.Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into( holder.imagem);



        holder.nome.setText( itemCardapio.getNomePrato());
        holder.descricao.setText("Descrição: " +  itemCardapio.getDescricao());
        holder.preco.setText("RS: " +  itemCardapio.getPreco());
        holder.serve.setText("Serve " +  itemCardapio.getServeQnt() + " pessoas");
    }

    @Override
    public int getItemCount() {
        return cardapio.size(); //retornar o tamanho dessa lista
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView  nome, descricao, preco, serve;
        ImageView imagem;

        public MyViewHolder( View itemView ){
            super(itemView);

            //Linkando os elemento de view
            imagem   = itemView.findViewById(R.id.fotoProdutoCardapio);
            nome   = itemView.findViewById(R.id.txtNomeProdutoCardapio);
            descricao    = itemView.findViewById(R.id.txtDescricaoProdutoCardapio);
            preco   = itemView.findViewById(R.id.txtPrecoProdutoCardapio);
            serve    = itemView.findViewById(R.id.txtServeQtdProdutoCardapio);
        }
    }
}
