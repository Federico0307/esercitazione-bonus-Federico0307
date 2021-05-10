// Federico Moro             matricola: 70/89/00424

package com.example.myapplication;

import java.io.Serializable;
import java.util.Calendar;


public class Persona implements Serializable {


    //get e set
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Calendar getData_nascita() {
        return data_nascita;
    }

    public void setData_nascita(Calendar data_nascita) {
        this.data_nascita = data_nascita;
    }



    //dichiarazione variabili
    private String username;
    private String password;
    private String città;
    private String type;
    private Calendar data_nascita;


    public Persona() {
        this.username = "";
        this.password = "";
        this.città = "";
        this.type = "";
    }

    public Persona (String username, String password, String tipo, String città) {
        this.username = username;
        this.password = password;
        this.città = città;
        this.type = tipo;
    }

    public boolean equals(Object o){
        if (o == this)
            return true;

        if (!(o instanceof Persona))
            return false;

        Persona p = (Persona) o;

        if (this.getUsername().equals(p.getUsername()) && this.getPassword().equals(p.getPassword()))
            return true;

        return false;
    }
}

