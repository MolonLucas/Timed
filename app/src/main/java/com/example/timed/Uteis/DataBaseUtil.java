package com.example.timed.Uteis;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseUtil extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "timed.db";
    private static final int DATABASE_VERSION = 1;

    public DataBaseUtil(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableMedicamento = "CREATE TABLE medicamento (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "dosagem INT," +
                "idFrequencia INT)";
        db.execSQL(createTableMedicamento);

        String createTableFrequencia = "CREATE TABLE frequencia (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT)";
        db.execSQL(createTableFrequencia);

        String createTableFrequenciaHorarios = "CREATE TABLE frequencia_horarios (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "idFrequencia INTEGER," +
                "datahora TEXT)";
        db.execSQL(createTableFrequenciaHorarios);

        insertDefaultFrequencias(db);
        insertDefaultMedicamentos(db);
        insertDefaultFrequenciaHorarios(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS medicamento");
        db.execSQL("DROP TABLE IF EXISTS frequencia");
        db.execSQL("DROP TABLE IF EXISTS frequencia_horarios");
        onCreate(db);
    }

    private void insertDefaultFrequencias(SQLiteDatabase db) {
        insertFrequencia(db, "De 4 em 4");
        insertFrequencia(db, "De 8 em 8");
        insertFrequencia(db, "De 12 em 12");
    }

    private void insertFrequencia(SQLiteDatabase db, String descricao) {
        ContentValues values = new ContentValues();
        values.put("descricao", descricao);
        db.insert("frequencia", null, values);
    }

    private void insertDefaultMedicamentos(SQLiteDatabase db) {
        insertMedicamento(db, "Benegripe", 1, 1);
        insertMedicamento(db, "Loratadina", 2, 2);
        insertMedicamento(db, "Dipirona", 3, 3);
    }

    private void insertMedicamento(SQLiteDatabase db, String nome, int dosagem, int idFrequencia) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("dosagem", dosagem);
        values.put("idFrequencia", idFrequencia);
        db.insert("medicamento", null, values);
    }

    private void insertDefaultFrequenciaHorarios(SQLiteDatabase db) {
        insertFrequenciaHorario(db, 1, "13:00");
        insertFrequenciaHorario(db, 2, "14:00");
        insertFrequenciaHorario(db, 3, "15:00");
    }

    private void insertFrequenciaHorario(SQLiteDatabase db, int idFrequencia, String datahora) {
        ContentValues values = new ContentValues();
        values.put("idFrequencia", idFrequencia);
        values.put("datahora", datahora);
        db.insert("frequencia_horarios", null, values);
    }
}
