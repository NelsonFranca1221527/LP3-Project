package com.example.oporto_olympics.Misc;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


/**
 * A classe {@link AlertHandler} é uma classe personalizada para facilitar a criação e gestão de alertas na aplicação.
 * Ela permite criar alertas com diferentes tipos de botões e personalizar o conteúdo, título e tipo do alerta.
 */
public class AlertHandler {

    private Alert alert;
    private Alert.AlertType alertType;

    public static ButtonType yesButton = new ButtonType("Sim");
    public static ButtonType noButton = new ButtonType("Não");

    /**
     * Constrói um objeto {@link Alert} com o tipo de alerta especificado, título e conteúdo.
     *
     * @param alertType o tipo de alerta (por exemplo, {@link Alert.AlertType#ERROR}, {@link Alert.AlertType#INFORMATION}, etc.)
     * @param title     o título do alerta
     * @param content   o conteúdo do alerta
     */
    public AlertHandler(Alert.AlertType alertType, String title, String content) {
        this.alert = new Alert(alertType, content);
        this.alert.setTitle(title);
        this.alert.setHeaderText(null);
        this.alert.setGraphic(null);
    }

    /**
     * Constrói um objeto {@link Alert} com o tipo de alerta especificado, título, conteúdo e botões personalizados.
     *
     * @param alertType o tipo de alerta (por exemplo, {@link Alert.AlertType#CONFIRMATION})
     * @param title     o título do alerta
     * @param content   o conteúdo do alerta
     * @param yesButton o botão personalizado para "Sim"
     * @param noButton  o botão personalizado para "Não"
     */
    public AlertHandler(Alert.AlertType alertType, String title, String content, ButtonType yesButton, ButtonType noButton) {
        this.alert = new Alert(alertType, content, noButton, yesButton);
        this.alert.setTitle(title);
        this.alert.setHeaderText(null);
        this.alert.setGraphic(null);
    }

    /**
     * Constrói uma nova instância vazia da classe {@link AlertHandler}.
     */
    public AlertHandler() {
    }

    /**
     * Retorna o objeto {@link Alert} associado a este {@link AlertHandler}.
     *
     * @return o objeto {@link Alert}
     */
    public Alert getAlert() {
        return alert;
    }

    /**
     * Define o objeto {@link Alert} para este {@link AlertHandler}.
     *
     * @param alert o objeto {@link Alert} a ser definido
     */
    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    /**
     * Retorna o tipo de alerta associado a este {@link AlertHandler}.
     *
     * @return o tipo de alerta (por exemplo, {@link Alert.AlertType#ERROR})
     */
    public Alert.AlertType getAlertType() {
        return alertType;
    }

    /**
     * Define o tipo de alerta para este {@link AlertHandler}.
     *
     * @param alertType o tipo de alerta a ser definido
     */
    public void setAlertType(Alert.AlertType alertType) {
        this.alertType = alertType;
    }


    /**
     * Retorna uma representação em forma de string do objeto {@link AlertHandler}.
     *
     * @return uma string que representa o estado do objeto
     */
    @Override
    public String toString() {
        return "AlertHandler{" +
                "alert=" + alert.toString() +
                ", alertType=" + alertType +
                '}';
    }
}
