package com.example.oporto_olympics.Controllers.ListagemModalidades.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Equipas.AprovarInscricaoEquipaDAO;
import com.example.oporto_olympics.DAO.Equipas.AprovarInscricaoEquipaDAOImp;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.AprovarInscricaoEquipa;
import com.example.oporto_olympics.Models.Equipa;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Models.Modalidade;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

/**
 * Controlador responsável por exibir as informações detalhadas de uma modalidade em uma interface gráfica.
 * Esta classe preenche os rótulos de uma interface com os dados de uma modalidade, como nome, tipo, gênero,
 * regras, descrição e informações do evento associado.
 */
public class ListagemModalidadesCardController {
    /**
     * Rótulo para mostrar a descrição da modalidade.
     */
    @FXML
    private Label DescricaoLabel;
    /**
     * Rótulo para mostrar as regras da modalidade.
     */
    @FXML
    private Label RegrasLabel;
    /**
     * Rótulo para mostrar o evento associado à modalidade.
     */
    @FXML
    private Label EventoLabel;
    /**
     * Rótulo para mostrar o género da modalidade (masculino/feminino/misto).
     */
    @FXML
    private Label GeneroLabel;
    /**
     * Rótulo para mostrar se a modalidade é composta por um único jogo.
     */
    @FXML
    private Label JogosLabel;
    /**
     * Rótulo para mostrar o nome da modalidade.
     */
    @FXML
    private Label NomeLabel;
    /**
     * Rótulo para mostrar o tipo da modalidade.
     */

    @FXML
    private Button IniciarModalidadeButton;

    @FXML
    private Label TipoLabel;
    /**
     * Rótulo para mostrar o número mínimo de participantes exigidos.
     */
    @FXML
    private Label MinpartLabel;
    /**
     * Rótulo para mostrar a unidade de medida usada na modalidade.
     */
    @FXML
    private Label MedidaLabel;

    /**
     * Mapa para armazenar as unidades de medida.
     */
    private HashMap<String, Integer> EventoMap = new HashMap<>();

    /**
     * Representa uma modalidade específica de um card criado.
     * Este campo armazena a instância da classe {@link Modalidade} correspondente.
     */
    private Modalidade modalidadeEspecifica;

    /**
     * Obtém a modalidade específica atualmente associada.
     * Este método retorna a instância da modalidade que foi definida.
     *
     * @return a modalidade específica associada, ou {@code null} caso nenhuma tenha sido definida.
     */
    public Modalidade getModalidadeEspecifica() {
        return modalidadeEspecifica;
    }

    /**
     * Define uma modalidade específica.
     * Este método permite associar uma nova instância de {@link Modalidade}
     *
     * @param modalidadeEspecifica a modalidade específica a ser associada. Pode ser {@code null}.
     */
    public void setModalidadeEspecifica(Modalidade modalidadeEspecifica) {
        this.modalidadeEspecifica = modalidadeEspecifica;
    }

    /**
     * Preenche os dados da modalidade nos rótulos correspondentes.
     *
     * @param modalidade O objeto {@link Modalidade} contendo os dados da modalidade a serem preenchidos.
     * @throws SQLException Se ocorrer um erro ao obter dados adicionais da base de dados.
     */
    public void PreencherDados (Modalidade modalidade) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        EventosDAOImp eventosDAOImp = new EventosDAOImp(conexao);
        LocaisDAOImp locaisDAOImp = new LocaisDAOImp(conexao);

        NomeLabel.setText(modalidade.getNome());
        TipoLabel.setText(modalidade.getTipo());
        JogosLabel.setText(modalidade.getOneGame());
        GeneroLabel.setText(modalidade.getGenero());
        MinpartLabel.setText(String.valueOf(modalidade.getMinParticipantes()));
        MedidaLabel.setText(modalidade.getMedida());
        DescricaoLabel.setText(modalidade.getDescricao());
        RegrasLabel.setText(modalidade.getRegras());

        List<Integer> eventoIds = modalidade.getListEventosID();

        String eventosTexto = "";

        for (int i = 0; i < eventoIds.size(); i++) {

            int eventoId = eventoIds.get(i);

            Evento evento = eventosDAOImp.getById(eventoId);

            String localNome = locaisDAOImp.getNomeById(evento.getLocal_id());

            eventosTexto += localNome + " - " + evento.getAno_edicao();

            EventoMap.put(localNome + " - " + evento.getAno_edicao(), evento.getId());

            if (i < eventoIds.size() - 1) {
                eventosTexto += ", ";
            }
        }

