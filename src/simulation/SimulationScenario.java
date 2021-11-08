package simulation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import consommateurs.*;
import producteurs.*;

/**
 * Cette classe permet de lancer les scénarii intégré et unitaires pour les
 * producteurs et les consommateurs de la ville.
 * 
 * @author Anna Barraqué
 */

public class SimulationScenario {

    /**
     * Cette méthode permet de lancer le scénario intégré qui permet à l'utilisateur
     * de saisir dans la console les producteurs et consommateurs de sa ville. Les
     * rapports énergétiques sont affichés dans la console et sauvegardés dans des
     * fichiers CSV.
     * 
     * @see SimulationVille
     */
    public static void play() {

        ArrayList<Producteur> producteurs = new ArrayList<>();
        ArrayList<Consommateur> consommateurs = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String reponse = null;
            System.out.println();
            System.out.println("Choisissez le(s) producteur(s) que vous voulez simuler:\n"
                    + "Tappez 'Eolien' pour un parc éolien + touche Entrée, 'PV' pour une ferme photovoltaïque + touche Entrée ou 'Nucleaire' pour une centrale nucléaire + touche Entrée\n"
                    + "Tappez 'fin' + touche Entrée pour arrêter d'ajouter des producteurs.");

            do {
                reponse = br.readLine();

                switch (reponse) {
                case "Eolien":
                    producteurs.add(new Eolien());
                    break;
                case "PV":
                    producteurs.add(new PV());
                    break;
                case "Nucleaire":
                    producteurs.add(new Nucleaire());
                    break;
                }
            } while (!reponse.equals("fin"));

            System.out.println("Choisissez le(s) consommateurs(s) que vous voulez simuler:\n"
                    + "Tappez 'Frigo' pour frigo, 'Cafe' pour une machine à café, ou 'Radiateur' pour un radiateur + touche Entrée\n"
                    + "Vous pouvez aussi tapper 'Particulier' ou 'Entreprise' pour créer des foyers possedant plusieurs appareils definis par defaut ou 'Unkown' pour un foyer possedant une liste d'appareils aléatoire\n"
                    + "Tappez 'fin' + touche Entrée pour arrêter d'ajouter des producteurs.");

            do {
                reponse = br.readLine();
                switch (reponse) {
                case "Frigo":
                    consommateurs.add(new Frigo());
                    break;
                case "Cafe":
                    consommateurs.add(new MachineCafe());
                    break;
                case "Radiateur":
                    consommateurs.add(new Radiateur());
                    break;
                case "Particulier":
                    consommateurs.add(new Foyer(TypeConsommateur.PARTICULIER));
                    break;
                case "Entreprise":
                    consommateurs.add(new Foyer(TypeConsommateur.ENTREPRISE));
                    break;
                case "Unknown":
                    consommateurs.add(new Foyer(TypeConsommateur.UNKNOWN));
                    break;
                }
            } while (!reponse.equals("fin"));

            Ville ville = new Ville(producteurs, consommateurs);

            System.out.println("Voulez-vous le rapport annuel? (Ecrivez 'oui' ou 'non' + touche Entrée)");
            String annuel = br.readLine();
            if (annuel.equals("oui")) {
                double[][] puissVilleDay = ville.computeDay();
                SimulationVille.displayVille(puissVilleDay);
                SimulationVille.writeToCsvVille("rapportYear.csv", puissVilleDay);
            }

            System.out.println("Voulez-vous le rapport journalier? (Ecrivez 'oui' ou 'non' + touche Entrée)");
            String journalier = br.readLine();
            if (journalier.equals("oui")) {
                int jour = -1;
                do {
                    System.out.println(
                            "De quel jour? (Ecrivez le numero de jour (compris entre 1 et 365) + Appuyez sur Entrée)");
                    jour = Integer.parseInt(br.readLine());
                } while (jour < 0 || jour > 365);
                double[][] puissVilleMin = ville.computeMinute(jour);
                SimulationVille.displayVille(puissVilleMin);
                SimulationVille.writeToCsvVille("rapportDay" + jour + ".csv", puissVilleMin);
            }

        } catch (IOException e) {
            System.out.println(e.toString());
        }

    }

    /**
     * Cette méthode permet de lancer le scénario unitaire pour un consommateur. Les
     * rapports énergétiques journalier et annuel sont affichés dans la console et
     * sauvergardés dans des fichiers CSV.
     * 
     * @param c       est le consommateur pour lequel on lance le scénario unitaire
     * @param journee est la journée pour laquelle on fait le rapport journalier.
     * @see SimulationVille
     */
    public static void playUnitaireConsommateur(Consommateur c, int journee) {
        ArrayList<Consommateur> consommateurs = new ArrayList<>();
        ArrayList<Producteur> producteurs = new ArrayList<>();

        consommateurs.add(c);

        Ville ville = new Ville(producteurs, consommateurs);
        double[][] dataMinute = ville.computeMinute(journee);
        double[][] dataYear = ville.computeDay();
        String filename = SimulationVille.filenameConsommateur(c);

        SimulationVille.displayVille(dataMinute);
        SimulationVille.writeToCsvVille(filename + ".Day" + journee + ".csv", dataMinute);
        SimulationVille.displayVille(dataYear);
        SimulationVille.writeToCsvVille(filename + ".Year.csv", dataYear);
    }

    /**
     * Cette classe permet de lancer le scénario unitaire pour un producteur. Les
     * rapports énergétiques journalier et annuel sont affichés dans la console et
     * sauvergardés dans des fichiers CSV.
     * 
     * @param c       est le consommateur pour lequel on lance le scénario unitaire
     * @param journee est la journée pour laquelle on fait le rapport journalier.
     * @see SimulationVille
     */
    public static void playUnitaireProducteur(Producteur p, int journee) {
        ArrayList<Consommateur> consommateurs = new ArrayList<>();
        ArrayList<Producteur> producteurs = new ArrayList<>();

        producteurs.add(p);

        Ville ville = new Ville(producteurs, consommateurs);
        double[][] dataMinute = ville.computeMinute(journee);
        double[][] dataYear = ville.computeDay();
        String filename = SimulationVille.filenameProducteur(p);

        SimulationVille.displayVille(dataMinute);
        SimulationVille.writeToCsvVille(filename + ".Day" + journee + ".csv", dataMinute);
        SimulationVille.displayVille(dataYear);
        SimulationVille.writeToCsvVille(filename + ".Year.csv", dataYear);
    }

}
