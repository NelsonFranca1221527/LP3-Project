package com.example.oporto_olympics.Controllers.MenuAtleta;

import com.example.oporto_olympics.ConnectBD.ConnectionBD;
import com.example.oporto_olympics.DAO.Atleta.CalendarioDAOImp;
import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Models.Atleta;
import com.example.oporto_olympics.Models.AtletasModalidades;
import com.example.oporto_olympics.Models.EventosModalidade;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

public class CalendarioAtletaController {

    @FXML
    private Button Voltar;

    @FXML
    private Button previousMonthButton;

    @FXML
    private Button nextMonthButton;

    @FXML
    private Label monthYearLabel;

    @FXML
    private GridPane calendarGrid;

    private Atleta atleta;
    private YearMonth currentMonth;
    private CalendarioDAOImp dao;

    @FXML
    public void initialize() {
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

    @FXML
    private void onPreviousMonthClick() {
        currentMonth = currentMonth.minusMonths(1);
        atualizarCalendario(atleta.getId());
    }

    @FXML
    private void onNextMonthClick() {
        currentMonth = currentMonth.plusMonths(1);
        atualizarCalendario(atleta.getId());
    }

    @FXML
    protected void OnClickVoltarButton() {
        try {
            Stage stage = (Stage) Voltar.getScene().getWindow();
            RedirecionarHelper.GotoMenuPrincipalAtleta().switchScene(stage);
        } catch (Exception e) {
            mostrarErro("Erro ao voltar", e.getMessage());
        }
    }

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
                    Label eventLabel = new Label("Evento");
                    eventLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                    dayPane.getChildren().add(eventLabel);
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

    private StackPane criarDiaPane(int day) {
        StackPane pane = new StackPane();
        pane.setPrefSize(70, 70);
        pane.setStyle("-fx-border-color: black; -fx-alignment: top-left;");
        Label dayLabel = new Label(String.valueOf(day));
        pane.getChildren().add(dayLabel);
        return pane;
    }

    private LocalDate converterDataParaLocalDate(java.util.Date date) {
        if (date == null) return null;
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem);
        alert.setHeaderText(titulo);
        alert.showAndWait();
    }
}
