package producteurs;

import simulation.Temps;

/**
 * Cette classe permet de constuire un producteur dont la production d'énergie
 * est constante puis de calculer sa production sur chaque minute d'une journée
 * et de chaque jour d'une année.
 * 
 * @author Anna Barraqué
 */
public class ProducteurControle implements Producteur {

    private double puissanceMax;

    /**
     * Cette méthode est le constructeur de la classe.
     * 
     * @param puissanceMax est la valeur décimale de la puissance maximale que le
     *                     producteur peut produire.
     */
    public ProducteurControle(double puissanceMax) {
        this.puissanceMax = puissanceMax;
    }

    /**
     * Cette méthode permet d'obtenir un tableau de la production du producteur pour
     * chaque minute de la journée donnée. La production d'énergie est égale à la
     * puissance maximale pour chaque minute de la journée.
     * 
     * @param journée est le jour pour lequel on veut le rapport journalier (valeur
     *                entière comprise entre 1 et 365)
     * @return puissanceMinute est un tableau de taille 1440 (nombre de minutes par
     *         jour) donnant la valeur de la puissance produite par le producteur
     *         pour chaque minute de la journée.
     */
    public double[] computeMinute(int journee) {
        double[] puissanceMinute = new double[Temps.NBMINUTESJOUR];
        for (int i = 0; i < Temps.NBMINUTESJOUR; i++) {
            puissanceMinute[i] = puissanceMax;
        }
        return puissanceMinute;
    }

    /**
     * Cette méthode permet de calculer la valeur de la puissance produite pour
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
            puissanceJour[i] = Temps.NBMINUTESJOUR * puissanceMax;
        }
        return puissanceJour;
    }

}
