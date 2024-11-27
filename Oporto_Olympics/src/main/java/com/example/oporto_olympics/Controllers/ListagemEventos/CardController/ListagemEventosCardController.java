package com.example.oporto_olympics.Controllers.ListagemEventos.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Eventos.InscricaonoEventoDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.Evento;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.SQLException;
/**
 * Controlador responsável por gerir a exibição dos dados de um evento em um card.
 * Esta classe preenche os rótulos e imagens do card com as informações do evento,
 * como o ano de edição, o local, o país, o logótipo e a mascote.
 */
public class ListagemEventosCardController {
    /**
     * Rótulo para mostrar o ano de edição do evento.
     */
    @FXML
    private Label AnoLabel;
    /**
     * Rótulo para mostrar o local do evento.
     */
    @FXML
    private Label LocalLabel;
    /**
     * Rótulo para mostrar o país do evento.
     */
    @FXML
    private Label PaisLabel;
    /**
     * Imagem para mostrar o logótipo do evento.
     */
    @FXML
    private ImageView img_logo;
    /**
     * Imagem para mostrar a mascote do evento.
     */
    @FXML
    private ImageView img_mascote;
    /**
     * Botão da interface gráfica associado à funcionalidade de inscrever o atleta.
     */
    @FXML
    private Button InscreverButton;
    /**
     * Representa um evento específico de um card.
     */
    private Evento eventoEspecifico;
    /**
     * Obtém o evento específico associado.
     *
     * @return o evento específico atualmente associado a esta classe.
     */
    public Evento getEventoEspecifico() {
        return eventoEspecifico;
    }
    /**
     * Define o evento específico associado.
     *
     * @param eventoEspecifico o novo evento específico a associar a esta classe.
     */
    public void setEventoEspecifico(Evento eventoEspecifico) {
        this.eventoEspecifico = eventoEspecifico;
    }
    /**
     * Preenche os dados do evento nos rótulos e imagens correspondentes.
     *
     * @param evento O objeto {@link Evento} contendo os dados do evento a serem preenchidos.
     * @throws SQLException Se ocorrer um erro na consulta da base de dados.
     */
    public void PreencherDados (Evento evento) throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();
        LocaisDAOImp locaisDAO = new LocaisDAOImp(conexao);

        AnoLabel.setText(String.valueOf(evento.getAno_edicao()));
        PaisLabel.setText(evento.getPais());

        // Define o LocalLabel com o nome do local usando o ID
        String nomeLocal = locaisDAO.getNomeById(evento.getLocal_id());
        if (nomeLocal != null) {
            LocalLabel.setText(nomeLocal);
        } else {
            LocalLabel.setText("Local não encontrado");
        }

        byte[] logoBytes = evento.getLogo();

        if (logoBytes != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(logoBytes);
                Image image = new Image(inputStream);
                img_logo.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        byte[] mascoteBytes = evento.getMascote();

        if (mascoteBytes != null) {
            try {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(mascoteBytes);
                Image image = new Image(inputStream);
                img_mascote.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }

        GestorSingleton GestorSingle = GestorSingleton.getInstance();
        AtletaSingleton AtletaSingle = AtletaSingleton.getInstance();

        if(GestorSingle.getGestor() != null && AtletaSingle.getAtleta() == null){
            InscreverButton.setDisable(true);
            InscreverButton.setVisible(false);
        }

        setEventoEspecifico(evento);
    }
    /**
     * Método acionado ao clicar no botão de inscrição.
     * Gera uma inscrição para o atleta no evento selecionado, verificando previamente o estado das inscrições existentes.
     *
     * @throws SQLException se ocorrer um erro ao aceder à base de dados.
     *
     * Regras de inscrição:
     * <ul>
     *   <li>Se o atleta já tiver um pedido de inscrição pendente para o evento, é apresentada uma mensagem de aviso.</li>
     *   <li>Se o atleta já estiver inscrito (estado aprovado) no evento, é apresentada uma mensagem de aviso.</li>
     *   <li>Se não existir uma inscrição, é criada uma nova com o estado "Pendente".</li>
     * </ul>
     * Em caso de erro inesperado, apresenta uma mensagem de erro ao utilizador.
     */
    @FXML
    public void OnClickInscreverButton() throws SQLException {
        ConnectionBD conexaoBD = ConnectionBD.getInstance();
        Connection conexao = conexaoBD.getConexao();

        InscricaonoEventoDAOImp InscreverEvnto = new InscricaonoEventoDAOImp(conexao);
        AtletaSingleton atletaSingle = AtletaSingleton.getInstance();
        Atleta atleta = atletaSingle.getAtleta();

        try {
            int eventoId = eventoEspecifico.getId();
            int atletaId = atleta.getId();

            System.out.println(eventoId);

            if (InscreverEvnto.existeInscricaoPendente(atletaId, eventoId)) {
                Alert pendenteAlert = new Alert(Alert.AlertType.WARNING, "Já existe um pedido pendente para este evento...");
                pendenteAlert.show();
            } else {

                if(InscreverEvnto.existeInscricaoAprovada(atletaId, eventoId)) {
                    Alert aprovadoAlert = new Alert(Alert.AlertType.WARNING,"Já está inscrito neste evento");
                    aprovadoAlert.show();
                } else {
                    String estado = "Pendente";
                    InscreverEvnto.inserirInscricao(estado,eventoId, atletaId);

                    Alert inscricaoAlert = new Alert(Alert.AlertType.INFORMATION, "Foi criada uma inscrição pedente. Porfavor aguarde para aprovação");
                    inscricaoAlert.showAndWait();
                }
            }
        } catch (RuntimeException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Erro ao realizar inscrição: " + e.getMessage());
            errorAlert.show();
        }

    }
}
