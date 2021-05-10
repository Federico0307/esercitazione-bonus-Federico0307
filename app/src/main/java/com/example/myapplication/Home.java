// Federico Moro             matricola: 70/89/00424

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    TextView nome, password, citta, data_nascita;
    Button back_login;
    TextView change_password, benvenuto;;
    Persona user;
    ArrayList<Persona> persone = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nome = findViewById(R.id.title_username);
        password = findViewById(R.id.title_password);
        citta = findViewById(R.id.title_città);
        data_nascita = findViewById(R.id.title_data);
        back_login = findViewById(R.id.logoutButton);
        benvenuto = findViewById(R.id.benvenuto_user);

        //per avere il testo sottolineato del TEXTedit di Modifica Password
        change_password = findViewById(R.id.cambio_password);
        SpannableString content = new SpannableString("Modifica Password");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        change_password.setText(content);

        // Trasferisce dall'activity Registration l'arraylist e l'user
        trasferisci_utente();

        nome.setText(user.getUsername());
        password.setText(user.getPassword());
        citta.setText(user.getCittà());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        data_nascita.setText(format.format(user.getData_nascita().getTime()));
        benvenuto.setText("Benvenuto, @" + user.getUsername());

        back_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Trasferisce dall'activity Registration l'arraylist e l'user
                trasferisci_utente();

                Intent showResult = new Intent (Home.this, MainActivity.class);
                showResult.putExtra(com.example.myapplication.Registration.PERSONA_PATH, persone);
                showResult.putExtra(com.example.myapplication.Registration.UTENTE_PATH, user);
                startActivity(showResult);
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent (Home.this, Modifica_Password.class);
                showResult.putExtra (Registration.PERSONA_PATH, persone);
                showResult.putExtra (Registration.UTENTE_PATH, user);
                startActivity (showResult);
            }
        });
    }

    public void trasferisci_utente() {

        Intent intent = getIntent();
        Serializable object = intent.getSerializableExtra(com.example.myapplication.Registration.PERSONA_PATH);

        if (object != null) {
            persone = (ArrayList<Persona>) object;
            object = intent.getSerializableExtra (com.example.myapplication.Registration.UTENTE_PATH);
            user = (Persona) object;
        }
    }
}