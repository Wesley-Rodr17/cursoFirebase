package com.example.cursofirebase.Activity.Classes;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

public class Usuario implements Parcelable {

    private String email;
    private String senha;
    private String nome;
    private String tipoUsuario;
    private String cpf;
    private String rua;
    private String numero;
    private String sexo;
    private String keyUsuario;

    public Usuario() {
    }

    protected Usuario(Parcel in) {
        email = in.readString();
        senha = in.readString();
        nome = in.readString();
        tipoUsuario = in.readString();
        cpf = in.readString();
        rua = in.readString();
        numero = in.readString();
        sexo = in.readString();
        keyUsuario = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(senha);
        dest.writeString(nome);
        dest.writeString(tipoUsuario);
        dest.writeString(cpf);
        dest.writeString(rua);
        dest.writeString(numero);
        dest.writeString(sexo);
        dest.writeString(keyUsuario);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    @Exclude
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getKeyUsuario() {
        return keyUsuario;
    }

    public void setKeyUsuario(String keyUsuario) {
        this.keyUsuario = keyUsuario;
    }
}
