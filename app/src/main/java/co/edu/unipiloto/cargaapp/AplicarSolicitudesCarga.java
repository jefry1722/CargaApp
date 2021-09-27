package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AplicarSolicitudesCarga extends AppCompatActivity {

    private TextView tv_data;
    public static String NOMBRE_USER="";
    private String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aplicar_solicitudes_carga);

        tv_data = findViewById(R.id.dataSolicitud);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USER);

        admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        baseDatos = admin.getWritableDatabase();
        Cursor consulta=admin.getSolicitudCarga(baseDatos, tablaText.split(" ")[3]);

        String data = "";
        while(consulta.moveToNext()){
            data += "Fecha de recolección: " +consulta.getString(0) + "\n" + "Hora de recolección: " + consulta.getString(1) + "\n" + "Dirección de recolección: " + consulta.getString(2) + "\n" + "Ciudad de recolección: " + consulta.getString(3) + "\n" + "Fecha de entrega: " + consulta.getString(4) + "\n" + "Hora de entrega: " + consulta.getString(5) + "\n" + "Dirección de entrega: " + consulta.getString(6) + "\n" + "Ciudad de entrega: " + consulta.getString(7) + "\n" + "ID solicitante: " + consulta.getString(8) + "\n" + "Nombres del solicitante: " + consulta.getString(9) + "\n" + "Apellidos del solicitante: " + consulta.getString(10) + "\n";
        }
        tv_data.setText(data);
        consulta.close();
    }

    public void aplicarSolicitud (View view) {
        admin.aplicarCarga(baseDatos, tablaText.split(" ")[1], tablaText.split(" ")[3]);
        Toast.makeText(this, "Ha aplicado a la carga seleccionada correctamente", Toast.LENGTH_SHORT).show();
        Intent intent2 = new Intent(AplicarSolicitudesCarga.this, LoginPropCamion.class);
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