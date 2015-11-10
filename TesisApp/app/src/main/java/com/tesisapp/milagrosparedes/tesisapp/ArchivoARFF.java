package com.tesisapp.milagrosparedes.tesisapp;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by Milagros Paredes on 05/11/2015.
 */
public class ArchivoARFF {
    public ArchivoARFF(String[] Data, int patron, int intento, ArrayList Nombres)
    {

        String filename = " ";
        String titulo = " ";

        if(intento == 1)
        {
            filename = "EntrenamientoP"+patron+".arff";
            titulo = "EntrenamientoP"+patron;
        }

        else
        {
            filename = "RegistrosP"+patron+".arff";
            titulo = "RegistrosP"+patron;
        }


        File dir = Environment.getExternalStorageDirectory();
        File[] Lista = dir.listFiles();
        Boolean existe = busqueda(Lista, filename);


        if(!existe)
        {
            CrearEncabezado(filename, dir, Nombres,titulo);
            CrearCuerpo(filename, dir, Data);
        }

        else
            CrearCuerpo(filename, dir, Data);


    }


    // CREAR ENCABEZADO DE ARCHIVO

        public void CrearEncabezado(String FileName, File dir, ArrayList Nombres, String titulo)
        {
            String atributos = "";
            String filename = FileName;
            File saveFile = new File(dir, filename);
            for (int pos = 0; pos < Nombres.size(); pos++) {
                if(pos == (Nombres.size()-1))
                    atributos = atributos + Nombres.get(pos).toString();
                else
                    atributos = atributos + Nombres.get(pos).toString()+",";
            }

            FileWriter fichero = null;
            PrintWriter wr = null;

            try {

                fichero = new FileWriter(saveFile,true);
                wr = new PrintWriter(fichero);

                wr.println("@relation " + titulo);

                for (int i = 0; i < 100; i++) {
                    wr.println("@attribute bag" + i + " relational");
                    wr.println("  @attribute x" + i + " real");
                    wr.println("  @attribute y" + i + " real");
                    wr.println("  @attribute coord_time" + i + " real");
                    wr.println("@end bag" + i);
                    wr.flush();
                }
                wr.println("@attribute cant_ptos numeric");
                wr.println("@attribute total_time real");
                wr.println("@attribute usuario {" + atributos + "}");
                wr.println("@data");

                wr.close();

            } catch (IOException e) {
               Log.d("FICHERO", "ERROR AL ESCRIBIR");
            }

            Log.d("ENCABEZADO","CREADO");
        }


        // ESCRIBIR DATOS

    public void CrearCuerpo(String FileName, File dir, String[] Data)
    {
        String filename = FileName;
        File saveFile = new File(dir, filename);
        String Aux[] = new String[6];

        FileWriter fichero = null;
        PrintWriter wr = null;

        try {
            fichero = new FileWriter(saveFile,true);
            wr = new PrintWriter(fichero);

            for (int i = 0; i < Data.length; i++) {
                if(Data[i].compareTo("0") != 0)
                {
                    Aux = Data[i].split(";");
                    if (i == 0)
                        wr.print('"' + Aux[0] + "," + Aux[1] + "," + Aux[2] + "\\"+"n");

                    if(i < 99)
                        wr.print(Aux[0] + "," + Aux[1] + "," + Aux[2] + "\\"+"n");

                    if (i == 99)
                        wr.print(Aux[0] + "," + Aux[1] + "," + Aux[2] + '"' + ",");
                }else
                {
                    if (i < 99)
                        wr.print(Data[i] +",0,0"+ "\\"+"n");

                    if(i == 99)
                        wr.print(Data[i] + ",0,0"+ '"' + ",");
                }


            }
            wr.println(Aux[3]+","+Aux[4]+","+Aux[5]);

            wr.flush();
            wr.close();


        }catch(IOException e){};



    }


        // BUSCAR FICHEROS

        public boolean busqueda(File[] lista, String nombreArchivo) {
        boolean existe = false;
        for (int i = 0; i < lista.length; i++) {
            if (nombreArchivo.equals(lista[i].getName())) {
                existe = true;
            }
        }
        return existe;
    }

    }
