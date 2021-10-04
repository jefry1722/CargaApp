package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class LoginPropCarga extends AppCompatActivity {

    public static String NOMBRE_USE="";
    private String nombreIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_prop_carga);

        Intent intent = getIntent();
        String nombreText = intent.getStringExtra(NOMBRE_USE);
        nombreIntent=nombreText+"";
        TextView nombreView = findViewById(R.id.user_login);
        nombreView.setText(nombreIntent.split(" ")[0]);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Adicionar items al appbar
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            //Codigo a ejecutar al seleccionar Item
            case R.id.action_solicitar_carga:
                Intent i = new Intent (this, SolicitarCarga.class);
                i.putExtra(SolicitarCarga.NOMBRE_USE,nombreIntent);
                startActivity(i);
                return true;
            case R.id.action_ver_cargas_solicitadas:
                Intent i2 = new Intent (this, ConsultarEstadoCargas.class);
                i2.putExtra(ConsultarEstadoCargas.NOMBRE_USE,nombreIntent);
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
        i.putExtra(ActualizarDatos.NOMBRE_USE,nombreIntent);
        startActivity(i);
    }

    public void onSolicitarTransporte(View view){
        Intent i = new Intent (this, SolicitarCarga.class);
        i.putExtra(SolicitarCarga.NOMBRE_USE,nombreIntent);
        startActivity(i);
    }
}