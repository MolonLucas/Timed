package com.example.timed.Home;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timed.Cruds.CrudFrequenciaActivity;
import com.example.timed.Cruds.CrudMedicacaoActivity;
import com.example.timed.Lists.ListFrequenciaActivity;
import com.example.timed.Lists.ListMedicacaoActivity;
import com.example.timed.R;

public class HomeActivity extends AppCompatActivity {

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

        Button btnCadastrarMedicamento = findViewById(R.id.btn_novo_medicamento);
        Button btnCadastrarFrequencia = findViewById(R.id.btn_nova_frequencia);
        Button btnEditarMedicamento = findViewById(R.id.btn_edit_medicamento);
        Button btnEditarFrequencia = findViewById(R.id.btn_edit_frequencia);

        btnCadastrarMedicamento.setOnClickListener(v -> RedirecionarParaActivity(CrudMedicacaoActivity.class));
        btnCadastrarFrequencia.setOnClickListener(v -> RedirecionarParaActivity(CrudFrequenciaActivity.class));
        btnEditarMedicamento.setOnClickListener(v -> RedirecionarParaActivity(ListMedicacaoActivity.class));
        btnEditarFrequencia.setOnClickListener(v -> RedirecionarParaActivity(ListFrequenciaActivity.class));
    }

    private void RedirecionarParaActivity(Class<?> activityClass){
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}