package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RegistrarCamion extends AppCompatActivity {

    private EditText placa, marca, modelo, capacidad, color, kilometraje;
    public static String NOMBRE_USE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_camion);


        placa = (EditText)findViewById(R.id.Placa);
        marca = (EditText)findViewById(R.id.Marca);
        modelo = (EditText)findViewById(R.id.Modelo);
        capacidad = (EditText)findViewById(R.id.Capacidad);
        color = (EditText)findViewById(R.id.Color);
        kilometraje = (EditText) findViewById(R.id.Kilometraje);

    }

    public void registrarCamion(View view) {
        Intent intent = getIntent();
        String tablaText = intent.getStringExtra(NOMBRE_USE);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        String placaC = placa.getText().toString();
        String marcaC = marca.getText().toString();
        String modeloC = modelo.getText().toString();
        int capacidadC = Integer.parseInt(capacidad.getText().toString());
        String colorC = color.getText().toString();
        int kilometrajeC = Integer.parseInt(kilometraje.getText().toString());

        if (!placaC.isEmpty() && !marcaC.isEmpty() && !modeloC.isEmpty()&& !colorC.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("placa", placaC);
            registro.put("marca", marcaC);
            registro.put("modelo", modeloC);
            registro.put("capacidad", capacidadC);
            registro.put("color", colorC);
            registro.put("kilometros",kilometrajeC);
            registro.put("id_propietario", tablaText.split(" " )[1]);

            baseDatos.insert("camion", null, registro);
            baseDatos.close();
            placa.setText("");
            marca.setText("");
            modelo.setText("");
            capacidad.setText("");
            color.setText("");
            kilometraje.setText("");
            Toast.makeText(this, "Registro de camión exitoso", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}