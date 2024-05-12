package com.example.timed.Model;

import java.util.List;

public class Frequencia {
    public Frequencia(int id, String descricao){
        this.Id = id;
        this.Descricao = descricao;
    }

    public int Id;
    public String Descricao;
    public List<FrequenciaHorario> frequenciaHorarios;
}
