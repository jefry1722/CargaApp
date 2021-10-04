package co.edu.unipiloto.cargaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {


    private final String SQL_PROP_CAMION="create table propietario_de_camion(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text, password text, correo text);";
    private final String SQL_PROP_CARGA="create table propietario_de_carga(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text, password text, correo text);";
    private final String SQL_COND_CAMION="create table conductor_de_camion(id text primary key, nombres text, apellidos text, fecha_nacimiento date, celular text, password text, correo text);";
    private final String SQL_CAMION="create table camion(placa text primary key, marca text, modelo text, capacidad integer, color text, kilometros integer, id_propietario text);";
    private final String SQL_SOLICITUD_CARGA="create table solicitud_carga(id integer primary key autoincrement, fecha_recoleccion date, hora_recoleccion text, direccion_recoleccion text, ciudad_recoleccion text, fecha_entrega date, hora_entrega text, direccion_entrega text, ciudad_entrega text, id_propietario_carga text, id_propietario_camion text, estado text, contenido text, peso text);";

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase baseDatos) {
        baseDatos.execSQL(SQL_PROP_CAMION);
        baseDatos.execSQL(SQL_PROP_CARGA);
        baseDatos.execSQL(SQL_COND_CAMION);
        baseDatos.execSQL(SQL_CAMION);
        baseDatos.execSQL(SQL_SOLICITUD_CARGA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase baseDatos, int oldVersion, int newVersion) {
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "propietario_de_camion"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "propietario_de_carga"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "conductor_de_camion"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "camion"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "solicitud_carga"+ "'");
        onCreate(baseDatos);
    }

    public Cursor getUsuario(SQLiteDatabase baseDatos,String id,String rol) {
        Cursor cursor = baseDatos.rawQuery("SELECT id,nombres,apellidos,fecha_nacimiento,celular,password,correo FROM " + rol + " WHERE id=" + id, null);
        return cursor;
    }

    public void actualizarDatos(SQLiteDatabase baseDatos, String id,String nombre, String apellido, String celular, String correo, String nombre_tabla){
        ContentValues cv= new ContentValues();

        if (!nombre.trim().equals("")){
            cv.put("nombres",nombre);
        }
        if (!apellido.trim().equals("")){
            cv.put("apellidos",apellido);
        }
        if (!celular.trim().equals("")){
            cv.put("celular",celular);
        }
        if (!correo.trim().equals("")){
            cv.put("correo",correo);
        }
        String[] args= new String[]{id};
        baseDatos.update(nombre_tabla,cv,"id=?",args);
    }

    public void aplicarCarga(SQLiteDatabase baseDatos,String id_propietario_camion, String id_propietario_carga, String estado){
        ContentValues cv= new ContentValues();
        cv.put("id_propietario_camion" ,id_propietario_camion);
        cv.put("estado" ,estado);
        String[] args= new String[]{id_propietario_carga};
        baseDatos.update("solicitud_carga",cv,"id=?",args);

    }

    public void cambiarPassword(SQLiteDatabase baseDatos,String id, String password, String nombre_tabla){
        baseDatos.execSQL("UPDATE "+nombre_tabla+" SET password='"+password+"' WHERE id="+id);
    }

    public Cursor getSolicitudesDeCarga(SQLiteDatabase baseDatos){
        String query="SELECT s.id as _id,  p.nombres || ' ' || p.apellidos || ' ' || s.ciudad_recoleccion || ' ' || s.fecha_recoleccion || ' ' || s.hora_recoleccion as informacion\n" +
                "FROM `solicitud_carga` s\n" +
                "JOIN `propietario_de_carga` p ON (p.id=s.id_propietario_carga)\n" +
                "WHERE s.id_propietario_camion IS NULL";
        return baseDatos.rawQuery(query,null);
    }

    public Cursor getSolicitudCarga(SQLiteDatabase baseDatos, String id){
        String query="SELECT s.fecha_recoleccion, s.hora_recoleccion,s.direccion_recoleccion,s.ciudad_recoleccion,s.fecha_entrega,s.hora_entrega,s.direccion_entrega,s.ciudad_entrega,p.id,p.nombres,p.apellidos, s.id, s.contenido, s.peso\n" +
                "FROM `solicitud_carga` s\n" +
                "JOIN `propietario_de_carga` p ON (p.id=s.id_propietario_carga)\n" +
                "WHERE s.id ="+id;
        return baseDatos.rawQuery(query,null);
    }

    public Cursor getEstadoCargas(SQLiteDatabase baseDatos, String id){
        String query="SELECT id as _id, 'CARGA: ' || contenido || ' ' || peso || ' ESTADO: ' || estado as informacion\n"+
                "FROM `solicitud_carga`\n"+
                "WHERE id_propietario_carga="+id;
        return baseDatos.rawQuery(query,null);
    }
}
