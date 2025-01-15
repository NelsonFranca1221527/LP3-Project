package com.example.oporto_olympics.Singleton;
/**
 * A classe {@link InserçãoXMLSingleton} implementa o padrão Singleton para garantir que apenas uma instância
 * dessa classe seja criada durante o ciclo de vida da aplicação. A classe é responsável por armazenar um
 * tipo de XML (representado por uma String) e garantir que este valor seja acessado de forma global e única.
 */
public class InserçãoXMLSingleton {
    /**
     * Instância única do singleton {@link InserçãoXMLSingleton}.
     */
    private static InserçãoXMLSingleton instance;
    /**
     * Tipo de XML que está a ser manipulado ou processado.
     */
    private String TipoXML;
    /**
     * Construtor privado que impede a criação de instâncias fora da classe.
     * Este construtor garante que a instância do singleton seja criada apenas dentro da própria classe.
     */
    private InserçãoXMLSingleton() {}
    /**
     * Obtém a instância única do Singleton. Caso a instância ainda não tenha sido criada,
     * ela será inicializada de forma segura utilizando a sincronização.
     *
     * @return A instância única do Singleton {@link InserçãoXMLSingleton}.
     */
    public static InserçãoXMLSingleton getInstance() {
        if (instance == null) {
            synchronized (InserçãoXMLSingleton.class) {
                if (instance == null) {
                    instance = new InserçãoXMLSingleton();
                }
            }
        }
        return instance;
    }
    /**
     * Obtém o tipo de XML armazenado no Singleton.
     *
     * @return O tipo de XML armazenado.
     */
    public String getTipoXML() {
        return TipoXML;
    }
    /**
     * Define o tipo de XML a ser armazenado no Singleton.
     *
     * @param tipo O tipo de XML a ser armazenado.
     */
    public void setTipoXML(String tipo) {
        this.TipoXML = tipo;
    }
}
