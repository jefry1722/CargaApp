package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

public class ConsultarSolicitudesDeCarga extends AppCompatActivity {

    public static String NOMBRE_USE="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_solicitudes_de_carga);


        try {
            Intent intent = getIntent();
            String tablaText = intent.getStringExtra(NOMBRE_USE);
            ArrayList cargasArr= new ArrayList();
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            SQLiteDatabase baseDatos = admin.getReadableDatabase();
            Cursor consulta=admin.getSolicitudesDeCarga(baseDatos);

            while(consulta.moveToNext()){
                cargasArr.add(consulta.getString(0)+" "+consulta.getString(10)+" "+consulta.getString(11)+"\n"+consulta.getString(1)+" "+consulta.getString(2));
            }
            consulta.close();
            admin.close();
            ArrayAdapter listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,cargasArr);
            ListView listSolicitudesCarga=(ListView)findViewById(R.id.list_solicitudes_carga);
            listSolicitudesCarga.setAdapter(listAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //Pass the drink the user clicks on to DrinkActivity
                    Intent intent2= new Intent(ConsultarSolicitudesDeCarga.this,AplicarSolicitudesCarga.class);
                    intent2.putExtra(AplicarSolicitudesCarga.NOMBRE_USE, tablaText);
                    intent2.putExtra(AplicarSolicitudesCarga.ID_SOLICITUD, id);
                    startActivity(intent2);
                }
            };

            listSolicitudesCarga.setOnItemClickListener(itemClickListener);


        }
        catch (Exception e){
            Toast.makeText(this, "Error: "+e, Toast.LENGTH_SHORT).show();
        }



    }
}