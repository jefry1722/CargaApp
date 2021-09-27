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
    public static String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;
    private Cursor consulta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_solicitudes_de_carga);
        Intent intent2 = getIntent();
        tablaText = intent2.getStringExtra(NOMBRE_USE);

        try {
            admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            baseDatos = admin.getReadableDatabase();
            consulta=admin.getSolicitudesDeCarga(baseDatos);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    consulta,new String[]{"informacion"},new int[]{android.R.id.text1},0);

            ListView listSolicitudesCarga=(ListView)findViewById(R.id.list_solicitudes_carga);
            listSolicitudesCarga.setAdapter(listAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //Pass the drink the user clicks on to DrinkActivity

                    Intent intent= new Intent(ConsultarSolicitudesDeCarga.this,AplicarSolicitudesCarga.class);
                    intent.putExtra(AplicarSolicitudesCarga.NOMBRE_USER, tablaText+" "+id);
                    startActivity(intent);

                }
            };

            listSolicitudesCarga.setOnItemClickListener(itemClickListener);


        }
        catch (Exception e) {
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        consulta.close();
        baseDatos.close();
        admin.close();
    }
}