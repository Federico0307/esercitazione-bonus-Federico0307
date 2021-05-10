// Federico Moro             matricola: 70/89/00424

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class Modifica_Password extends AppCompatActivity {

    TextView nome, old_password;
    EditText new_password, conf_password;
    TextView testo_modifica_confermata;
    Button home, confirm_password;
    TextView err_newPassword, err_confPassword, err_diversePassword, err_pass_uguale_veccchia;

    Persona user, copia_user;
    ArrayList<Persona> persone = new ArrayList<>();
    String USERNAME, CHIAVE_VECCHIA, CHIAVE_NUOVA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica__password);

        nome = findViewById(R.id.title_username);
        old_password = findViewById(R.id.title_password);
        new_password = findViewById(R.id.nuova_password);
        conf_password = findViewById(R.id.conf_password);
        home = findViewById(R.id.back_home);
        confirm_password = findViewById(R.id.aggiorna_password);


        // messaggi di errore
        err_newPassword = findViewById(R.id.errore_nuovaPassword);
        err_confPassword = findViewById(R.id.errore_confermaPassword);
        err_diversePassword = findViewById(R.id.errore_passwordDiverse);
        testo_modifica_confermata = findViewById(R.id.text_modifica);
        err_pass_uguale_veccchia = findViewById(R.id.errore_password_uguale_precedente);

        trasferisci_utente();

        nome.setText(user.getUsername());
        old_password.setText(user.getPassword());

        setButton();

        confirm_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInput()) {

                    USERNAME = user.getUsername();
                    CHIAVE_VECCHIA = user.getPassword();
                    CHIAVE_NUOVA = new_password.getText().toString();

                    user = find_user(persone, USERNAME, CHIAVE_VECCHIA, CHIAVE_NUOVA);

                    confirm_password.setVisibility(View.GONE);
                    testo_modifica_confermata.setVisibility(View.VISIBLE);
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult;
                if (user.getType().equals("admin")) {
                    // dichiaro l'intenzione di andare alla prossima activity
                    showResult = new Intent (Modifica_Password.this, HomeAdmin.class);
                    showResult.putExtra (Registration.PERSONA_PATH, persone);
                    showResult.putExtra (Registration.UTENTE_PATH, user);
                } else {
                    // dichiaro l'intenzione di andare alla prossima activity
                    showResult = new Intent (Modifica_Password.this, Home.class);
                    showResult.putExtra (Registration.PERSONA_PATH, persone);
                    showResult.putExtra (Registration.UTENTE_PATH, user);
                }
                startActivity (showResult);
            }
        });
    }

    public boolean checkInput() {
        int errori = 0;
        String nuova_password, conferma_password;

        if (new_password.getText() == null || new_password.getText().length() == 0) {
            errori++;
            err_newPassword.setVisibility(View.VISIBLE);
        } else
            err_newPassword.setVisibility(View.GONE);

        if (conf_password.getText() == null || conf_password.getText().length() == 0) {
            errori++;
            err_confPassword.setVisibility(View.VISIBLE);
        } else
            err_confPassword.setVisibility(View.GONE);


        nuova_password = new_password.getText().toString();
        conferma_password = conf_password.getText().toString();

        if (nuova_password.equals(user.getPassword()))
        {
            err_pass_uguale_veccchia.setVisibility(View.VISIBLE);
            errori++;

        } else {
            err_pass_uguale_veccchia.setVisibility(View.GONE);

            if (conferma_password.equals(nuova_password)) {
                 err_diversePassword.setVisibility(View.GONE);
            } else {
                 errori++;
                 err_diversePassword.setVisibility(View.VISIBLE);
            }
        }

        return errori == 0;
    }

    public void setButton() {
        if (!(user.getPassword().equals(conf_password.getText().toString())))
        {
            confirm_password.setVisibility(View.VISIBLE);
            testo_modifica_confermata.setVisibility(View.GONE);
        } else {
            confirm_password.setVisibility(View.GONE);
            testo_modifica_confermata.setVisibility(View.VISIBLE);
        }
    }

    public Persona find_user (ArrayList<Persona> utenti, String nome, String chiave_vecchia, String chiave_nuova){

        for (Persona user: utenti) {
            if(user.getUsername().equals(nome) && user.getPassword().equals(chiave_vecchia)) {

                copia_user = user;
                //sostituisco la password vecchia con quella nuova
                copia_user.setPassword(chiave_nuova);
                persone.remove(user);
                persone.add(copia_user);

                return copia_user;
            }
        }
        return null;
    }

    public void trasferisci_utente() {

        Intent intent = getIntent();
        Serializable object = intent.getSerializableExtra(Registration.PERSONA_PATH);

        if (object != null) {
            persone = (ArrayList<Persona>) object;
            object = intent.getSerializableExtra (Registration.UTENTE_PATH);
            user = (Persona) object;
        }
    }
}
