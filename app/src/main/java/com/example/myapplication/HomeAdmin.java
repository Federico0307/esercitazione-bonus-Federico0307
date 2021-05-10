// Federico Moro             matricola: 70/89/00424

package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class HomeAdmin extends AppCompatActivity {

    TextView nome, password, città, data;
    Button gestisci_utenti, logout;
    TextView modifica_password, benvenuto;

    ArrayList<Persona> persone = new ArrayList<>();
    Persona user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        nome = findViewById(R.id.title_username);
        password = findViewById(R.id.title_password);
        città = findViewById(R.id.title_città);
        data = findViewById(R.id.title_data);

        gestisci_utenti = findViewById(R.id.gestisci_utenti);
        logout = findViewById(R.id.logoutButton);

        benvenuto = findViewById(R.id.benvenuto_user);
        //per avere il testo sottolineato del TEXTedit di Modifica Password
        modifica_password = findViewById(R.id.cambio_password);
        SpannableString content = new SpannableString("Modifica Password");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        modifica_password.setText(content);

        // Trasferisce dall'activity MainActivity l'arraylist e l'user
        trasferisci_utente();

        benvenuto.setText("Benvenuto, admin @" + user.getUsername());
        nome.setText(user.getUsername());
        password.setText(user.getPassword());
        città.setText(user.getCittà());
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        data.setText(format.format(user.getData_nascita().getTime()));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Trasferisce dall'activity Registration l'arraylist e l'user
                trasferisci_utente();

                Intent showResult = new Intent (HomeAdmin.this, MainActivity.class);
                showResult.putExtra(com.example.myapplication.Registration.PERSONA_PATH, persone);
                showResult.putExtra(com.example.myapplication.Registration.UTENTE_PATH, user);
                startActivity(showResult);
            }
        });

        modifica_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent (HomeAdmin.this, Modifica_Password.class);
                showResult.putExtra (Registration.PERSONA_PATH, persone);
                showResult.putExtra (Registration.UTENTE_PATH, user);
                startActivity (showResult);
            }
        });

        gestisci_utenti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent (HomeAdmin.this, Gestione_Utenti.class);
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