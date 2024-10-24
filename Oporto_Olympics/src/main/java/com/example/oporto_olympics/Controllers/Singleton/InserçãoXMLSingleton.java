package com.example.oporto_olympics.Controllers.Singleton;

public class InserçãoXMLSingleton {

    private static InserçãoXMLSingleton instance;

    private String TipoXML;

    private InserçãoXMLSingleton() {}

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

    public String getTipoXML() {
        return TipoXML;
    }

    public void setTipoXML(String tipo) {
        this.TipoXML = tipo;
    }
}
