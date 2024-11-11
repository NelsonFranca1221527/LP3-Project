package com.example.oporto_olympics.Singleton;

import com.example.oporto_olympics.Models.Atleta;
/**
 * A classe {@link AtletaSingleton} implementa o padrão Singleton para garantir que apenas uma instância
 * da classe seja criada ao longo do ciclo de vida da aplicação.
 * Esta classe armazena um único objeto {@link Atleta}.
 */
public class AtletaSingleton {
    private static volatile AtletaSingleton instance;
    private static final Object mutex = new Object();
    private Atleta atleta;
    private int Id;

    private AtletaSingleton() {
        // Impede a criação de instâncias através de new
    }

    /**
     * Obtém a instância única do singleton.
     *
     * @return Instância única do singleton
     */
    public static AtletaSingleton getInstance() {
        AtletaSingleton result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new AtletaSingleton();
                }
            }
        }

        return result;
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
