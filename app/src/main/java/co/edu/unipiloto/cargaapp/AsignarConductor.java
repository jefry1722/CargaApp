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

public class AsignarConductor extends AppCompatActivity {

    private TextView tv_data;
    public static String NOMBRE_USE="";
    private String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;
    private Spinner et_conductores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asignar_conductor);

        tv_data = findViewById(R.id.dataCamion);
        et_conductores = findViewById(R.id.spinnerConductores);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USE);


        admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        baseDatos = admin.getReadableDatabase();
        Cursor consulta=admin.getCamionesInfo(baseDatos, tablaText.split(" ")[3]);
        Cursor consultaConductores=admin.getConductor(baseDatos);

        //Adapter del spinner
        ArrayList<String> conductores=new ArrayList<>();
        while(consultaConductores.moveToNext()){
            conductores.add(consultaConductores.getString(1));
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,conductores);
        et_conductores.setAdapter(adapter);

        String data = "";
        while(consulta.moveToNext()){
            data += "Placa: " +consulta.getString(0) + "\n" + "Marca: " + consulta.getString(1) + "\n" + "Modelo: " + consulta.getString(2) + "\n" + "Capacidad: " + consulta.getString(3) + "\n" + "Color: " + consulta.getString(4) + "\n" + "Kilometros: "+consulta.getString(5);
        }
        tv_data.setText(data);
        consulta.close();
    }

    public void actionAsignarConductor(View view){

        Spinner spinnerConductor = findViewById(R.id.spinnerConductores);
        String conductorStr = String.valueOf(spinnerConductor.getSelectedItem());
        admin.asignacionDeConductor(baseDatos, tablaText.split(" ")[3], conductorStr.split(" ")[0]);

        Toast.makeText(this, "Ha asignado correctamente el conductor", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(AsignarConductor.this, LoginPropCamion.class);
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