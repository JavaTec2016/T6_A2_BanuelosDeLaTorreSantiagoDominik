package com.example.bd_sqlite_2024;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import db.EscuelaDB;
import entities.Alumno;

public class ActivityBajas extends Activity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private  RecyclerView.LayoutManager layoutManager;
    private TextView cajaBajas;
    ArrayList<Alumno> datos = null;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bajas);

        recyclerView = findViewById(R.id.lista_alumnos);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        EscuelaDB db = EscuelaDB.getDB(getBaseContext());
        cajaBajas = findViewById(R.id.cajaBajas);

        cajaBajas.addTextChangedListener(new TextWatcher() {
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
                        datos = (ArrayList<Alumno>)db.alumnoDAO().filtrarAlumnosPorNumControl(cajaBajas.getText().toString()+"%");
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
    public void eliminarAlumno(View v){
        EscuelaDB db = EscuelaDB.getDB(getBaseContext());
        if(datos.isEmpty()){
            Toast.makeText(getBaseContext(), "Alumno no encontrado", Toast.LENGTH_LONG).show();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.alumnoDAO().eliminarAlumnoPorNumControl(cajaBajas.getText().toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getBaseContext(), "Eliminación exitosa", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).start();
    }
}
