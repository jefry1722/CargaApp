package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    public static final String NOMBRE_USE="";
    public static final String TABLA="";
    public static final String ID="";
    private String nombreText;
    private String tablaText;
    private String idText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        nombreText = intent.getStringExtra(NOMBRE_USE);
        tablaText = intent.getStringExtra(TABLA);
        idText = intent.getStringExtra(ID);
        TextView nombreView = findViewById(R.id.user_login);
        nombreView.setText(nombreText+" "+tablaText+" "+idText);
    }

    public void onCerrarSesion (View view){
        Intent i = new Intent (this, MainActivity.class);
        startActivity(i);
    }

    public void onActualizarDatos(View view){
        Intent i = new Intent (this, ActualizarDatos.class);
        i.putExtra(ActualizarDatos.NOMBRE_TABLE,tablaText);
        i.putExtra(ActualizarDatos.ID,idText);
        startActivity(i);
    }
}