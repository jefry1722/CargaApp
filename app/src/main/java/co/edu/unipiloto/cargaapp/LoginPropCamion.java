package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginPropCamion extends AppCompatActivity {

    public static String NOMBRE_USE="";
    private String nombreIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prop_camion);

        Intent intent = getIntent();
        String nombreText = intent.getStringExtra(NOMBRE_USE);
        nombreIntent=nombreText+"";
        TextView nombreView = findViewById(R.id.propCamion_login);
        nombreView.setText(nombreIntent.split(" ")[0]);
    }

    public void onCerrarSesion (View view){
        Intent i = new Intent (this, MainActivity.class);
        startActivity(i);
    }

    public void onActualizarDatos(View view){
        Intent i = new Intent (this, ActualizarDatos.class);
        i.putExtra(ActualizarDatos.NOMBRE_USE,nombreIntent);
        startActivity(i);
    }

    public void onCambiarContra(View view){
        Intent i = new Intent (this, CambiarPassword.class);
        i.putExtra(CambiarPassword.NOMBRE_USE,nombreIntent);
        startActivity(i);
    }

    public void onRegistrarCamion(View view){
        Intent i = new Intent (this, RegistrarCamion.class);
        i.putExtra(RegistrarCamion.NOMBRE_USE,nombreIntent);
        startActivity(i);
    }

}