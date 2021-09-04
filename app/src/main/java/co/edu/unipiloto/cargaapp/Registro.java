package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Registro extends AppCompatActivity {

    private EditText et_nombre, et_apellido, et_id, et_fecha, et_numero, et_passwd, et_correo;
    private Spinner et_rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        Intent intent = getIntent();

        et_nombre = (EditText)findViewById(R.id.nombre);
        et_apellido = (EditText)findViewById(R.id.apellido);
        et_id = (EditText)findViewById(R.id.id);
        et_fecha = (EditText)findViewById(R.id.fecha);
        et_numero = (EditText)findViewById(R.id.celular);
        et_rol = (Spinner) findViewById(R.id.rol);
        et_passwd=(EditText)findViewById(R.id.passwd);
        et_correo=(EditText)findViewById(R.id.correo);
    }

    public void registrarUsuario(View view) {
        String rol = String.valueOf(et_rol.getSelectedItem());

        //Tomar el nombre de la tabla a insertar los datos
        String nombre_tabla="";
        for(String name:rol.split(" ")){
            nombre_tabla+=name+"_";
        }
        nombre_tabla=nombre_tabla.substring(0, nombre_tabla.length()-1).toLowerCase();

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        String nombre = et_nombre.getText().toString();
        String apellido = et_apellido.getText().toString();
        String id = et_id.getText().toString();
        String fecha = et_fecha.getText().toString();
        String numero = et_numero.getText().toString();
        String password=et_passwd.getText().toString();
        String correo=et_correo.getText().toString();


        if (!nombre.isEmpty() && !apellido.isEmpty() && !id.isEmpty() && !fecha.isEmpty() && !numero.isEmpty() && !password.isEmpty() && !correo.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("nombres", nombre);
            registro.put("apellidos", apellido);
            registro.put("id", id);
            registro.put("fecha_nacimiento", fecha);
            registro.put("celular", numero);
            registro.put("password",password);
            registro.put("correo",correo);

            Cursor consulta=admin.getUsuario(baseDatos,id,nombre_tabla);
            if(consulta.getCount()>0){
                Toast.makeText(this, "Usuario ya existente", Toast.LENGTH_SHORT).show();
            }else{
                baseDatos.insert(nombre_tabla, null, registro);
                baseDatos.close();
                et_nombre.setText("");
                et_apellido.setText("");
                et_id.setText("");
                et_fecha.setText("");
                et_numero.setText("");
                et_passwd.setText("");
                et_correo.setText("");

                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
            }
            consulta.close();


        }
        else {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}