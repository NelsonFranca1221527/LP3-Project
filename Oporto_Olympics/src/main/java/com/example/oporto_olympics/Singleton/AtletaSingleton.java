package com.example.oporto_olympics.Singleton;

import com.example.oporto_olympics.Models.Atleta;
/**
 * A classe {@link AtletaSingleton} implementa o padrão Singleton para garantir que apenas uma instância
 * da classe seja criada ao longo do ciclo de vida da aplicação.
 * Esta classe armazena um único objeto {@link Atleta}.
 */
public class AtletaSingleton {
    private static AtletaSingleton instance;

    private Atleta atleta;

    private AtletaSingleton() {
        // Impede a criação de instâncias através de new
    }

    /**
     * Obtém a instância única do Singleton. Caso a instância ainda não tenha sido criada,
     * ela será inicializada de forma segura utilizando a sincronização.
     *
     * @return A instância única do Singleton {@link AtletaSingleton}.
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
     * Obtém o utilizador armazenado no singleton.
     *
     * @return Utilizador armazenado no singleton
     */
    public Atleta getAtleta() {
        return atleta;
    }

    /**
     * Define o utilizador a ser armazenado no singleton.
     *
     * @param atleta Utilizador a ser armazenado no singleton
     */
    public void setAtleta(Atleta atleta) {
        this.atleta = atleta;
    }

}
