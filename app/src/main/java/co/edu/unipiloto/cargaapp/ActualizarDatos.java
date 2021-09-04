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
        et_nombre = (EditText)findViewById(R.id.nombreAD);
        et_apellido = (EditText)findViewById(R.id.apellidoAD);
        et_celular = (EditText)findViewById(R.id.celularAD);
        et_correo=(EditText)findViewById(R.id.correoAD);

        Intent intent = getIntent();
        String tablaText = intent.getStringExtra(NOMBRE_TABLE);
        String idText=intent.getStringExtra(ID);

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        //Cursor consulta=admin.getUsuario(baseDatos,ID,NOMBRE_TABLE);
        et_nombre.setText(idText);
        et_apellido.setText(tablaText);

        /*if(consulta.getCount()==0){
            et_nombre.setText("NO");
        }
        else{
            while (consulta.moveToNext()){

                et_nombre.setText(consulta.getString(1));
                et_apellido.setText(consulta.getString(2));
                et_celular.setText(consulta.getString(4));
                et_correo.setText(consulta.getString(6));
            }
            consulta.close();
        }*/


    }

    public void actualizar(View view){



    }
}