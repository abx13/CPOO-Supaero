package consommateurs;

/**
 * Cette interface définit les méthodes que doit comporter tout consommateur.
 * 
 * @author Anna Barraqué
 */
public interface Consommateur {

    /**
     * Chaque consommateur doit calculer sa puissance consommée sur l'année.
     */
    public double[] computeDay();

    /**
     * Chaque consommateur doit calculer sa puissance consommée par minute sur une
     * journée donnée.
     */
    public double[] computeMinute(int journee);

}