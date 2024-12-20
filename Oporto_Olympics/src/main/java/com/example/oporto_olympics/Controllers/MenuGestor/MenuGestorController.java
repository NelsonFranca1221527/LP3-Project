package com.example.oporto_olympics.Controllers.MenuGestor;

import com.example.oporto_olympics.Misc.RedirecionarHelper;
import com.example.oporto_olympics.Singleton.AtletaSingleton;
import com.example.oporto_olympics.Singleton.GestorSingleton;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Controlador da interface de utilizador do menu do Gestor. Esta classe é responsável por gerir as ações e interações
 * realizadas no menu do Gestor, incluindo navegação para outras partes da aplicação e manipulação de dados.
 */
public class MenuGestorController {
    /**
     * Botão para aceder à secção de atletas.
     */
    @FXML
    private Button AtletasBtn;
    /**
     * Botão para aceder à secção de inscrições.
     */
    @FXML
    private Button InscricoesBtn;
    /**
     * Botão para aceder à secção de equipas.
     */
    @FXML
    private Button EquipasBtn;
    /**
     * Botão para aceder à secção de eventos.
     */
    @FXML
    private Button EventoBtn;
    /**
     * Botão para aceder à secção de locais.
     */
    @FXML
    private Button LocalBtn;
    /**
     * Botão para inserir novos atletas.
     */
    @FXML
    private Button InsertAtletasBtn;
    /**
     * Botão para aceder à secção de modalidades.
     */
    @FXML
    private Button ModalidadeBtn;
    /**
     * Botão para terminar a sessão e sair.
     */
    @FXML
    private Button SairBtn;
    /**
     * Botão para inserir novos eventos.
     */
    @FXML
    private Button InsertEventoBtn;
    /**
     * Botão para inserir novas modalidades.
     */
    @FXML
    private Button InsertModalidadeBtn;
    /**
     * Botão para listar os ficheiros XML.
     */
    @FXML
    private Button btnFXML;
    /**
     * Botão para listar os pedidos de inscrição em um Evento.
     */
    @FXML
    private Button InscricoesBtnEvento;
    /**
     * Botão para aceder à secção de importação de dados via XML.
     */
    @FXML
    private Button XMLBtn;
    /**
     * Botão para inserir novas equipas.
     */
    @FXML
    private Button InsertEquipasBtn;
    /**
     * Botão para aceder ao perfil do utilizador.
     */
    @FXML
    private Button PerfilBtn;
    @FXML
    private Button ResultadosBtn;
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de equipas.
     */
    @FXML
    protected void OnInsertEquipasButtonClick(){
        try {
            Stage s = (Stage) InsertEquipasBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirEquipa().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de modalidades.
     */
    @FXML
    protected void OnInsertModalidadesButtonClick(){
        try {
            Stage s = (Stage) InsertModalidadeBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirModalidade().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de eventos.
     */
    @FXML
    protected void OnInsertEventosButtonClick(){
        try {
            Stage s = (Stage) InsertEventoBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirEventosOlimpicos().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inserção de atletas.
     */
    @FXML
    protected void OnInsertAtletasButtonClick(){
        try {
            Stage s = (Stage) InsertAtletasBtn.getScene().getWindow();

            RedirecionarHelper.GotoInserirAtleta().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de inscrições.
     */
    @FXML
    protected void OnInscricoesButtonClick(){
        try {
            Stage s = (Stage) InscricoesBtn.getScene().getWindow();

            RedirecionarHelper.GotoInscricoesEquipa().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de atletas.
     */
    @FXML
    protected void OnAtletasButtonClick() {
        try {
            Stage s = (Stage) AtletasBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemAtleta().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de equipas.
     */
    @FXML
    protected void OnEquipasButtonClick() {
        try {
            Stage s = (Stage) EquipasBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemEquipas().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de eventos.
     */
    @FXML
    protected void OnEventosButtonClick() {
        try {
            Stage s = (Stage) EventoBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemEventos().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de seleção de XML.
     */
    @FXML
    protected void OnInserirXMLButtonClick() {
        try {
            Stage s = (Stage) XMLBtn.getScene().getWindow();

            RedirecionarHelper.GotoSeleçãoXML().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de locais.
     */
    @FXML
    protected void OnLocaisButtonClick( ) {
        try {
            Stage s = (Stage) LocalBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemLocais().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para a secção de listagem de modalidades.
     */
    @FXML
    protected void OnModalidadesButtonClick() {
        try {
            Stage s = (Stage) ModalidadeBtn.getScene().getWindow();

            RedirecionarHelper.GotoListagemModalidades().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para o perfil pessoal.
     */
    @FXML
    protected void OnPerfilButtonCLick() {
        try {
            Stage s = (Stage) PerfilBtn.getScene().getWindow();

            RedirecionarHelper.GotoDadosPessoaisGestor().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para redirecionar o utilizador para os resultados.
     */
    @FXML
    protected void OnVerResultadosButtonClick(){
        try{
            Stage s = (Stage) ResultadosBtn.getScene().getWindow();

            RedirecionarHelper.GotoVerResultados().switchScene(s);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    /**
     * Ação para terminar a sessão e redirecionar o utilizador para o ecrã de login.
     */
    @FXML
    protected void OnSairButtonClick() {
        try {
            Stage s = (Stage) SairBtn.getScene().getWindow();

            RedirecionarHelper.GotoLogin().switchScene(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void OnListarXMLButtonClick(){
        try{
            Stage s = (Stage) btnFXML.getScene().getWindow();

            RedirecionarHelper.GotoListarXML().switchScene(s);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void OnInscricoesEventoButtonClick(){
        try{
            Stage s = (Stage) InscricoesBtnEvento.getScene().getWindow();

            RedirecionarHelper.GotoAprovarAtletaEvento().switchScene(s);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

}