package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ConsultarUbicacionCargasRecogidas extends AppCompatActivity {

    public static String NOMBRE_USE="";
    private String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;
    private Cursor consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_ubicacion_cargas_recogidas);
        Intent intent2 = getIntent();
        tablaText = intent2.getStringExtra(NOMBRE_USE);

        try {
            admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            baseDatos = admin.getReadableDatabase();
            consulta=admin.getSolicitudesRecogidasPorConductor(baseDatos,tablaText.split(" ")[1]);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    consulta,new String[]{"informacion"},new int[]{android.R.id.text1},0);

            ListView listSolicitudesCarga=(ListView)findViewById(R.id.list_consultar_solicitudes_por_camionUBICACION);
            listSolicitudesCarga.setAdapter(listAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent= new Intent(ConsultarUbicacionCargasRecogidas.this,Gps.class);
                    intent.putExtra(Gps.NOMBRE_USE, tablaText+" "+id);
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
        baseDatos.close();
        admin.close();
    }
}