package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Adicionar items al appbar
        getMenuInflater().inflate(R.menu.menu_conductor,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            //Codigo a ejecutar al seleccionar Item
            case R.id.action_asignar_camion:
                Intent i = new Intent (this, ConsultarSolicitudesAsignacionCamion.class);
                i.putExtra(ConsultarSolicitudesAsignacionCamion.NOMBRE_USE,nombreIntent);
                startActivity(i);
                return true;
            case R.id.action_asignar_conductor:
                Intent i2 = new Intent (this, ConsultarCamiones.class);
                i2.putExtra(ConsultarCamiones.NOMBRE_USE,nombreIntent);
                startActivity(i2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    public void onConsultarCargas(View view){
        Intent i = new Intent (this, ConsultarSolicitudesDeCarga.class);
        i.putExtra(ConsultarSolicitudesDeCarga.NOMBRE_USE,nombreIntent);
        startActivity(i);
    }

}