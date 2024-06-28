package com.example.timed.Cruds;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timed.Home.HomeActivity;
import com.example.timed.Model.Frequencia;
import com.example.timed.R;
import com.example.timed.Repository.FrequenciaRepository;
import com.example.timed.Repository.MedicamentoRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class CrudMedicacaoActivity extends AppCompatActivity {

    FrequenciaRepository frequenciaRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.crud_medicacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frequenciaRepository = new FrequenciaRepository(this);

        EditText inputNome = findViewById(R.id.input_nome_medicamento);
        EditText inputDosagem = findViewById(R.id.input_dosagem_medicamento);
        Spinner cmbFrequencia = findViewById(R.id.cmb_frequencia_medicamento);
        Button btnFinalizar = findViewById(R.id.btn_finalizar_crud_medicamento);

        CarregarFrequencias(cmbFrequencia);

        btnFinalizar.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void CarregarFrequencias(Spinner cmbFrequencia){
        List<Frequencia> frequencias = frequenciaRepository.getAllFrequencias();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add("Frequências");
        for (Frequencia frequencia: frequencias) {
            adapter.add(frequencia.Descricao);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbFrequencia.setAdapter(adapter);
    }
}