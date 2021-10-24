package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginConductor extends AppCompatActivity {

    public static String NOMBRE_USE="";
    private String nombreIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_conductor);

        Intent intent = getIntent();
        String nombreText = intent.getStringExtra(NOMBRE_USE);
        nombreIntent=nombreText+"";
        TextView nombreView = findViewById(R.id.user_loginConductor);
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

    public void onConsultaRecoleccionCarga(View view){
        Intent i = new Intent (this, ConsultaRecoleccionCarga.class);
        i.putExtra(ConsultaRecoleccionCarga.NOMBRE_USE,nombreIntent);
        startActivity(i);
    }

    public void onConsultaEntregaCarga(View view){
        Intent i = new Intent (this, ConsultaEntregaDeCarga.class);
        i.putExtra(ConsultaEntregaDeCarga.NOMBRE_USE,nombreIntent);
        startActivity(i);
    }

}