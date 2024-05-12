package com.example.timed.Model;

public class Item {
    public int Id;
    public String Descricao;

    public Item(int id, String descricao) {
        this.Id = id;
        this.Descricao = descricao;
    }

    public static Item Criar(Medicacao medicacao) {
        return new Item(medicacao.Id, medicacao.Nome);
    }
}