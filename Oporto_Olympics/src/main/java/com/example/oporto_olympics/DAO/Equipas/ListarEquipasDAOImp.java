package com.example.oporto_olympics.DAO.Equipas;

import com.example.oporto_olympics.Models.AtletaInfo;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import com.example.oporto_olympics.Models.ResultadosEquipa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Implementação da interface {@link ListarEquipasDAO} para listar todas as equipas registradas na base de dados.
 * Esta classe utiliza uma conexão com a base de dados para recuperar as informações das equipas.
 */
public class ListarEquipasDAOImp implements ListarEquipasDAO {
    /**
     * Objeto de conexão com a base de dados.
     */
    private Connection connection;

    /**
     * Construtor que inicializa a instância com a conexão à base de dados.
     *
     * @param connection A conexão com a base de dados que será utilizada para executar as consultas.
     */
    public ListarEquipasDAOImp(Connection connection) {
        this.connection = connection;
    }


    /**
     * Recupera uma lista de equipas da base de dados, com a possibilidade de aplicar um filtro pelo nome.
     *
     * Este método permite ir buscar todas as equipas registadas na base de dados. Caso seja fornecido um valor no
     * parâmetro `filtroNome`, apenas as equipas cujo nome contenha esse valor serão retornadas.
     * Se o `filtroNome` for nulo ou vazio, todas as equipas serão retornadas.
     *
     * A consulta SQL utiliza a cláusula `LIKE` para realizar a filtragem, caso seja especificado um filtro.
     *
     * @param filtroNome O nome (ou parte do nome) a ser utilizado como filtro na busca das equipas.
     *                   Pode ser nulo ou vazio para buscar todas as equipas.
     * @return Uma lista de objetos {@link InscricaoEquipas}, representando as equipas recuperadas da base de dados.
     * @throws RuntimeException Se ocorrer um erro ao carregar as equipas da base de dados.
     */
    @Override
    public List<InscricaoEquipas> getEquipas(String filtroNome) {
        List<InscricaoEquipas> equipas = new ArrayList<>();
        String query;

        if (filtroNome == null || filtroNome.trim().isEmpty()) {
            query = "SELECT * FROM equipas";
        } else {
            query = "SELECT * FROM equipas WHERE nome LIKE ?";
        }

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            if (filtroNome != null && !filtroNome.trim().isEmpty()) {
                pstmt.setString(1, "%" + filtroNome + "%");
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                equipas.add(new InscricaoEquipas(
                        rs.getInt("id"),
                        rs.getString("pais_sigla"),
                        rs.getInt("ano_fundacao"),
                        rs.getInt("modalidade_id"),
                        rs.getString("nome"),
                        rs.getString("genero"),
                        rs.getString("desporto")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar equipas: " + e.getMessage(), e);
        }

        return equipas;
    }

    /**
     * Recupera o histórico de resultados de uma equipa com base no seu ID.
     *
     * @param equipaId o ID da equipa
     * @return uma lista de objetos {@link ResultadosEquipa} com o histórico de resultados
     */
    @Override
    public List<ResultadosEquipa> getHistorico(int equipaId) {
        List<ResultadosEquipa> resultados = new ArrayList<>();
        String query = "SELECT * FROM historico_equipas_competicoes WHERE equipa_id = ? ORDER BY ano";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, equipaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    int ano = rs.getInt("ano");
                    String resultado = rs.getString("resultado");

                    ResultadosEquipa resultadoEquipa = new ResultadosEquipa(id, equipaId, ano, resultado);
                    resultados.add(resultadoEquipa);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultados;
    }

    /**
     * Recupera a lista de atletas associados a uma equipa específica, com todos os detalhes dos atletas.
     *
     * Este método realiza duas consultas a base de dados. A primeira consulta obtém os IDs dos atletas
     * na tabela `atletas_equipa` que estão associados a uma equipa específica. A segunda consulta utiliza
     * esses IDs para recuperar todos os detalhes de cada atleta na tabela `atleta`, incluindo nome, país,
     * gênero, altura, peso, e data de nascimento.
     *
     * @param equipaId O ID da equipa cujos atletas serão recuperados.
     * @return Uma lista de {@link AtletaInfo} contendo os atletas associados à equipa especificada.
     *         Se não houver atletas para a equipa, retorna uma lista vazia.
     */
    @Override
    public List<AtletaInfo> getAtletasByEquipaId(int equipaId) {
        List<AtletaInfo> atletas = new ArrayList<>();
        String queryAtletasEquipa = "SELECT atleta_id FROM atletas_equipas WHERE equipa_id = ?";
        String queryAtletaDetalhes = "SELECT user_id, nome, pais_sigla, genero, altura_cm, peso_kg, data_nascimento FROM atletas WHERE user_id = ?";

        try (PreparedStatement pstmtAtletasEquipa = connection.prepareStatement(queryAtletasEquipa)) {
            pstmtAtletasEquipa.setInt(1, equipaId);

            try (ResultSet rsAtletasEquipa = pstmtAtletasEquipa.executeQuery()) {
                while (rsAtletasEquipa.next()) {
                    int atletaId = rsAtletasEquipa.getInt("atleta_id");

                    // Para cada atleta_id, buscar os detalhes do atleta na tabela atleta
                    try (PreparedStatement pstmtAtletaDetalhes = connection.prepareStatement(queryAtletaDetalhes)) {
                        pstmtAtletaDetalhes.setInt(1, atletaId);

                        try (ResultSet rsAtletaDetalhes = pstmtAtletaDetalhes.executeQuery()) {
                            if (rsAtletaDetalhes.next()) {
                                int id = rsAtletaDetalhes.getInt("user_id");
                                String nome = rsAtletaDetalhes.getString("nome");
                                String pais = rsAtletaDetalhes.getString("pais_sigla");
                                String genero = rsAtletaDetalhes.getString("genero");
                                int altura = rsAtletaDetalhes.getInt("altura_cm");
                                int peso = rsAtletaDetalhes.getInt("peso_kg");
                                Date dataNascimento = rsAtletaDetalhes.getDate("data_nascimento");
                                if (dataNascimento != null) {
                                    dataNascimento = new Date(dataNascimento.getTime());
                                }

                                AtletaInfo atletaInfo = new AtletaInfo(id, nome, pais, genero, altura, peso, dataNascimento);
                                atletas.add(atletaInfo);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return atletas;
    }




}
