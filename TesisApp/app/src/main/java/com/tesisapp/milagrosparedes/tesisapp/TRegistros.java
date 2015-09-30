package com.tesisapp.milagrosparedes.tesisapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Milagros Paredes on 09/09/2015.
 */
public class TRegistros extends SQLiteOpenHelper {

    public static final  String DB_NAME ="Registros.db";
    public static final String TABLE_NAME = "Registros";
    public static final String FIELD_ID = "_id";
    public static final String FIELD_USUARIO = "usuario";
    public static final String FIELD_PATRON = "patron";
    public static final String FIELD_INTENTO = "intento";
    public static final String FIELD_X = "x";
    public static final String FIELD_Y = "y";
    public static final String FIELD_COORD_TIME = "coord_time";       // Tiempo entre una coordenada y otra
    public static final String FIELD_TOTAL_TIME = "total_time";      // Tiempo total de trazado
    public static final String FIELD_PUNTOS = "cant_ptos";            // Número de puntos que se tocan en el trazado

    public static final String CREATE_DB_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +"("+
            FIELD_ID + " integer primary key autoincrement,"+
            FIELD_USUARIO + " text,"+
            FIELD_PATRON + " integer not null,"+
            FIELD_INTENTO + " integer not null,"+
            FIELD_X + " real not null,"+
            FIELD_Y + " real not null,"+
            FIELD_PUNTOS + " integer not null," +
            FIELD_COORD_TIME + " real,"+
            FIELD_TOTAL_TIME + " long"
            + ")";


    public TRegistros(Context context)
    {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_DB_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
        onCreate(db);
        Log.d("ACTUALIZACION DE BD", "ACTUALIZANDO BD");
    }

    public boolean insertRegistro (String usuario, int patron, int intento, float coord_time, long total_time, int puntos, float x, float y)
    {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(FIELD_USUARIO,usuario);
        contentValues.put(FIELD_PATRON,patron);
        contentValues.put(FIELD_INTENTO,intento);
        contentValues.put(FIELD_X,x);
        contentValues.put(FIELD_Y,y);
        contentValues.put(FIELD_PUNTOS,puntos);
        contentValues.put(FIELD_COORD_TIME,coord_time);
        contentValues.put(FIELD_TOTAL_TIME,total_time);
        db.insert(TABLE_NAME, null, contentValues);
        Log.d("INSERTADO!!","REGISTRO EXITOSO");

        return true;
    }

/*


---------------------------------- OBTENER UN DATO EN ESPECÍFICO ------------------

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts where id="+id+"", null );
        return res;
    }

-------------------------------- OBTENER NÚMERO DE REGISTROS -------------------------

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

------------------------------- ACTUALIZAR REGISTRO ESPECÍFICO -----------------------

    public boolean updateContact (Integer id, String name, String phone, String email, String street,String place)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

----------------------------------- ELIMINAR REGISTRO ESPECÍFICO -----------------------

    public Integer deleteContact (Integer id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("contacts",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

---------------------------------- OBTENER TODOS LOS REGISTROS ----------------------------

    public ArrayList<String> getAllCotacts()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from contacts", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    } */

}