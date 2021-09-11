package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActualizarDatos extends AppCompatActivity {

    private EditText et_nombre, et_apellido, et_celular, et_correo;
    public static String NOMBRE_USE="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos);
        et_nombre = (EditText)findViewById(R.id.nombreAD);
        et_apellido = (EditText)findViewById(R.id.apellidoAD);
        et_celular = (EditText)findViewById(R.id.celularAD);
        et_correo=(EditText)findViewById(R.id.correoAD);

        Intent intent = getIntent();
        String tablaText = intent.getStringExtra(NOMBRE_USE);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        //Cursor consulta=admin.getUsuario(baseDatos,tablaText.split(" ")[1],tablaText.split(" ")[2]);

           /* while (consulta.moveToNext()){
                et_nombre.setText(consulta.getString(1));
                et_apellido.setText(consulta.getString(2));
                et_celular.setText(consulta.getString(4));
                et_correo.setText(consulta.getString(6));
            }
            consulta.close();*/
        admin.close();

    }

    public void actualizar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String correo=et_correo.getText().toString();
        String celular = et_celular.getText().toString();
        Intent intent = getIntent();
        String tablaText = intent.getStringExtra(NOMBRE_USE);

        admin.actualizarDatos(baseDatos,tablaText.split(" ")[1],nombre,apellido,celular,correo,tablaText.split(" ")[2]);
        baseDatos.close();
        admin.close();

        Intent intentSender=new Intent(this, LoginPropCarga.class);
        String dataStr=nombre+" "+tablaText.split(" ")[1]+" "+tablaText.split(" ")[2];
        intentSender.putExtra(LoginPropCarga.NOMBRE_USE,dataStr);
        startActivity(intentSender);
    }
}