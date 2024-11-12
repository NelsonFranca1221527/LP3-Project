package com.example.oporto_olympics.Singleton;

import com.example.oporto_olympics.Models.Gestor;

/**
 * A classe {@link GestorSingleton} implementa o padrão Singleton para garantir que apenas uma instância
 * da classe seja criada ao longo do ciclo de vida da aplicação.
 * Esta classe armazena um único objeto {@link Gestor}.
 */
public class GestorSingleton {
    private static volatile GestorSingleton instance;
    private static final Object mutex = new Object();
    private Gestor gestor;

    private GestorSingleton() {
    // Impede a criação de instâncias através de new
    }

    /**
    * Obtém a instância única do singleton.
    *
    * @return Instância única do singleton
    */
    public static GestorSingleton getInstance() {
        GestorSingleton result = instance;

        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new GestorSingleton();
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