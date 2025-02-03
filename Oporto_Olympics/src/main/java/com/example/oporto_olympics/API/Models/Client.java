package com.example.oporto_olympics.API.Models;
/**
 * Representa um cliente no sistema.
 * Esta classe contém informações sobre o cliente, incluindo o seu ID, grupo, nome, e-mail e estado de ativação.
 */
public class Client {
    /**
     * Identificador único do cliente.
     */
    private String id;
    /**
     * Identificador do grupo ao qual o cliente pertence.
     */
    private String GroupID;
    /**
     * Nome do cliente.
     */
    private String Name;
    /**
     * Endereço de e-mail do cliente.
     */
    private String Email;
    /**
     * Indica se o cliente está ativo.
     */
    private Boolean Active;
    /**
     * Construtor para criar uma instância de Cliente.
     *
     * @param id Identificador único do cliente.
     * @param GroupID Identificador do grupo do cliente.
     * @param Name Nome do cliente.
     * @param Email Endereço de e-mail do cliente.
     * @param Active Estado de ativação do cliente (true para ativo, false para inativo).
     */
    public Client(String id, String GroupID, String Name, String Email, Boolean Active) {
        this.id = id;
        this.GroupID = GroupID;
        this.Name = Name;
        this.Email = Email;
        this.Active = Active;
    }
    /**
     * Obtém o identificador único do cliente.
     *
     * @return ID do cliente.
     */
    public String getId() {
        return id;
    }
    /**
     * Define o identificador único do cliente.
     *
     * @param id Novo ID do cliente.
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * Obtém o identificador do grupo do cliente.
     *
     * @return ID do grupo do cliente.
     */
    public String getGroupID() {
        return GroupID;
    }
    /**
     * Define o identificador do grupo do cliente.
     *
     * @param GroupID Novo ID do grupo do cliente.
     */
    public void setGroupID(String GroupID) {
        this.GroupID = GroupID;
    }
    /**
     * Obtém o nome do cliente.
     *
     * @return Nome do cliente.
     */
    public String getName() {
        return Name;
    }
    /**
     * Define o nome do cliente.
     *
     * @param name Novo nome do cliente.
     */
    public void setName(String name) {
        Name = name;
    }
    /**
     * Obtém o endereço de e-mail do cliente.
     *
     * @return E-mail do cliente.
     */
    public String getEmail() {
        return Email;
    }
    /**
     * Define o endereço de e-mail do cliente.
     *
     * @param Email Novo endereço de e-mail do cliente.
     */
    public void setEmail(String Email) {
        this.Email = Email;
    }
    /**
     * Obtém o estado de ativação do cliente.
     *
     * @return True se o cliente estiver ativo, false caso contrário.
     */
    public Boolean getActive() {
        return Active;
    }
    /**
     * Define o estado de ativação do cliente.
     *
     * @param Active Novo estado de ativação do cliente.
     */
    public void setActive(Boolean Active) {
        this.Active = Active;
    }

}
