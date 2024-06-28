package com.example.timed.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timed.Uteis.DataBaseUtil;

public class FrequenciaHorariosRepository {
    private SQLiteDatabase db;

    public FrequenciaHorariosRepository(Context context) {
        DataBaseUtil dbHelper = new DataBaseUtil(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertFrequenciaHorario(int frequenciaId, String datahora) {
        ContentValues values = new ContentValues();
        values.put("frequenciaId", frequenciaId);
        values.put("datahora", datahora);
        return db.insert("frequencia_horarios", null, values);
    }

    public Cursor getAllFrequenciaHorarios() {
        return db.rawQuery("SELECT * FROM frequencia_horarios", null);
    }

    public Cursor getFrequenciaHorariosByFrequenciaId(int frequenciaId) {
        return db.rawQuery("SELECT * FROM frequencia_horarios WHERE frequenciaId = ?", new String[]{String.valueOf(frequenciaId)});
    }

    public Cursor getFrequenciaHorarioById(int id) {
        return db.rawQuery("SELECT * FROM frequencia_horarios WHERE id = ?", new String[]{String.valueOf(id)});
    }

    public int updateFrequenciaHorario(int id, int frequenciaId, String datahora) {
        ContentValues values = new ContentValues();
        values.put("frequenciaId", frequenciaId);
        values.put("datahora", datahora);
        return db.update("frequencia_horarios", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteFrequenciaHorario(int id) {
        return db.delete("frequencia_horarios", "id = ?", new String[]{String.valueOf(id)});
    }
}