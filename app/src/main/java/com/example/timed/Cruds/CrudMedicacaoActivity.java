package com.example.timed.Cruds;

import android.content.Intent;
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
import com.example.timed.R;
import com.google.android.material.textfield.TextInputEditText;

public class CrudMedicacaoActivity extends AppCompatActivity {

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

        EditText inputNome = findViewById(R.id.input_nome_medicamento);
        EditText inputDosagem = findViewById(R.id.input_dosagem_medicamento);
        Spinner cmbFrequencia = findViewById(R.id.cmb_frequencia_medicamento);
        Button btnFinalizar = findViewById(R.id.btn_finalizar_crud_medicamento);

        // Adiciona itens de teste no spinner, será removido para fazer o procedimento de buscar no banco
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.add("Frequências");
        adapter.add("De 4 em 4");
        adapter.add("De 8 em 8");
        adapter.add("De 12 em 12");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbFrequencia.setAdapter(adapter);

        btnFinalizar.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        });
    }
}