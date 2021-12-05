package consommateurs;

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
        return this.getName();
    }

    public double[] getTable(){
        Consommateurs consoModele = new Consommateurs(this.name);
        return consoModele.getTable;
    }

}