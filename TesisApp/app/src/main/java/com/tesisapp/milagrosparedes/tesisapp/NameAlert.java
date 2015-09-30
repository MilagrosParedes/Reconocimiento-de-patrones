package com.tesisapp.milagrosparedes.tesisapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;

public class NameAlert extends AppCompatActivity implements Serializable {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_alert);
    }

    public void InputName (View v)
    {
        AlertDialog.Builder ventana =  new AlertDialog.Builder(this);
        ventana.setMessage("Nombre y Apellido");

        final EditText ET_nombre = new EditText(this);
        ventana.setView(ET_nombre);

        ventana.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nombre = ET_nombre.getText().toString();
                if (nombre.length() != 0) {
                    Toast.makeText(NameAlert.this, "Registrado como: " + nombre, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NameAlert.this, Principal.class);
                    intent.putExtra("parametro", nombre);
                    startActivity(intent);
                }


                else
                    Toast.makeText(NameAlert.this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();

            }
        });

        ventana.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(NameAlert.this,"Debe ingresar un nombre", Toast.LENGTH_SHORT).show();

            }
        });

        ventana.show();
    }

}

