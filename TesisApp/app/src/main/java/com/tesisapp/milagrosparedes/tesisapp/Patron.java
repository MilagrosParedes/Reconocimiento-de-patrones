package com.tesisapp.milagrosparedes.tesisapp;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Milagros Paredes on 09/09/2015.
 */
public class Patron {

    private float rango = 40;
    private static String direccion_base; // Dirección del patrón base
    private static String direccion_sig; // Dirección de patrones siguientes
    private static int patron_actual = 0; // Dirección de patrones siguientes
    private static String pto_salida_base = "";
    private static String pto_salida_sig;
    private static String recorrido_base[] = {"null","null","null","null"}; // Recorrido de puntos del patrón base

    private float X1_P1, X2_P1, X3_P1, Y1_P1, Y2_P1; // Coordenadas de patrón 1 - W

    private float X1_P2, X2_P2, X3_P2, X4_P2, Y1_P2, Y2_P2, Y3_P2, Y4_P2; // Coordenadas de patrón 2 - Variante de cuadrado

    private float X1_P3, X2_P3, Y1_P3, Y2_P3; // Coordenadas de patrón 3 - Z


    public Patron()
    {
        X1_P1 = 250;
        X2_P1 = 400;
        X3_P1 = 550;
        Y1_P1 = 400;
        Y2_P1 = 700;

        X1_P2 = 200;
        X2_P2 = 500;
        X3_P2 = 400;
        X4_P2 = 300;
        Y1_P2 = 400;
        Y2_P2 = 700;
        Y3_P2 = 500;
        Y4_P2 = 600;

        X1_P3 = 200;
        X2_P3 = 500;
        Y1_P3 = 400;
        Y2_P3 = 700;



        direccion_sig = "null";
        pto_salida_sig = "";


    }

    public boolean ValidarPtosIniciales (float x, float y,int intento, int patron)
    {
        boolean valido = false;

        if(patron == 1)
        {
            if((x >=(X1_P1 - rango) && x <= (X1_P1 + rango) && y >= (Y1_P1 - rango) && y <= (Y1_P1 + rango))
                    ||(x >=(X3_P1 - rango) && x <= (X3_P1 + rango) && y >= (Y1_P1 - rango) && y <= (Y1_P1 + rango))  )
                valido = true;
        }

        if(patron == 2)
        {
            if((x >=(X1_P2 - rango) && x <= (X1_P2 + rango) && y >= (Y1_P2 - rango) && y <= (Y1_P2 + rango))
                    ||(x >=(X4_P2 - rango) && x <= (X4_P2 + rango) && y >= (Y4_P2 - rango) && y <= (Y4_P2 + rango))  )
                valido = true;
        }

        if (patron == 3)
        {
            if((x >=(X1_P3 - rango) && x <= (X1_P3 + rango) && y >= (Y1_P3 - rango) && y <= (Y1_P3 + rango))
                    ||(x >=(X2_P3 - rango) && x <= (X2_P3 + rango) && y >= (Y2_P3 - rango) && y <= (Y2_P3 + rango))  )
                valido = true;
        }

        return valido;
    }


