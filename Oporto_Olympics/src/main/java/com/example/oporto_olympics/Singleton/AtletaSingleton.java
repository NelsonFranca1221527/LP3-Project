package com.example.oporto_olympics.Singleton;

import com.example.oporto_olympics.Models.Atleta;

/**
 * Classe {@link AtletaSingleton} que implementa o padrão Singleton para garantir que apenas uma única instância
 * da classe seja criada durante o ciclo de vida da aplicação.
 * <p>
 * Esta classe armazena um único objeto {@link Atleta}, permitindo que seja acessado globalmente dentro da aplicação.
 */
public class AtletaSingleton {
    private static AtletaSingleton instance;
    private Atleta atleta;

    /**
     * Construtor privado para impedir a criação de instâncias fora da classe.
     */
    private AtletaSingleton() {
        // Construtor privado para evitar instâncias diretas
    }

    /**
     * Obtém a instância única do {@link AtletaSingleton}. Caso a instância ainda não tenha sido criada,
     * ela será inicializada de forma segura utilizando a sincronização.
     *
     * @return A instância única do {@link AtletaSingleton}.
     */
    public static AtletaSingleton getInstance() {
        if (instance == null) {
            synchronized (AtletaSingleton.class) {
                if (instance == null) {
                    instance = new AtletaSingleton();
                }
            }
        }
        return instance;
    }

    /**
     * Obtém o atleta armazenado no singleton.
     *
     * @return O objeto {@link Atleta} atualmente armazenado no singleton.
     */
    public Atleta getAtleta() {
        return atleta;
    }

    /**
     * Define o atleta a ser armazenado no singleton.
     *
     * @param atleta O objeto {@link Atleta} a ser armazenado no singleton.
     */
    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }
}
