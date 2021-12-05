package consommateurs;

import java.util.ArrayList;


/**
 * Cette classe permet de construire un Foyer puis de calculer sa consommation
 * journalière et annuelle. Un Foyer est composé d'un ensemble d'appareils. Cet
 * ensemble est différent selon le type du foyer (particulier, entreprise,
 * unknown)
 * 
 * @author Anna Barraqué
 */
public class Foyer extends Consommateur {
    /**
     * Cette classe a 2 attributs: une liste d'appareils un type définissant la
     * liste d'appareils du foyer
     */

    private ArrayList<Consommateur> appareils;

    public Foyer(String name, int nbFrigo, int nbMachineCafe, int nbRadiateur) {
        super(name);
        this.appareils = new ArrayList<>();
        for(int i=0; i<nbFrigo; i++){
            appareils.add(new Consommateur("frigo"));
        }
        for(int i=0; i<nbMachineCafe; i++){
            appareils.add(new Consommateur("cafe"));
        }
        for(int i=0; i<nbRadiateur; i++){
            appareils.add(new Consommateur("radiateur"));
        }
        
    }

    public double[] getTable(){
        
        for(int i=0; i<appareils.size(); i++){

        }
    }
}