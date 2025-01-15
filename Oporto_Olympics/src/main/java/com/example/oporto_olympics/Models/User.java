package com.example.oporto_olympics.Models;

import java.util.Date;
/**
 * A classe {@link User} representa um utilizador do sistema, contendo informações como o identificador,
 * o número mecanográfico, a senha, o tipo de utilizador e a data de criação.
 */
public class User {
    /**
     * Identificador único do utilizador.
     */
    private  int Id;
    /**
     * Número mecanográfico do utilizador.
     */
    private int Num_Mecanografico;
    /**
     * Palavra-passe associada à conta do utilizador.
     */
    private String Password;
    /**
     * Tipo de utilizador (por exemplo, administrador, atleta, gestor).
     */
    private String UserType;
    /**
     * Data de criação da conta do utilizador.
     */
    private Date Criado_Em;


    /**
     * Construtor da classe {@link User}.
     *
     * @param Id               Identificador único do utilizador.
     * @param num_mecanografico Número mecanográfico do utilizador.
     * @param password         Senha do utilizador.
     * @param usertype         Nível de permissão associado ao utilizador (ex: "Admin", "Regular").
     * @param criado_em        Data em que o utilizador foi criado.
     */
    public User (int Id, int num_mecanografico, String password, String usertype, Date criado_em) {
        this.Id = Id;
        this.Num_Mecanografico = num_mecanografico;
        this.Password = password;
        this.UserType = usertype;
        this.Criado_Em = criado_em;
    }
    /**
     * Obtém o identificador único do utilizador.
     *
     * @return O identificador do utilizador.
     */
    public int getId() {
        return Id;
    }
    /**
     * Define o identificador único do utilizador.
     *
     * @param id O identificador do utilizador.
     */
    public void setId(int id) {
        this.Id = id;
    }
    /**
     * Obtém o número mecanográfico do utilizador.
     *
     * @return O número mecanográfico do utilizador.
     */
    public int getNum_Mecanografico() {
        return Num_Mecanografico;
    }
    /**
     * Define o número mecanográfico do utilizador.
     *
     * @param num_Mecanografico O número mecanográfico do utilizador.
     */
    public void setNum_Mecanografico(int num_Mecanografico) {
        Num_Mecanografico = num_Mecanografico;
    }
    /**
     * Obtém a senha do utilizador.
     *
     * @return A senha do utilizador.
     */
    public String getPassword() {
        return Password;
    }
    /**
     * Define a senha do utilizador.
     *
     * @param password A senha do utilizador.
     */
    public void setPassword(String password) {
        this.Password = password;
    }
    /**
     * Obtém o tipo de utilizador (ex: "Admin", "Regular").
     *
     * @return O tipo de utilizador.
     */
    public String getUserType() { return UserType; }
    /**
     * Define o tipo de utilizador (ex: "Admin", "Regular").
     *
     * @param usertype O tipo de utilizador.
     */
    public void setUserType(String usertype) { this.UserType = usertype; }
    /**
     * Obtém a data de criação do utilizador.
     *
     * @return A data em que o utilizador foi criado.
     */
    public Date getCriado_Em() {
        return Criado_Em;
    }
    /**
     * Define a data de criação do utilizador.
     *
     * @param criado_Em A data em que o utilizador foi criado.
     */
    public void setCriado_Em(Date criado_Em) {
        Criado_Em = criado_Em;
    }
}
