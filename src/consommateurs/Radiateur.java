package consommateurs;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe est la modélisation d'un radiateur, appareil cyclique.
 * 
 * @author Anna Barraqué
 */
public class Radiateur extends AppareilCyclique {

   /**
    * Cette méthode est le constructeur initialisant le radiateur avec des valeurs
    * par défaut.
    */
   public Radiateur() {

      super(1000, // 1000W pour 15m2
            new HashMap<>(Map.ofEntries(Map.entry("winter", 1.), Map.entry("spring", 0.5), Map.entry("summer", 0.),
                  Map.entry("fall", 0.5))),
            new Double[][] { { 0.75, 0.75, 0.75, 0.75, 0.75, 1., 1., 1., 1., 1., 1., 1. },
                  { 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 0.75, 0.75 } });
   }

   /**
    * Cette méthode est le constructeur permettant de choisir les valeurs
    * d'initilisation du radiateur.
    * 
    * @param puissanceMax est la valeur de la puissance maximale du radiateur.
    * @param moisActif    associe a chaque saison un pourcentage de la puissance
    *                     maximale qui est consommé.
    * @param heureActif   est un tableau de deux lignes et douze colonnes donnant
    *                     un pourcentage de puissance maximale consommée pour
    *                     chaque heure de la journée.
    * 
    */
   public Radiateur(double puissanceMax, HashMap<String, Double> moisActif, Double[][] heureActif) {
      // 1000W pour 15m2
      super(puissanceMax, moisActif, heureActif);
   }
}
