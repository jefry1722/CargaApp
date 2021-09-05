package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CambiarPassword extends AppCompatActivity {

    private EditText et_nueva,et_confirmar;

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
            admin.cambiarPassword(baseDatos,"1",nueva,"propietario_de_carga");
        }else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }
}