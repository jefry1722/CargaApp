package co.edu.unipiloto.cargaapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.os.Bundle;

public class Email extends AppCompatActivity {

    private static String emailAccount = "cargaappupc@gmail.com";
    private static String emailPassword = "prueba01";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        Button btnJava = (Button) findViewById(R.id.butJava);
        btnJava.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final ProgressDialog pd = ProgressDialog.show(Email.this, "Enviando", "Enviando correo Gmail usando Java API Email...", true, false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            EditText fromET = (EditText) findViewById(R.id.box1);
                            EditText toET = (EditText) findViewById(R.id.box2);
                            EditText subjET = (EditText) findViewById(R.id.box3);
                            EditText bodyET = (EditText) findViewById(R.id.box4);

                            String emailFrom = fromET.getText().toString();
                            String emailTo = toET.getText().toString();
                            String emailSubj = subjET.getText().toString();
                            String emailBody = bodyET.getText().toString();

                            GmailSender sender = new GmailSender(emailAccount, emailPassword);
                            sender.sendMail(emailSubj, emailBody, emailFrom, emailTo);
                        } catch (Exception e){
                            Log.e("SendMail", e.getMessage(), e);
                        }
                        pd.dismiss();
                    }
                }).start();
            }
        });
    }
}