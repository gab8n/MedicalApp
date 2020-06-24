package com.example.medicalapp;

public class User {
    public String nume, prenume, telefon, serie, CNP, CUI, numeFirma, eliberare, adresa, email, stare;

    public User(){

    }

    public User(String nume, String prenume, String telefon, String serie, String CNP, String CUI, String numeFirma, String eliberare, String adresa, String email, String stare) {
        this.nume = nume;
        this.prenume = prenume;
        this.telefon = telefon;
        this.serie = serie;
        this.CNP = CNP;
        this.CUI = CUI;
        this.numeFirma = numeFirma;
        this.eliberare = eliberare;
        this.adresa = adresa;
        this.email = email;
        this.stare = stare;
    }
}
