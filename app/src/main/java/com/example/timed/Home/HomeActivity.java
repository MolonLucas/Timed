package com.example.timed.Home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timed.Cruds.CrudFrequenciaActivity;
import com.example.timed.Cruds.CrudMedicacaoActivity;
import com.example.timed.Lists.ListFrequenciaActivity;
import com.example.timed.Lists.ListMedicacaoActivity;
import com.example.timed.R;
import com.example.timed.Repository.FrequenciaRepository;
import com.example.timed.Repository.MedicamentoRepository;

public class HomeActivity extends AppCompatActivity {

    FrequenciaRepository frequenciaRepository;
    MedicamentoRepository medicamentoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Método em branco para bloquear o click do botão voltar
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        frequenciaRepository = new FrequenciaRepository(this);
        medicamentoRepository = new MedicamentoRepository(this);

        Button btnCadastrarMedicamento = findViewById(R.id.btn_novo_medicamento);
        Button btnCadastrarFrequencia = findViewById(R.id.btn_nova_frequencia);
        Button btnEditarMedicamento = findViewById(R.id.btn_edit_medicamento);
        Button btnEditarFrequencia = findViewById(R.id.btn_edit_frequencia);

        btnCadastrarMedicamento.setOnClickListener(v -> {
            if (PossuiFrequenciaCadastrada())
                RedirecionarParaActivity(CrudMedicacaoActivity.class);
            else
                MostrarAlertaNenhumaFrequenciaCadastradaParaMedicamento();
        });
        btnCadastrarFrequencia.setOnClickListener(v -> RedirecionarParaActivity(CrudFrequenciaActivity.class));
        btnEditarMedicamento.setOnClickListener(v -> {
            if (PossuiMedicamentoCadastrado())
                RedirecionarParaActivity(ListMedicacaoActivity.class);
            else
                MostrarAlertaNenhumMedicamentoCadastrado();
        });
        btnEditarFrequencia.setOnClickListener(v -> {
            if (PossuiFrequenciaCadastrada())
                RedirecionarParaActivity(ListFrequenciaActivity.class);
            else
                MostrarAlertaNenhumaFrequenciaCadastrada();
        });
    }

    private boolean PossuiFrequenciaCadastrada(){
        return frequenciaRepository.getAllFrequencias().getCount() > 0;
    }

    private boolean PossuiMedicamentoCadastrado(){
        return medicamentoRepository.getAllMedicamentos().getCount() > 0;
    }

    private void MostrarAlertaNenhumaFrequenciaCadastradaParaMedicamento(){
        MostrarAlerta("Você precisa cadastrar uma frequência antes de cadastrar um medicamento.");
    }

    private void MostrarAlertaNenhumaFrequenciaCadastrada(){
        MostrarAlerta("Você precisa cadastrar uma frequência para poder entrar na tela de edição.");
    }

    private void MostrarAlertaNenhumMedicamentoCadastrado(){
        MostrarAlerta("Você precisa cadastrar um medicamento para poder entrar na tela de edição.");
    }

    private void MostrarAlerta(String mensagem){
        new AlertDialog.Builder(this)
                .setTitle("Atenção")
                .setMessage(mensagem)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void RedirecionarParaActivity(Class<?> activityClass){
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}