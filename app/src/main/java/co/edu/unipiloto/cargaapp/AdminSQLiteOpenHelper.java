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
    private final String SQL_CAMION="create table camion(id integer primary key autoincrement, placa text, marca text, modelo text, capacidad integer, color text, kilometros integer, id_propietario text, id_conductor text);";
    private final String SQL_SOLICITUD_CARGA="create table solicitud_carga(id integer primary key autoincrement, fecha_recoleccion date, hora_recoleccion text, direccion_recoleccion text, ciudad_recoleccion text, fecha_entrega date, hora_entrega text, direccion_entrega text, ciudad_entrega text, id_propietario_carga text, id_propietario_camion text, estado text, contenido text, peso text, id_camion text);";
    private final String SQL_UBICACION="create table ubicacion(id integer primary key autoincrement, latitud text, longitud text, fecha date, id_solicitud);";

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
        baseDatos.execSQL(SQL_UBICACION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase baseDatos, int oldVersion, int newVersion) {
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "propietario_de_camion"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "propietario_de_carga"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "conductor_de_camion"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "camion"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "solicitud_carga"+ "'");
        baseDatos.execSQL("DROP TABLE IF EXISTS '" + "ubicacion"+ "'");
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

    public Cursor getCamiones(SQLiteDatabase baseDatos,String id_propietario){
        String query="SELECT id as _id, 'PLACA: ' || placa || ' MARCA: ' || marca as informacion\n"+
                "FROM `camion`\n"+
                "WHERE id_propietario="+id_propietario+" AND id_conductor IS NULL";
        return baseDatos.rawQuery(query,null);
    }

    public Cursor getCamionesInfo(SQLiteDatabase baseDatos,String id){
        String query="SELECT placa, marca, modelo, capacidad, color, kilometros\n"+
                "FROM `camion`\n"+
                "WHERE id="+id;
        return baseDatos.rawQuery(query,null);
    }

    public Cursor getSolicitudCargaAplicadas(SQLiteDatabase baseDatos,String id){
        String query="SELECT id as _id, 'CARGA: ' || contenido || ' ' || peso || ' CIUDAD DE DESTINO: ' || ciudad_entrega as informacion\n"+
                "FROM `solicitud_carga`\n"+
                "WHERE id_propietario_camion="+id+" AND id_camion IS NULL";
        return baseDatos.rawQuery(query,null);
    }

    public Cursor getConductor(SQLiteDatabase baseDatos){
        String query="SELECT id as _id, id || ' ' || nombres || ' ' || apellidos as informacion\n"+
                "FROM `conductor_de_camion`";
        return baseDatos.rawQuery(query,null);
    }

    public void asignacionDeConductor(SQLiteDatabase baseDatos,String id_camion, String id_conductor){
        ContentValues cv= new ContentValues();
        cv.put("id_conductor" ,id_conductor);
        String[] args= new String[]{id_camion};
        baseDatos.update("camion",cv,"id=?",args);
    }

    public Cursor getCamionesSolicitudDeCarga(SQLiteDatabase baseDatos,String id_propietario_camion){
        String query="SELECT id as _id,id || ' ' || placa || ' ' || marca as informacion\n"+
                "FROM `camion`\n"+
                "WHERE id_propietario="+id_propietario_camion;
        return baseDatos.rawQuery(query,null);
    }


    public void asignacionDeCamion(SQLiteDatabase baseDatos, String id_solicitud, String id_camion){
        ContentValues cv= new ContentValues();
        cv.put("id_camion" ,id_camion);
        String[] args= new String[]{id_solicitud};
        baseDatos.update("solicitud_carga",cv,"id=?",args);
    }

    public Cursor getSolicitudesPorConductor(SQLiteDatabase baseDatos, String id_conductor){

        Cursor cursorCamiones=getCamionesPorConductor(baseDatos,id_conductor);
        String camionesStr="";

        while(cursorCamiones.moveToNext()){
            camionesStr+=cursorCamiones.getString(0)+",";
        }
        camionesStr=camionesStr.substring(0, camionesStr.length()-1);

        String query="SELECT s.id as _id, 'PLACA: ' ||c.placa || ' CIUDAD REC: ' || s.ciudad_recoleccion || ' FECHA REC: ' || s.fecha_recoleccion as informacion\n" +
                "FROM `solicitud_carga` s\n" +
                "JOIN `camion` c ON (c.id=s.id_camion)\n" +
                "WHERE s.id_camion IN ("+camionesStr+") AND s.estado='Tomado'";
        return baseDatos.rawQuery(query,null);
    }

    private Cursor getCamionesPorConductor(SQLiteDatabase baseDatos, String id_conductor){
        String query="SELECT id\n"+
                "FROM `camion`"+
                "WHERE id_conductor="+id_conductor;
        return baseDatos.rawQuery(query,null);
    }

    public void recogerCarga(SQLiteDatabase baseDatos,String id, String estado){
        ContentValues cv= new ContentValues();
        cv.put("estado" ,estado);
        String[] args= new String[]{id};
        baseDatos.update("solicitud_carga",cv,"id=?",args);
    }

    public Cursor getSolicitudesRecogidasPorConductor(SQLiteDatabase baseDatos, String id_conductor){

        Cursor cursorCamiones=getCamionesPorConductor(baseDatos,id_conductor);
        String camionesStr="";

        while(cursorCamiones.moveToNext()){
            camionesStr+=cursorCamiones.getString(0)+",";
        }
        camionesStr=camionesStr.substring(0, camionesStr.length()-1);

        String query="SELECT s.id as _id, 'PLACA: ' ||c.placa || ' CIUDAD DEST: ' || s.ciudad_entrega || ' FECHA DEST: ' || s.fecha_entrega as informacion\n" +
                "FROM `solicitud_carga` s\n" +
                "JOIN `camion` c ON (c.id=s.id_camion)\n" +
                "WHERE s.id_camion IN ("+camionesStr+") AND s.estado='Recogido'";
        return baseDatos.rawQuery(query,null);
    }

    public Cursor getCorreo(SQLiteDatabase baseDatos, String id){
        String query="SELECT p.correo\n" +
                "FROM `solicitud_carga` s\n" +
                "JOIN `propietario_de_carga` p ON (p.id=s.id_propietario_carga)\n" +
                "WHERE s.id ="+id;
        return baseDatos.rawQuery(query,null);
    }

    public Cursor getLugaresRecoger(SQLiteDatabase baseDatos, String id){
        String query="SELECT s.id as _id, s.direccion_recoleccion,s.ciudad_recoleccion\n" +
                "FROM `solicitud_carga` s\n" +
                "WHERE s.id ="+id;
        return baseDatos.rawQuery(query,null);
    }

    public Cursor getLugaresEntrega(SQLiteDatabase baseDatos, String id){
        String query="SELECT s.id as _id, s.direccion_entrega,s.ciudad_entrega \n" +
                "FROM `solicitud_carga` s\n" +
                "WHERE s.id ="+id;
        return baseDatos.rawQuery(query,null);
    }
}
