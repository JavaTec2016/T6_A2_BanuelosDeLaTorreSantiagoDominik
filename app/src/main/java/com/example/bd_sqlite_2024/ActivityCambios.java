package com.example.bd_sqlite_2024;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import db.EscuelaDB;
import entities.Alumno;

public class ActivityCambios extends Activity {
    EditText cajaNumControl, cajaNombre, cajaEdad;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    ArrayList<Alumno> datos = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambios);

        cajaNumControl = findViewById(R.id.cajaNumControl);
        cajaNombre = findViewById(R.id.cajaNombre);
        cajaEdad = findViewById(R.id.cajaEdad);

        recyclerView = findViewById(R.id.lista_alumnos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        EscuelaDB db = EscuelaDB.getDB(getBaseContext());

        cajaNumControl = findViewById(R.id.cajaNumControl);

        cajaNumControl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        datos = (ArrayList<Alumno>)db.alumnoDAO().filtrarAlumnosPorNumControl(cajaNumControl.getText().toString()+"%");
                        //usar Handler y Looper
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter = new CustomAdapter(datos);
                                recyclerView.setAdapter(adapter);
                            }
                        });
                    }
                }).start();
            }
        });
    }
    public void actualizarAlumno(View v){
        EscuelaDB db = EscuelaDB.getDB(getBaseContext());
        if(datos.isEmpty()){
            Toast.makeText(getBaseContext(), "Alumno no encontrado", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                String nc = cajaNumControl.getText().toString();
                String nom = cajaNombre.getText().toString();
                byte ed = Byte.parseByte(cajaEdad.getText().toString());
                db.alumnoDAO().actualizarAlumnoPorNumControl(nom, ed, nc);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Cambios guardados", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }
}
