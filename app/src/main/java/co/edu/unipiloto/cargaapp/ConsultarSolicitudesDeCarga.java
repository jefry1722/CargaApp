package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ConsultarSolicitudesDeCarga extends AppCompatActivity {

    public static String NOMBRE_USE="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_solicitudes_de_carga);
        ArrayList cargasArr= new ArrayList();
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getReadableDatabase();
        Cursor consulta=admin.getSolicitudesDeCarga(baseDatos);

        while(consulta.moveToNext()){
            cargasArr.add(consulta.getString(10)+" "+consulta.getString(11)+"\n"+consulta.getString(1)+" "+consulta.getString(2));
        }
        consulta.close();
        admin.close();
        ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,cargasArr);
        ListView listSolicitudesCarga=(ListView)findViewById(R.id.list_solicitudes_carga);
        listSolicitudesCarga.setAdapter(listAdapter);
    }
}