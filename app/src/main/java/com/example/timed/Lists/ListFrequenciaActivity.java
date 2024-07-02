package com.example.timed.Lists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timed.Cruds.CrudFrequenciaActivity;
import com.example.timed.Model.Frequencia;
import com.example.timed.R;
import com.example.timed.Repository.FrequenciaRepository;

import java.util.ArrayList;
import java.util.List;

public class ListFrequenciaActivity extends AppCompatActivity {

    FrequenciaRepository frequenciaRepository;

    LinearLayout listFrequencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_frequencia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        frequenciaRepository = new FrequenciaRepository(this);

        listFrequencias = findViewById(R.id.layout_list_frequencia);

        adicionarItensLista(frequenciaRepository.getAllFrequencias());
    }

    private void adicionarItensLista(List<Frequencia> frequenciaList) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Frequencia frequencia : frequenciaList) {
            LinearLayout itemFrequencia = (LinearLayout) inflater.inflate(R.layout.item_list_edicao, listFrequencias, false);

            TextView txtDescricao = itemFrequencia.findViewById(R.id.txt_item_edicao);
            ImageButton btnEditar = itemFrequencia.findViewById(R.id.btn_edit_item_lista);

            txtDescricao.setText(frequencia.Descricao);

            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(this, CrudFrequenciaActivity.class);
                intent.putExtra("FREQUENCIA_ID", frequencia.Id);
                startActivity(intent);
            });

            listFrequencias.addView(itemFrequencia);
        }
    }
}