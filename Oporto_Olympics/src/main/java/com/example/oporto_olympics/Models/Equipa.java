package com.example.oporto_olympics.Models;

import java.util.ArrayList;
import java.util.List;

public class Equipa {

    private int id;

    private String nome;

    private String pais;

    private String genero;

    private String desporto;

    private int modalidadeID;

    private int anoFundacao;

    private List<ParticipaçõesEquipa> participaçõesEquipa;

    public Equipa(int id, String nome, String pais, String genero, String desporto, int modalidadeID, int anoFundacao, List<ParticipaçõesEquipa> participaçõesEquipa) {
        this.id = id;
        this.nome = nome;
        this.pais = pais;
        this.genero = genero;
        this.desporto = desporto;
        this.modalidadeID = modalidadeID;
        this.anoFundacao = anoFundacao;
        this.participaçõesEquipa = participaçõesEquipa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDesporto() {
        return desporto;
    }

    public void setDesporto(String desporto) {
        this.desporto = desporto;
    }

    public int getModalidadeID() {
        return modalidadeID;
    }

    public void setModalidadeID(int modalidadeID) {
        this.modalidadeID = modalidadeID;
    }

    public int getAnoFundacao() {
        return anoFundacao;
    }

    public void setAnoFundacao(int anoFundacao) {
        this.anoFundacao = anoFundacao;
    }

    public List<ParticipaçõesEquipa> getParticipaçõesEquipa() {
        return participaçõesEquipa;
    }

    public void setParticipaçõesEquipa(List<ParticipaçõesEquipa> participaçõesEquipa) {
        this.participaçõesEquipa = participaçõesEquipa;
    }
}
