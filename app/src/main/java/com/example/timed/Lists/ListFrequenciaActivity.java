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

import java.util.ArrayList;
import java.util.List;

public class ListFrequenciaActivity extends AppCompatActivity {

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

        listFrequencias = findViewById(R.id.layout_list_frequencia);

        List<Frequencia> frequenciaList = this.getFrequenciasMock();

        adicionarItensLista(frequenciaList);
    }

    private List<Frequencia> getFrequenciasMock() {
        List<Frequencia> retorno = new ArrayList<>();

        Frequencia f1 = new Frequencia(1, "De 4 em 4");
        Frequencia f2 = new Frequencia(2, "De 8 em 8");
        Frequencia f3 = new Frequencia(3, "De 12 em 12");

        retorno.add(f1);
        retorno.add(f2);
        retorno.add(f3);

        return retorno;
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
                startActivity(intent);
            });

            listFrequencias.addView(itemFrequencia);
        }
    }
}