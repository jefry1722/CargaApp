package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //Pass the drink the user clicks on to DrinkActivity

                    Cursor consultaUbicacion=admin.getUltimaUbicacion(baseDatos,id+"");
                    String ubicacionStr="_";

                    if(consultaUbicacion.getCount()>0) {
                        ubicacionStr="";
                        while (consultaUbicacion.moveToNext()) {
                            ubicacionStr += "Longitud:" + consultaUbicacion.getString(1) + "\n_Latitud:" + consultaUbicacion.getString(0) + "\n_Linea:_" + consultaUbicacion.getString(2);
                        }
                    }

                    Intent intent= new Intent(ConsultarEstadoCargas.this, UltimaUbicacion.class);
                    intent.putExtra(UltimaUbicacion.NOMBRE_USE, tablaText+" "+id+" "+ubicacionStr);
                    startActivity(intent);

                }
            };

            listEstadoCarga.setOnItemClickListener(itemClickListener);

        }catch (Exception e){
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
        }

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Bot??n de regreso
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