    public String ValidarFigura (String[] ruta, int intento, int patron)      //Ruta del recorrido, número de intento y patron
    {

        String estado = "Incorrecto";
        String aux[] = ruta;
        String coord[] = new String[2];
        String puntos[] = new String[100];
        float xx,yy;
        int pos = 0;

        for (int i = 0; i < puntos.length; i++) {
            puntos[i]="null";
        }

        for (int z = 0; z <aux.length ; z++)
        {
            xx = 0;
            yy = 0;

            if(!(aux[z].compareTo("null")== 0))
            {
                coord = aux[z].split(";");
                xx = Float.valueOf(coord[0]);
                yy = Float.valueOf(coord[1]);


                if(patron == 1)
                {
                    if ((xx >= (X1_P1 - rango) && xx <= (X1_P1 + rango)) && (yy >= (Y1_P1 - rango) && yy <= (Y1_P1 + rango)))
                    {
                        puntos[pos] = "1";
                        pos++;
                    }
                    else if ((xx >= (X1_P1 - rango) && xx <= (X1_P1 + rango)) && (yy >= (Y2_P1 - rango) && yy <= (Y2_P1 + rango)))
                    {
                        puntos[pos] = "2";
                        pos++;
                    }
                    else if ((xx >= (X2_P1 - rango) && xx <= (X2_P1 + rango)) && (yy >= (Y1_P1 - rango) && yy <= (Y1_P1 + rango)))
                    {
                        puntos[pos] = "3";
                        pos++;
                    }
                    else if ((xx >= (X3_P1 - rango) && xx <= (X3_P1 + rango)) && (yy >= (Y2_P1 - rango) && yy <= (Y2_P1 + rango)))
                    {
                        puntos[pos] = "4";
                        pos++;
                    }
                    else if((xx >= (X3_P1 - rango) && xx <= (X3_P1 + rango)) && (yy >= (Y1_P1 - rango) && yy <= (Y1_P1 + rango)))
                    {
                        puntos[pos] = "5";
                        pos++;
                    }

                }

                if(patron == 2)
                {
                      if ((xx >= (X1_P2 - rango) && xx <= (X1_P2 + rango)) && (yy >= (Y1_P2 - rango) && yy <= (Y1_P2 + rango)))
                      {
                          puntos[pos] = "1";
                          pos++;
                      }
                      else if ((xx >= (X2_P2 - rango) && xx <= (X2_P2 + rango)) && (yy >= (Y1_P2 - rango) && yy <= (Y1_P2 + rango)))
                      {
                          puntos[pos] = "2";
                          pos++;
                      }
                      else if ((xx >= (X2_P2 - rango) && xx <= (X2_P2 + rango)) && (yy >= (Y2_P2 - rango) && yy <= (Y2_P2 + rango)))
                      {
                          puntos[pos] = "3";
                          pos++;
                      }
                      else if ((xx >= (X1_P2 - rango) && xx <= (X1_P2 + rango)) && (yy >= (Y2_P2 - rango) && yy <= (Y2_P2 + rango)))
                      {
                          puntos[pos] = "4";
                          pos++;
                      }
                      else if ((xx >= (X1_P2 - rango) && xx <= (X1_P2 + rango)) && (yy >= (Y3_P2 - rango) && yy <= (Y3_P2 + rango)))
                      {
                          puntos[pos] = "5";
                          pos++;
                      }
                      else if ((xx >= (X3_P2 - rango) && xx <= (X3_P2 + rango)) && (yy >= (Y3_P2 - rango) && yy <= (Y3_P2 + rango)))
                      {
                          puntos[pos] = "6";
                          pos++;
                      }
                      else if ((xx >= (X3_P2 - rango) && xx <= (X3_P2 + rango)) && (yy >= (Y4_P2 - rango) && yy <= (Y4_P2 + rango)))
                      {
                          puntos[pos] = "7";
                          pos++;
                      }
                      else if ((xx >= (X4_P2 - rango) && xx <= (X4_P2 + rango)) && (yy >= (Y4_P2 - rango) && yy <= (Y4_P2 + rango)))
                      {
                          puntos[pos] = "8";
                          pos++;
                      }
                }

                if(patron == 3)
                {
                    if ((xx >= (X1_P3 - rango) && xx <= (X1_P3 + rango)) && (yy >= (Y1_P3 - rango) && yy <= (Y1_P3 + rango)))
                    {
                        puntos[pos] = "1";
                        pos++;
                    }
                    else if ((xx >= (X2_P3 - rango) && xx <= (X2_P3 + rango)) && (yy >= (Y1_P3 - rango) && yy <= (Y1_P3 + rango)))
                    {
                        puntos[pos] = "2";
                        pos++;
                    }
                    else if ((xx >= (X1_P3 - rango) && xx <= (X1_P3 + rango)) && (yy >= (Y2_P3 - rango) && yy <= (Y2_P3 + rango)))
                    {
                        puntos[pos] = "3";
                        pos++;
                    }
                    else if ((xx >= (X2_P3 - rango) && xx <= (X2_P3 + rango)) && (yy >= (Y2_P3 - rango) && yy <= (Y2_P3 + rango)))
                    {
                        puntos[pos] = "4";
                        pos++;
                    }
                }



                }
        }

        recorrido_base = simplificar(puntos,patron);
        boolean direc = VerificarDireccion(recorrido_base,intento,patron);

        if(direc)
            estado = "Correcto";
        else
            estado = "Incorrecto";

        return estado;
    }

