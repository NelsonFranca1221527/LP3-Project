package com.example.oporto_olympics.Singleton;

import com.example.oporto_olympics.API.Models.Client;

public class ClientSingleton {

    private static ClientSingleton instance;

    private Client client;

    private ClientSingleton(){

    }

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


    public Client getClient() {
        return client ;
    }


    public void setClient(Client client) {
        this.client = client;
    }

}
