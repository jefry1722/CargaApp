package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AsignarCamion extends AppCompatActivity {

    private TextView tv_data;
    public static String NOMBRE_USE="";
    private String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;
    private Spinner et_camiones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_camion);
        tv_data = findViewById(R.id.dataSolicitudSeleccionada);
        et_camiones = findViewById(R.id.spinnerCamiones);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USE);


        admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        baseDatos = admin.getReadableDatabase();
        Cursor consulta=admin.getSolicitudCarga(baseDatos, tablaText.split(" ")[3]);
        Cursor consultaCamiones=admin.getCamionesSolicitudDeCarga(baseDatos,tablaText.split(" ")[1]);

        //Adapter del spinner
        ArrayList<String> camiones=new ArrayList<>();
        while(consultaCamiones.moveToNext()){
            camiones.add(consultaCamiones.getString(1));
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,camiones);
        et_camiones.setAdapter(adapter);

        String data = "";
        while(consulta.moveToNext()){
            data += "Fecha de recolección: " +consulta.getString(0) + "\n" + "Hora de recolección: " + consulta.getString(1) + "\n" + "Dirección de recolección: " + consulta.getString(2) + "\n" + "Ciudad de recolección: " + consulta.getString(3) + "\n" + "Fecha de entrega: " + consulta.getString(4) + "\n" + "Hora de entrega: " + consulta.getString(5) + "\n" + "Dirección de entrega: " + consulta.getString(6) + "\n" + "Ciudad de entrega: " + consulta.getString(7) + "\n" + "ID solicitante: " + consulta.getString(8) + "\n" + "Nombres del solicitante: " + consulta.getString(9) + "\n" + "Apellidos del solicitante: " + consulta.getString(10) + "\n"+ "Contenido: " + consulta.getString(12) + "\n"+ "Peso: " + consulta.getString(13) + "\n";
        }
        tv_data.setText(data);
        consulta.close();
    }

    public void actionAsignarCamion(View view){

        Spinner spinnerCamion = findViewById(R.id.spinnerCamiones);
        String conductorStr = String.valueOf(spinnerCamion.getSelectedItem());
        admin.asignacionDeCamion(baseDatos, tablaText.split(" ")[3], conductorStr.split(" ")[0]);

        Toast.makeText(this, "Ha asignado correctamente el camion", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(AsignarCamion.this, LoginPropCamion.class);
        intent2.putExtra(LoginPropCamion.NOMBRE_USE, tablaText.split(" ")[0]+" "+tablaText.split(" ")[1]+" "+tablaText.split(" ")[2]);
        startActivity(intent2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        baseDatos.close();
        admin.close();
    }
}