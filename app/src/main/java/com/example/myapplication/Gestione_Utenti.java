// Federico Moro             matricola: 70/89/00424

package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Gestione_Utenti extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MyAdapter adapter;

    public ArrayList<Persona> persone = new ArrayList<>();
    public ArrayList<Persona> persone_copia;
    Persona user;
    Button torna_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestione__utenti);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        torna_home = findViewById(R.id.torna_home);

        // Trasferisce dall'activity HomeAdmin l'arraylist e l'user
        trasferisci_utente();

        persone_copia = new ArrayList<>(persone);
        adapter = new MyAdapter(persone_copia, this, user);
        recyclerView.setAdapter(adapter);

        torna_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showResult = new Intent(Gestione_Utenti.this, HomeAdmin.class);
                showResult.putExtra(Registration.PERSONA_PATH, persone);
                showResult.putExtra(Registration.UTENTE_PATH, user);
                startActivity(showResult);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
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