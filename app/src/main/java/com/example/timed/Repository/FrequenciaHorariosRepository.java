package com.example.timed.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timed.Uteis.DataBaseUtil;
import com.example.timed.Model.FrequenciaHorario;

import java.util.ArrayList;
import java.util.List;

public class FrequenciaHorariosRepository {
    private SQLiteDatabase db;

    public FrequenciaHorariosRepository(Context context) {
        DataBaseUtil dbHelper = new DataBaseUtil(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertFrequenciaHorario(int frequenciaId, String datahora) {
        ContentValues values = new ContentValues();
        values.put("idFrequencia", frequenciaId);
        values.put("datahora", datahora);
        return db.insert("frequencia_horarios", null, values);
    }

    public List<FrequenciaHorario> getAllFrequenciaHorarios() {
        List<FrequenciaHorario> horarios = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM frequencia_horarios", null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex("id");
                    int idFrequenciaIndex = cursor.getColumnIndex("idFrequencia");
                    int datahoraIndex = cursor.getColumnIndex("datahora");

                    if (idIndex >= 0 && idFrequenciaIndex >= 0 && datahoraIndex >= 0) {
                        int id = cursor.getInt(idIndex);
                        int idFrequencia = cursor.getInt(idFrequenciaIndex);
                        String datahora = cursor.getString(datahoraIndex);
                        FrequenciaHorario horario = new FrequenciaHorario(id, idFrequencia, datahora);
                        horarios.add(horario);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return horarios;
    }

    public List<FrequenciaHorario> getFrequenciaHorariosByFrequenciaId(int frequenciaId) {
        List<FrequenciaHorario> horarios = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM frequencia_horarios WHERE idFrequencia = ?", new String[]{String.valueOf(frequenciaId)});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex("id");
                    int idFrequenciaIndex = cursor.getColumnIndex("idFrequencia");
                    int datahoraIndex = cursor.getColumnIndex("datahora");

                    if (idIndex >= 0 && idFrequenciaIndex >= 0 && datahoraIndex >= 0) {
                        int id = cursor.getInt(idIndex);
                        int idFrequencia = cursor.getInt(idFrequenciaIndex);
                        String datahora = cursor.getString(datahoraIndex);
                        FrequenciaHorario horario = new FrequenciaHorario(id, idFrequencia, datahora);
                        horarios.add(horario);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return horarios;
    }

    public FrequenciaHorario getFrequenciaHorarioById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM frequencia_horarios WHERE id = ?", new String[]{String.valueOf(id)});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int idFrequenciaIndex = cursor.getColumnIndex("idFrequencia");
                int datahoraIndex = cursor.getColumnIndex("datahora");

                if (idIndex >= 0 && idFrequenciaIndex >= 0 && datahoraIndex >= 0) {
                    int idFrequencia = cursor.getInt(idFrequenciaIndex);
                    String datahora = cursor.getString(datahoraIndex);
                    return new FrequenciaHorario(id, idFrequencia, datahora);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

    public int updateFrequenciaHorario(int id, int frequenciaId, String datahora) {
        ContentValues values = new ContentValues();
        values.put("idFrequencia", frequenciaId);
        values.put("datahora", datahora);
        return db.update("frequencia_horarios", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteFrequenciaHorario(int id) {
        return db.delete("frequencia_horarios", "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteFrequenciaHorariosByFrequenciaId(int frequenciaId) {
        return db.delete("frequencia_horarios", "idFrequencia = ?", new String[]{String.valueOf(frequenciaId)});
    }
}