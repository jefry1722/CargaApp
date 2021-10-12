package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private EditText et_id, et_passwd;
    private Spinner et_rol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent i = getIntent();
    }

    public void onButton(View view){
        Intent intent = new Intent (this, Registro.class);
        startActivity(intent);
    }

    public void loginButton(View view){
        //Declaraciones
        et_id = findViewById(R.id.idMain);
        et_passwd=findViewById(R.id.passMain);

        String password=et_passwd.getText().toString();
        String id = et_id.getText().toString();
        et_rol = findViewById(R.id.spinnerRol);

        String rol = String.valueOf(et_rol.getSelectedItem());
        String nombre_tabla="";
        for(String name:rol.split(" ")){
            nombre_tabla+=name+"_";
        }
        nombre_tabla=nombre_tabla.substring(0, nombre_tabla.length()-1).toLowerCase();


        //Lógica de login
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getReadableDatabase();
        Cursor consulta=admin.getUsuario(baseDatos,id,nombre_tabla);

        if (consulta.getCount()==0){
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }
        else{
            while (consulta.moveToNext()){
                if (consulta.getString(5).equals(password) && rol.equals("Propietario de carga")){
                    String datosIntent=consulta.getString(1)+" "+consulta.getString(0)+" "+nombre_tabla;
                    Intent intent = new Intent (this, LoginPropCarga.class);
                    intent.putExtra(LoginPropCarga.NOMBRE_USE,datosIntent);
                    //intent.putExtra(LoginPropCarga.ID_USE, datosIntent);
                    startActivity(intent);
                }
                else if (consulta.getString(5).equals(password) && rol.equals("Propietario de camion")){
                    String datosIntent=consulta.getString(1)+" "+consulta.getString(0)+" "+nombre_tabla;
                    Intent intent = new Intent (this, LoginPropCamion.class);
                    intent.putExtra(LoginPropCamion.NOMBRE_USE,datosIntent);
                    startActivity(intent);
                }
                else if (consulta.getString(5).equals(password) && rol.equals("Conductor de camion")){
                    String datosIntent=consulta.getString(1)+" "+consulta.getString(0)+" "+nombre_tabla;
                    Intent intent = new Intent (this, LoginConductor.class);
                    intent.putExtra(LoginConductor.NOMBRE_USE,datosIntent);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
            consulta.close();

        }
    }


}