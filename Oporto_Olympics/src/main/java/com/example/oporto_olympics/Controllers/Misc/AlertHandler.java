package com.example.oporto_olympics.Controllers.Misc;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;


/**
 * The class {@link AlertHandler} is a custom class to ease the instation of an alert on the app.
 */
public class AlertHandler {

    private Alert alert;
    private Alert.AlertType alertType;

    public static ButtonType yesButton = new ButtonType("Sim");
    public static ButtonType noButton = new ButtonType("Não");

    /**
     * Constructs an {@link Alert} object with the specified AlertType, title, and content.
     *
     * @param alertType the type of the alert
     * @param title     the title of the alert
     * @param content   the content of the alert
     */
    public AlertHandler(Alert.AlertType alertType, String title, String content) {
        this.alert = new Alert(alertType, content);
        this.alert.setTitle(title);
        this.alert.setHeaderText(null);
        this.alert.setGraphic(null);
    }

    /**
     * Constructs an {@link Alert} object with the specified AlertType, title, content, and button types.
     *
     * @param alertType the type of the alert
     * @param title     the title of the alert
     * @param content   the content of the alert
     * @param yesButton the button type for the "Yes" button
     * @param noButton  the button type for the "No" button
     */
    public AlertHandler(Alert.AlertType alertType, String title, String content, ButtonType yesButton, ButtonType noButton) {
        this.alert = new Alert(alertType, content, noButton, yesButton);
        this.alert.setTitle(title);
        this.alert.setHeaderText(null);
        this.alert.setGraphic(null);
    }

    /**
     * Constructs a new empty instance of the {@link AlertHandler} class.
     */
    public AlertHandler() {
    }

    /**
     * Returns the Alert object.
     *
     * @return the Alert object
     */
    public Alert getAlert() {
        return alert;
    }

    /**
     * Sets the Alert object.
     *
     * @param alert the Alert object to set
     */
    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    /**
     * Returns the AlertType.
     *
     * @return the AlertType
     */
    public Alert.AlertType getAlertType() {
        return alertType;
    }

    /**
     * Sets the AlertType.
     *
     * @param alertType the AlertType to set
     */
    public void setAlertType(Alert.AlertType alertType) {
        this.alertType = alertType;
    }


    /**
     * Returns a string representation of the {@link AlertHandler} object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "AlertHandler{" +
                "alert=" + alert.toString() +
                ", alertType=" + alertType +
                '}';
    }
}
