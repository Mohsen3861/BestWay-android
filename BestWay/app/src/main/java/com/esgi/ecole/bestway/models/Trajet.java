package com.esgi.ecole.bestway.models;

/**
 * Created by mohsen raeisi on 07/09/2016.
 */
public class Trajet {
   private String temps;
   private int type;

    public Trajet(String temps, int type) {
        this.temps = temps;
        this.type = type;
    }

    public String getTemps() {
        return temps;
    }

    public void setTemps(String temps) {
        this.temps = temps;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
