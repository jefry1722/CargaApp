package co.edu.unipiloto.cargaapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    private final String SQL_PROP_CAMION="create table propietario_de_camion(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text, password text, correo text);";
    private final String SQL_PROP_CARGA="create table propietario_de_carga(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text, password text, correo text);";
    private final String SQL_COND_CAMION="create table conductor_de_camion(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text, password text, correo text);";

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase baseDatos) {
        baseDatos.execSQL(SQL_PROP_CAMION);
        baseDatos.execSQL(SQL_PROP_CARGA);
        baseDatos.execSQL(SQL_COND_CAMION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase baseDatos, int oldVersion, int newVersion) {
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "propietario_de_camion"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "propietario_de_carga"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "conductor_de_camion"+ "'");
        onCreate(baseDatos);
    }

    public Cursor getUsuario(SQLiteDatabase baseDatos,String id,String rol) {
        Cursor cursor = baseDatos.rawQuery("SELECT id,nombres,apellidos,fecha_nacimiento,celular,password,correo FROM " + rol + " WHERE id=" + id, null);
        return cursor;
    }

    public void actualizarDatos(SQLiteDatabase baseDatos, String id,String nombre, String apellido, String celular, String correo, String nombre_tabla){
        String update="UPDATE "+nombre_tabla+" SET ";
        if (!nombre.trim().equals("")){
            update+="nombres ='"+nombre+"',";
        }
        if (!apellido.trim().equals("")){
            update+="apellidos ='"+apellido+"',";
        }
        if (!celular.trim().equals("")){
            update+="celular =''"+celular+"',";
        }
        if (!correo.trim().equals("")){
            update+="correo ='"+correo+"'";
        }
        update+=" WHERE id="+id;
        baseDatos.execSQL(update);

    }

    public void cambiarPassword(SQLiteDatabase baseDatos,String id, String password, String nombre_tabla){
        baseDatos.execSQL("UPDATE "+nombre_tabla+" SET password='"+password+"' WHERE id="+id);
    }
}
