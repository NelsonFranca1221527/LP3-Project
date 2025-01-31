package com.example.oporto_olympics.Singleton;

import com.example.oporto_olympics.Models.Gestor;

/**
 * A classe {@link GestorSingleton} implementa o padrão Singleton para garantir que apenas uma única instância
 * da classe seja criada durante o ciclo de vida da aplicação.
 * <p>
 * Esta classe armazena um único objeto {@link Gestor}, permitindo acesso global ao mesmo dentro da aplicação.
 */
public class GestorSingleton {
    private static GestorSingleton instance;
    private Gestor gestor;

    /**
     * Construtor privado para impedir a criação de instâncias fora da classe.
     */
    private GestorSingleton() {
        // Construtor privado para evitar instâncias diretas
    }

    /**
     * Obtém a instância única do {@link GestorSingleton}. Caso a instância ainda não tenha sido criada,
     * ela será inicializada de forma segura utilizando a sincronização.
     *
     * @return A instância única do {@link GestorSingleton}.
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
     * Obtém o gestor armazenado no singleton.
     *
     * @return O objeto {@link Gestor} atualmente armazenado no singleton.
     */
    public Gestor getGestor() {
        return gestor;
    }

    /**
     * Define o gestor a ser armazenado no singleton.
     *
     * @param gestor O objeto {@link Gestor} a ser armazenado no singleton.
     */
    public void setGestor(Gestor gestor) {
        this.gestor = gestor;
    }
}
