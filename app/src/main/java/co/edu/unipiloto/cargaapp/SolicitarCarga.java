package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SolicitarCarga extends AppCompatActivity {

    private EditText fechar, horar, direccionr, ciudadr, fechae, horae, direccione, ciudade, contenido, peso;
    public static String NOMBRE_USE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_carga);

        fechar = (EditText)findViewById(R.id.FechaR);
        horar = (EditText)findViewById(R.id.HoraR);
        direccionr = (EditText)findViewById(R.id.DireccionR);
        ciudadr = (EditText)findViewById(R.id.CiudadR);
        fechae = (EditText)findViewById(R.id.FechaE);
        horae = (EditText) findViewById(R.id.HoraE);
        direccione = (EditText)findViewById(R.id.DireccionE);
        ciudade = (EditText) findViewById(R.id.CiudadE);
        contenido=(EditText)findViewById(R.id.Contenido);
        peso=(EditText)findViewById(R.id.Peso);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Botón de regreso
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void SolicitarTransporte(View view) {
        Intent intent = getIntent();
        String tablaText = intent.getStringExtra(NOMBRE_USE);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        String fechaR = fechar.getText().toString();
        String horaR = horar.getText().toString();
        String direccionR = direccionr.getText().toString();
        String ciudadR = ciudadr.getText().toString();
        String fechaE = fechae.getText().toString();
        String horaE = horae.getText().toString();
        String direccionE = direccione.getText().toString();
        String ciudadE = ciudade.getText().toString();
        String contenidoStr=contenido.getText().toString();
        String pesoStr= peso.getText().toString();

        if (!fechaR.isEmpty() && !horaR.isEmpty() && !direccionR.isEmpty()&& !ciudadR.isEmpty() && !fechaE.isEmpty() && !horaE.isEmpty() && !direccionE.isEmpty()&& !ciudadE.isEmpty() && !contenidoStr.isEmpty() && !pesoStr.isEmpty()){
            ContentValues registro = new ContentValues();

            registro.put("fecha_recoleccion",fechaR);
            registro.put("hora_recoleccion", horaR);
            registro.put("direccion_recoleccion", direccionR);
            registro.put("ciudad_recoleccion", ciudadR);
            registro.put("fecha_entrega", fechaE);
            registro.put("hora_entrega", horaE);
            registro.put("direccion_entrega", direccionE);
            registro.put("ciudad_entrega", ciudadE);
            registro.put("id_propietario_carga", tablaText.split(" " )[1]);
            registro.put("estado", "Pendiente");
            registro.put("contenido",contenidoStr);
            registro.put("peso",pesoStr);

            baseDatos.insert("solicitud_carga", null, registro);
            baseDatos.close();
            fechar.setText("");
            horar.setText("");
            direccionr.setText("");
            ciudadr.setText("");
            fechae.setText("");
            horae.setText("");
            direccione.setText("");
            ciudade.setText("");
            contenido.setText("");
            peso.setText("");

            Toast.makeText(this, "La solicitud de transporte fue enviada con éxito", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}