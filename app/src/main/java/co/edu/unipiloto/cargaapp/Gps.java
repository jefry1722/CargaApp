package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.text.HtmlCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;
import static androidx.constraintlayout.motion.widget.Debug.getLocation2;

public class Gps extends AppCompatActivity {

    Button btLocation;
    TextView textView1, textView2, textView3, textView4, textView5, location_view;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static String NOMBRE_USE="";
    private String tablaText="";
    private AdminSQLiteOpenHelper admin;
    private SQLiteDatabase baseDatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USE);
        admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        btLocation = findViewById(R.id.bt_location);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 = findViewById(R.id.text_view3);
        textView4 = findViewById(R.id.text_view4);
        textView5 = findViewById(R.id.text_view5);
        location_view = findViewById(R.id.location_view);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        location_view.setText("Check permission");
        if (ActivityCompat.checkSelfPermission(Gps.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location_view.setText("when permission granted");
            getLocation();
        } else {
            location_view.setText("when permission denied");
            ActivityCompat.requestPermissions(Gps.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }


        getLocation();
    }

    public void getLocation() {
        baseDatos = admin.getWritableDatabase();
        if (ActivityCompat.checkSelfPermission(Gps.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            location_view.setText("Entrando a getLocation");
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                    new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            location_view.setText("Entrando a onComplete");
                            Location location = task.getResult();
                            location_view.setText("Entrando a task");
                            if (location != null) {
                                location_view.setText("Entrando a geocoder");
                                Geocoder geocoder = new Geocoder(Gps.this, Locale.getDefault());
                                location_view.setText("Entrando a try");
                                try {
                                    List<Address> addresses = geocoder.getFromLocation(
                                            location.getLatitude(), location.getLongitude(), 1);
                                    location_view.setText("Corriendo el try");
                                    textView1.setText("Latitud: " + (double) addresses.get(0).getLatitude());
                                    textView2.setText("Longitud: " + (double) addresses.get(0).getLongitude());
                                    textView3.setText("Pa??s: " + addresses.get(0).getCountryName());
                                    textView4.setText("Localidad: " + addresses.get(0).getLocality());
                                    textView5.setText("L??nea: " + addresses.get(0).getAddressLine(0));

                                    ContentValues registro = new ContentValues();
                                    registro.put("latitud", (double) addresses.get(0).getLatitude()+"");
                                    registro.put("longitud", (double) addresses.get(0).getLongitude()+"");
                                    registro.put("linea", addresses.get(0).getAddressLine(0)+"");
                                    registro.put("id_solicitud", tablaText.split(" ")[3]);

                                    baseDatos.insert("ubicacion", null, registro);

                                    Intent intent2 = new Intent(Gps.this, LoginConductor.class);
                                    intent2.putExtra(LoginConductor.NOMBRE_USE, tablaText.split(" ")[0]+" "+tablaText.split(" ")[1]+" "+tablaText.split(" ")[2]);
                                    startActivity(intent2);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    location_view.setText(e+"");
                                }
                            }
                        }
                    });
        }
        else {
            if (ActivityCompat.checkSelfPermission(Gps.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                location_view.setText("Entrando a geo2");
                getLocation2();
            }
        }
    }
}