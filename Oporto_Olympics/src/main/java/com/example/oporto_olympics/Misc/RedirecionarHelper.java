package com.example.oporto_olympics.Misc;

import com.example.oporto_olympics.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * A classe {@link RedirecionarHelper} é responsável por ajudar na navegação entre diferentes cenários (views) na aplicação JavaFX.
 * Ela simplifica o processo de redirecionamento para diferentes páginas da aplicação, permitindo a configuração do título da janela e o carregamento da cena correspondente.
 */
public class RedirecionarHelper {


    private String url;
    private String titulo;
    /**
     * Constrói um objeto {@link RedirecionarHelper} com a URL da view e o título da janela.
     *
     * @param url    A URL da view a ser carregada (por exemplo, "Views/Login.fxml")
     * @param titulo O título a ser exibido na janela
     */
    public RedirecionarHelper(String url, String titulo) {
        this.url = url;
        this.titulo = titulo;
    }
    /**
     * Muda a cena (view) atual para a cena especificada pela URL e título fornecidos.
     * Este método carrega o ficheiro FXML da view correspondente e atualiza a janela principal.
     *
     * @param stage O estágio (janela) onde a cena será aplicada.
     */
    public void switchScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(url));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(titulo);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para o subMenu das Listagens.
     *
     * @return Um {@link RedirecionarHelper} para o subMenu das Listagens.
     */
    public static RedirecionarHelper GotoSubMenuListagens(){return new RedirecionarHelper("Views/MenuPrincipal-Gestor/Sub-menus/SubMenuGestor-Listagens.fxml","Listagens");}
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para o subMenu das Inserções.
     *
     * @return Um {@link RedirecionarHelper} para o subMenu das Inserções.
     */
    public static RedirecionarHelper GotoSubMenuInsercoes(){return new RedirecionarHelper("Views/MenuPrincipal-Gestor/Sub-menus/SubMenuGestor-Insercoes.fxml","Inserções");}
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para o subMenu do XML.
     *
     * @return Um {@link RedirecionarHelper} para o subMenu do XML.
     */
    public static RedirecionarHelper GotoSubMenuXML(){return new RedirecionarHelper("Views/MenuPrincipal-Gestor/Sub-menus/SubMenuGestor-XML.fxml","XML");}
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para o subMenu do cliente.
     *
     * @return Um {@link RedirecionarHelper} para a subMenu do Cliente.
     */
    public static RedirecionarHelper GotoSubMenuCliente(){return new RedirecionarHelper("Views/MenuPrincipal-Gestor/Sub-menus/SubMenuGestor-Cliente.fxml","Cliente");}
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de login.
     *
     * @return Um {@link RedirecionarHelper} para a página de login.
     */
    public static RedirecionarHelper GotoLogin() {
        return new RedirecionarHelper("Views/Login.fxml", "Login");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de dados pessoais.
     *
     * @return Um {@link RedirecionarHelper} para a página de dados pessoais.
     */
    public static RedirecionarHelper GotoDadosPessoais() {
        return new RedirecionarHelper("Views/DadosPessoais/VerDadosPessoais.fxml", "Dados Pessoais");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de dados pessoais.
     *
     * @return Um {@link RedirecionarHelper} para a página de dados pessoais.
     */
    public static RedirecionarHelper GotoDadosPessoaisGestor() {
        return new RedirecionarHelper("Views/DadosPessoais/VerDadosPessoaisGestor.fxml", "Dados Pessoais");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de alterar de password.
     *
     * @return Um {@link RedirecionarHelper} para a página de alterar password.
     */
    public static RedirecionarHelper GotoAtlerarPassword() {
        return new RedirecionarHelper("Views/DadosPessoais/AlterarPassword/AlterarPassword.fxml", "Atlerar Password");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para o menu principal do gestor.
     *
     * @return Um {@link RedirecionarHelper} para o menu principal do gestor.
     */
    public static RedirecionarHelper GotoMenuPrincipalGestor() {
        return new RedirecionarHelper("Views/MenuPrincipal-Gestor/MenuGestor.fxml", "Menu Principal Gestor");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para o menu principal do atleta.
     *
     * @return Um {@link RedirecionarHelper} para o menu principal do atleta.
     */
    public static RedirecionarHelper GotoMenuPrincipalAtleta() {
        return new RedirecionarHelper("Views/MenuPrincipal-Atleta/MenuAtleta.fxml", "Menu Principal Atleta");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de listagem de atletas.
     *
     * @return Um {@link RedirecionarHelper} para a página de listagem de atletas.
     */
    public static RedirecionarHelper GotoListagemAtleta() {
        return new RedirecionarHelper("Views/ListagemAtletas/ListAtletas.fxml", "Lista de Atletas");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de listagem de modalidades.
     *
     * @return Um {@link RedirecionarHelper} para a página de listagem de modalidades.
     */
    public static RedirecionarHelper GotoListagemModalidades() {
        return new RedirecionarHelper("Views/ListagemModalidades/ListagemModalidades.fxml", "Lista de Modalidades");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de listagem de locais.
     *
     * @return Um {@link RedirecionarHelper} para a página de listagem de locais.
     */
    public static RedirecionarHelper GotoListagemLocais() {
        return new RedirecionarHelper("Views/ListagemLocais/ListagemLocais.fxml", "Lista de Locais");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de listagem de equipas.
     *
     * @return Um {@link RedirecionarHelper} para a página de listagem de equipas.
     */
    public static RedirecionarHelper GotoListagemEquipas() {
        return new RedirecionarHelper("Views/Equipas/ListarEquipas.fxml", "Lista de Equipas");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inserção de eventos olímpicos.
     *
     * @return Um {@link RedirecionarHelper} para a página de inserção de eventos olímpicos.
     */
    public static RedirecionarHelper GotoInserirEventosOlimpicos() {
        return new RedirecionarHelper("Views/EventosOlimpicos/InserirEventosOlimpicos.fxml", "Criar Eventos Olímpicos");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inserção de XML.
     *
     * @return Um {@link RedirecionarHelper} para a página de inserção de XML.
     */
    public static RedirecionarHelper GotoInserçãoXML() {
        return new RedirecionarHelper("Views/ImportacoesXML/InsercaoXML/InsercaoXML.fxml", "Inserir XML");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inserção de local.
     *
     * @return Um {@link RedirecionarHelper} para a página de inserção de local.
     */
    public static RedirecionarHelper GotoInserirLocal() {
        return new RedirecionarHelper("Views/EventosOlimpicos/InserirLocal.fxml", "Inserir Local");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de seleção de XML.
     *
     * @return Um {@link RedirecionarHelper} para a página de seleção de XML.
     */
    public static RedirecionarHelper GotoSeleçãoXML() {
        return new RedirecionarHelper("Views/ImportacoesXML/SelecaoXML/SelecaoXML.fxml", "Selecionar XML");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de listagem de eventos.
     *
     * @return Um {@link RedirecionarHelper} para a página de listagem de eventos.
     */
    public static RedirecionarHelper GotoListagemEventos() {
        return new RedirecionarHelper("Views/ListagemEventos/ListagemEventos.fxml", "Listagem de Eventos");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inserção de modalidade.
     *
     * @return Um {@link RedirecionarHelper} para a página de inserção de modalidade.
     */
    public static RedirecionarHelper GotoInserirModalidade() {
        return new RedirecionarHelper("Views/Modalidades/InserirModalidades.fxml", "Inserir Modalidades");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inserção de equipa.
     *
     * @return Um {@link RedirecionarHelper} para a página de inserção de equipa.
     */
    public static RedirecionarHelper GotoInserirEquipa() {
        return new RedirecionarHelper("Views/Equipas/InserirEquipas.fxml", "Inserir Equipas");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inserção de atleta.
     *
     * @return Um {@link RedirecionarHelper} para a página de inserção de atleta.
     */
    public static RedirecionarHelper GotoInserirAtleta() {
        return new RedirecionarHelper("Views/Atleta/InserirAtleta.fxml", "Inserir Atletas");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inscrições em equipas.
     *
     * @return Um {@link RedirecionarHelper} para a página de inscrições em equipas.
     */
    public static RedirecionarHelper GotoInscricoesEquipa() {
        return new RedirecionarHelper("Views/AprovarInscricaoEquipa/AprovarInscricaoEquipa.fxml", "Inscrições em Equipas");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inscrição em equipas.
     *
     * @return Um {@link RedirecionarHelper} para a página de inscrição em equipas.
     */
    public static RedirecionarHelper GotoInscreverEquipa() {
        return new RedirecionarHelper("Views/InscreverEquipas/InscreverEquipas.fxml", "Inscrever numa Equipas");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de resultados do atleta.
     *
     * @return Um {@link RedirecionarHelper} para a página de resultados do atleta.
     */
    public static RedirecionarHelper GotoVerResultadosAtleta() {
        return new RedirecionarHelper("Views/VerResultados/VerResultadosAtleta.fxml", "Ver Resultados");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de resultados.
     *
     * @return Um {@link RedirecionarHelper} para a página de resultados do atleta.
     */
    public static RedirecionarHelper GotoVerResultados() {
        return new RedirecionarHelper("Views/VerResultados/VerResultados.fxml", "Resultados");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página da listagem dos XMLs.
     *
     * @return Um {@link RedirecionarHelper} para a página da listagem do XML.
     */
    public static RedirecionarHelper GotoListarXML() {
        return new RedirecionarHelper("Views/ListagemXML/ListagemXML.fxml", "Ficheiros XML");
    }
    public static RedirecionarHelper GotoAprovarAtletaEvento() {
        return new RedirecionarHelper("Views/AprovarInscricaoEvento/AprovarInscricaoEvento.fxml", "Atletas Eventos");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inserção de gestor.
     *
     * @return Um {@link RedirecionarHelper} para a página de inserção de gestor.
     */
    public static RedirecionarHelper GotoInserirGestor() {
        return new RedirecionarHelper("Views/Gestor/InserirGestor.fxml", "Inserir Gestores");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página do calendário do Atleta.
     *
     * @return Um {@link RedirecionarHelper} para a página do calendário do Atleta.
     */
    public static RedirecionarHelper GotoCalendario() {
        return new RedirecionarHelper("Views/Atleta/CalendarioAtleta.fxml", "Calendário");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página inicial do Cliente.
     *
     * @return Um {@link RedirecionarHelper} para a página inicial do cliente.
     */
    public static RedirecionarHelper GotoHomeClient() {
        return new RedirecionarHelper("Views/MenuPrincipal-Cliente/MenuPrincipalCliente.fxml", "Home Page");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de inserir um Cliente.
     *
     * @return Um {@link RedirecionarHelper} para a página de inserir um cliente.
     */
    public static RedirecionarHelper GotoInsertClient() {
        return new RedirecionarHelper("Views/Cliente/InserirClient.fxml", "Inserir Cliente");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de listagem de Clientes.
     *
     * @return Um {@link RedirecionarHelper} para a página de listagem de clientes.
     */
    public static RedirecionarHelper GotoListClient() {
        return new RedirecionarHelper("Views/Cliente/ListagemClientes.fxml", "Listagem Clientes");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de listagem de Jogos.
     *
     * @return Um {@link RedirecionarHelper} para a página de listagem de jogos.
     */
    public static RedirecionarHelper GotoListagemJogos() {
        return new RedirecionarHelper("Views/ListagemJogos/ListagemJogos.fxml", "Listagem Jogos");
    }
    /**
     * Retorna um {@link RedirecionarHelper} configurado para redirecionar para a página de listagem de Tickets.
     *
     * @return Um {@link RedirecionarHelper} para a página de listagem de tickets.
     */
    public static RedirecionarHelper GotoListagemTickets() {
        return new RedirecionarHelper("Views/ListagemTickets/ListagemTickets.fxml", "Listagem Tickets");
    }
    /**
     * Redireciona o utilizador para a página de perfil do cliente.
     *
     * @return Uma instância de {@link RedirecionarHelper} configurada para carregar a vista do perfil do cliente.
     */
    public static RedirecionarHelper GotoProfileCliente() {
        return new RedirecionarHelper("Views/Cliente/Perfil.fxml", "Perfil");
    }

    /**
     * Redireciona o utilizador para a página de alteração de palavra-passe.
     *
     * @return Uma instância de {@link RedirecionarHelper} configurada para carregar a vista de alteração de palavra-passe do cliente.
     */
    public static RedirecionarHelper GotoUpdatePasswordClient() {
        return new RedirecionarHelper("Views/Cliente/AlterarPasswordCliente.fxml", "Alterar Password");
    }
}
