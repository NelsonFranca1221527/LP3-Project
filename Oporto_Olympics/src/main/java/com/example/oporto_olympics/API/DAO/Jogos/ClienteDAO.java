package com.example.oporto_olympics.API.DAO.Jogos;

import java.io.IOException;
/**
 * Interface ClienteDAO que define o contrato para operações relacionadas com clientes.
 */
public interface ClienteDAO {
    /**
     * Método para validar as credenciais de um cliente.
     * Envia os dados do cliente para a API e valida se as credenciais são válidas.
     *
     * @param email    O endereço de e-mail do cliente.
     * @param password A palavra-passe do cliente.
     * @return Uma mensagem que indica o resultado da validação das credenciais.
     * @throws IOException Caso ocorra um erro de comunicação com a API.
     */
    String ValidateCredentials(String email, String password) throws IOException;

}
