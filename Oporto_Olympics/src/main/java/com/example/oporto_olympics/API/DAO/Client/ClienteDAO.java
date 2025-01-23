package com.example.oporto_olympics.API.DAO.Client;

import com.example.oporto_olympics.API.Models.Client;

import java.io.IOException;
import java.util.List;

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
    /**
     * Insere um novo cliente na API.
     *
     * Este método envia uma solicitação POST para a API com os dados do cliente a ser inserido,
     * incluindo o nome, e-mail e senha. A resposta da API será utilizada para retornar uma mensagem
     * indicando o sucesso ou falha da operação.
     *
     * @param name O nome do cliente a ser inserido.
     * @param email O e-mail do cliente a ser inserido.
     * @param password A senha do cliente a ser inserido.
     *
     * @return Uma mensagem indicando o sucesso ou falha na inserção do cliente.
     *
     * @throws IOException Se ocorrer um erro ao fazer a solicitação ou ao processar a resposta.
     */
    String insertClient(String name, String email, String password) throws IOException;
    /**
     * Recupera a lista de todos os clientes da API.
     *
     * Este método envia uma solicitação GET para a API, recebe a resposta em formato JSON
     * e a converte para uma lista de objetos {@link Client}. Se a resposta da API for bem-sucedida
     * e contiver a chave "Status" com valor "OK", os dados dos clientes serão extraídos e retornados.
     * Caso contrário, será gerado um erro e uma exceção {@link IOException} será lançada.
     *
     * @return Uma lista de objetos {@link Client} com os dados dos clientes recuperados da API.
     *
     * @throws IOException Se ocorrer um erro ao fazer a solicitação ou se o status da resposta não for "OK".
     */
    List<Client> getAllClients() throws IOException;
    /**
     * Remove um cliente da API com base no ID fornecido.
     *
     * Este método envia uma solicitação DELETE para a API, com o ID do cliente que se deseja remover.
     * Se a resposta da API for bem-sucedida (código de resposta HTTP 200), o cliente será removido e
     * uma mensagem de sucesso será retornada. Caso contrário, uma mensagem de erro será retornada.
     *
     * @param id O ID do cliente a ser removido.
     *
     * @return Uma mensagem indicando o sucesso ou falha na remoção do cliente.
     *
     * @throws IOException Se ocorrer um erro ao fazer a solicitação ou ao processar a resposta.
     */
    String removeClient(String id) throws IOException;
}
