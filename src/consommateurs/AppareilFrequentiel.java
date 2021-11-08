package consommateurs;

import simulation.Temps;

/**
 * Cette classe permet de constuire un appareil fréquentiel puis de calculer sa
 * consommation sur chaque minute d'une journée et de chaque jour d'une année.
 * Un appareil fréquentiel est un appareil dont la consommation dépend d'une
 * fréquence d'utilisation, d'un temps d'utilisation et d'une plage horaire
 * d'utilisation.
 * 
 * @author Anna Barraqué
 */
public class AppareilFrequentiel implements Consommateur {
    /**
     * Cette classe a 4 attributs: la puissance maximale de l'appareil la fréquence
     * à laquelle l'appareil est utilisé le temps pendant lequel l'appareil consomme
     * de l'énergie pour chaque utilisation la plage horaire pendant laquelle
     * l'appareil est utilisé.
     */
    private double puissanceMax;
    private double frequence;
    private double tempsUtilisation;
    private Integer[] plageUtilisation;

    /**
     * Cette méthode est le constructeur de la classe.
     * 
     * @param puissanceMax     est la valeur décimale de la puissance maximale de
     *                         l'appareil
     * @param frequence        est la valeur décimale (en minutes^(-1)) de la
     *                         fréquence à laquelle est utilisé l'appareil
     * @param tempsUtilisation est la valeur décimale (en minutes) du temps
     *                         d'utilisation de l'appareil.
     * @param plageUtilsation  est un tableau comprenant l'heure de début et l'heure
     *                         de fin d'utilisation de cet appareil fréquentiel.
     */
    public AppareilFrequentiel(double puissanceMax, double frequence, double tempsUtilisation,
            Integer[] plageUtilisation) {
        this.puissanceMax = puissanceMax;
        this.frequence = frequence;
        this.tempsUtilisation = tempsUtilisation;
        this.plageUtilisation = plageUtilisation;
    }

    /**
     * Cette méthode permet d'obtenir un tableau de la consommation de l'appareil
     * pour chaque minute de la journée donnée. Dans la plage d'utilisation de
     * l'appareil, l'appareil est utilisé à fréquence régulière. A chaque fréquence,
     * et pendant le temps d'utilisation de l'appareil, la puissance est maximale,
     * sinon elle est nulle.
     * 
     * @param journée est le jour pour lequel on veut le rapport énergétique (valeur
     *                entière comprise entre 1 et 365)
     * @return puissanceMinute est un tableau de taille 1440 (nombre de minutes par
     *         jour) donnant la valeur de la puissance consommée par l'appareil pour
     *         chaque minute de la journée.
     */
    public double[] computeMinute(int journee) {
        double[] puissanceMinute = new double[Temps.NBMINUTESJOUR]; // nbminutes dans une journee

        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            puissanceMinute[i] = 0;
        }

        for (int f = plageUtilisation[0] * Temps.NBMINUTESHEURE; f < plageUtilisation[1] * Temps.NBMINUTESHEURE; f += 1
                / frequence) {
            for (int t = 0; t < tempsUtilisation; t++) {
                puissanceMinute[f + t] = puissanceMax;
            }
        }

        return puissanceMinute;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance consommée pour
     * chaque journée de l'année. Cette méthode appelle pour chaque journée la
     * méthode computeMinute qui retourne un tableau de la puissance consommée pour
     * chaque minute de la journée. Puis appelle la méthode cumulDay de la classe
     * Temps du Package simulation qui somme les valeurs de chaque minute d'une
     * journée pour obtenir la valeur de la production journalière cumulée
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

}
