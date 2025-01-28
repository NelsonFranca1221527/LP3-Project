package com.example.oporto_olympics.API.Models;

public class TicketInfo {

    private String Id;

    private String dataInicio;

    private String dataFim;

    private String local;

    private int lugar;

    private String codigoQR;

    public TicketInfo(String id, String dataInicio, String dataFim, String local, int lugar, String codigoQR) {
        Id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.lugar = lugar;
        this.codigoQR = codigoQR;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getLugar() {
        return lugar;
    }

    public void setLugar(int lugar) {
        this.lugar = lugar;
    }

    public String getCodigoQR() {
        return codigoQR;
    }

    public void setCodigoQR(String codigoQR) {
        this.codigoQR = codigoQR;
    }
}
