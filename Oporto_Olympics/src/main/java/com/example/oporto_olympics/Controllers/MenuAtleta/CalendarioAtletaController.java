package com.example.oporto_olympics.Controllers.MenuAtleta;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.Controllers.MenuAtleta.CardController.CalendarioModalidadeCardController;
import com.example.oporto_olympics.DAO.Atleta.CalendarioDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.*;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.util.*;
/**
 * Controlador responsável pela gestão do calendário do atleta.
 *
 * Esta classe é responsável por controlar as interações do utilizador com o calendário do atleta,
 * incluindo a navegação entre os meses, a exibição dos eventos do atleta no calendário e a
 * interação com a base de dados para obter os eventos. Ela contém métodos para atualizar o calendário,
 * avançar ou retroceder entre os meses e redirecionar para o menu principal.
 */
public class CalendarioAtletaController {

    /**
     * Botão utilizado para voltar ao menu principal.
     */
    @FXML
    private Button VoltarButton;
    /**
     * Botão utilizado para navegar para o mês anterior.
     */
    @FXML
    private Button previousMonthButton;
    /**
     * Botão utilizado para navegar para o próximo mês.
     */
    @FXML
    private Button nextMonthButton;
    /**
     * Rótulo que exibe o mês e ano atuais no calendário.
     */
    @FXML
    private Label monthYearLabel;
    /**
     * Grade onde os dias do mês são exibidos.
     */
    @FXML
    private GridPane calendarGrid;
    /**
     * O atleta atual logado e associado ao calendário.
     */
    private Atleta atleta;
    /**
     * O mês e ano atuais, utilizados para calcular o calendário.
     */
    private YearMonth currentMonth;

    /**
     * Objeto de acesso a dados (DAO) utilizado para interagir com a base de dados e obter eventos.
     */
    private CalendarioDAOImp dao;
    /**
     * Inicializa o controlador, configurando a conexão com a base de dados e os dados do atleta.
     *
     * Este método é chamado automaticamente quando a classe é carregada. Ele obtém o atleta logado
     * utilizando o padrão Singleton, inicializa a conexão com a base de dados e o objeto DAO
     * responsável pelas interações com a base. Além disso, define o mês atual e atualiza o calendário
     * com os eventos do atleta para o mês em questão.
     *
     * Caso ocorra algum erro durante a inicialização, será exibida uma mensagem de erro ao utilizador.
     */
    @FXML
    public void initialize() {
        // Obtém o atleta logado via Singleton
        AtletaSingleton atletaSingleton = AtletaSingleton.getInstance();
        atleta = atletaSingleton.getAtleta();

        try {

            Connection conexao = ConnectionBD.getInstance().getConexao();
            dao = new CalendarioDAOImp(conexao);
            currentMonth = YearMonth.now();
            atualizarCalendario(atleta.getId());
        } catch (Exception e) {
            mostrarErro("Erro ao inicializar", e.getMessage());
        }
    }
    /**
     * Manipula o clique no botão para avançar para o mês anterior no calendário.
     *
     * Este método é chamado quando o utilizador clica no botão para avançar para o mês anterior.
     */
    @FXML
    private void onPreviousMonthClick() {
        currentMonth = currentMonth.minusMonths(1);
        atualizarCalendario(atleta.getId());
    }
    /**
     * Manipula o clique no botão para avançar para o próximo mês no calendário.
     *
     * Este método é chamado quando o utilizador clica no botão para avançar para o mês seguinte.
     */
    @FXML
    private void onNextMonthClick() {
        currentMonth = currentMonth.plusMonths(1);
        atualizarCalendario(atleta.getId());
    }
    /**
     * Manipula o clique no botão "Voltar" para redirecionar o utilizador para o menu principal.
     *
     * Este método é chamado quando o utilizador clica no botão "Voltar". Ele tenta fechar a janela
     * atual e redirecionar o utilizador para o menu principal do atleta, utilizando a classe
     * {@link RedirecionarHelper}. Caso ocorra algum erro durante o processo, será exibida uma mensagem de erro
     * ao utilizador.
     */
    @FXML
    protected void OnClickVoltarButton() {
        try {
            Stage stage = (Stage) VoltarButton.getScene().getWindow();
            RedirecionarHelper.GotoMenuPrincipalAtleta().switchScene(stage);
        } catch (Exception e) {
            mostrarErro("Erro ao voltar", e.getMessage());
        }
    }
    /**
     * Atualiza o calendário para o mês e ano atual, exibindo os eventos do atleta.
     *
     * Este método atualiza o calendário exibido na interface gráfica, configurando o título
     * do mês e ano, e adicionando os dias da semana e os dias do mês. Além disso, verifica se
     * existem eventos para o atleta no mês atual e, caso haja, exibe um botão "Ver Inscrição"
     * nos dias correspondentes aos eventos. O utilizador pode clicar no botão para visualizar detalhes
     * sobre o evento.
     *
     * @param atletaId o identificador único do atleta, utilizado para obter os eventos associados.
     */
    private void atualizarCalendario(int atletaId) {

        monthYearLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());
        calendarGrid.getChildren().clear();
        adicionarDiasDaSemana();


