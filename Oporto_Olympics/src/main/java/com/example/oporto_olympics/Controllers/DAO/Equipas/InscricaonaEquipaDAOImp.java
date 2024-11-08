package com.example.oporto_olympics.Controllers.DAO.Equipas;

import com.example.oporto_olympics.Controllers.DAO.DAO;
import com.example.oporto_olympics.Models.InscricaoEquipas;
import com.example.oporto_olympics.Models.Equipa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class InscricaonaEquipaDAOImp implements InscricaoEquipaDAO {
    private Connection connection;

    /**
     * Construtor da classe `InscricaonaEquipaDAOImp`. Inicializa uma nova instância do DAO de inscrições
     * em equipas, utilizando a ligação à base de dados fornecida.
     *
     * @param connection A conexão ativa com a base de dados para realizar operações de inscrição.
     */
    public InscricaonaEquipaDAOImp(Connection connection) {
        this.connection = connection;
    }


    /**
     * Obtém a lista de equipas de um país específico a partir da base de dados.
     * O método executa uma consulta SQL que seleciona todas as equipas onde a sigla do país e do género
     * corresponde ao valor do utilizador. Cada registo resultante é mapeado para uma instância
     * de `InscricaoEquipas` e adicionado à lista de equipas.
     *
     * @param pais A sigla do país (ISO) cujas equipas serão carregadas.
     * @param genero O género do utilizador para aplicar na filtragem das equipas.
     * @return Uma lista de objetos `InscricaoEquipas` representando as equipas do país.
     * @throws RuntimeException Se ocorrer um erro de SQL ao carregar as equipas.
     */
    @Override
    public List<InscricaoEquipas> getEquipas(String pais, String genero) {
        List<InscricaoEquipas> equipas = new ArrayList<>();
        String query = "SELECT * FROM equipas WHERE pais_sigla = ? AND genero = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, pais);
            pstmt.setString(2, genero);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                equipas.add(new InscricaoEquipas(
                        rs.getInt("id"),
                        rs.getString("pais_sigla"),
                        rs.getInt("ano_fundacao"),
                        rs.getInt("modalidade_id"),
                        rs.getInt("participacoes"),
                        rs.getInt("medalhas"),
                        rs.getString("nome"),
                        rs.getString("genero"),
                        rs.getString("desporto")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar equipas: " + e.getMessage());
        }
        return equipas;
    }

    /**
     * Insere uma nova inscrição na base de dados com os dados. O método executa uma
     * operação de inserção (INSERT) na tabela `inscricoes`, utilizando os valores fornecidos para o estado
     * da inscrição, modalidade, atleta e equipa.
     *
     * @param status       O estado da inscrição (e.g., "Pendente", "Ativo").
     * @param modalidadeId O ID da modalidade associada à inscrição.
     * @param atletaId     O ID do atleta que realiza a inscrição.
     * @param equipaId     O ID da equipa na qual o atleta se inscreve.
     * @throws RuntimeException Se ocorrer um erro de SQL ao inserir a inscrição.
     */
    public void inserirInscricao(String status, int modalidadeId, int atletaId, int equipaId) {
        String insertQuery = "INSERT INTO inscricoes (status, modalidade_id, atleta_id, equipa_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, modalidadeId);
            pstmt.setInt(3, atletaId);
            pstmt.setInt(4, equipaId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir inscrição: " + e.getMessage());
        }
    }

    /**
     * Verifica se existe um pedido pendente de inscrição para a equipa.
     * Este método consulta a tabela `inscricoes` para contar o número de registos que correspondem
     * ao atleta e equipa especificos e têm o estado "Pendente". Retorna `true` se existir pelo menos
     * um registo, indicando assim que já existe um pedido pendente.
     *
     * @param atletaId O ID do atleta a verificar.
     * @param equipaId O ID da equipa a verificar.
     * @return `true` se existir um pedido pendente para o atleta e equipa especificados, caso contrário `false`.
     * @throws RuntimeException Se ocorrer um erro de SQL ao verificar o pedido pendente.
     */
    public boolean existePedidoPendente(int atletaId, int equipaId) {
        String query = "SELECT COUNT(*) FROM inscricoes WHERE atleta_id = ? AND equipa_id = ? AND status = 'Pendente'";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, atletaId);
            pstmt.setInt(2, equipaId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se o contador for maior que 0, indicando que existe um pedido pendente
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar pedido pendente: " + e.getMessage());
        }
        return false;
    }

    public boolean existePedidoAprovado(int atletaId, int equipaId) {
        String query = "SELECT COUNT(*) FROM inscricoes WHERE atleta_id = ? AND equipa_id = ? AND status = 'Aprovado'";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, atletaId);
            pstmt.setInt(2, equipaId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Retorna true se o contador for maior que 0, indicando que existe um pedido pendente
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar pedido pendente: " + e.getMessage());
        }
        return false;
    }

}