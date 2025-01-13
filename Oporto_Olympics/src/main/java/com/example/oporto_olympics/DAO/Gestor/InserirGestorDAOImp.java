package com.example.oporto_olympics.DAO.Gestor;

import com.example.oporto_olympics.DAO.Gestor.InserirGestorDAO;
import com.example.oporto_olympics.Misc.AlertHandler;
import javafx.scene.control.Alert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;

/**
 * Implementação da interface {@link InserirGestorDAO} para a gestão de operações de inserção e atualização de dados de gestores na base de dados.
 * Esta classe é responsável por guardar ou atualizar os dados de um gestor,
 * e converter strings para um hash utilizando o algoritmo SHA-256.
 */
public class InserirGestorDAOImp implements InserirGestorDAO{

    private Connection conexao;

    /**
     * Construtor que inicializa a conexão com a base de dados
     *
     * @param conexao conexão com a base de dados
     */
    public InserirGestorDAOImp(Connection conexao) {
        this.conexao = conexao;
    }

    /**
     * Método responsável por guardar ou atualizar os dados de um gestor na base de dados.
     * O fluxo:
     * 1. Gera o número mecanográfico do gestor.
     * 2. Obtém o ID de função para o tipo de utilizador 'Gestor'.
     * 3. Insere um novo utilizador na tabela 'users'.
     * 4. Recupera o ID do utilizador recém-criado.
     * 5. Atualiza os dados do gestor na tabela 'gestores', associando-o ao utilizador recém-criado.
     *
     * @param nome O nome do gestor a ser guardado/atualizado.
     */
    @Override
    public void saveGestor(String nome) {

        AlertHandler alertHandler;

        try {
            CallableStatement cs = conexao.prepareCall("{CALL SaveGestor(?,?)}");
            cs.setString(1, nome);
            cs.registerOutParameter(2, java.sql.Types.NVARCHAR);
            cs.executeUpdate();

            //Busca o número mecanográfico gerado para o novo gestor
            String num = cs.getString(2);

            //Mostra um alerta a dizer ao utilizador qual é o número mecanogáfico do novo gestor
            alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Gestor Criado com Sucesso!","O gestor foi criado com sucesso. Número mecanográfico: " + num);
            alertHandler.getAlert().showAndWait();

            cs.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            alertHandler = new AlertHandler(Alert.AlertType.ERROR, "Erro ao atualizar gestor", "Erro ao salvar o gestor na base de dados. Verifique o console.");
            alertHandler.getAlert().showAndWait();
        }
    }

}
