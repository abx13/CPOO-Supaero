package producteurs;

/**
 * Cette classe modélise une centrale nucléaire, producteur constant dont on
 * peut contrôler la puissance produite.
 * 
 * @author Anna Barraqué
 */
public class Nucleaire extends ProducteurControle {

    /**
     * Cette méthode est le constructeur initialisant la centrale nucléaire avec une
     * valeur par défaut de sa puissance maximale.
     */
    public Nucleaire() {
        super(1000000000);
    }

    /**
     * Cette méthode est le constructeur permettant de choisir la valeur de la
     * puissance maximale de la centrale nucléaire.
     * 
     * @param puissanceMax est la valeur de la puissance maximale du frigo.
     */
    public Nucleaire(double puissanceMax) {
        super(puissanceMax);
    }
}
