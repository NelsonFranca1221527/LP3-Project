package com.example.oporto_olympics.Singleton;

import com.example.oporto_olympics.API.Models.Client;

/**
 * Classe {@link ClientSingleton} que implementa o padrão Singleton para garantir que apenas uma única instância
 * da classe seja criada durante o ciclo de vida da aplicação.
 * <p>
 * Esta classe armazena um único objeto {@link Client}, permitindo que seja acessado globalmente dentro da aplicação.
 */
public class ClientSingleton {

    private static ClientSingleton instance;
    private Client client;

    /**
     * Construtor privado para impedir a criação de instâncias fora da classe.
     */
    private ClientSingleton() {
        // Construtor privado para evitar instâncias diretas
    }

    /**
     * Obtém a instância única do {@link ClientSingleton}. Caso a instância ainda não tenha sido criada,
     * ela será inicializada de forma segura utilizando a sincronização.
     *
     * @return A instância única do {@link ClientSingleton}.
     */
    public static ClientSingleton getInstance() {
        if (instance == null) {
            synchronized (ClientSingleton.class) {
                if (instance == null) {
                    instance = new ClientSingleton();
                }
            }
        }
        return instance;
    }

    /**
     * Obtém o cliente armazenado no singleton.
     *
     * @return O objeto {@link Client} atualmente armazenado no singleton.
     */
    public Client getClient() {
        return client;
    }

    /**
     * Define o cliente a ser armazenado no singleton.
     *
     * @param client O objeto {@link Client} a ser armazenado no singleton.
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