    public boolean VerificarDireccion (String recorrido[], int intento, int patron){

        boolean valido = false;

        if(patron != patron_actual)
        {
            pto_salida_base = "";
            direccion_base = "";
            patron_actual = patron;
        }

        if (patron == 1)
        {
            if(!(recorrido[4].compareTo("null") == 0))      // Patrón completo?
            {
                if(intento == 1)
                    pto_salida_base = recorrido[3];

                if (recorrido[0].compareTo("1") == 0 && recorrido[1].compareTo("2") == 0)
                    if(intento == 1)
                    {
                        direccion_base = "derecha";
                        valido = true;
                    }

                    else
                        direccion_sig = "derecha";

                else
                if(intento == 1)
                {
                    direccion_base = "izquierda";
                    valido = true;
                }
                else
                    direccion_sig = "izquierda";

                if(intento != 1)
                {
                    Log.d("DIRECCION BASE",direccion_base + "PATRON: "+patron_actual);
                    Log.d("DIRECCION " + intento,direccion_sig + "PATRON: "+patron_actual);
                    if (direccion_sig.compareTo(direccion_base)== 0 && recorrido[3].compareTo(pto_salida_base)== 0)
                        valido = true;

                    else
                        valido = false;

                }


            }else valido = false;

        }

        if(patron == 2)
        {
            if(!(recorrido[7].compareTo("null") == 0)){     // Patrón completo
                if(intento == 1)
                    pto_salida_base = recorrido[0];

                if (recorrido[0].compareTo("1") == 0 && recorrido[1].compareTo("2") == 0)
                    if(intento == 1)
                    {
                        direccion_base = "derecha";
                        valido = true;
                    }

                    else
                        direccion_sig = "derecha";

                else
                if(intento == 1)
                {
                    direccion_base = "izquierda";
                    valido = true;
                }
                else
                    direccion_sig = "izquierda";

                if(intento != 1)
                {
                  //  Log.d("DIRECCION BASE",direccion_base);
                  //  Log.d("DIRECCION " + intento,direccion_sig);
                    if (direccion_sig.compareTo(direccion_base)== 0 && recorrido[0].compareTo(pto_salida_base)== 0)
                        valido = true;

                    else
                        valido = false;

                }


            }else valido = false;

        }

        if(patron == 3)
        {
            if(!(recorrido[3].compareTo("null") == 0)){     // Patrón completo
                if(intento == 1)
                    pto_salida_base = recorrido[0];

                if (recorrido[0].compareTo("1") == 0 && recorrido[1].compareTo("2") == 0)
                    if(intento == 1)
                    {
                        direccion_base = "derecha";
                        valido = true;
                    }

                    else
                        direccion_sig = "derecha";

                else
                if(intento == 1)
                {
                    direccion_base = "izquierda";
                    valido = true;
                }
                else
                    direccion_sig = "izquierda";

                if(intento != 1)
                {
                    //  Log.d("DIRECCION BASE",direccion_base);
                    //  Log.d("DIRECCION " + intento,direccion_sig);
                    if (direccion_sig.compareTo(direccion_base)== 0 && recorrido[0].compareTo(pto_salida_base)== 0)
                        valido = true;

                    else
                        valido = false;

                }


            }else valido = false;
        }


return valido;
    }


    public  String[] simplificar (String vector[], int patron){
        int pos = 0;
        String vector_final[] = {"null","null","null","null","null","null","null","null"};


         for(int i=0;i<vector.length;i++){
                for(int j=0;j<vector.length-1;j++){
                    if(i!=j){
                        if(vector[i].compareTo(vector[j])== 0 || vector[i].compareTo("null")== 0){
                            vector[i]=" ";

                        }
                    }
                }
            }

            int n = vector.length;
            for (int k = 0; k <= n-1; k++){
                if(vector[k]!=" "){
                    vector_final[pos] = vector[k];
                    pos++;
                }
            }
        if(patron == 1)
        {
            vector_final[5] = "0";
            vector_final[6] = "0";
            vector_final[7] = "0";
        }
        if(patron == 3)
        {
            vector_final[4] = "0";
            vector_final[5] = "0";
            vector_final[6] = "0";
            vector_final[7] = "0";
        }


            for (int i = 0; i < vector_final.length; i++) {
                Log.d("VECTOR FINAL",vector_final[i]);

        }
        return vector_final;
    }




    public float getX1_P1() { return X1_P1; }

    public float getX2_P1() { return X2_P1; }

    public float getX3_P1() { return X3_P1; }

    public float getY1_P1() { return Y1_P1; }

    public float getY2_P1() { return Y2_P1; }


    public float getX1_P2() { return X1_P2; }

    public float getX2_P2() { return X2_P2; }

    public float getX3_P2() { return X3_P2; }

    public float getX4_P2() { return X4_P2; }

    public float getY1_P2() { return Y1_P2; }

    public float getY2_P2() { return Y2_P2; }

    public float getY3_P2() { return Y3_P2; }

    public float getY4_P2() { return Y4_P2; }



    public float getX1_P3() { return X1_P3; }

    public float getX2_P3() { return X2_P3; }

    public float getY1_P3() { return Y1_P3; }

    public float getY2_P3() { return Y2_P3; }


}
