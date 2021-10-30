package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ConsultarCargasAsignadasPorConductor extends AppCompatActivity {

    public static String NOMBRE_USE="";
    private String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;
    private Cursor consulta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_cargas_asignadas_por_conductor);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USE);

        try{
            admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
            baseDatos = admin.getReadableDatabase();
            consulta=admin.getSolicitudesRecogidasPorConductor(baseDatos,tablaText.split(" ")[1]);

            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    consulta,new String[]{"informacion"},new int[]{android.R.id.text1},0);

            ListView listCargasRecogidas=(ListView)findViewById(R.id.list_cargas_recogidas);
            listCargasRecogidas.setAdapter(listAdapter);

            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //Pass the drink the user clicks on to DrinkActivity

                    Intent intent= new Intent(ConsultarCargasAsignadasPorConductor.this,VerCargaRecogida.class);
                    intent.putExtra(VerCargaRecogida.NOMBRE_USE, tablaText+" "+id);
                    startActivity(intent);

                }
            };

            listCargasRecogidas.setOnItemClickListener(itemClickListener);


        }catch (Exception e){
            Toast.makeText(this, "Error: " + e, Toast.LENGTH_SHORT).show();
            System.out.println(e);
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