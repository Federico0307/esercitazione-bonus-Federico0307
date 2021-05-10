// Federico Moro             matricola: 70/89/00424

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Registration extends AppCompatActivity {

    //dichiarazione variabili
    EditText username, password, conferma_password, città, data_nascita;
    TextView errore_username, errore_password, errore_confpassword, errore_città, errore_dataNascita, errore_utente, errore_duplicato_username;
    Button registra, back;

    //Persona persona;
    Persona user;
    ArrayList<Persona> persone = new ArrayList<>();
    String NOME, CHIAVE;


    public static final String PERSONA_PATH = "com.example.persona";
    public static final String UTENTE_PATH = "com.example.utente";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // collegamento variabili con i blocchi XML
        username = findViewById(R.id.attr_username);
        password = findViewById(R.id.attr_password);
        conferma_password = findViewById(R.id.attr_confermapassword);
        città = findViewById(R.id.attr_citta);
        data_nascita = findViewById(R.id.attr_datanascita);
        registra = findViewById(R.id.registratiButton);
        back = findViewById(R.id.backButton);


        // gli errori possibili
        errore_username = findViewById(R.id.attr_errore_username);
        errore_password = findViewById(R.id.attr_errore_password);
        errore_confpassword = findViewById(R.id.attr_errore_confermapassword);
        errore_città = findViewById(R.id.attr_errore_città);
        errore_dataNascita = findViewById(R.id.attr_errore_data);
        errore_utente = findViewById(R.id.attr_errore_utente_presente);
        errore_duplicato_username = findViewById(R.id.attr_errore_username_gia_presente);

        // creare User da attaccare all'hashset e di tipo Persona
        user = new Persona();

        // fa apparire il calendario quando schiaccio sopra il TextEdit della data di nascita
        data_nascita.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showDialog();
                }
            }
        });

        registra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // riceve l'array con gia l'utente ADMIN
                trasferisci_utente();

                // controlla se tutti i dati sono inseriti
                if (checkInput())
                {
                    creaUser();
                    NOME = username.getText().toString();
                    CHIAVE = password.getText().toString();

                    // controlla se l'utente USER con i dati inseriti negli edit_text è gia presente
                    if (checkUser(persone, NOME, CHIAVE)) {

                        errore_utente.setVisibility(View.GONE);

                        // impedisce a due utenti di avere lo stesso username
                        if(checkUsername(persone, NOME)) {

                            errore_duplicato_username.setVisibility(View.GONE);

                            persone.add(user);

                            Intent showResult = new Intent(Registration.this, MainActivity.class);
                            showResult.putExtra(PERSONA_PATH, persone);
                            //showResult.putExtra(UTENTE_PATH, user);
                            startActivity(showResult);
                        } else {
                            errore_duplicato_username.setVisibility(View.VISIBLE);
                        }
                    } else {
                        errore_utente.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent (Registration.this, MainActivity.class);
                startActivity(showResult);
            }
        });
    }

    void showDialog() {
        DialogFragment newFragment = com.example.myapplication.DatePickerFragment.newInstance();
        newFragment.show(getSupportFragmentManager(), "dialog");
    }

    public void doPositiveClick(Calendar date) {
        // Do stuff here.
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/YYYY");
        data_nascita.setText(format.format(date.getTime())); //10/10/2020
    }

    public void doNegativeClick() {
        // Do stuff here.
    }

    public void updateDataDiNAscita(Calendar date) {
        this.user.setData_nascita(date);
    }

    public boolean checkInput () {
        int errors = 0;
        String Password, Conferma_password;

        if (username.getText() == null || username.getText().length() == 0) {
            errors++;
            username.setError("Inserire nome");    //setError fa comparire al lato il segno di errore
        }
        else username.setError(null);

        if (password.getText() == null || password.getText().length() == 0) {
            errors++;
            password.setError("Inserire password");
        }
        else password.setError(null);

        if (conferma_password.getText() == null || conferma_password.getText().length() == 0) {
            errors++;
            conferma_password.setError("Inserire password");
        }
        else conferma_password.setError(null);

        // controlla se le 2 password sono uguali
        Password = password.getText().toString();
        Conferma_password = conferma_password.getText().toString();

        if (Conferma_password.equals(Password))
        {
            errore_confpassword.setVisibility(View.GONE);
        } else {
            errors++;
            errore_confpassword.setVisibility(View.VISIBLE);
        }

        if (città.getText() == null || città.getText().length() == 0) {
            errors++;
            città.setError("Inserire indirizzo");
        }
        else città.setError(null);

        if (data_nascita.getText() == null || data_nascita.getText().length() == 0) {
            errors++;
            data_nascita.setError("Inserire data di nascita");
        }
        else data_nascita.setError(null);


        return errors == 0;
    }

    private void creaUser() {

        this.user.setUsername(this.username.getText().toString());
        this.user.setPassword(this.password.getText().toString());
        this.user.setCittà(this.città.getText().toString());
        this.user.setType("non_admin");
    }

    public void trasferisci_utente() {

        Intent intent = getIntent();
        Serializable object = intent.getSerializableExtra(Registration.PERSONA_PATH);

        if (object != null) {
            persone = (ArrayList<Persona>) object;
        }
    }

    public boolean checkUsername(ArrayList<Persona> utenti, String nome) {

        for (Persona user: utenti) {
            if(user.getUsername().equals(nome))
                //ritorna falso. nel senso che l'if non può andare avanti
                return false;
        }
        return true;
    }

    public boolean checkUser (ArrayList<Persona> utenti, String nome, String chiave){

        for (Persona user: utenti) {
            if(user.getUsername().equals(nome) && user.getPassword().equals(chiave))
                //ritorna falso. nel senso che l'if non può andare avanti
                return false;
        }
        return true;
    }
}
