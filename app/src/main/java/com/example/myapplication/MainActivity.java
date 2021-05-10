// Federico Moro             matricola: 70/89/00424

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    TextView registrati;
    Button accedi;
    TextView errore_username, errore_password, errore_registrazione, errore_registrazione_utente;

    public ArrayList<Persona> persone = new ArrayList<>();
    Persona user;
    String USERNAME, PASSWORD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        errore_username = findViewById(R.id.errore_username);
        errore_password = findViewById(R.id.errore_password);
        errore_registrazione = findViewById(R.id.attr_errore_utenteNONpresente);
        accedi = findViewById(R.id.accediButton);
        errore_registrazione_utente = findViewById(R.id.attr_errore_utenteNONpresente);

        //per avere il testo sottolineato del TEXTedit di Registrati
        registrati = findViewById(R.id.registratiButton);
        SpannableString content = new SpannableString("Nuovo utente? Registrati");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        registrati.setText(content);

        crea_admin();

        // Trasferisce dall'activity Registration l'arraylist e l'user
        trasferisci_utente();

        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(MainActivity.this, com.example.myapplication.Registration.class);
                showResult.putExtra (com.example.myapplication.Registration.PERSONA_PATH, persone);
                startActivity(showResult);
            }
        });

        accedi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // controllo se i campi non sono vuoti
                if (checkInput()) {

                    USERNAME = username.getText().toString();
                    PASSWORD = password.getText().toString();

                    // metto in user quell'utente che ha come password e username le cose inserite negli EditText
                    user = searchByNameAndSurname (persone, USERNAME, PASSWORD);

                    // controllo se i dati inseriti sono validi
                    if (checkUser()) {

                        errore_registrazione_utente.setVisibility(View.GONE);

                        Intent showResult;
                        // controllo il tipo dell'utente inserito
                        if (user.getType().equals("admin")) {
                            // dichiaro l'intenzione di andare alla prossima activity
                            showResult = new Intent(MainActivity.this, HomeAdmin.class);
                            showResult.putExtra(Registration.PERSONA_PATH, persone);
                            showResult.putExtra(Registration.UTENTE_PATH, user);
                        } else {
                            // dichiaro l'intenzione di andare alla prossima activity
                            showResult = new Intent(MainActivity.this, Home.class);
                            showResult.putExtra(Registration.PERSONA_PATH, persone);
                            showResult.putExtra(Registration.UTENTE_PATH, user);
                        }
                        startActivity(showResult);
                    } else {
                        errore_registrazione_utente.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    public void crea_admin() {

        user = new Persona("admin", "admin", "admin", ""); //lascio il campo della città vuoto
        user.setData_nascita(Calendar.getInstance());

        persone.add(user);
    }

    public boolean checkInput() {
        int errori = 0;

        if (username.getText() == null || username.getText().length() == 0) {
            errori++;
            errore_username.setVisibility(View.VISIBLE);
        } else {
            errore_username.setVisibility(View.GONE);
        }

        if (password.getText() == null || password.getText().length() == 0) {
            errori++;
            errore_password.setVisibility(View.VISIBLE);
        } else {
            errore_password.setVisibility(View.GONE);
        }

        return errori == 0;
    }

    public boolean checkUser() {

        if (persone.contains (user))
            return true;

        return false;
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

    public Persona searchByNameAndSurname (ArrayList<Persona> utenti, String nome, String chiave){

        for (Persona user: utenti) {
            if(user.getUsername().equals(nome) && user.getPassword().equals(chiave))
                return user;
        }
        return null;
    }
}


