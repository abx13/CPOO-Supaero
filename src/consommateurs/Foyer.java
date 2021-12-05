package consommateurs;

import java.util.ArrayList;

import Modelconso.Consommateurs;


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
        double[][] puiss = new double[this.appareils.size()][];
        for(int i=0; i<this.appareils.size(); i++){
            Consommateurs c = new Consommateurs(this.appareils.get(i).getName());
            puiss[i] = c.getTable();
        }

        double[] table = new double[puiss[0].length];
        for(int i=0; i< table.length; i++){
            for(int j=0; j<this.appareils.size(); j++){
                table[i] += puiss[j][i];
            }
        }
        return table;
    }

    
}