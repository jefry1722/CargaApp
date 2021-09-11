package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
}