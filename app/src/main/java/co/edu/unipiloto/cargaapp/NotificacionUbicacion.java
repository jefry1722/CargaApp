package co.edu.unipiloto.cargaapp;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class NotificacionUbicacion extends IntentService {

    public static final String EXTRA_MESSAGE="message";
    public static final int NOTIFICATION_ID=5453;
    private String tablaText="";

    public NotificacionUbicacion() {
        super("NotificacionUbicacion");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            tablaText=intent.getStringExtra(EXTRA_MESSAGE);
            mostrar_mensaje(tablaText.split(" ")[4].replace("_"," "));
        }
    }

    public void mostrar_mensaje(final String text){
        NotificationCompat.Builder builder=
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.sym_def_app_icon)
                        .setContentTitle("Ubicación")
                        .setContentText(text)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setVibrate(new long[]{0,1000})
                        .setAutoCancel(true);

        //Create an action
        Intent actionIntent=new Intent(this,ConsultarEstadoCargas.class);
        //actionIntent.putExtra(ConsultarEstadoCargas.NOMBRE_USE, tablaText.split(" ")[0]+" "+tablaText.split(" ")[1]+" "+tablaText.split(" ")[2]);
        PendingIntent actionPendingIntent= PendingIntent.getActivity(this,0,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentIntent(actionPendingIntent);

        //Issue the notificaction
        NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,builder.build());
    }
}