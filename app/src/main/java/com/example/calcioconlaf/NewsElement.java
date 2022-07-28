package com.example.calcioconlaf;

public class NewsElement {
    private String orario;
    private String descrizione;
    private String autore;
    private String URL;
    private String link;

    public NewsElement(String orario, String descrizione, String URL, String autore, String link) {
        this.orario = orario;
        this.descrizione = descrizione;
        this.URL=URL;
        this.autore=autore;
        this.link=link;
    }

    public String getOrario() {
        return orario;
    }

    public void setOrario(String orario) {
        this.orario = orario;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String description) {
        this.descrizione = descrizione;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getURL() {
        return URL;
    }

    public String getLink() {
        return link;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
