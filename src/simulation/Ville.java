package simulation;

import java.util.ArrayList;

import consommateurs.Consommateur;
import producteurs.Producteur;

/**
 * Cette classe permet de simuler une ville ayant des producteurs et des
 * consommateurs d'énergie. Elle calcule la production et consommation d'énergie
 * pour chaque minute de la journée donnée ainsi que pour chaque jour de
 * l'année.
 * 
 * @author Anna Barraqué
 */
public class Ville {
    /**
     * La ville est composée d'une liste de producteurs et d'une liste de
     * consommateurs.
     */
    private ArrayList<Producteur> producteurs;
    private ArrayList<Consommateur> consommateurs;

    /**
     * Cette méthode est le constructeur pour instancier la ville avec deux listes
     * de consommateurs et producteurs données.
     * 
     * @param producteurs   est une liste de producteurs d'énergie de la ville
     * @param consommateurs est une liste de consommateurs d'énergie de la ville
     */
     public Ville(ArrayList<Producteur> producteurs, ArrayList<Consommateur> consommateurs) {
        this.producteurs = producteurs;
        this.consommateurs = consommateurs;
    }

    /**
     * Cette méthode est le constructeur pour instancier les listes de producteurs et consommateurs
     */
    public Ville() {
        this.producteurs = new ArrayList<>();
        this.consommateurs = new ArrayList<>();
    }

    /**
     * Cette méthode est un setteur pour l'attribut producteurs 
     */
    public void addProducteurs(ArrayList<Producteur> producteursAdd){
        ArrayList<Producteur> prod = new ArrayList<>();
        prod.addAll(this.producteurs);
        prod.addAll(producteursAdd);
        this.producteurs = prod;
    }

