package com.tesisapp.milagrosparedes.tesisapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Principal extends AppCompatActivity {

    private TRegistros mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.layout1);
        Lienzo fondo = new Lienzo(this);
        layout1.addView(fondo);

        mydb = new TRegistros(this);

    }


        class Lienzo extends View {
                                         // Valores
            String accion = "";
            String[] ruta = new String [1000];

            float x, y;

            int intento = 1;
            int usuario = 1;
            int patron = 1; //CUAL PATRON ES!?
            int posicion = 0;
            boolean registrado = false; // Ya ingreso el nombre

            boolean valido = false;

            ArrayList<Long> array_time = new ArrayList<Long>();
            long total_time;
                                        //Objetos
            Patron figura = new Patron();

                                        //Figura
            Rect rect;
            Paint p_rect;
            Path path = new Path();

            Paint pincel1;
            Paint pincelText;



            public Lienzo(Context context) {
                super(context);
            }

            protected void onDraw (Canvas canvas){


                canvas.drawColor(Color.BLACK);
                pincel1 = new Paint();
                pincel1.setAntiAlias(true);

                pincel1.setColor(Color.GRAY);
                pincel1.setStyle(Paint.Style.STROKE);
                pincel1.setStrokeWidth(5);

                pincelText = new Paint();
                pincelText.setAntiAlias(true);

                pincelText.setColor(Color.GRAY);
                pincelText.setStyle(Paint.Style.STROKE);
                pincelText.setStrokeWidth(3);

                canvas.drawText("Patrón: " + String.valueOf(patron), 50, 50, pincelText);
                canvas.drawText("Intento: " + String.valueOf(intento), 50, 70, pincelText);
                figura = new Patron();




                if(patron == 1)
                {
                    canvas.drawLine(Math.round(figura.getX1_P1()), Math.round(figura.getY1_P1()), Math.round(figura.getX1_P1()), Math.round(figura.getY2_P1()), pincel1);
                    canvas.drawLine(Math.round(figura.getX1_P1()), Math.round(figura.getY2_P1()), Math.round(figura.getX2_P1()), Math.round(figura.getY1_P1()), pincel1);
                    canvas.drawLine(Math.round(figura.getX2_P1()), Math.round(figura.getY1_P1()), Math.round(figura.getX3_P1()), Math.round(figura.getY2_P1()), pincel1);
                    canvas.drawLine(Math.round(figura.getX3_P1()), Math.round(figura.getY1_P1()), Math.round(figura.getX3_P1()), Math.round(figura.getY2_P1()), pincel1);
                }
               if (patron == 2)
                {
                    canvas.drawLine(Math.round(figura.getX1_P2()), Math.round(figura.getY1_P2()), Math.round(figura.getX2_P2()), Math.round(figura.getY1_P2()), pincel1);
                    canvas.drawLine(Math.round(figura.getX2_P2()), Math.round(figura.getY1_P2()), Math.round(figura.getX2_P2()), Math.round(figura.getY2_P2()), pincel1);
                    canvas.drawLine(Math.round(figura.getX2_P2()), Math.round(figura.getY2_P2()), Math.round(figura.getX1_P2()), Math.round(figura.getY2_P2()), pincel1);
                    canvas.drawLine(Math.round(figura.getX1_P2()), Math.round(figura.getY2_P2()), Math.round(figura.getX1_P2()), Math.round(figura.getY3_P2()), pincel1);
                    canvas.drawLine(Math.round(figura.getX1_P2()), Math.round(figura.getY3_P2()), Math.round(figura.getX3_P2()), Math.round(figura.getY3_P2()), pincel1);
                    canvas.drawLine(Math.round(figura.getX3_P2()), Math.round(figura.getY3_P2()), Math.round(figura.getX3_P2()), Math.round(figura.getY4_P2()), pincel1);
                    canvas.drawLine(Math.round(figura.getX3_P2()), Math.round(figura.getY4_P2()), Math.round(figura.getX4_P2()), Math.round(figura.getY4_P2()), pincel1);

                }

                if(patron == 3)
                {
                    canvas.drawLine(Math.round(figura.getX1_P3()), Math.round(figura.getY1_P3()), Math.round(figura.getX2_P3()), Math.round(figura.getY1_P3()), pincel1);
                    canvas.drawLine(Math.round(figura.getX1_P3()), Math.round(figura.getY2_P3()), Math.round(figura.getX2_P3()), Math.round(figura.getY1_P3()), pincel1);
                    canvas.drawLine(Math.round(figura.getX1_P3()), Math.round(figura.getY2_P3()), Math.round(figura.getX2_P3()), Math.round(figura.getY2_P3()), pincel1);

                }



                // Paint del Trazado
                Paint paint = new Paint();
                paint.setAntiAlias(true);

                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(4);
                paint.setColor(Color.WHITE);

                if(accion == "down" )
                {
                    path.moveTo(x, y);
                }
                if(accion == "move" && valido) {

                    path.lineTo(x, y);
                    String [] coord = new String[2];
                    float x_ant = 0;
                    float y_ant = 0;

                   // array_time.add(System.currentTimeMillis());

                    coord = ruta[posicion].split(";");
                    x_ant = Float.valueOf(coord[0]);
                    y_ant = Float.valueOf(coord[1]);

                    if(x!=x_ant || y != y_ant)
                    {

                        posicion ++;
                        ruta[posicion] = String.valueOf(x) + ";" + String.valueOf(y);
                        array_time.add(System.currentTimeMillis());
                    }

                    canvas.drawPath(path, paint);
                }
                if(accion == "up")
                {

                        String sirve =  figura.ValidarFigura(ruta,intento,patron);
                        Log.d("SIRVE", sirve);
                        if (sirve.compareTo("Correcto")== 0)
                        {
                            Toast.makeText(getBaseContext(),"Trazado Válido", Toast.LENGTH_SHORT).show();
                            array_time.add(System.currentTimeMillis());
                            total_time = array_time.get(array_time.size()-1) - array_time.get(0);

                            Log.d("TIEMPO TOTAL " + intento, String.valueOf(total_time));

                            RegistrarEnBD(ruta, array_time, usuario, patron, intento);
                            BD_backup();
                            intento++;

                            if (intento > 2)
                            {
                                patron ++;
                                intento = 1;
                            }

                            if(patron > 3)
                            {
                                patron = 0;
                                usuario ++;
                            }


                        }else Toast.makeText(getBaseContext(),"Trazado Inválido", Toast.LENGTH_SHORT).show();



                    Toast.makeText(getBaseContext(),"Size" + array_time.size(), Toast.LENGTH_SHORT).show();
                    array_time.clear();

                    canvas.drawColor(Color.BLACK);
                    path.reset();


                    if(patron == 1)
                    {
                        canvas.drawLine(Math.round(figura.getX1_P1()), Math.round(figura.getY1_P1()), Math.round(figura.getX1_P1()), Math.round(figura.getY2_P1()), pincel1);
                        canvas.drawLine(Math.round(figura.getX1_P1()), Math.round(figura.getY2_P1()), Math.round(figura.getX2_P1()), Math.round(figura.getY1_P1()), pincel1);
                        canvas.drawLine(Math.round(figura.getX2_P1()), Math.round(figura.getY1_P1()), Math.round(figura.getX3_P1()), Math.round(figura.getY2_P1()), pincel1);
                        canvas.drawLine(Math.round(figura.getX3_P1()), Math.round(figura.getY1_P1()), Math.round(figura.getX3_P1()), Math.round(figura.getY2_P1()), pincel1);
                    }
                    if (patron == 2)
                    {
                        canvas.drawLine(Math.round(figura.getX1_P2()), Math.round(figura.getY1_P2()), Math.round(figura.getX2_P2()), Math.round(figura.getY1_P2()), pincel1);
                        canvas.drawLine(Math.round(figura.getX2_P2()), Math.round(figura.getY1_P2()), Math.round(figura.getX2_P2()), Math.round(figura.getY2_P2()), pincel1);
                        canvas.drawLine(Math.round(figura.getX2_P2()), Math.round(figura.getY2_P2()), Math.round(figura.getX1_P2()), Math.round(figura.getY2_P2()), pincel1);
                        canvas.drawLine(Math.round(figura.getX1_P2()), Math.round(figura.getY2_P2()), Math.round(figura.getX1_P2()), Math.round(figura.getY3_P2()), pincel1);
                        canvas.drawLine(Math.round(figura.getX1_P2()), Math.round(figura.getY3_P2()), Math.round(figura.getX3_P2()), Math.round(figura.getY3_P2()), pincel1);
                        canvas.drawLine(Math.round(figura.getX3_P2()), Math.round(figura.getY3_P2()), Math.round(figura.getX3_P2()), Math.round(figura.getY4_P2()), pincel1);
                        canvas.drawLine(Math.round(figura.getX3_P2()), Math.round(figura.getY4_P2()), Math.round(figura.getX4_P2()), Math.round(figura.getY4_P2()), pincel1);

                     }

                    if(patron == 3)
                    {
                        canvas.drawLine(Math.round(figura.getX1_P3()), Math.round(figura.getY1_P3()), Math.round(figura.getX2_P3()), Math.round(figura.getY1_P3()), pincel1);
                        canvas.drawLine(Math.round(figura.getX1_P3()), Math.round(figura.getY2_P3()), Math.round(figura.getX2_P3()), Math.round(figura.getY1_P3()), pincel1);
                        canvas.drawLine(Math.round(figura.getX1_P3()), Math.round(figura.getY2_P3()), Math.round(figura.getX2_P3()), Math.round(figura.getY2_P3()), pincel1);

                    }


                    posicion = 0;


                }

            }

            public boolean onTouchEvent (MotionEvent evento)
            {
                int acc = evento.getAction();
                x = evento.getX();
                y = evento.getY();

                if(acc == MotionEvent.ACTION_DOWN)
                {
                    accion = "down";
                    total_time = 0;

                    for (int i = 0; i <ruta.length ; i++) {
                        ruta[i] = "null";
                    }

                    valido = figura.ValidarPtosIniciales(x, y, intento,patron);

                    if(valido){
                        Log.d("INICIO VALIDO", String.valueOf(x)+";"+String.valueOf(y));
                        ruta[0] = String.valueOf(x) + ";" + String.valueOf(y);
                    }


                }

                if(acc == MotionEvent.ACTION_MOVE)
                {
                    accion = "move";
                }

                if(acc == MotionEvent.ACTION_UP)
                {
                    accion = "up";
                }

                invalidate();
                return true;
            }


            public void RegistrarEnBD(String recorrido[], ArrayList<Long> array_time, int usuario, int patron, int intento )
            {
                long coord_time = 0;
                String [] aux = new String[2];
                float x = 0;
                float y = 0;
                float ct = 0;
                int puntos = array_time.size();

                for (int i = 0; i < array_time.size(); i++) {
                    if(i>0){
                        coord_time = (array_time.get(i) - array_time.get(i-1));
                        ct = Float.valueOf(coord_time)/1000;
                    }

                    aux = recorrido[i].split(";");
                    x = Float.valueOf(aux[0]);
                    y = Float.valueOf(aux[1]);
                    mydb.insertRegistro(usuario,patron,intento,ct,total_time,puntos,x,y);
     Log.d("LO DE LA BD: ",String.valueOf(usuario)+";"+String.valueOf(patron)+";"+String.valueOf(intento)+";"+String.valueOf(ct)+";"+String.valueOf(total_time)+";"+String.valueOf(array_time.size())+";"+String.valueOf(x)+";"+String.valueOf(y));
                }


            }


        // CLASE PARA EXPORTAR LA BASE DE DATOS



            public void BD_backup()
            {
                SQLiteDatabase db = mydb.getReadableDatabase();
                Cursor c =  db.rawQuery( "select * from Registros", null );

                try {
                    int rowcount = 0;
                    int colcount = 0;
                    File sdCardDir = Environment.getExternalStorageDirectory();
                    String filename = "MyBackUp.csv";
                    // the name of the file to export with
                    File saveFile = new File(sdCardDir, filename);
                    FileWriter fw = new FileWriter(saveFile);
                    BufferedWriter bw = new BufferedWriter(fw);
                    rowcount = c.getCount();
                    colcount = c.getColumnCount();
                    if (rowcount > 0) {
                        c.moveToFirst();
                        for (int i = 0; i < colcount; i++) {
                            if (i != colcount - 1) {
                                bw.write(c.getColumnName(i) + ",");
                            } else {
                                bw.write(c.getColumnName(i));
                            }
                        }
                        bw.newLine();
                        for (int i = 0; i < rowcount; i++) {
                            c.moveToPosition(i);
                            for (int j = 0; j < colcount; j++) {
                                if (j != colcount - 1)
                                    bw.write(c.getString(j) + ",");
                                else
                                    bw.write(c.getString(j));
                            }
                            bw.newLine();
                        }
                        bw.flush();
                        Log.d("EXPORTADO","EXPORTADO CON EXITO");
                    }
                } catch (Exception ex) {
                    if (db.isOpen()) {
                        db.close();

                    }
                }
            }




        }

}
