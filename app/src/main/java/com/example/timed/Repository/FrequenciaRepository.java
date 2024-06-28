package com.example.timed.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timed.Uteis.DataBaseUtil;

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

    public Cursor getAllFrequencias() {
        return db.rawQuery("SELECT * FROM frequencia", null);
    }

    public Cursor getFrequenciaById(int id) {
        return db.rawQuery("SELECT * FROM frequencia WHERE id = ?", new String[]{String.valueOf(id)});
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