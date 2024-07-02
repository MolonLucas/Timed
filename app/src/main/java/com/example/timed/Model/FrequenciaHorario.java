package com.example.timed.Model;

import java.time.LocalTime;

public class FrequenciaHorario {
    private int id;
    private int idFrequencia;
    private String datahora;

    public FrequenciaHorario(int id, int idFrequencia, String datahora) {
        this.id = id;
        this.idFrequencia = idFrequencia;
        this.datahora = datahora;
    }

    public int getId() {
        return id;
    }

    public int getIdFrequencia() {
        return idFrequencia;
    }

    public String getDatahora() {
        return datahora;
    }
}
