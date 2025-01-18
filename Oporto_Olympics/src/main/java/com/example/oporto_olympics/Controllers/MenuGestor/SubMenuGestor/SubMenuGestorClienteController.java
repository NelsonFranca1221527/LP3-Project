package com.example.oporto_olympics.Controllers.MenuGestor.SubMenuGestor;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SubMenuGestorClienteController {
    /**
     * Botão para listar clientes.
     */
    @FXML
    private Button ClientesBtn;
    /**
     * Botão para criar clientes.
     */
    @FXML
    private Button CriarClienteBtn;
    /**
     * Botão para voltar.
     */
    @FXML
    private Button VoltarBtn;

    /**
     * Manipula o evento de clique no botão "Criar Cliente".
     *
     *
     * @throws RuntimeException se ocorrer um erro ao carregar o ficheiro FXML
     *                          ou ao aplicar a nova cena.
     */
    @FXML
    protected void OnCriarClienteButtonClick() {
        try {
            Stage s = (Stage) CriarClienteBtn.getScene().getWindow();

            //Adicionar Redirecionar
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Manipula o evento de clique no botão "Listar Cliente".
     *
     *
     * @throws RuntimeException se ocorrer um erro ao carregar o ficheiro FXML
     *                          ou ao aplicar a nova cena.
     */
    @FXML
    protected void OnListClientessButtonClick() {
        try {
            Stage s = (Stage) ClientesBtn.getScene().getWindow();

            //Adicionar Redirecionar
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Manipula o evento de clique no botão "Voltar".
     *
     * Redireciona o utilizador para o submenu do cliente
     *
     * @throws RuntimeException se ocorrer um erro ao carregar o ficheiro FXML
     *                          ou ao aplicar a nova cena.
     */
    @FXML
    protected void OnVoltarButtonClick() {
        try {
            Stage s = (Stage) VoltarBtn.getScene().getWindow();

            RedirecionarHelper.GotoSubMenuCliente().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
