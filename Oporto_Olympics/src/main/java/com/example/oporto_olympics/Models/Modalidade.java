package com.example.oporto_olympics.Models;

import com.example.oporto_olympics.Models.RegistoModalidades.RegistoOlimpico;
import java.util.List;

/**
 * A classe {@link Modalidade} representa uma modalidade desportiva, incluindo informações como
 * o tipo de modalidade, o género, o nome, a descrição, os participantes mínimos, a medida usada,
 * a regra do "One Game", o evento relacionado, os recordes olímpicos e os vencedores olímpicos.
 */


public class Modalidade {

    private int id;

    private String tipo;

    private String genero;

    private String nome;

    private String descricao;

    private int minParticipantes;

    private String medida;

    private String OneGame;

    private List<Integer> ListEventosID;

    private RegistoOlimpico recordeOlimpico;

    private RegistoOlimpico vencedorOlimpico;

    private String regras;

    /**
     * Construtor da classe {@link Modalidade} que inicializa todos os campos da modalidade.
     *
     * @param id                Identificador único da modalidade.
     * @param tipo              Tipo da modalidade (individual ou em equipa).
     * @param genero            Gênero da modalidade (masculino, feminino ou misto).
     * @param nome              Nome da modalidade.
     * @param descricao         Descrição detalhada da modalidade.
     * @param minParticipantes  Número mínimo de participantes exigido.
     * @param medida            A unidade de medida usada para a modalidade (ex.: metros, minutos, etc.).
     * @param oneGame           Indica se a modalidade é jogada em apenas um jogo.
     * @param listEventosID     Lista de Identificadores dos eventos ao qual a modalidade pertence.
     * @param recordeOlimpico   Registo olímpico atual da modalidade.
     * @param vencedorOlimpico  Vencedor olímpico da modalidade.
     * @param regras            Regras da modalidade.
     */
    public Modalidade(int id, String tipo, String genero, String nome, String descricao, int minParticipantes, String medida, String oneGame, List<Integer> listEventosID, RegistoOlimpico recordeOlimpico, RegistoOlimpico vencedorOlimpico, String regras) {
        this.id = id;
        this.tipo = tipo;
        this.genero = genero;
        this.nome = nome;
        this.descricao = descricao;
        this.minParticipantes = minParticipantes;
        this.medida = medida;
        OneGame = oneGame;
        ListEventosID = listEventosID;
        this.recordeOlimpico = recordeOlimpico;
        this.vencedorOlimpico = vencedorOlimpico;
        this.regras = regras;
    }
    /**
     * Obtém o identificador único da modalidade.
     *
     * @return O identificador único da modalidade.
     */
    public int getId() {
        return id;
    }
    /**
     * Define o identificador único da modalidade.
     *
     * @param id O identificador único da modalidade.
     */
    public void setId(int id) {
        this.id = id;
    }
    /**
     * Obtém o tipo da modalidade (individual ou em equipa).
     *
     * @return O tipo da modalidade.
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * Define o tipo da modalidade (individual ou em equipa).
     *
     * @param tipo O tipo da modalidade.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    /**
     * Obtém o género da modalidade (masculino, feminino ou misto).
     *
     * @return O género da modalidade.
     */
    public String getGenero() {
        return genero;
    }
    /**
     * Define o género da modalidade (masculino, feminino ou misto).
     *
     * @param genero O género da modalidade.
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }
    /**
     * Obtém o nome da modalidade.
     *
     * @return O nome da modalidade.
     */
    public String getNome() {
        return nome;
    }
    /**
     * Define o nome da modalidade.
     *
     * @param nome O nome da modalidade.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }
    /**
     * Obtém a descrição detalhada da modalidade.
     *
     * @return A descrição da modalidade.
     */
    public String getDescricao() {
        return descricao;
    }
    /**
     * Define a descrição detalhada da modalidade.
     *
     * @param descricao A descrição da modalidade.
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    /**
     * Obtém o número mínimo de participantes exigido para a modalidade.
     *
     * @return O número mínimo de participantes.
     */
    public int getMinParticipantes() {
        return minParticipantes;
    }
    /**
     * Define o número mínimo de participantes exigido para a modalidade.
     *
     * @param minParticipantes O número mínimo de participantes.
     */
    public void setMinParticipantes(int minParticipantes) {
        this.minParticipantes = minParticipantes;
    }
    /**
     * Obtém a unidade de medida usada para a modalidade (exemplo: metros, minutos, etc.).
     *
     * @return A unidade de medida da modalidade.
     */
    public String getMedida() {
        return medida;
    }
    /**
     * Define a unidade de medida usada para a modalidade (exemplo: metros, minutos, etc.).
     *
     * @param medida A unidade de medida.
     */
    public void setMedida(String medida) {
        this.medida = medida;
    }
    /**
     * Obtém a informação sobre se a modalidade é jogada em apenas um jogo.
     *
     * @return "Sim" se a modalidade é jogada em um único jogo, "Não" caso contrário.
     */
    public String getOneGame() {
        return OneGame;
    }
    /**
     * Define se a modalidade é jogada em apenas um jogo.
     *
     * @param oneGame "Sim" se a modalidade for jogada em um único jogo, "Não" caso contrário.
     */
    public void setOneGame(String oneGame) {
        OneGame = oneGame;
    }
    /**
     * Obtém uma lista de identificadores dos eventos ao qual a modalidade pertence.
     *
     * @return A lista de identificadores dos eventos.
     */
    public List<Integer> getListEventosID() {
        return ListEventosID;
    }
    /**
     * Define uma lista de identificadores dos eventos ao qual a modalidade pertence.
     *
     * @param listEventosID Lista de identificadores dos eventos.
     */
    public void setListEventosID(List<Integer> listEventosID) {
        ListEventosID = listEventosID;
    }
    /**
     * Obtém o recorde olímpico da modalidade.
     *
     * @return O recorde olímpico.
     */
    public RegistoOlimpico getRecordeOlimpico() {
        return recordeOlimpico;
    }
    /**
     * Define o recorde olímpico da modalidade.
     *
     * @param recordeOlimpico O recorde olímpico da modalidade.
     */
    public void setRecordeOlimpico(RegistoOlimpico recordeOlimpico) {
        this.recordeOlimpico = recordeOlimpico;
    }
    /**
     * Obtém o vencedor olímpico da modalidade.
     *
     * @return O vencedor olímpico.
     */
    public RegistoOlimpico getVencedorOlimpico() {
        return vencedorOlimpico;
    }
    /**
     * Define o vencedor olímpico da modalidade.
     *
     * @param vencedorOlimpico O vencedor olímpico da modalidade.
     */
    public void setVencedorOlimpico(RegistoOlimpico vencedorOlimpico) {
        this.vencedorOlimpico = vencedorOlimpico;
    }
    /**
     * Obtém as regras da modalidade.
     *
     * @return As regras da modalidade.
     */
    public String getRegras() {
        return regras;
    }
    /**
     * Define as regras da modalidade.
     *
     * @param regras As regras da modalidade.
     */
    public void setRegras(String regras) {
        this.regras = regras;
    }
}
