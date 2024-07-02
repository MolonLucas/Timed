package com.example.timed.Cruds;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timed.Home.HomeActivity;
import com.example.timed.Model.Frequencia;
import com.example.timed.Model.Medicacao;
import com.example.timed.R;
import com.example.timed.Repository.FrequenciaRepository;
import com.example.timed.Repository.MedicacaoRepository;

import java.util.List;

public class CrudMedicacaoActivity extends AppCompatActivity {

    String FREQUENCIA_DEFAULT = "Frequências";

    FrequenciaRepository frequenciaRepository;
    MedicacaoRepository medicacaoRepository;

    int medicacaoId = -1;

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
        medicacaoRepository = new MedicacaoRepository(this);

        EditText inputNome = findViewById(R.id.input_nome_medicamento);
        EditText inputDosagem = findViewById(R.id.input_dosagem_medicamento);
        Spinner cmbFrequencia = findViewById(R.id.cmb_frequencia_medicamento);
        Button btnFinalizar = findViewById(R.id.btn_finalizar_crud_medicamento);

        CarregarFrequencias(cmbFrequencia);

        if (getIntent().hasExtra("MEDICACAO_ID")) {
            medicacaoId = getIntent().getIntExtra("MEDICACAO_ID", -1);
            if (medicacaoId != -1) {
                loadMedicacaoData(medicacaoId, inputNome, inputDosagem, cmbFrequencia);
            }
        }

        btnFinalizar.setOnClickListener(v -> {
            String nome = inputNome.getText().toString().trim();
            String dosagem = inputDosagem.getText().toString().trim();
            String frequenciaSelecionada = cmbFrequencia.getSelectedItem().toString();

            if (nome.isEmpty()) {
                inputNome.setError("O nome do medicamento é obrigatório");
                inputNome.requestFocus();
                return;
            }

            if (dosagem.isEmpty()) {
                inputDosagem.setError("A dosagem do medicamento é obrigatória");
                inputDosagem.requestFocus();
                return;
            }

            try {
                Integer.parseInt(dosagem);
            } catch (NumberFormatException e) {
                inputDosagem.setError("A dosagem deve ser um valor inteiro");
                inputDosagem.requestFocus();
                return;
            }

            if (frequenciaSelecionada.equals(FREQUENCIA_DEFAULT)) {
                Toast.makeText(this, "Selecione uma frequência válida", Toast.LENGTH_SHORT).show();
                cmbFrequencia.requestFocus();
                return;
            }

            int idFrequenciaSelecionada = frequenciaRepository.getFrequenciaByDescricao(frequenciaSelecionada).Id;

            if (medicacaoId == -1) {
                medicacaoRepository.insertMedicacao(nome, Integer.parseInt(dosagem), idFrequenciaSelecionada);
                Toast.makeText(this, "Medicação inserida com sucesso!", Toast.LENGTH_SHORT).show();
            } else {
                medicacaoRepository.updateMedicacao(medicacaoId, nome, Integer.parseInt(dosagem), idFrequenciaSelecionada);
                Toast.makeText(this, "Medicação atualizada com sucesso!", Toast.LENGTH_SHORT).show();
            }

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void CarregarFrequencias(Spinner cmbFrequencia){
        List<Frequencia> frequencias = frequenciaRepository.getAllFrequencias();

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add(FREQUENCIA_DEFAULT);
        for (Frequencia frequencia: frequencias) {
            adapter.add(frequencia.Descricao);
        }

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbFrequencia.setAdapter(adapter);
    }

    private void loadMedicacaoData(int medicacaoId, EditText inputNome, EditText inputDosagem, Spinner cmbFrequencia) {
        Medicacao medicacao = medicacaoRepository.getMedicacaoById(medicacaoId);
        if (medicacao != null) {
            inputNome.setText(medicacao.Nome);
            inputDosagem.setText(String.valueOf(medicacao.Dosagem));

            Frequencia frequencia = frequenciaRepository.getFrequenciaById(medicacao.idFrequencia);
            if (frequencia != null) {
                String descricaoFrequencia = frequencia.Descricao;
                int position = ((ArrayAdapter<String>) cmbFrequencia.getAdapter()).getPosition(descricaoFrequencia);
                cmbFrequencia.setSelection(position);
            }
        }
    }
}