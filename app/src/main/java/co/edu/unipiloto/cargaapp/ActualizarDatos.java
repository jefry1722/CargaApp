package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActualizarDatos extends AppCompatActivity {

    private EditText et_nombre, et_apellido, et_celular, et_correo;
    public static final String NOMBRE_TABLE="";
    public static final String ID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);
        et_nombre = (EditText)findViewById(R.id.nombre);
        et_apellido = (EditText)findViewById(R.id.apellido);
        et_celular = (EditText)findViewById(R.id.celular);
        et_correo=(EditText)findViewById(R.id.correo);
    }

    public void actualizar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getReadableDatabase();
        Cursor consulta=admin.getUsuario(baseDatos,ID,NOMBRE_TABLE);

        while (consulta.moveToNext()){
            et_nombre.setText(consulta.getString(1));
            et_apellido.setText(consulta.getString(2));
            et_celular.setText(consulta.getString(4));
            et_correo.setText(consulta.getString(6));
        }


    }
}