package producteurs;

import java.util.HashMap;

import simulation.Temps;

/**
 * Cette classe permet de constuire un producteur dépendant des conditions
 * extérieures puis de calculer sa production sur chaque minute d'une journée et
 * de chaque jour d'une année. Un producteur dépendant des conditions
 * extérieures est un producteur dont la production dépend des saisons d'une
 * année et/ou de l'heure de la journée.
 * 
 * @author Anna Barraqué
 */
public abstract class ProducteurCondExt implements Producteur {
    /**
     * Cette classe a 3 attributs: la puissance maximale du producteur,une HashMap
     * associant un pourcentage de production d'énergie à chaque saison, un tableau
     * associant un pourcentage de production d'énergie à chaque heure de la journée
     */
    private double puissanceMax;
    private HashMap<String, Double> moisActif;
    private Double[][] heureActif;

    /**
     * Cette méthode est le constructeur de la classe.
     * 
     * @param puissanceMax est la valeur décimale de la puissance maximale du
     *                     producteur.
     * @param moisActif    est une HashMap qui lie pour chaque saison un pourcentage
     *                     de production par rapport à la puissance maximale.
     * @param heureActif   est un tableau de deux lignes et 12 colonnes donnant le
     *                     pourcentage de production de puissance pour chaque heure
     *                     de la journee.
     */
    public ProducteurCondExt(double puissanceMax, HashMap<String, Double> moisActif, Double[][] heureActif) {
        this.puissanceMax = puissanceMax;
        this.moisActif = moisActif;
        this.heureActif = heureActif;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite pour
     * chaque journée de l'année. Cette méthode appelle pour chaque journée la
     * méthode computeMinute qui retourne un tableau de la puissance produite pour
     * chaque minute de la journée. Puis appelle la méthode cumulDay de la classe
     * Temps du Package simulation qui somme les valeurs de chaque minute d'une
     * journée pour obtenir la valeur de la production journalière cumulée.
     * 
     * @return puissanceJour, un tableau de taille 365 (nombre de jours dans
     *         l'année) comprenant la valeur de la puissance consommée pour chaque
     *         journée.
     * 
     * @see simulation.Temps
     */
    public double[] computeDay() {
        double[] puissanceJour = new double[Temps.NBJOURSANNEE];
        for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
            puissanceJour[i] = Temps.cumulDay(computeMinute(i));
        }
        return puissanceJour;
    }

    /**
     * Cette méthode permet d'obtenir un tableau de la production du producteur pour
     * chaque minute de la journée donnée. La production d'énergie dépend de la
     * saison et de l'heure de la journée.
     * 
     * @param journée est le jour pour lequel on veut le rapport énergétique (valeur
     *                entière comprise entre 1 et 365)
     * @return puissanceMinute est un tableau de taille 1440 (nombre de minutes par
     *         jour) donnant la valeur de la puissance consommée par l'appareil pour
     *         chaque minute de la journée.
     */
    public double[] computeMinute(int journee) {
        double[] puissanceMinute = new double[Temps.NBMINUTESJOUR];

        int mois = Temps.dayToMonth(journee);
        String saison = Temps.monthToSeason(mois);

        double puissanceMaxJournee = moisActif.get(saison) * puissanceMax;

        // pour toutes les heures de la journee, la puissance produite par minute est la
        // meme
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < this.heureActif[i].length; j++) {
                for (int m = 0; m < Temps.NBMINUTESHEURE; m++) {
                    puissanceMinute[(i * (this.heureActif[i].length) + j) * Temps.NBMINUTESHEURE + m] = heureActif[i][j]
                            * puissanceMaxJournee;
                }

            }
        }
        return puissanceMinute;
    }

}
