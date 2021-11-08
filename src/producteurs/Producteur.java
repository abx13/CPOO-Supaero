package producteurs;

/**
 * Cette interface définit les méthodes que doit comporter tout producteur.
 * 
 * @author Anna Barraqué
 */
public interface Producteur {

    /**
     * Chaque producteur doit calculer la puissance produite sur l'année.
     */
    public double[] computeDay();

    /**
     * Chaque producteur doit calculer la puissance produite par minute sur une
     * journée donnée.
     */
    public double[] computeMinute(int journee);

}