package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CambiarPassword extends AppCompatActivity {

    private EditText et_nueva,et_confirmar;
    public static String NOMBRE_USE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_password);
        et_nueva = (EditText)findViewById(R.id.nuevaPass);
        et_confirmar = (EditText)findViewById(R.id.confirmarPass);
    }

    public void cambiarContra(View view){
        String nueva = et_nueva.getText().toString();
        String confirmar=et_confirmar.getText().toString();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        if (nueva.equals(confirmar)){
            Intent intent = getIntent();
            String tablaText = intent.getStringExtra(NOMBRE_USE);
            admin.cambiarPassword(baseDatos,tablaText.split(" ")[1],nueva,tablaText.split(" ")[2]);
            baseDatos.close();
            admin.close();

            Intent intentSender=new Intent(this, LoginPropCarga.class);
            intentSender.putExtra(LoginPropCarga.NOMBRE_USE,tablaText);
            startActivity(intentSender);
        }else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }
}