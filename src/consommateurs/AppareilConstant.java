package consommateurs;

import simulation.Temps;

/**
 * Cette classe permet de constuire un appareil constant puis de calculer sa
 * consommation sur chaque minute d'une journée et de chaque jour d'une année.
 * Un appareil constant est un appareil dont la consommation est constante pour
 * tout jour de l'année et toute minute de la journée.
 * 
 * @author Anna Barraqué
 */

public class AppareilConstant implements Consommateur {
    /**
     * Cette classe a un attribut qui est sa puissance maximale (en W).
     */
    private double puissanceMax;

    /**
     * Cette méthode est le constructeur de la classe.
     * 
     * @param puissanceMax est la valeur décimale de la puissance maximale de
     *                     l'appareil
     */
    public AppareilConstant(double puissanceMax) {
        this.puissanceMax = puissanceMax;
    }

    /**
     * Cette méthode permet d'obtenir un tableau de la consommation de l'appareil
     * pour chaque minute de la journée donnée. La consommation d'énergie est égale
     * à la puissance maximale pour chaque minute de la journée.
     * 
     * @param journée est le jour pour lequel on veut avoir le rapport énergétique
     *                (valeur entière comprise entre 1 et 365)
     * @return puissanceMinute est un tableau de taille 1440 (nombre de minutes par
     *         jour) donnant la valeur de la puissance consommée par l'appareil pour
     *         chaque minute de la journée.
     */
    public double[] computeMinute(int journee) {
        double[] puissanceMinute = new double[Temps.NBMINUTESJOUR];

        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            puissanceMinute[i] = this.puissanceMax;
        }

        return puissanceMinute;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance consommée pour
     * chaque journée de l'année. Elle multiplie la valeur de la puissance maximale
     * par le nombre de minutes par jour.
     * 
     * @return puissanceJour, un tableau de taille 365 (nombre de jours dans
     *         l'année) comprenant la valeur de la puissance consommée pour chaque
     *         journée.
     * 
     */
    public double[] computeDay() {
        double[] puissanceJour = new double[Temps.NBJOURSANNEE];
        for (int i = 0; i < Temps.NBJOURSANNEE; i++) {
            puissanceJour[i] = Temps.NBMINUTESJOUR * this.puissanceMax;
        }
        return puissanceJour;
    }
}
