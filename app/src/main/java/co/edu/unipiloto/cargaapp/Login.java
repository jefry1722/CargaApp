package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    public static final String NOMBRE_USER="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String nombreText = intent.getStringExtra(NOMBRE_USER);
        TextView nombreView = findViewById(R.id.nombre);
        nombreView.setText(nombreText);
    }
}