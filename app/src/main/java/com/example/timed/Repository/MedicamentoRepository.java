package com.example.timed.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timed.Uteis.DataBaseUtil;

public class MedicamentoRepository {
    private SQLiteDatabase db;

    public MedicamentoRepository(Context context) {
        DataBaseUtil dbHelper = new DataBaseUtil(context);
        db = dbHelper.getWritableDatabase();
    }

    public long insertMedicamento(String nome, int dosagem, int idFrequencia) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("dosagem", dosagem);
        values.put("idFrequencia", idFrequencia);
        return db.insert("medicamento", null, values);
    }

    public Cursor getAllMedicamentos() {
        return db.rawQuery("SELECT * FROM medicamento", null);
    }

    public Cursor getMedicamentoById(int id) {
        return db.rawQuery("SELECT * FROM medicamento WHERE id = ?", new String[]{String.valueOf(id)});
    }

    public int updateMedicamento(int id, String nome, int dosagem, int idFrequencia) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("dosagem", dosagem);
        values.put("idFrequencia", idFrequencia);
        return db.update("medicamento", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteMedicamento(int id) {
        return db.delete("medicamento", "id = ?", new String[]{String.valueOf(id)});
    }
}