package com.example.timed.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timed.Uteis.DataBaseUtil;
import com.example.timed.Model.Frequencia;
import com.example.timed.Model.FrequenciaHorario;

import java.util.ArrayList;
import java.util.List;

public class FrequenciaRepository {
    private SQLiteDatabase db;

    public FrequenciaRepository(Context context) {
        DataBaseUtil dbHelper = new DataBaseUtil(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertFrequencia(String descricao) {
        ContentValues values = new ContentValues();
        values.put("descricao", descricao);
        return db.insert("frequencia", null, values);
    }

    public List<Frequencia> getAllFrequencias() {
        List<Frequencia> frequencias = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM frequencia", null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex("id");
                    int descricaoIndex = cursor.getColumnIndex("descricao");

                    if (idIndex >= 0 && descricaoIndex >= 0) {
                        int id = cursor.getInt(idIndex);
                        String descricao = cursor.getString(descricaoIndex);
                        Frequencia frequencia = new Frequencia(id, descricao);
                        frequencias.add(frequencia);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return frequencias;
    }

    public Frequencia getFrequenciaById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM frequencia WHERE id = ?", new String[]{String.valueOf(id)});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int descricaoIndex = cursor.getColumnIndex("descricao");

                if (idIndex >= 0 && descricaoIndex >= 0) {
                    String descricao = cursor.getString(descricaoIndex);
                    return new Frequencia(id, descricao);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null; // Retorna null se não encontrar a frequência com o ID especificado
    }

    public int updateFrequencia(int id, String descricao) {
        ContentValues values = new ContentValues();
        values.put("descricao", descricao);
        return db.update("frequencia", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteFrequencia(int id) {
        return db.delete("frequencia", "id = ?", new String[]{String.valueOf(id)});
    }
}