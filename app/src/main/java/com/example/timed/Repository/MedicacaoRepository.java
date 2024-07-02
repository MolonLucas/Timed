package com.example.timed.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.timed.Uteis.DataBaseUtil;
import com.example.timed.Model.Medicacao;
import com.example.timed.Model.Frequencia;

import java.util.ArrayList;
import java.util.List;

public class MedicacaoRepository {
    private SQLiteDatabase db;
    private Context context;

    public MedicacaoRepository(Context context) {
        DataBaseUtil dbHelper = new DataBaseUtil(context);
        db = dbHelper.getWritableDatabase();
        this.context = context;
    }

    public long insertMedicacao(String nome, double dosagem, int idFrequencia) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("dosagem", dosagem);
        values.put("idFrequencia", idFrequencia);
        return db.insert("medicamento", null, values);
    }

    public List<Medicacao> getAllMedicacoes() {
        List<Medicacao> medicacoes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM medicamento", null);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int idIndex = cursor.getColumnIndex("id");
                    int nomeIndex = cursor.getColumnIndex("nome");
                    int dosagemIndex = cursor.getColumnIndex("dosagem");
                    int idFrequenciaIndex = cursor.getColumnIndex("idFrequencia");

                    if (idIndex >= 0 && nomeIndex >= 0 && dosagemIndex >= 0 && idFrequenciaIndex >= 0) {
                        int id = cursor.getInt(idIndex);
                        String nome = cursor.getString(nomeIndex);
                        double dosagem = cursor.getDouble(dosagemIndex);
                        int idFrequencia = cursor.getInt(idFrequenciaIndex);

                        // Fetch the corresponding Frequencia
                        FrequenciaRepository frequenciaRepository = new FrequenciaRepository(context);
                        Frequencia frequencia = frequenciaRepository.getFrequenciaById(idFrequencia);

                        Medicacao medicacao = new Medicacao(id, nome, dosagem);
                        medicacao.idFrequencia = idFrequencia;
                        medicacao.Frequencia = frequencia;
                        medicacoes.add(medicacao);
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return medicacoes;
    }

    public Medicacao getMedicacaoById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM medicamento WHERE id = ?", new String[]{String.valueOf(id)});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int nomeIndex = cursor.getColumnIndex("nome");
                int dosagemIndex = cursor.getColumnIndex("dosagem");
                int idFrequenciaIndex = cursor.getColumnIndex("idFrequencia");

                if (idIndex >= 0 && nomeIndex >= 0 && dosagemIndex >= 0 && idFrequenciaIndex >= 0) {
                    String nome = cursor.getString(nomeIndex);
                    double dosagem = cursor.getDouble(dosagemIndex);
                    int idFrequencia = cursor.getInt(idFrequenciaIndex);

                    // Fetch the corresponding Frequencia
                    FrequenciaRepository frequenciaRepository = new FrequenciaRepository(context);
                    Frequencia frequencia = frequenciaRepository.getFrequenciaById(idFrequencia);

                    Medicacao medicacao = new Medicacao(id, nome, dosagem);
                    medicacao.idFrequencia = idFrequencia;
                    medicacao.Frequencia = frequencia;
                    return medicacao;
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null; // Retorna null se não encontrar a medicação com o ID especificado
    }

    public Medicacao getMedicacaoByNome(String nome) {
        Cursor cursor = db.rawQuery("SELECT * FROM medicamento WHERE nome = ?", new String[]{nome});
        try {
            if (cursor != null && cursor.moveToFirst()) {
                int idIndex = cursor.getColumnIndex("id");
                int nomeIndex = cursor.getColumnIndex("nome");
                int dosagemIndex = cursor.getColumnIndex("dosagem");
                int idFrequenciaIndex = cursor.getColumnIndex("idFrequencia");

                if (idIndex >= 0 && nomeIndex >= 0 && dosagemIndex >= 0 && idFrequenciaIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    double dosagem = cursor.getDouble(dosagemIndex);
                    int idFrequencia = cursor.getInt(idFrequenciaIndex);

                    // Fetch the corresponding Frequencia
                    FrequenciaRepository frequenciaRepository = new FrequenciaRepository(context);
                    Frequencia frequencia = frequenciaRepository.getFrequenciaById(idFrequencia);

                    Medicacao medicacao = new Medicacao(id, nome, dosagem);
                    medicacao.idFrequencia = idFrequencia;
                    medicacao.Frequencia = frequencia;
                    return medicacao;
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null; // Retorna null se não encontrar a medicação com o nome especificado
    }

    public int updateMedicacao(int id, String nome, double dosagem, int idFrequencia) {
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("dosagem", dosagem);
        values.put("idFrequencia", idFrequencia);
        return db.update("medicamento", values, "id = ?", new String[]{String.valueOf(id)});
    }

    public int deleteMedicacao(int id) {
        return db.delete("medicamento", "id = ?", new String[]{String.valueOf(id)});
    }
}