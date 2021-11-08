package producteurs;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe modélise un parc éolien, producteur dépendant des conditions extérieurs.
 * En France, les productions PV sont maximales en été.
 * 
 * @author Anna Barraqué
 */
public class PV extends ProducteurCondExt{

    /**
     * Cette méthode est le constructeur initialisant le parc éolien avec des valeurs par défaut (puissance maximale, mois actif, heures actif).
     */  
    public PV(){
        //1MW
        super(1000000, 
        new HashMap<>(Map.ofEntries(Map.entry("winter", 0.50), Map.entry("spring", 0.75), Map.entry("summer", 1.), Map.entry("fall", 0.75))),
        new Double[][]{{0.,0.,0.,0.,0.,0.,0.25,0.25,0.50,0.75,1.,1.}, {1.,1.,1.,0.75,0.75,0.50,0.50,0.25,0.,0.,0.,0.}}
        ); 
    }

    /**
   * Cette méthode est le constructeur permettant de choisir les valeurs d'initilisation du parc PV.
   * @param puissanceMax est la valeur de la puissance maximale du parc PV.
   * @param moisActif associe a chaque saison un pourcentage de la puissance maximale qui est produite.
   * @param heureActif est un tableau de deux lignes et douze colonnes donnant un pourcentage de puissance maximale produite pour chaque heure de la journée.
   */
    public PV(double puissanceMax, HashMap<String, Double> moisActif, Double[][] heureActif){
        //1MW
        super(puissanceMax, moisActif, heureActif);
    }
   
 
    
}
