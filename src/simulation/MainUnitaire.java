package simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import consommateurs.*;
import producteurs.*;

/**
 * Cette classe est la classe executable permettant de générer les scénarii
 * unitaires pour chacun des consommateurs et producteurs. L'utilisateur peut
 * modifier les différents paramètres des consommateurs et producteurs ainsi que
 * la journée du rapport journalier directement dans la méthode. L'utilisateur
 * peut aussi mettre en commentaire certains consommateurs ou producteurs s'il
 * ne souhaite pas générer leur rapport énergétique. Ces rapports sont affichés
 * dans la console et sauvergardés dans des fichiers CSV plus facilement
 * consultables.
 * 
 * @author Anna Barraqué
 */
public class MainUnitaire {
    public static void main(String[] args) {

        // Vous pouvez changer le jour de l'annee pour lequel vous allez generer le
        // rapport journalier
        int journee = 1;

        
        // FRIGO//
        // Vous pouvez changer la valeur de la puissance maximale du frigo (en W)

        double puissanceMaxFrigo = 200;

        Consommateur frigo = new Frigo(puissanceMaxFrigo);
        SimulationScenario.playUnitaireConsommateur(frigo, journee);
        
        // RADIATEUR//
        // Vous pouvez changer la valeur de la puissance maximale du radiateur (en W)
        // ainsi que les pourcentages de consommation selon les saisons et les heures de
        // la journée :

        double puissanceMaxRadiateur = 1000;

        HashMap<String, Double> moisActifRadiateur = new HashMap<>(Map.ofEntries(Map.entry("winter", 1.),
                Map.entry("spring", 0.5), Map.entry("summer", 0.), Map.entry("fall", 0.5)));

        Double[][] heureActifRadiateur = new Double[][] { { 0.75, 0.75, 0.75, 0.75, 0.75, 1., 1., 1., 1., 1., 1., 1. },
                { 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 0.75, 0.75 } };

        Consommateur radiateur = new Radiateur(puissanceMaxRadiateur, moisActifRadiateur, heureActifRadiateur);
        SimulationScenario.playUnitaireConsommateur(radiateur, journee);
        
        // MACHINE A CAFE//

        // Vous pouvez changer la valeur de la puissance maximale de la machine a cafe
        // (en W)
        // ainsi que de la frequence d'utilisation (en min^(-1)), du temps d'utilisation
        // (en min), et de la plage horaire d'utilisation (en h)

        double puissanceMaxCafe = 800;
        double frequenceCafe = 0.002;
        double tempsUtilisationCafe = 2;
        Integer[] plageUtilisationCafe = new Integer[] { 9, 18 };

        Consommateur cafe = new MachineCafe(puissanceMaxCafe, frequenceCafe, tempsUtilisationCafe,
                plageUtilisationCafe);
        SimulationScenario.playUnitaireConsommateur(cafe, journee);

        // FOYER//

        // PARTICULIER
        // Vous pouvez changer le nombre de frigos, machines a cafe et radiateurs du
        // particulier

        ArrayList<Consommateur> appareilsFamille = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            appareilsFamille.add(frigo);
        }
        for (int i = 0; i < 2; i++) {
            appareilsFamille.add(cafe);
        }
        for (int i = 0; i < 7; i++) {
            appareilsFamille.add(radiateur);
        }

        Consommateur famille = new Foyer(appareilsFamille, TypeConsommateur.PARTICULIER);
        SimulationScenario.playUnitaireConsommateur(famille, journee);

        // ENTREPRISE
        // Vous pouvez changer le nombre de frigos, machine a cafe et radiateur de
        // l'entreprise
        ArrayList<Consommateur> appareilsEntreprise = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            appareilsEntreprise.add(frigo);
        }
        for (int i = 0; i < 50; i++) {
            appareilsEntreprise.add(cafe);
        }
        for (int i = 0; i < 200; i++) {
            appareilsEntreprise.add(radiateur);
        }
        Consommateur entreprise = new Foyer(appareilsEntreprise, TypeConsommateur.ENTREPRISE);
        SimulationScenario.playUnitaireConsommateur(entreprise, journee);

        // NUCLEAIRE//
        // Vous pouvez changer la valeur de la puissance maximale de la centrale
        // nucleaire (en W)
        double puissanceMaxNucleaire = 1000000000;

        Producteur nucleaire = new Nucleaire(puissanceMaxNucleaire);
        SimulationScenario.playUnitaireProducteur(nucleaire, journee);

        // EOLIEN//
        // Vous pouvez changer la valeur de la puissance maximale du parc eolien (en W)
        // ainsi que les pourcentages de production selon les saisons et les heures de
        // la journée
        double puissanceMaxEolien = 1000000;
        HashMap<String, Double> moisActifEolien = new HashMap<>(Map.ofEntries(Map.entry("winter", 1.),
                Map.entry("spring", 0.75), Map.entry("summer", 0.50), Map.entry("fall", 0.75)));
        Double[][] heureActifEolien = new Double[][] { { 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1. },
                { 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1., 1. } };

        Producteur eolien = new Eolien(puissanceMaxEolien, moisActifEolien, heureActifEolien);
        SimulationScenario.playUnitaireProducteur(eolien, journee);

        // PHOTOVOLTAIQUE//
        // Vous pouvez changer la valeur de la puissance maximale de la ferme
        // photovoltaique (en W)
        // ainsi que les pourcentages de production selon les saisons et les heures de
        // la journée
        double puissanceMaxPV = 1000000;
        HashMap<String, Double> moisActifPV = new HashMap<>(Map.ofEntries(Map.entry("winter", 0.50),
                Map.entry("spring", 0.75), Map.entry("summer", 1.), Map.entry("fall", 0.75)));
        Double[][] heureActifPV = new Double[][] { { 0., 0., 0., 0., 0., 0., 0.25, 0.25, 0.50, 0.75, 1., 1. },
                { 1., 1., 1., 0.75, 0.75, 0.50, 0.50, 0.25, 0., 0., 0., 0. } };

        Producteur pv = new PV(puissanceMaxPV, moisActifPV, heureActifPV);
        SimulationScenario.playUnitaireProducteur(pv, journee);
        
    }
}
