package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

public class UltimaUbicacion extends AppCompatActivity {

    private TextView tv_data;
    public static String NOMBRE_USE="";
    private String tablaText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultima_ubicacion);

        tv_data = findViewById(R.id.datosUltimaUbicacion);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USE);
        String data=(tablaText.split(" ")[4].replace("_"," "));
        tv_data.setText(data);

    }
}