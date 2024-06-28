package com.example.timed.Uteis;

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
        Log.d("DataBaseUtil", "onCreate: Creating database");

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
                "frequenciaId INTEGER," +
                "datahora TEXT)";
        db.execSQL(createTableFrequenciaHorarios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS medicamento");
        db.execSQL("DROP TABLE IF EXISTS frequencia");
        db.execSQL("DROP TABLE IF EXISTS frequencia_horarios");
        onCreate(db);
    }
}
