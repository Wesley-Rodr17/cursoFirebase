package com.example.cursofirebase.Activity.Classes;

import android.os.Parcel;
import android.os.Parcelable;

public class Cardapio implements Parcelable {
    private String keyProduto;
    private String nomePrato;
    private String descricao;
    private String serveQnt;
    private String urlImagem;
    private String preco;

    public Cardapio() {
    }

    protected Cardapio(Parcel in) {
        keyProduto = in.readString();
        nomePrato = in.readString();
        descricao = in.readString();
        serveQnt = in.readString();
        urlImagem = in.readString();
        preco = in.readString();
    }

    public static final Creator<Cardapio> CREATOR = new Creator<Cardapio>() {
        @Override
        public Cardapio createFromParcel(Parcel in) {
            return new Cardapio(in);
        }

        @Override
        public Cardapio[] newArray(int size) {
            return new Cardapio[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keyProduto);
        dest.writeString(nomePrato);
        dest.writeString(descricao);
        dest.writeString(serveQnt);
        dest.writeString(urlImagem);
        dest.writeString(preco);
    }

    public String getKeyProduto() {
        return keyProduto;
    }

    public void setKeyProduto(String keyProduto) {
        this.keyProduto = keyProduto;
    }

    public String getNomePrato() {
        return nomePrato;
    }

    public void setNomePrato(String nomePrato) {
        this.nomePrato = nomePrato;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getServeQnt() {
        return serveQnt;
    }

    public void setServeQnt(String serveQnt) {
        this.serveQnt = serveQnt;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
