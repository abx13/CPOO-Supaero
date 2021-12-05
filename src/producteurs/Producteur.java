package producteurs;

import Modelconso.Producteurs;

/**
 * Cette interface définit les méthodes que doit comporter tout producteur.
 * 
 * @author Anna Barraqué
 */
public class Producteur {

    private String name;

    public Producteur(String name){
        this.name = name;
    }

    public String getName(){
        return this.getName();
    }

    public double[] getTable(){
        Producteurs p = new Producteurs(this.name);
        return p.getTable();
    }

    

}