package com.tesisapp.milagrosparedes.tesisapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Principal extends AppCompatActivity {

    private TRegistros mydb;
    NameAlert objeto;

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
            String[] ptos_selec = new String[100];
            String usuario = getIntent().getStringExtra("parametro");
            String consulta = "";
            String DatosAux[];
            String Datos[];
            float x, y;

            int intento = 1;
            int patron = 1; //CUAL PATRON ES!?
            int posicion = 0;

            boolean valido = false;

            ArrayList<Long> array_time = new ArrayList<Long>();
            long total_time;
                                        //Objetos
            Patron figura = new Patron();
            ArchivoARFF archivo;
                                        //Figura
            Rect rect;
            Paint circulo;

            Path path = new Path();

            Paint pincel1;
            Paint pincelText;



            public Lienzo(Context context) {
                super(context);
            }

            protected void onDraw (Canvas canvas){

                for (int i = 1; i < 4; i++) {
                   for (int j = 1; j < 5; j++)
                    {
                        consulta = "SELECT * FROM Registros WHERE patron = "+ i+" and intento = "+j;
                        Cursor c = mydb.getData(consulta);

                        ObtenerRegistros(c,i,j);
                    }
                 }


                canvas.drawColor(Color.BLACK);
                pincel1 = new Paint();
                pincel1.setAntiAlias(true);

                pincel1.setColor(Color.GRAY);
                pincel1.setStyle(Paint.Style.STROKE);
                pincel1.setStrokeWidth(5);

                circulo = new Paint();
                circulo.setAntiAlias(true);
                circulo.setColor(Color.GREEN);
                circulo.setStyle(Paint.Style.STROKE);
                circulo.setStrokeWidth(1);

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
                {//int band = 0;

                        String sirve =  figura.ValidarFigura(ruta,intento,patron);
                        Log.d("SIRVE", sirve);
                        if (sirve.compareTo("Correcto")== 0)
                        {

                            Toast.makeText(getBaseContext(),"Trazado Válido", Toast.LENGTH_SHORT).show();
                            array_time.add(System.currentTimeMillis());
                            total_time = array_time.get(array_time.size()-1) - array_time.get(0);

                            Log.d("TIEMPO TOTAL " + intento, String.valueOf(total_time));

                            RegistrarEnBD(ruta, array_time, usuario, patron, intento);

                            intento++;

                            if (intento > 4)
                            {
                                patron ++;
                                intento = 1;
                            }

                            if(patron > 3)
                            {
                                patron = 0;
                                Intent intent = new Intent(Principal.this, NameAlert.class);
                                startActivity(intent);
                            }


                        }else Toast.makeText(getBaseContext(),"Trazado Inválido", Toast.LENGTH_SHORT).show();


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
                    }else Toast.makeText(getBaseContext(),"Pto. inicial inválido", Toast.LENGTH_SHORT).show();


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

            // Registrar Datos en la BD

            public void RegistrarEnBD(String recorrido[], ArrayList<Long> array_time, String usuario, int patron, int intento )
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

                }


            }

            // Dividir y organizar los datos

            public void ObtenerRegistros(Cursor data, int patron, int intento)
            {
                Datos = new String[data.getCount()];
                DatosAux = new String[data.getCount()];
                ArrayList<String> usuarios = new ArrayList<String>();

                int posiciones[] = new int[100];
                int posicionesOrd[] = new int [100];
                int patronAnt = 0;


                int i = 0;
                boolean band = true; // Manejar ambos vectores

                VaciarVector(Datos);
                VaciarVector(DatosAux);

                if (data.moveToFirst()) {
                    do {
                        if(band)
                        {
                            if(i >0)
                            {
                                if(Datos[i-1].contains(data.getString(7)))
                                {
                                    Datos[i] = data.getString(2)+";"+data.getString(3)+";"+data.getString(5)+";"+data.getString(4)+";"+data.getString(6)+";"+data.getString(7);
                                    usuarios.add(data.getString(7));
                                    i++;
                                }else
                                {
                                    i = 0;
                                    DatosAux[0]= data.getString(2)+";"+data.getString(3)+";"+data.getString(5)+";"+data.getString(4)+";"+data.getString(6)+";"+data.getString(7);
                                    i++;
                                    band = false;

                                }


                            }else
                            {
                                Datos[i] = data.getString(2)+";"+data.getString(3)+";"+data.getString(5)+";"+data.getString(4)+";"+data.getString(6)+";"+data.getString(7);
                                usuarios.add(data.getString(7));
                                i++;
                            }

                        }else
                        {
                            DatosAux[i]= data.getString(2)+";"+data.getString(3)+";"+data.getString(5)+";"+data.getString(4)+";"+data.getString(6)+";"+data.getString(7);
                            usuarios.add(data.getString(7));
                            i++;
                        }
                    } while(data.moveToNext());

                }


                ArrayList<String> ArrayNombres = SimplificarArray(usuarios);
                int tam = ArrayNombres.size();

                SeleccionarPuntos(Datos, patron, intento, ArrayNombres);
                VaciarVector(Datos);

                for (int j = 0; j <= tam; j++) {
                    if(Datos[0].compareTo("null")!=0)
                    {
                        SeleccionarPuntos(Datos,patron, intento,ArrayNombres);
                        VaciarVector(Datos);
                    }else
                        RellenarVector(DatosAux);
                }


            }


            //Seleccionar los puntos finales


            public void SeleccionarPuntos(String[] Datos, int patron, int intento, ArrayList Nombres)
            {

                String Puntos[] = new String[100];
                int cant_registros = 0;


                for (int j = 0; j < Puntos.length; j++)
                {
                    Puntos[j] = "0";
                }

                for (int recorrido = 0; recorrido < Datos.length; recorrido++)
                {
                    if(Datos[recorrido].compareTo("null") != 0)
                   cant_registros++;
                }



                float tamaño = (float)cant_registros/(float)100;
                Log.d("TAMAÑO", String.valueOf(tamaño));
                int n = entero(tamaño);


                if(n == 0)          // Es flotante
                {
                    if(cant_registros <= 100)
                    {Log.d("DATOS","MENOR A 100");
                        for (int m = 0; m < cant_registros; m++) {
                            Puntos[m] = Datos[m];
                        }
                    }
                    else
                    {
                        if(cant_registros > 100 && cant_registros <= 200)
                        {Log.d("DATOS","MENOR A 200");
                            for (int idx = 0; idx < Puntos.length; idx++) {
                                if((idx * 2) < cant_registros)
                                    Puntos[idx] = Datos[idx*2];
                            }
                        }else{Log.d("DATOS","MAS DE 200");
                            for (int idx = 0; idx < Puntos.length; idx++) {
                                if((idx * 3) < cant_registros)
                                {
                                    Puntos[idx] = Datos[idx*3];
                                }

                            }}

                    }

                }else
                {
                    for (int px = 0; px < 100; px++) {
                        Puntos[px] = Datos[px * (int)tamaño];
                    }
                }
                for (int ii = 0; ii < Puntos.length; ii++) {
                    Log.d("PTOS SELECCIONADOS",Puntos[ii].toString());
                }

                archivo = new ArchivoARFF(Puntos,patron,intento, Nombres);

            }


            // SIMPLIFICAR ARRAY_LIST

            public ArrayList SimplificarArray(ArrayList arreglo)
            {
                int tamaño = 0;
                ArrayList<String> ArrayFinal = new ArrayList<String>();
                for(int i=0;i<arreglo.size();i++){
                    for(int j=0;j<arreglo.size()-1;j++){
                        if(i!=j){
                            if(arreglo.get(i).toString().compareTo(arreglo.get(j).toString()) == 0 || arreglo.get(i).toString().compareTo("null")==0){
                                arreglo.set(i,"null");
                            }
                        }
                    }
                }


                for (int i = 0; i < arreglo.size(); i++) {
                    if (arreglo.get(i).toString() != "null")
                        ArrayFinal.add(arreglo.get(i).toString());

                }



                return ArrayFinal;
            }

            // ES ENTERO O FLOTANTE

            public int entero(double x) {
                if (x % 1 == 0) {
                    return 1;       // es entero
                } else {
                    return 0;       //es flotante
                }
            }

            // VACIAR VECTOR STRING

            public void VaciarVector(String[] Datos)
            {
                for (int i = 0; i < Datos.length; i++) {
                    Datos[i] = "null";
                }

                Log.d("VECTOR VACIADO","VECTOR VACIADO");
            }


            // RELLENAR VECTOR DE DATOS A ANALIZAR

            public void RellenarVector(String[] DatosAux)
            {

                int pos = 0;
                boolean ocupado = false;
                String nombre = " ";

                //---- Identificar el nombre

                for (int recorrido = 0; recorrido < DatosAux.length; recorrido++) {
                    if(DatosAux[recorrido]!= null && !ocupado)
                    {
                        String[] cadena = DatosAux[recorrido].split(";");
                        nombre = cadena[5];
                        ocupado = true;
                    }
                }


                for (int i = 0; i < DatosAux.length; i++)
                {
                   if (DatosAux[i]!= null && DatosAux[i].contains(nombre))
                        {
                            Datos[pos] = DatosAux[i];
                            DatosAux[i] = null;
                            pos++;

                        }
                }

            }


         //CLASE PARA EXPORTAR LA BASE DE DATOS



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
