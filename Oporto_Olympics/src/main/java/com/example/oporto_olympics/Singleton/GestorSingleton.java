package com.example.oporto_olympics.Singleton;

import com.example.oporto_olympics.Models.Gestor;

/**
 * A classe {@link GestorSingleton} implementa o padrão Singleton para garantir que apenas uma instância
 * da classe seja criada ao longo do ciclo de vida da aplicação.
 * Esta classe armazena um único objeto {@link Gestor}.
 */
public class GestorSingleton {
    private static GestorSingleton instance;

    private Gestor gestor;

    private GestorSingleton() {
    // Impede a criação de instâncias através de new
    }

    /**
     * Obtém a instância única do Singleton. Caso a instância ainda não tenha sido criada,
     * ela será inicializada de forma segura utilizando a sincronização.
     *
     * @return A instância única do Singleton {@link GestorSingleton}.
     */
    public static GestorSingleton getInstance() {
        if (instance == null) {
            synchronized (GestorSingleton.class) {
                if (instance == null) {
                    instance = new GestorSingleton();
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
    public Gestor getGestor() {
    return gestor;
    }

    /**
    * Define o utilizador a ser armazenado no singleton.
    *
    * @param gestor Utilizador a ser armazenado no singleton
    */
    public void setGestor(Gestor gestor) {
    this.gestor = gestor;
    }

}