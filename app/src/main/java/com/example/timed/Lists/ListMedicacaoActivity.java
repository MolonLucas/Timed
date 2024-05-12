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

import com.example.timed.Cruds.CrudMedicacaoActivity;
import com.example.timed.Model.Medicacao;
import com.example.timed.R;

import java.util.ArrayList;
import java.util.List;

public class ListMedicacaoActivity extends AppCompatActivity {

    LinearLayout listMedicacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.list_medicacao);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        listMedicacao = findViewById(R.id.layout_list_medicacao);

        List<Medicacao> frequenciaList = this.getMedicacaoMock();

        adicionarItensLista(frequenciaList);
    }

    private List<Medicacao> getMedicacaoMock() {
        List<Medicacao> retorno = new ArrayList<>();

        Medicacao f1 = new Medicacao(1, "Paracetamol", 100);
        Medicacao f2 = new Medicacao(2, "Fluoxetina", 200);
        Medicacao f3 = new Medicacao(3, "Propranolol", 300);

        retorno.add(f1);
        retorno.add(f2);
        retorno.add(f3);

        return retorno;
    }

    private void adicionarItensLista(List<Medicacao> medicacaoList) {
        LayoutInflater inflater = LayoutInflater.from(this);

        for (Medicacao medicacao : medicacaoList) {
            LinearLayout itemFrequencia = (LinearLayout) inflater.inflate(R.layout.item_list_edicao, listMedicacao, false);

            TextView txtNome = itemFrequencia.findViewById(R.id.txt_item_edicao);
            ImageButton btnEditar = itemFrequencia.findViewById(R.id.btn_edit_item_lista);

            txtNome.setText(medicacao.Nome);

            btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(this, CrudMedicacaoActivity.class);
                startActivity(intent);
            });

            listMedicacao.addView(itemFrequencia);
        }
    }
}