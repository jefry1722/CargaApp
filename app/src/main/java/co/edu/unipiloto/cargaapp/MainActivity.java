package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
        //AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        String rol = String.valueOf(et_rol.getSelectedItem());
        String nombre_tabla="";
        for(String name:rol.split(" ")){
            nombre_tabla+=name+"_";
        }
        nombre_tabla=nombre_tabla.substring(0, nombre_tabla.length()-1).toLowerCase();


        //Lógica de login
        String consulta=getUsuario(getApplicationContext().openOrCreateDatabase("administracion", Context.MODE_PRIVATE,null),id,password,nombre_tabla);

        if (consulta.equals("")){
            Toast.makeText(this, "El usuario no existe", Toast.LENGTH_SHORT).show();
        }
        else if(consulta.equals("c&t&i")){
            Toast.makeText(this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent intent = new Intent (this, Login.class);
            intent.putExtra(Login.NOMBRE_USER, consulta);
            startActivity(intent);
        }
    }

    private String getUsuario(SQLiteDatabase baseDatos,String id,String passwd, String rol){
        Cursor cursor=baseDatos.rawQuery("SELECT id,nombres,password FROM "+rol+" WHERE id=" + id,null);

        if(cursor.getCount()==0){
            cursor.close();
            return "";
        }else{
            String passwdStr;
            while(cursor.moveToNext()){
                passwdStr=cursor.getString(2);
                if(passwdStr.equals(passwd)){
                    String nombreCursor= cursor.getString(1);
                    cursor.close();
                    return nombreCursor;

                }
            }
            cursor.close();
            return "c&t&i";
        }
    }


}