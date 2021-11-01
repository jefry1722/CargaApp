package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.os.Bundle;
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
    private EditText etTo, etSubject, etMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        etTo = findViewById(R.id.box2);
        etSubject = findViewById(R.id.box3);
        etMessage = findViewById(R.id.box4);
        Button btnJava = (Button) findViewById(R.id.butJava);

        btnJava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            InternetAddress.parse(etTo.getText().toString().trim()));
                    message.setSubject(etSubject.getText().toString().trim());
                    message.setText(etMessage.getText().toString().trim());
                    new SendMail().execute(message);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
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
                        etTo.setText("");
                        etSubject.setText("");
                        etMessage.setText("");
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