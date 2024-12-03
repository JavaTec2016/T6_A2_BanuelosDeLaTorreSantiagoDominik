package com.example.bd_sqlite_2024;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import db.EscuelaDB;
import entities.Alumno;

public class ActivityAltas extends Activity {

    EditText cajaNumControl, cajaNombre, cajaEdad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altas);

        cajaNumControl = findViewById(R.id.cajaNumControl);
        cajaNombre = findViewById(R.id.cajaNombre);
        cajaEdad = findViewById(R.id.cajaEdad);

    }

    public void agregarAlumno(View v){
        EscuelaDB db = EscuelaDB.getDB(getBaseContext());

        //Ejecutar en un HILO separado del hilo principal

        new Thread(new Runnable() {
            @Override
            public void run() {
                String numCon = cajaNumControl.getText().toString();
                String nombre = cajaNombre.getText().toString();
                int edadR = Integer.parseInt(cajaEdad.getText().toString());
                if(edadR > Byte.MAX_VALUE || edadR < 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "la edad excede 127", Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    byte edad = (byte)edadR;
                    db.alumnoDAO().agregarAlumno(new Alumno(numCon, nombre, edad));
                    Log.i("DBThread", "insercion correcta");

                    //los toast deben ir siempre en el mismo hilo de la UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(), "insercion correcta", Toast.LENGTH_LONG).show();
                        }
                    });
                }


            }
        }).start();


    }
}
