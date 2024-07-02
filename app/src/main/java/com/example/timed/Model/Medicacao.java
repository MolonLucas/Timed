package com.example.timed.Model;

public class Medicacao{
    public Medicacao(int id, String nome, int dosagem){
        this.Id = id;
        this.Nome = nome;
        this.Dosagem = dosagem;
    }

    public int Id;
    public String Nome;
    public int Dosagem;
    public int idFrequencia;
    public Frequencia Frequencia;
}