        EventoLabel.setText(eventosTexto);

        setModalidadeEspecifica(modalidade);

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();

        if(GestorSingle.getGestor() == null && AtletaSingle.getAtleta() != null){
            IniciarModalidadeButton.setDisable(true);
            IniciarModalidadeButton.setVisible(false);
        }
    }

    /**
     * Método chamado ao clicar no botão de iniciar modalidade.
     *
     * Este método verifica se há participantes suficientes para iniciar a modalidade,
     * se existem eventos disponíveis para a modalidade e permite ao utilizador selecionar
     * um evento para iniciar a modalidade. Caso as condições sejam satisfeitas, o estado
     * da modalidade e das equipas é atualizado para iniciado/fechado.
     *
     * @param event evento associado à ação de clique no botão.
     * @throws SQLException caso ocorra um erro na conexão ou execução de queries na base de dados.
     */
    @FXML
    void onClickIniciarModalidadeButton(ActionEvent event) throws SQLException {

        AlertHandler alertHandler;

        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);

        Modalidade modalidade = getModalidadeEspecifica();

        Iterator<Map.Entry<String, Integer>> iterator = EventoMap.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();

            int eventoId = entry.getValue();

            boolean statusModalidade = modalidadeDAOImp.getStatusModalidade(eventoId, modalidade.getId());

            if (statusModalidade) {
                iterator.remove();
            }
        }

        if(EventoMap.isEmpty()){
            alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Sem Eventos Disponiveis!!", "Não existe eventos em que possa iniciar a modalidade " + NomeLabel.getText());
            alertHandler.getAlert().showAndWait();
            return;
        }

        alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Selecione um Evento!!", "De que evento é que deseja iniciar a modalidade " + NomeLabel.getText()  +  " ?");

        List<ButtonType> buttonTypes = new ArrayList<>();

        for (String valor : EventoMap.keySet()) {
            buttonTypes.add(new ButtonType(valor));
        }
        buttonTypes.add(ButtonType.CANCEL);
        alertHandler.getAlert().getButtonTypes().setAll(buttonTypes);

        Optional<ButtonType> result = alertHandler.getAlert().showAndWait();

        if (result.isPresent()) {
            ButtonType clickedButton = result.get();
            if (clickedButton == ButtonType.CANCEL) {
                return;
            }

                alertHandler = new AlertHandler(Alert.AlertType.CONFIRMATION, "Iniciar Modalidade!!!", "Deseja iniciar a modalidade " + NomeLabel.getText()  +  " ( " + clickedButton.getText() + " ) ?");
                Optional<ButtonType> rs = alertHandler.getAlert().showAndWait();

                if (rs.isPresent() && rs.get() != ButtonType.OK) {
                    return;
                }

                int modalidadeID = modalidade.getId();

                int eventoID = EventoMap.get(clickedButton.getText());

                int participantes;

                if(modalidade.getTipo().equals("Individual")){
                    participantes = modalidadeDAOImp.getTotalParticipantesIndividual(eventoID,modalidadeID);
                }else {
                    participantes = modalidadeDAOImp.getTotalParticipantesColetivo(eventoID,modalidadeID);
                }

                if(participantes < modalidade.getMinParticipantes()){
                    alertHandler = new AlertHandler(Alert.AlertType.WARNING, "Sem Participantes!!", "A modalidade " + NomeLabel.getText() + " não possui participantes suficientes para iniciar a mesma. Possui " + participantes + " participantes.");
                    alertHandler.getAlert().showAndWait();
                    return;
                }

                modalidadeDAOImp.updateEventos_ModalidadesStatus(eventoID, modalidadeID, 1);

                alertHandler = new AlertHandler(Alert.AlertType.INFORMATION, "Iniciação Bem Sucedida!!", "A modalidade " + NomeLabel.getText() + " foi iniciada com Sucesso!");
                alertHandler.getAlert().showAndWait();

                EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

                for (Equipa equipa : equipaDAOImp.getAll()) {
                    if(equipa.getModalidadeID() == modalidade.getId()){
                        equipaDAOImp.updateStatus(equipa.getId(), 1);
                    }
                }

                for (Equipa equipa : equipaDAOImp.getAll()) {
                    if(equipa.getModalidadeID() == modalidade.getId()){
                        equipaDAOImp.updateStatus(equipa.getId(), 0);
                    }
                }
        }
    }
}
