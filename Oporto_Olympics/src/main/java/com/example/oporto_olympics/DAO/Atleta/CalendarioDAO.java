package com.example.oporto_olympics.DAO.Atleta;

import com.example.oporto_olympics.Models.*;

import java.util.List;

public interface CalendarioDAO {

    /**
     * Recupera o histórico de competições de um atleta com base no seu ID.
     *
     * @param id O identificador único do atleta.
     * @return Uma lista de resultados de competições associadas ao atleta.
     *         Se o atleta não tiver histórico, a lista estará vazia.
     */
    List<AtletasModalidades> getAtletaModalidade(int id);
    /**
     * Obtém uma lista de eventos-modalidade associados a um evento e modalidade específicos.
     *
     * Este método é utilizado para recuperar todos os registros da tabela `eventos_modalidades`
     * que correspondem ao `evento_id` e `modalidade_id` fornecidos.
     *
     * @param evento_id O ID do evento a ser pesquisado.
     * @param modalidade_id O ID da modalidade a ser pesquisada.
     * @return Uma lista de objetos {@link EventosModalidade} contendo os dados encontrados.
     *         Se nenhum registro for encontrado, a lista estará vazia.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta.
     */
    List<EventosModalidade> getEventosAtleta(int evento_id, int modalidade_id);
    /**
     * Obtém os detalhes de um evento com base no seu ID.
     *
     * Este método pesquisa a tabela `eventos` para recuperar os dados do evento correspondente ao ID fornecido.
     *
     * @param eventoid O ID do evento a ser pesquisado.
     * @return Um objeto {@link Evento} com os dados do evento. Se o evento não for encontrado, retorna null.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta.
     */
    Evento getById(int eventoid);
    /**
     * Obtém os detalhes de uma modalidade com base no seu ID.
     *
     * Este método pesquisa a tabela `modalidades` para recuperar os dados da modalidade correspondente ao ID fornecido.
     *
     * @param id O ID da modalidade a ser pesquisada.
     * @return Um objeto {@link Modalidade} com os dados da modalidade. Se a modalidade não for encontrada, retorna null.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta.
     */
    Modalidade getModalidadeById(int id);
    /**
     * Obtém os detalhes de um evento-modalidade com base no ID do evento e no ID da modalidade.
     *
     * Este método pesquisa a tabela `eventos_modalidades` para recuperar os dados do evento-modalidade
     * correspondente ao `eventoId` e `modalidadeId` fornecidos.
     *
     * @param eventoId O ID do evento.
     * @param modalidadeId O ID da modalidade.
     * @return Um objeto {@link EventosModalidade} contendo os detalhes do evento-modalidade.
     *         Se nenhum registro for encontrado, retorna null.
     * @throws RuntimeException Se ocorrer um erro durante a execução da consulta.
     */
    EventosModalidade getEventoModalidade(int eventoId, int modalidadeId);
    /**
     * Obtém um local específico com base no seu ID.
     *
     * Este método é utilizado para recuperar os detalhes de um local da base de dados
     * através do ID fornecido. Se o local existir, será retornado um objeto {@code Local}
     * contendo as suas informações.
     *
     * @param id O ID do local a ser pesquisado.
     * @return Um objeto {@code Local} que representa o local correspondente ao ID fornecido.
     * @throws RuntimeException Se ocorrer um erro ao aceder à base de dados ou se o local não for encontrado.
     */
    Local getLocalById(int id);

}
