package com.example.oporto_olympics.Models;

import java.util.Date;

public class User {

    private  int Id;
    private int Num_Mecanografico;
    private String Password;
    private String UserType;
    private Date Criado_Em;


    /**
     * Construtor da classe Utilizador.
     *
     * @param Id Identificador único do utilizador.
     * @param num_mecanografico Número mecanográfico do utilizador.
     * @param password Senha do utilizador.
     * @param usertype Nível de permissão associado ao utilizador.
    */
    public User (int Id, int num_mecanografico, String password, String usertype, Date criado_em) {
        this.Id = Id;
        this.Num_Mecanografico = num_mecanografico;
        this.Password = password;
        this.UserType = usertype;
        this.Criado_Em = criado_em;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getNum_Mecanografico() {
        return Num_Mecanografico;
    }

    public void setNum_Mecanografico(int num_Mecanografico) {
        Num_Mecanografico = num_Mecanografico;
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getUserType() { return UserType; }

    public void setUserType(String usertype) { this.UserType = usertype; }

    public Date getCriado_Em() {
        return Criado_Em;
    }

    public void setCriado_Em(Date criado_Em) {
        Criado_Em = criado_Em;
    }
}
