package com.example.oporto_olympics.Controllers.ListagemModalidades.CardController;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Equipas.AprovarInscricaoEquipaDAO;
import com.example.oporto_olympics.DAO.Equipas.AprovarInscricaoEquipaDAOImp;
import com.example.oporto_olympics.DAO.Equipas.ListarEquipasDAOImp;
import com.example.oporto_olympics.DAO.Eventos.EventosDAOImp;
import com.example.oporto_olympics.DAO.Locais.LocaisDAOImp;
import com.example.oporto_olympics.DAO.Resultados.ResultadosModalidadeDAOImp;
import com.example.oporto_olympics.DAO.XML.AtletaDAOImp;
import com.example.oporto_olympics.DAO.XML.EquipaDAOImp;
import com.example.oporto_olympics.DAO.XML.ModalidadeDAOImp;
import com.example.oporto_olympics.Misc.AlertHandler;
import com.example.oporto_olympics.Models.*;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

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

            EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

            for (Equipa equipa : equipaDAOImp.getAll()) {
                if(equipa.getModalidadeID() == modalidade.getId()){
                    equipaDAOImp.updateStatus(equipa.getId(), 1);
                }
            }

            if (modalidade.getOneGame().equals("One")) {
                gerarResultadosOneGame(modalidade, eventoID);
            }
            if (modalidade.getOneGame().equals("Multiple")) {
                gerarResultadosMultiple(modalidade, eventoID);
            }

            for (Equipa equipa : equipaDAOImp.getAll()) {
                if(equipa.getModalidadeID() == modalidade.getId()){
                    equipaDAOImp.updateStatus(equipa.getId(), 0);
                }
            }
        }
    }

    /**
     * Gera e exibe os resultados de um evento para uma modalidade específica, atribuindo medalhas aos participantes e armazena os resultados na base de dados.
     *
     * Este método executa as seguintes ações:
     *     Recupera os participantes (atletas ou equipas) do evento e da modalidade específica;
     *     Gera um resultado aleatório para cada participante;
     *     Ordena os participantes com base nos seus resultados em ordem crescente (o melhor resultado recebe a medalha de ouro);
     *     Exibe os resultados ordenados num diálogo gráfico;
     *     Atribui medalhas aos participantes com base no ranking (Ouro para o melhor, Prata para o segundo, Bronze para o terceiro);
     *     Guarda os resultados e a medalha na base de dados;
     *     Atualiza o estado do evento na base de dados para indicar que os resultados foram gerados.
     *
     * @param modalidade A modalidade desportiva para a qual os resultados estão a ser gerados.
     * @param eventoID O ID do evento desportivo no qual a modalidade está a ser realizada.
     */
    private void gerarResultadosOneGame(Modalidade modalidade, int eventoID) {
        try {
            ConnectionBD conexaoBD = ConnectionBD.getInstance();
            Connection conexao = conexaoBD.getConexao();

            ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);
            ResultadosModalidadeDAOImp resultadosModalidadeDAOImp = new ResultadosModalidadeDAOImp(conexao);

            // Obter participantes (IDs e nomes)
            boolean isIndividual = modalidade.getTipo().equalsIgnoreCase("Individual");
            Map<Integer, String> participantes = isIndividual
                    ? modalidadeDAOImp.getAtletasPorEvento(eventoID, modalidade.getId())
                    : modalidadeDAOImp.getEquipasPorEvento(eventoID, modalidade.getId());

            // Criar o diálogo para exibir resultados
            Dialog<ButtonType> resultadosDialog = new Dialog<>();
            resultadosDialog.setTitle("Resultados Gerados");

            GridPane resultadosGrid = new GridPane();
            resultadosGrid.setHgap(20);
            resultadosGrid.setVgap(10);
            resultadosGrid.setStyle("-fx-padding: 20; -fx-alignment: center-left;");

            resultadosGrid.add(new Label("Participante"), 0, 0);
            resultadosGrid.add(new Label("Resultado"), 1, 0);

            Random random = new Random();
            Map<Integer, Double> resultadoParticipante = new HashMap<>();

            // Gerar resultados e armazenar no Map
            for (Map.Entry<Integer, String> entry : participantes.entrySet()) {
                Integer participanteID = entry.getKey();
                double resultado = 10 + (90 * random.nextDouble());
                resultadoParticipante.put(participanteID, resultado);
            }

            // Ordenar os resultados em ordem crescente (menor resultado recebe ouro)
            List<Map.Entry<Integer, Double>> sortedResults = resultadoParticipante.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue()) // Ordenação crescente
                    .collect(Collectors.toList());

            // Exibir os resultados ordenados na tabela
            for (int i = 0; i < sortedResults.size(); i++) {
                Integer participanteID = sortedResults.get(i).getKey();
                Double resultado = sortedResults.get(i).getValue();
                int row = resultadosGrid.getRowCount(); // Obter a próxima linha
                resultadosGrid.add(new Label(participantes.get(participanteID)), 0, row);
                resultadosGrid.add(new Label(String.format("%.2f", resultado)), 1, row);
            }

            resultadosDialog.getDialogPane().setContent(resultadosGrid);
            resultadosDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Mostrar o diálogo e salvar os resultados aceites
            Optional<ButtonType> dialogResult = resultadosDialog.showAndWait();
            if (dialogResult.isPresent() && dialogResult.get() == ButtonType.OK) {
                // Atribuir medalhas com base no ranking após a ordenação
                int ranking = 1;
                for (Map.Entry<Integer, Double> entry : sortedResults) {
                    Integer participanteID = entry.getKey();
                    Double resultado = entry.getValue();

                    resultado = Double.valueOf(String.format("%.2f", resultado).replace(",", "."));

                    int ouro = 0;

                    int prata = 0;

                    int bronze = 0;

                    // Determinar medalha (agora com o melhor resultado recebendo ouro)

                    String medalha = "Nenhuma";
                    if (ranking == 1) {
                        ouro++;
                        medalha = "Ouro";
                    } else if (ranking == 2) {
                        prata++;
                        medalha = "Prata";
                    } else if (ranking == 3) {
                        bronze++;
                        medalha = "Bronze";
                    }

                    Date data = new Date();
                    // Guardar o resultado na base de dados
                    ResultadosModalidade resultadosModalidade;

                    if (isIndividual) {
                        resultadosModalidade = new ResultadosModalidade(0, data, resultado, modalidade.getMedida(), medalha, modalidade.getId(), participanteID, 0);
                    } else {
                        resultadosModalidade = new ResultadosModalidade(0, data, resultado, modalidade.getMedida(), medalha, modalidade.getId(), 0, participanteID);
                    }

                    resultadosModalidadeDAOImp.save(resultadosModalidade);

                    EventosDAOImp eventosDAOImp = new EventosDAOImp(conexao);

                    Evento evento = eventosDAOImp.getById(eventoID);

                    AtletaDAOImp atletaDAOImp = new AtletaDAOImp(conexao);

                    if (isIndividual) {

                        atletaDAOImp.saveHistorico(participanteID, eventoID, new ParticipaçõesAtleta(evento.getAno_edicao(), ouro, prata, bronze));

                    } else {

                        if (ranking > 3) {
                            medalha = "Diploma";
                        }

                        EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

                            equipaDAOImp.saveHistorico(participanteID, eventoID, new ParticipaçõesEquipa(evento.getAno_edicao(), medalha));

                        ListarEquipasDAOImp listarEquipasDAOImp = new ListarEquipasDAOImp(conexao);

                        List<AtletaInfo> lstAtletaInfo = listarEquipasDAOImp.getAtletasByEquipaId(participanteID);

                        if(lstAtletaInfo != null && !lstAtletaInfo.isEmpty()) {

                            for (AtletaInfo atletaInfo : lstAtletaInfo) {
                                atletaDAOImp.saveHistorico(atletaInfo.getId(), eventoID, new ParticipaçõesAtleta(evento.getAno_edicao(), ouro, prata, bronze));
                            }
                        }
                    }

                    ranking++;

                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Resultados aceites foram salvos com sucesso.");
                alert.show();

                modalidadeDAOImp.updateEventos_ModalidadesStatus(eventoID, modalidade.getId(), 1);
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao gerar resultados: " + ex.getMessage());
            alert.show();
        }
    }

    /**
     * Gera e apresenta os resultados de múltiplos jogos entre equipas participantes de uma modalidade.
     * A função realiza os seguintes passos:
     *     Obtém as equipas participantes da modalidade e inicializa os seus pontos.
     *     Realiza jogos entre todas as equipas (todos contra todos) e atualiza os pontos conforme os resultados.
     *     Em caso de empate na pontuação final, organiza jogos adicionais para desempate.
     *     Apresenta o resumo dos jogos e pontuações ao utilizador para revisão e aprovação.
     *     Se aprovado, salva os resultados na base de dados, atribuindo medalhas conforme a classificação final.
     * Em caso de erro, exibe uma mensagem de alerta informando o problema.
     *
     * @param modalidade o objeto {@link Modalidade} correspondente à modalidade do evento.
     * @param eventoID o ID do evento cujas equipas e resultados devem ser processados.
     */
    private void gerarResultadosMultiple(Modalidade modalidade, int eventoID) {
        try {
            ConnectionBD conexaoBD = ConnectionBD.getInstance();
            Connection conexao = conexaoBD.getConexao();

            ModalidadeDAOImp modalidadeDAOImp = new ModalidadeDAOImp(conexao);
            ResultadosModalidadeDAOImp resultadosModalidadeDAOImp = new ResultadosModalidadeDAOImp(conexao);

            Map<Integer, String> equipas = modalidadeDAOImp.getEquipasPorEvento(eventoID, modalidade.getId());

            Dialog<ButtonType> resultadosDialog = new Dialog<>();
            resultadosDialog.setTitle("Resultados Finais");

            GridPane resultadosGrid = new GridPane();
            resultadosGrid.setHgap(20);
            resultadosGrid.setVgap(10);
            resultadosGrid.setStyle("-fx-padding: 20; -fx-alignment: center-left;");

            resultadosGrid.add(new Label("Equipa"), 0, 0);
            resultadosGrid.add(new Label("Pontos"), 1, 0);

            Map<Integer, Integer> pontosEquipas = new HashMap<>();
            for (Integer equipaID : equipas.keySet()) {
                pontosEquipas.put(equipaID, 0);
            }

            Random random = new Random();
            StringBuilder resultadosJogos = new StringBuilder();

            List<Integer> equipaIDs = new ArrayList<>(equipas.keySet());
            for (int i = 0; i < equipaIDs.size(); i++) {
                for (int j = i + 1; j < equipaIDs.size(); j++) {
                    Integer equipa1 = equipaIDs.get(i);
                    Integer equipa2 = equipaIDs.get(j);

                    int resultadoEquipa1 = random.nextInt(5);
                    int resultadoEquipa2 = random.nextInt(5);

                    if (resultadoEquipa1 > resultadoEquipa2) {
                        pontosEquipas.merge(equipa1, 3, Integer::sum);
                    } else if (resultadoEquipa1 < resultadoEquipa2) {
                        pontosEquipas.merge(equipa2, 3, Integer::sum);
                    } else {
                        pontosEquipas.merge(equipa1, 1, Integer::sum);
                        pontosEquipas.merge(equipa2, 1, Integer::sum);
                    }

                    resultadosJogos.append(String.format("Jogo: %s %d x %d %s%n",
                            equipas.get(equipa1), resultadoEquipa1, resultadoEquipa2, equipas.get(equipa2)));
                }
            }

            boolean empates = true;
            while (empates) {
                Map<Integer, List<Integer>> pontosEmpatados = pontosEquipas.entrySet().stream()
                        .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));

                empates = false;
                for (Map.Entry<Integer, List<Integer>> entry : pontosEmpatados.entrySet()) {
                    if (entry.getValue().size() > 1) {
                        List<Integer> equipasEmpatadas = entry.getValue();
                        empates = true;

                        // Resolver o empate com jogos adicionais
                        for (int i = 0; i < equipasEmpatadas.size(); i++) {
                            for (int j = i + 1; j < equipasEmpatadas.size(); j++) {
                                Integer equipa1 = equipasEmpatadas.get(i);
                                Integer equipa2 = equipasEmpatadas.get(j);

                                int resultadoEquipa1 = random.nextInt(5);
                                int resultadoEquipa2 = random.nextInt(5);

                                if (resultadoEquipa1 > resultadoEquipa2) {
                                    pontosEquipas.merge(equipa1, 1, Integer::sum);
                                } else if (resultadoEquipa1 < resultadoEquipa2) {
                                    pontosEquipas.merge(equipa2, 1, Integer::sum);
                                }

                                resultadosJogos.append(String.format("Desempate: %s %d x %d %s%n",
                                        equipas.get(equipa1), resultadoEquipa1, resultadoEquipa2, equipas.get(equipa2)));
                            }
                        }
                    }
                }
            }

            Alert resultadosJogosAlert = new Alert(Alert.AlertType.INFORMATION);
            resultadosJogosAlert.setTitle("Resultados dos Jogos");
            resultadosJogosAlert.setHeaderText("Aqui estão os resultados de todos os jogos:");
            resultadosJogosAlert.setContentText(resultadosJogos.toString());
            resultadosJogosAlert.showAndWait();

            List<Map.Entry<Integer, Integer>> sortedPontos = pontosEquipas.entrySet().stream()
                    .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                    .collect(Collectors.toList());

            for (Map.Entry<Integer, Integer> entry : sortedPontos) {
                Integer equipaID = entry.getKey();
                Integer pontos = entry.getValue();
                int row = resultadosGrid.getRowCount();
                resultadosGrid.add(new Label(equipas.get(equipaID)), 0, row);
                resultadosGrid.add(new Label(pontos.toString()), 1, row);
            }

            resultadosDialog.getDialogPane().setContent(resultadosGrid);
            resultadosDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            Optional<ButtonType> dialogResult = resultadosDialog.showAndWait();
            if (dialogResult.isPresent() && dialogResult.get() == ButtonType.OK) {
                for (int i = 0; i < sortedPontos.size(); i++) {
                    Integer equipaID = sortedPontos.get(i).getKey();
                    Integer pontos = sortedPontos.get(i).getValue();

                    int ouro = 0;

                    int prata = 0;

                    int bronze = 0;

                    String medalha = "Nenhuma";
                    if (i == 0) {
                        ouro++;
                        medalha = "Ouro";
                    } else if (i == 1) {
                        prata++;
                        medalha = "Prata";
                    } else if (i == 2) {
                        bronze++;
                        medalha = "Bronze";
                    }

                    ResultadosModalidade resultado = new ResultadosModalidade(0, new Date(), (double) pontos, modalidade.getMedida(), medalha, modalidade.getId(), 0, equipaID);

                    resultadosModalidadeDAOImp.save(resultado);

                        if (i > 2) {
                            medalha = "Diploma";
                        }

                    EventosDAOImp eventosDAOImp = new EventosDAOImp(conexao);

                    Evento evento = eventosDAOImp.getById(eventoID);

                    EquipaDAOImp equipaDAOImp = new EquipaDAOImp(conexao);

                        equipaDAOImp.saveHistorico(equipaID, eventoID, new ParticipaçõesEquipa(evento.getAno_edicao(), medalha));

                    ListarEquipasDAOImp listarEquipasDAOImp = new ListarEquipasDAOImp(conexao);

                    AtletaDAOImp atletaDAOImp = new AtletaDAOImp(conexao);

                    List<AtletaInfo> lstAtletaInfo = listarEquipasDAOImp.getAtletasByEquipaId(equipaID);

                    if(lstAtletaInfo != null && !lstAtletaInfo.isEmpty()) {

                        for (AtletaInfo atletaInfo : lstAtletaInfo) {
                            atletaDAOImp.saveHistorico(atletaInfo.getId(), eventoID, new ParticipaçõesAtleta(evento.getAno_edicao(), ouro, prata, bronze));
                        }
                    }
                }

                modalidadeDAOImp.updateEventos_ModalidadesStatus(eventoID, modalidade.getId(), 1);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Resultados aceites foram salvos com sucesso.");
                alert.show();
            } else if (dialogResult.isPresent() && dialogResult.get() == ButtonType.CANCEL) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Deseja rejeitar os resultados?");
                Optional<ButtonType> confirmationResult = alert.showAndWait();
            }
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erro ao gerar resultados: " + ex.getMessage());
            alert.show();
        }
    }
}
