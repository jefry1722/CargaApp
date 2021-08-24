package co.edu.unipiloto.cargaapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    private final String SQL_PROP_CAMION="create table propietario_de_camion(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text);";
    private final String SQL_PROP_CARGA="create table propietario_de_carga(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text);";
    private final String SQL_COND_CAMION="create table conductor_de_camion(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text);";

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
}
