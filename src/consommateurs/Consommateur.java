package consommateurs;

import Modelconso.Consommateurs;

/**
 * Cette interface définit les méthodes que doit comporter tout consommateur.
 * 
 * @author Anna Barraqué
 */
public class Consommateur {

    private String name;

    public Consommateur(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    } 

    public double[] getTable(){
        Consommateurs c = new Consommateurs(this.name);
        return c.getTable();
    }

}