package com.example.oporto_olympics.Models;
/**
 * A classe {@link Evento} representa um evento com informações sobre seu identificador, ano de edição, país anfitrião,
 * logotipo, mascote e local de realização.
 */
public class Evento {
    private int Id;
    private int Ano_edicao;
    private String Pais;
    private byte[] Logo;
    private byte[] Mascote;
    private int Local_id;
    /**
     * Construtor para a classe {@link Evento}, que inicializa todos os atributos do evento.
     *
     * @param id         Identificador único do evento.
     * @param ano_edicao Ano de edição do evento.
     * @param pais       País anfitrião do evento.
     * @param logo       Logotipo do evento em formato de array de bytes.
     * @param mascote    Mascote do evento em formato de array de bytes.
     * @param local_id   Identificador do local onde o evento será realizado.
     */
    public Evento (int id, int ano_edicao, String pais, byte[] logo, byte[] mascote, int local_id) {
        this.Id = id;
        this.Ano_edicao = ano_edicao;
        this.Pais = pais;
        this.Logo = logo;
        this.Mascote = mascote;
        this.Local_id = local_id;
    }
    /**
     * Obtém o identificador único do evento.
     *
     * @return O identificador do evento.
     */
    public int getId() {
        return Id;
    }
    /**
     * Define o identificador único do evento.
     *
     * @param id O novo identificador do evento.
     */
    public void setId(int id) {
        this.Id = id;
    }
    /**
     * Obtém o ano de edição do evento.
     *
     * @return O ano de edição do evento.
     */
    public int getAno_edicao() {
        return Ano_edicao;
    }
    /**
     * Define o ano de edição do evento.
     *
     * @param ano_edicao O novo ano de edição do evento.
     */
    public void setAno_edicao(int ano_edicao) {
        this.Ano_edicao = ano_edicao;
    }
    /**
     * Obtém o país anfitrião do evento.
     *
     * @return O país anfitrião do evento.
     */
    public String getPais() {
        return Pais;
    }
    /**
     * Define o país anfitrião do evento.
     *
     * @param pais O novo país anfitrião do evento.
     */
    public void setPais(String pais) {
        this.Pais = pais;
    }
    /**
     * Obtém o logotipo do evento.
     *
     * @return O logotipo do evento como um array de bytes.
     */
    public byte[] getLogo() {
        return Logo;
    }
    /**
     * Define o logotipo do evento.
     *
     * @param logo O novo logotipo do evento como um array de bytes.
     */
    public void setLogo(byte[] logo) {
        this.Logo = logo;
    }
    /**
     * Obtém a mascote do evento.
     *
     * @return A mascote do evento como um array de bytes.
     */
    public byte[] getMascote() {
        return Mascote;
    }
    /**
     * Define a mascote do evento.
     *
     * @param mascote A nova mascote do evento como um array de bytes.
     */
    public void setMascote(byte[] mascote) {
        this.Mascote = mascote;
    }
    /**
     * Obtém o identificador do local onde o evento será realizado.
     *
     * @return O identificador do local do evento.
     */
    public int getLocal_id() {
        return Local_id;
    }
    /**
     * Define o identificador do local onde o evento será realizado.
     *
     * @param local_id O novo identificador do local do evento.
     */
    public void setLocal_id(int local_id) {
        this.Local_id = local_id;
    }
}
