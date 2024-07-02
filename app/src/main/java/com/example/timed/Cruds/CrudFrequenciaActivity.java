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
    EditText inputDescricaso;
    Button btnFinalizar;

    List<String> horarios;

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
        inputDescricaso = findViewById(R.id.input_text_descricao_crud_frequencia);
        btnFinalizar = findViewById(R.id.btn_finalizar_crud_frequencia);

        horarios = new ArrayList<>();

        btnAddHorario.setOnClickListener(v -> addHorarioView() );

        btnFinalizar.setOnClickListener(v -> {
            String descricao = inputDescricaso.getText().toString().trim();

            if (descricao.isEmpty()) {
                inputDescricaso.setError("A descrição da frequência é obrigatória");
                inputDescricaso.requestFocus();
                return;
            }

            if (!validateHorarios()) {
                return;
            }

            long idFrequencia = frequenciaRepository.insertFrequencia(descricao);
            for (String horario : horarios) {
                frequenciaHorariosRepository.insertFrequenciaHorario((int) idFrequencia, horario);
            }

            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }

    private void addHorarioView(){
        View itemHorario = getLayoutInflater().inflate(R.layout.item_list_horario, null, false);

        EditText horario = itemHorario.findViewById(R.id.input_horario_hora);
        ImageButton btnRemover = itemHorario.findViewById(R.id.btn_remover_horario);

        btnRemover.setOnClickListener(this::removeHorarioView);

        layoutList.addView(itemHorario);
    }

    private void removeHorarioView(View v){
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

            String formattedHorario = formatTime(horario);
            if (formattedHorario == null) {
                inputHorario.setError("Erro ao formatar horário");
                inputHorario.requestFocus();
                return false;
            }

            horarios.add(formattedHorario);
        }
        return true;
    }

    private boolean isValidTimeFormat(String time) {
        return time.matches("^([01]?\\d|2[0-3]):[0-5]\\d$");
    }

    private String formatTime(String time) {
        try {
            // Using the current date for the format
            SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
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
}