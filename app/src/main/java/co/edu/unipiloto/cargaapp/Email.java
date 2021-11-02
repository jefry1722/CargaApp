package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.text.Html;


import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email extends AppCompatActivity {

    private static String emailAccount = "cargaappupc@gmail.com";
    private static String emailPassword = "prueba01";
    public static String NOMBRE_USE="";
    private String tablaText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        Intent intent = getIntent();
        tablaText = intent.getStringExtra(NOMBRE_USE);

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.gmail.com");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailAccount, emailPassword);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailAccount));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(tablaText.split(" ")[3]));
            message.setSubject(tablaText.split(" ")[4].replace("-"," "));
            message.setText(tablaText.split(" ")[5].replace("-"," "));
            new SendMail().execute(message);

            Intent intent2 = new Intent(Email.this, LoginConductor.class);
            intent2.putExtra(LoginConductor.NOMBRE_USE, tablaText.split(" ")[0]+" "+tablaText.split(" ")[1]+" "+tablaText.split(" ")[2]);
            startActivity(intent2);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    private class SendMail extends AsyncTask<Message,String,String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Email.this, "Por favor, espera", "Enviando email...", true, false);
        }
        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Envío exitoso";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error al enviar el correo";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s.equals("Envío exitoso")){
                AlertDialog.Builder builder = new AlertDialog.Builder(Email.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color = '509324'>Envío exitoso</font>"));
                builder.setMessage("El email fue enviado exitosamente");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
            }
            else {
                Toast.makeText(getApplicationContext(),
                        "Algo salió mal", Toast.LENGTH_SHORT).show();
            }
        }
    }
}