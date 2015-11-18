package com.tesisapp.milagrosparedes.tesisapp;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Milagros Paredes on 05/11/2015.
 */
public class ArchivoARFF {
    public ArchivoARFF(String[] Data, int patron, int intento, ArrayList ArrayNombres)
    {
        Log.d("ARCHIVO","ENTRA AL ARCHIVO");
        String filename = " ";
        String titulo = " ";

        if(intento == 1)
        {
            filename = "EntrenamientoP"+patron+".arff";
            titulo = "EntrenamientoP"+String.valueOf(patron);
        }

        else
        {
            filename = "RegistrosP"+patron+".arff";
            titulo = "RegistrosP"+String.valueOf(patron);
        }


        File dir = Environment.getExternalStorageDirectory();
        File[] Lista = dir.listFiles();
        Boolean existe = busqueda(Lista, filename);


        if(!existe)
        {
            CrearEncabezado(filename, dir,titulo,ArrayNombres);
            CrearCuerpo(filename, dir, Data,ArrayNombres);
        }

        else
            CrearCuerpo(filename, dir, Data, ArrayNombres);








    }


    // CREAR ENCABEZADO DE ARCHIVO

        public void CrearEncabezado(String FileName, File dir, String titulo, ArrayList ArrayNombres)
        {

            String filename = FileName;
            File saveFile = new File(dir, filename);

            FileWriter fichero = null;
            PrintWriter wr = null;

            try {

                fichero = new FileWriter(saveFile,true);
                wr = new PrintWriter(fichero);

                wr.append("@relation " + titulo + "\n");

                for (int i = 1; i <= 100; i++)
                {
                    wr.append(" @attribute x"+ i +" real\n");
                    wr.append(" @attribute y"+ i +" real\n");
                    wr.append(" @attribute coord_time"+ i +" real\n");
                    wr.flush();
                }


                wr.append("@attribute cant_ptos numeric\n");
                wr.append("@attribute total_time real\n");
                wr.append("@attribute usuario {}\n");
                wr.append("@data\n");

                wr.close();

            } catch (IOException e) {
               Log.d("FICHERO", "ERROR AL ESCRIBIR");
            }

            Log.d("ENCABEZADO", "CREADO");
        }


        // ESCRIBIR DATOS

    public void CrearCuerpo(String FileName, File dir, String[] Data, ArrayList ArrayNombres)
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
                    wr.print(Aux[0] + "," + Aux[1] + "," + Aux[2]+",");
                }else
                {
                    if (i < 99)
                        wr.print(Data[i] +",0,0,");

                    if(i == 99)
                        wr.print(Data[i] + ",0,0,");
                }


            }
            wr.append(Aux[3] + "," + Aux[4] + "," + Aux[5] + "\n");

            wr.flush();
            wr.close();

            String LineaAnt = "@attribute usuario";
            ModificarEncabezado(saveFile,LineaAnt,ArrayNombres);




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


    public static  void ModificarEncabezado(File FficheroAntiguo,String Satigualinea,ArrayList ArrayNombres){

        Random numaleatorio= new Random(3816L);

        String SnombFichNuev=FficheroAntiguo.getParent()+"/auxiliar"+String.valueOf(Math.abs(numaleatorio.nextInt()))+".txt";
        String atributos = "";
        String Lineanva = "";

        for (int i = 0; i < ArrayNombres.size(); i++) {
            if(ArrayNombres.size()==1)
                atributos = atributos + ArrayNombres.get(i).toString();
            else
            {
                if(i != (ArrayNombres.size() -1))
                    atributos = atributos + ArrayNombres.get(i).toString()+",";
                else
                    atributos = atributos + ArrayNombres.get(i).toString();
            }

        }

        File FficheroNuevo=new File(SnombFichNuev);
        try {

            if(FficheroAntiguo.exists()){

                BufferedReader Flee= new BufferedReader(new FileReader(FficheroAntiguo));
                String Slinea;

                while((Slinea=Flee.readLine())!=null) {

                    if (Slinea.toUpperCase().trim().contains(Satigualinea.toUpperCase().trim())) {
                        Lineanva = Satigualinea+" {"+atributos+"}";
                        ReecribirFichero(FficheroNuevo,Lineanva);
                    }else{
                        ReecribirFichero(FficheroNuevo,Slinea);
                    }
                }


                String SnomAntiguo=FficheroAntiguo.getName();
                FficheroAntiguo.delete();

                FficheroNuevo.renameTo(FficheroAntiguo);
                /*Cierro el flujo de lectura*/
                Flee.close();
            }else{
                System.out.println("Fichero No Existe");
            }
        } catch (Exception ex) {
            /*Captura un posible error y le imprime en pantalla*/
            System.out.println(ex.getMessage());
        }
    }


    public static void ReecribirFichero(File Ffichero,String SCadena){
        try {
            //Si no Existe el fichero lo crea
            if(!Ffichero.exists()){
                Ffichero.createNewFile();
            }

            BufferedWriter Fescribe=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Ffichero,true), "utf-8"));

            Fescribe.write(SCadena + "\r\n");
            Fescribe.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

}
