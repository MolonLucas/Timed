package com.example.timed.Cruds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timed.Home.HomeActivity;
import com.example.timed.Model.Frequencia;
import com.example.timed.Model.FrequenciaHorario;
import com.example.timed.R;
import com.example.timed.Repository.FrequenciaHorariosRepository;
import com.example.timed.Repository.FrequenciaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CrudFrequenciaActivity extends AppCompatActivity {

    FrequenciaRepository frequenciaRepository;
    FrequenciaHorariosRepository frequenciaHorariosRepository;

    LinearLayout layoutList;
    ImageButton btnAddHorario;
    EditText inputDescricao;
    Button btnFinalizar;

    List<String> horarios;
    int frequenciaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.crud_frequencia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frequenciaRepository = new FrequenciaRepository(this);
        frequenciaHorariosRepository = new FrequenciaHorariosRepository(this);

        layoutList = findViewById(R.id.list_horarios);
        btnAddHorario = findViewById(R.id.btn_add_horario);
        inputDescricao = findViewById(R.id.input_text_descricao_crud_frequencia);
        btnFinalizar = findViewById(R.id.btn_finalizar_crud_frequencia);

        horarios = new ArrayList<>();

        if (getIntent().hasExtra("FREQUENCIA_ID")) {
            frequenciaId = getIntent().getIntExtra("FREQUENCIA_ID", -1);
            if (frequenciaId != -1) {
                loadFrequenciaData(frequenciaId);
            }
        }

        btnAddHorario.setOnClickListener(v -> addHorarioView());

        btnFinalizar.setOnClickListener(v -> {
            String descricao = inputDescricao.getText().toString().trim();

            if (descricao.isEmpty()) {
                inputDescricao.setError("A descrição da frequência é obrigatória");
                inputDescricao.requestFocus();
                return;
            }

            if (!validateHorarios()) {
                return;
            }

            String mensagem = "";
            if (frequenciaId == -1) {
                long idFrequencia = frequenciaRepository.insertFrequencia(descricao);
                for (String horario : horarios) {
                    frequenciaHorariosRepository.insertFrequenciaHorario((int) idFrequencia, horario);
                }
                mensagem = "Frequência salva com sucesso!";
            } else {
                frequenciaRepository.updateFrequencia(frequenciaId, descricao);
                frequenciaHorariosRepository.deleteFrequenciaHorariosByFrequenciaId(frequenciaId);
                for (String horario : horarios) {
                    frequenciaHorariosRepository.insertFrequenciaHorario(frequenciaId, horario);
                }
                mensagem = "Frequência atualizada com sucesso!";
            }

            Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void loadFrequenciaData(int id) {
        Frequencia frequencia = frequenciaRepository.getFrequenciaById(id);
        if (frequencia != null) {
            inputDescricao.setText(frequencia.Descricao);
            List<FrequenciaHorario> horariosList = frequenciaHorariosRepository.getFrequenciaHorariosByFrequenciaId(id);
            for (FrequenciaHorario horario : horariosList) {
                addHorarioView(horario.getDatahora());
            }
        }
    }

    private void addHorarioView() {
        View itemHorario = getLayoutInflater().inflate(R.layout.item_list_horario, null, false);

        EditText horario = itemHorario.findViewById(R.id.input_horario_hora);
        ImageButton btnRemover = itemHorario.findViewById(R.id.btn_remover_horario);

        btnRemover.setOnClickListener(this::removeHorarioView);

        layoutList.addView(itemHorario);
    }

    private void addHorarioView(String horarioText) {
        View itemHorario = getLayoutInflater().inflate(R.layout.item_list_horario, null, false);

        EditText horarioEditText = itemHorario.findViewById(R.id.input_horario_hora);
        ImageButton btnRemover = itemHorario.findViewById(R.id.btn_remover_horario);

        String formattedHorario = formatTime(horarioText);
        if (formattedHorario != null) {
            horarioEditText.setText(formattedHorario);
        } else {
            Toast.makeText(this, "Erro ao formatar o horário: " + horarioText, Toast.LENGTH_SHORT).show();
        }

        btnRemover.setOnClickListener(this::removeHorarioView);

        layoutList.addView(itemHorario);
    }

    private String formatTime(String time) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date date = inputFormat.parse(time);
            if (date != null) {
                return outputFormat.format(date);
            } else {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void removeHorarioView(View v) {
        View parentLayout = (View) v.getParent();
        layoutList.removeView(parentLayout);
    }

    private boolean validateHorarios() {
        int childCount = layoutList.getChildCount();
        horarios.clear();

        if (childCount == 1) {
            Toast.makeText(this, "Adicione pelo menos um horário", Toast.LENGTH_SHORT).show();
            return false;
        }

        for (int i = 1; i < childCount; i++) {
            View horarioView = layoutList.getChildAt(i);
            EditText inputHorario = horarioView.findViewById(R.id.input_horario_hora);
            String horario = inputHorario.getText().toString().trim();

            if (horario.isEmpty()) {
                inputHorario.setError("O horário é obrigatório");
                inputHorario.requestFocus();
                return false;
            }

            if (!isValidTimeFormat(horario)) {
                inputHorario.setError("Formato de horário inválido");
                inputHorario.requestFocus();
                return false;
            }

            horarios.add(horario);
        }
        return true;
    }

    private boolean isValidTimeFormat(String time) {
        return time.matches("^([01]?\\d|2[0-3]):[0-5]\\d$");
    }
}