package producteurs;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe modélise un parc éolien, producteur dépendant des conditions
 * extérieurs. En France, les productions éoliennes sont maximales les mois
 * d’hiver.
 * 
 * @author Anna Barraqué
 */
public class Eolien extends ProducteurCondExt {

    /**
     * Cette méthode est le constructeur initialisant le parc éolien avec des
     * valeurs par défaut (puissance maximale, mois actif, heures actif).
     */
    public Eolien() {
        super(1000000,
                new HashMap<>(Map.ofEntries(Map.entry("winter", 1.), Map.entry("spring", 0.75),
                        Map.entry("summer", 0.50), Map.entry("fall", 0.75))),
                new Double[][] { { 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1. },
                        { 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1. } });
    }

    /**
     * Cette méthode est le constructeur permettant de choisir les valeurs
     * d'initilisation du parc éolien.
     * 
     * @param puissanceMax est la valeur de la puissance maximale du parc éolien.
     * @param moisActif    associe a chaque saison un pourcentage de la puissance
     *                     maximale qui est produite.
     * @param heureActif   est un tableau de deux lignes et douze colonnes donnant
     *                     un pourcentage de puissance maximale produite pour chaque
     *                     heure de la journée.
     */
    public Eolien(double puissanceMax, HashMap<String, Double> moisActif, Double[][] heureActif) {
        super(puissanceMax, moisActif, heureActif);
    }

}
