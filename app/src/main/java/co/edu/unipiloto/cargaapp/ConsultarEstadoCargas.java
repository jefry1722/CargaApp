package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ConsultarEstadoCargas extends AppCompatActivity {

    public static String NOMBRE_USE="";
    private String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;
    private Cursor consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_estado_cargas);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USE);

        try{
            admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            baseDatos = admin.getReadableDatabase();
            consulta=admin.getEstadoCargas(baseDatos,tablaText.split(" ")[1]);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    consulta,new String[]{"informacion"},new int[]{android.R.id.text1},0);
            ListView listEstadoCarga=(ListView)findViewById(R.id.list_estado_cargas);
            listEstadoCarga.setAdapter(listAdapter);

        }catch (Exception e){
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Botón de regreso
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        consulta.close();
        baseDatos.close();
        admin.close();
    }
}