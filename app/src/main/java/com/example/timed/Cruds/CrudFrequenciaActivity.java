package com.example.timed.Cruds;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timed.Home.HomeActivity;
import com.example.timed.R;

public class CrudFrequenciaActivity extends AppCompatActivity {

    LinearLayout layoutList;
    ImageButton btnAddHorario;
    Button btnFinalizar;

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

        layoutList = findViewById(R.id.list_horarios);
        btnAddHorario = findViewById(R.id.btn_add_horario);
        btnFinalizar = findViewById(R.id.btn_finalizar_crud_frequencia);

        btnAddHorario.setOnClickListener(v -> addHorarioView() );

        btnFinalizar.setOnClickListener(v -> {
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
}