    /**
     * Cette méthode est un setteur pour l'attribut consommateurs 
     */
    public void addConsommateurs(ArrayList<Consommateur> consommateursAdd){
        ArrayList<Consommateur> conso = new ArrayList<>();
        conso.addAll(this.consommateurs);
        conso.addAll(consommateursAdd);
        this.consommateurs = conso;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite et
     * consommée dans la ville pour chaque minute de la journée donnée. Cette
     * méthode appelle pour des méthodes permettant de calculer les puissances
     * totales consommées et produites.
     * 
     * @return puissanceJour, un tableau de deux dimensions, 1440 lignes (nombre de
     *         minutes par jour) et - 5 colonnes pour le scenario integre - 3
     *         colonnes pour le scenario unitaire comprenant les valeurs de la
     *         puissance consommée et/ou produite pour chaque journée ainsi que les
     *         valeurs cumulées sur la journée.
     * 
     * @see producteurs
     * @see consommateurs
     * @see simulation.Temps
     */
    public double[][] computeMinute(int journee) {
        // on instancie ces tableaux plus tard pour qu'ils correspondent au format
        // demandé
        double[] puissConsoTot = null;
        double[] puissProdTot = null;
        double[][] puissanceMinute = null;
        // on instancie a zero pour pouvoir faire des incrementations plus tard
        double cumulConso = 0;
        double cumulProd = 0;

        // on instancie le tableau au format correspondant selon le nombre de
        // consommateurs et producteurs

        // format scenario unitaire: num min, puissconso min || puissprod min,
        // puissconso cumul || puissprod cumul
        if (producteurs.size() == 0 || consommateurs.size() == 0) {
            puissanceMinute = new double[Temps.NBMINUTESJOUR][5];
            // format unitaire pour les producteurs
            if (consommateurs.size() == 0) {
                puissProdTot = this.computeMinuteProducteur(journee);
                for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
                    puissanceMinute[i][0] = i;
                    puissanceMinute[i][1] = 0.;
                    puissanceMinute[i][2] = puissProdTot[i];
                    puissanceMinute[i][3] = 0.;
                    puissanceMinute[i][4] = cumulProd + (puissProdTot[i] / Temps.NBMINUTESHEURE);
                    cumulProd = puissanceMinute[i][4];
                }
            // format unitaire pour les consommateurs
            } else {
                puissConsoTot = this.computeMinuteConsommateur(journee);
                for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
                    puissanceMinute[i][0] = i;
                    puissanceMinute[i][1] = puissConsoTot[i];
                    puissanceMinute[i][2] = 0.;
                    puissanceMinute[i][3] = cumulConso + (puissConsoTot[i] / Temps.NBMINUTESHEURE);
                    puissanceMinute[i][4] = 0.;
                    cumulConso = puissanceMinute[i][3];
                }
            }
        // format scenario integre : num min, puissconso min, puissprod min, puissconso
        // cumul, puissprod cumul
        } else {
            puissConsoTot = this.computeMinuteConsommateur(journee);
            puissProdTot = this.computeMinuteProducteur(journee);

            puissanceMinute = new double[Temps.NBMINUTESJOUR][5];
            for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
                puissanceMinute[i][0] = i;
                puissanceMinute[i][1] = puissConsoTot[i];
                puissanceMinute[i][2] = puissProdTot[i];
                puissanceMinute[i][3] = cumulConso + (puissConsoTot[i] / Temps.NBMINUTESHEURE);
                puissanceMinute[i][4] = cumulProd + (puissProdTot[i] / Temps.NBMINUTESHEURE);
                cumulConso = puissanceMinute[i][3];
                cumulProd = puissanceMinute[i][4];
            }
       }

        return puissanceMinute;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance consommée dans la
     * ville pour chaque minute de la journée donnée. Cette méthode somme les
     * puissances consommées par l'ensemble des consommateurs sur une minute.
     * 
     * @param journee, journée pour laquelle on génère le rapport journalier.
     * @return puissanceConsoTot, un tableau à une dimension comprenant la somme des
     *         puissances consommées par tous les consommateurs pour chacune des
     *         1440 minutes de la journée.
     */
    public double[] computeMinuteConsommateur(int journee) {
        // on get les puissances de tous les consommateurs
        double[][] puissConso = new double[consommateurs.size()][Temps.NBMINUTESJOUR];
        for (int j = 0; j < consommateurs.size(); j++) {
            puissConso[j] = consommateurs.get(j).computeMinute(journee);
        }

        // on calcule la somme de la consommation sur chaque minute
        double[] puissConsoTot = new double[Temps.NBMINUTESJOUR];
        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            puissConsoTot[i] = puissConso[0][i];
            for (int j = 1; j < consommateurs.size(); j++) {
                puissConsoTot[i] += puissConso[j][i];
            }
        }
        return puissConsoTot;

    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite dans la
     * ville pour chaque minute de la journée donnée. Cette méthode somme les
     * puissances produite par l'ensemble des producteur sur une minute.
     * 
     * @param journee, journée pour laquelle on génère le rapport journalier.
     * @return puissanceProdTot, un tableau à une dimension comprenant la somme des
     *         puissances produites par tous les producteurs pour chacune des 1440
     *         minutes de la journée.
     */
    public double[] computeMinuteProducteur(int journee) {

        // on get les puissances de tous les prod
        double[][] puissProd = new double[producteurs.size()][Temps.NBMINUTESJOUR];
        for (int j = 0; j < producteurs.size(); j++) {
            puissProd[j] = producteurs.get(j).computeMinute(journee);
        }

        // on calcule la somme de la production sur chaque minute
        double[] puissProdTot = new double[Temps.NBMINUTESJOUR];
        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            puissProdTot[i] = puissProd[0][i];
            for (int j = 1; j < producteurs.size(); j++) {
                puissProdTot[i] += puissProd[j][i];
            }
        }
        return puissProdTot;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite et
     * consommée dans la ville pour chaque journée de l'année. Cette méthode appelle
     * pour chaque journée la méthode computeMinute qui retourne un tableau de la
     * puissance produite et consommée pour chaque minute de la journée pour obtenir
     * les valeurs energetiques cumulees sur la journee.
     * 
     * @return puissanceJour, un tableau de deux dimensions, a 365 lignes (nombre de
     *         jours dans l'année) et - 5 colonnes (champs demandés dans le format
     *         csv) pour le scenario integre - 3 colonnes pour le scenario unitaire
     *         comprenant la valeur de la puissance consommée et>/ou produite pour
     *         chaque journée ainsi que sa valeur cumulée sur l'annee.
     * 
     * @see producteurs
     * @see consommateurs
     * @see simulation.Temps
     */
    public double[][] computeDay() {
        // on instancie ces tableaux plus tard pour qu'ils correspondent au format
        // demandé
        double[][] puissanceDay = null;
        double[][] valeursJournalieres = null;

        // on instancie a zero pour pouvoir faire des incrementations plus tard
        double cumulConso = 0;
        double cumulProd = 0;
        double cumul = 0;

        // on instancie le tableau au format correspondant selon le nombre de
        // consommateurs et producteurs

        // scenario unitaire
       /* if (producteurs.size() == 0 || consommateurs.size() == 0) {

            // format unitaire: num jour, puissconso jour || puissprod jour, puissconso
            // cumul || puissprod cumul
            puissanceDay = new double[Temps.NBJOURSANNEE][3];

            // on get les valeurs journalieres : on prend la derniere ligne du tableau
            // minute pour chaque jour de l'annee
            valeursJournalieres = new double[Temps.NBJOURSANNEE][3];
            for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
                valeursJournalieres[i] = this.computeMinute(i)[Temps.NBMINUTESJOUR - 1];
            }

            for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
                puissanceDay[i][0] = i;
                puissanceDay[i][1] = valeursJournalieres[i][2] * Temps.NBHEURESJOUR;
                puissanceDay[i][2] = cumul + (puissanceDay[i][1] / Temps.NBHEURESJOUR);
                cumul = puissanceDay[i][2];
            }

            // scenario integre
        } else {*/
            puissanceDay = new double[Temps.NBJOURSANNEE][5];

            // on get les valeurs journalieres : on prend la derniere ligne du tableau
            // minute pour chaque jour de l'annee
            valeursJournalieres = new double[Temps.NBJOURSANNEE][5];
            for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
                valeursJournalieres[i] = this.computeMinute(i)[Temps.NBMINUTESJOUR - 1];
            }

            // on remplit le tableau puissanceDay (format demande pour le fichier CSV)
            for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
                puissanceDay[i][0] = i;
                puissanceDay[i][1] = valeursJournalieres[i][3] * Temps.NBHEURESJOUR;
                puissanceDay[i][2] = valeursJournalieres[i][4] * Temps.NBHEURESJOUR;
                puissanceDay[i][3] = cumulConso + (puissanceDay[i][1] / Temps.NBHEURESJOUR);
                puissanceDay[i][4] = cumulProd + (puissanceDay[i][2] / Temps.NBHEURESJOUR);
                cumulConso = puissanceDay[i][3];
                cumulProd = puissanceDay[i][4];
            }
        //}

        return puissanceDay;
    }

    /**
     * Cette méthode est le getter de la liste des producteurs de la ville
     * 
     * @return producteurs, la liste des producteurs de la ville
     */
    public ArrayList<Producteur> getProducteurs() {
        return this.producteurs;
    }

    /**
     * Cette méthode est le getter de la liste des consommateurs de la ville
     * 
     * @return consommateurs, la liste des consommateurs de la ville
     */
    public ArrayList<Consommateur> getConsommateurs() {
        return this.consommateurs;
    }

}