        LocalDate firstOfMonth = currentMonth.atDay(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        if (dayOfWeek == 7) dayOfWeek = 0;

        int daysInMonth = currentMonth.lengthOfMonth();
        int row = 1;
        int col = dayOfWeek;


        List<EventosModalidade> eventos = obterEventos(atletaId);

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentMonth.atDay(day);
            StackPane dayPane = criarDiaPane(day);

            for (EventosModalidade evento : eventos) {
                LocalDate eventDate = converterDataParaLocalDate(evento.getData_modalidade());
                if (eventDate != null && eventDate.equals(date)) {
                    Button eventButton = new Button("Ver Inscrição");
                    eventButton.setStyle("-fx-font-size: 12px; -fx-text-fill: white; -fx-background-color: #007bff; -fx-cursor: hand;");
                    eventButton.setOnAction(e -> abrirDetalhesEvento(eventDate, atletaId));

                    StackPane.setAlignment(eventButton, javafx.geometry.Pos.CENTER);
                    dayPane.getChildren().add(eventButton);
                }
            }

            calendarGrid.add(dayPane, col, row);

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }
    }
    /**
     * Abre uma janela pop-up com os detalhes de um evento e suas modalidades associadas.
     *
     * Este método busca as informações de um evento, suas modalidades e os detalhes relacionados
     * (como data e duração) a partir de seus respectivos IDs. Em seguida, exibe essas informações
     * em uma janela pop-up. Caso algum dos dados não seja encontrado ou ocorra um erro, uma
     * mensagem de erro será exibida.
     *
     */
    private void abrirDetalhesEvento(LocalDate dataOriginal, int atletaId) {
        try {
            // Converter dataOriginal para o início do dia em Timestamp
            Timestamp firstData = Timestamp.valueOf(dataOriginal.atStartOfDay());

            // Adicionar um dia e converter para o início do próximo dia em Timestamp
            Timestamp secondData = Timestamp.valueOf(dataOriginal.plusDays(1).atStartOfDay());

            List<EventosModalidade> eventosModalidade = dao.getEventosDataAtleta(firstData, secondData, atletaId);

            if (eventosModalidade != null && !eventosModalidade.isEmpty()) {
                // Criar o Stage para o popup
                Stage popupStage = new Stage();
                popupStage.initModality(Modality.APPLICATION_MODAL);
                popupStage.setTitle("Detalhes do Evento");

                // Criar o VBox para conter os cards
                VBox vbox = new VBox(10);
                vbox.setPadding(new javafx.geometry.Insets(10));

                // Criar o Pane que será usado dentro do ScrollPane
                Pane pane = new Pane();
                pane.setPrefWidth(380); // Ajustar largura conforme necessário

                // Adicionar o VBox ao Pane
                pane.getChildren().add(vbox);

                // Criar o ScrollPane e adicionar o Pane
                ScrollPane scrollPane = new ScrollPane();
                scrollPane.setContent(pane);
                scrollPane.setFitToWidth(true);
                scrollPane.setPannable(true);

                for (EventosModalidade em : eventosModalidade) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/oporto_olympics/Views/Atleta/Cards/CalendarioModalidadeCard.fxml"));
                        Pane card = loader.load();
                        CalendarioModalidadeCardController cardController = loader.getController();
                        cardController.PreencherDados(em);

                        card.setUserData(em);
                        vbox.getChildren().add(card);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                // Configurar e exibir o popup
                Scene scene = new Scene(scrollPane, 820, 400);
                popupStage.setScene(scene);
                popupStage.showAndWait();
            } else {
                mostrarErro("Erro", "Evento, Modalidade ou Detalhes não encontrados.");
            }
        } catch (Exception e) {
            mostrarErro("Erro ao abrir detalhes do evento", e.getMessage());
        }
    }


    /**
     * Obtém a lista de eventos associados a um atleta específico.
     *
     * Este método consulta as modalidades relacionadas a um atleta, identificadas pelo seu ID,
     * e retorna uma lista de eventos associados a essas modalidades. Caso ocorra algum erro
     * durante a execução, uma mensagem de erro é exibida ao utilizador.
     *
     * @param atletaId o identificador único do atleta cujos eventos serão buscados.
     * @return uma lista de {@link EventosModalidade} representando os eventos associados
     *         ao atleta. A lista estará vazia se nenhuma modalidade ou evento for encontrado,
     *         ou se ocorrer algum erro durante a consulta.
     * @throws RuntimeException se ocorrer um erro inesperado durante a obtenção de dados.
     */
    private List<EventosModalidade> obterEventos(int atletaId) {
        List<EventosModalidade> eventos = new ArrayList<>();
        try {

            List<AtletasModalidades> modalidades = dao.getAtletaModalidade(atletaId);
            for (AtletasModalidades modalidade : modalidades) {

                eventos.addAll(dao.getEventosAtleta(modalidade.getEvento_id(), modalidade.getModalidade_id()));
            }
        } catch (Exception e) {
            mostrarErro("Erro ao buscar eventos", e.getMessage());
        }
        return eventos;
    }
    /**
     * Adiciona os dias da semana na primeira linha do calendário.
     *
     * Este método preenche a primeira linha de uma grade de calendário com os nomes dos
     * dias da semana. Cada dia é representado por um {@link StackPane} contendo um
     * {@link Label} estilizado. Os nomes dos dias são exibidos no formato abreviado
     * de acordo com o idioma padrão do sistema ({@link Locale#getDefault()}).
     *
     * O estilo dos elementos inclui bordas e alinhamento, e cada célula tem um tamanho
     * predefinido de 100x50 pixels.
     */
    private void adicionarDiasDaSemana() {

        DayOfWeek[] daysOfWeek = DayOfWeek.values();
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i].getDisplayName(java.time.format.TextStyle.SHORT, Locale.getDefault()));
            dayLabel.setStyle("-fx-font-weight: bold; -fx-alignment: center; -fx-font-size: 14px;");
            StackPane dayPane = new StackPane(dayLabel);
            dayPane.setStyle("-fx-alignment: center; -fx-border-color: black; -fx-border-width: 1;");
            dayPane.setPrefSize(100, 50);
            calendarGrid.add(dayPane, i, 0);
        }
    }
    /**
     * Cria um painel de interface gráfica (GUI) para representar um dia específico.
     *
     * Este método cria e retorna um objeto {@link StackPane} que representa um dia,
     * com dimensões predefinidas e estilo visual. O painel contém uma etiqueta
     * ({@link Label}) exibindo o número do dia especificado.
     *
     * @param day o número do dia a ser exibido no painel.
     * @return um {@link StackPane} configurado para representar o dia especificado.
     */
    private StackPane criarDiaPane(int day) {
        StackPane pane = new StackPane();
        pane.setPrefSize(70, 70);
        pane.setStyle("-fx-border-color: black; -fx-alignment: center;");
        Label dayLabel = new Label(String.valueOf(day));
        StackPane.setAlignment(dayLabel, javafx.geometry.Pos.TOP_LEFT);
        pane.getChildren().add(dayLabel);
        return pane;
    }
    /**
     * Converte uma data do tipo {@link java.util.Date} para {@link java.time.LocalDate}.
     *
     * Este método recebe um objeto {@link java.util.Date} e converte-o para um
     * objeto {@link java.time.LocalDate}, preservando a informação de data.
     * Caso a entrada seja nula, o método retorna {@code null}.
     *
     * @param date o objeto {@link java.util.Date} a ser convertido.
     *             Pode ser {@code null}.
     * @return o objeto {@link java.time.LocalDate} equivalente ou {@code null}
     *         se a entrada for {@code null}.
     */
    private LocalDate converterDataParaLocalDate(java.util.Date date) {
        if (date == null) return null;
        return new java.sql.Date(date.getTime()).toLocalDate();
    }
    /**
     * Exibe uma mensagem de erro numa janela de alerta.
     *
     * Este método cria e apresenta uma janela de alerta do tipo "Erro" com um título e uma mensagem
     * especificados. O alerta é exibido de forma modal, pausando a execução do programa até
     * que o utilizador interaja com o alerta.
     *
     * @param titulo   o título a ser exibido no cabeçalho do alerta.
     * @param mensagem a mensagem de erro a ser exibida no corpo do alerta.
     *
     * @throws IllegalArgumentException se o título ou a mensagem forem nulos.
     */
    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem);
        alert.setHeaderText(titulo);
        alert.showAndWait();
    }
}
