package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class VerCargaRecogida extends AppCompatActivity {

    private TextView tv_data;
    public static String NOMBRE_USE="";
    private String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_carga_recogida);

        tv_data = findViewById(R.id.dataInformacionSolicitudRecogidaPorCamion);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USE);

        admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        baseDatos = admin.getReadableDatabase();
        Cursor consulta=admin.getSolicitudCarga(baseDatos, tablaText.split(" ")[3]);

        String data = "";
        while(consulta.moveToNext()){
            data += "Fecha de recolección: " +consulta.getString(0) + "\n" + "Hora de recolección: " + consulta.getString(1) + "\n" + "Dirección de recolección: " + consulta.getString(2) + "\n" + "Ciudad de recolección: " + consulta.getString(3) + "\n" + "Fecha de entrega: " + consulta.getString(4) + "\n" + "Hora de entrega: " + consulta.getString(5) + "\n" + "Dirección de entrega: " + consulta.getString(6) + "\n" + "Ciudad de entrega: " + consulta.getString(7) + "\n" + "ID solicitante: " + consulta.getString(8) + "\n" + "Nombres del solicitante: " + consulta.getString(9) + "\n" + "Apellidos del solicitante: " + consulta.getString(10) + "\n"+ "Contenido: " + consulta.getString(12) + "\n"+ "Peso: " + consulta.getString(13) + "\n";
        }
        tv_data.setText(data);
        consulta.close();
    }

    protected void onDestroy() {
        super.onDestroy();
        baseDatos.close();
        admin.close();
    }
}